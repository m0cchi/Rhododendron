package io.mocchi.adaptor;

import io.mocchi.PathSorter;
import io.mocchi.RhoUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

public class ZipAdaptor extends Adaptor {
	@SuppressWarnings("serial")
	final protected static ArrayList<String> EXTS = new ArrayList<String>() {
		{
			add("zip");
		}
	};
	private int size;
	static {
		Adaptor.register(ZipAdaptor.class);
	}

	@Override
	public OptimizedImage page(int pageNumber) {
		return images[pageNumber];
	}

	@Override
	public void openBook() {
		File file = new File(this.path);
		try {
			ZipFile zipFile = new ZipFile(file);
			ArrayList<OptimizedImage> images = new ArrayList<>();
			ArrayList<Thread> threads = new ArrayList<>();
			size = 0;
			for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e
					.hasMoreElements();) {
				ZipEntry entry = e.nextElement();
				if (entry.isDirectory())
					continue;
				if (RhoUtil.isSupportedFormats(entry.getName())) {
					size++;
					Thread thread = new Thread() {
						@Override
						public void run() {
							InputStream is = null;
							try {
								is = zipFile.getInputStream(entry);
								OptimizedImage image = new OptimizedImage(
										ImageIO.read(is), entry.getName());
								images.add(image);
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					};
					thread.start();
					threads.add(thread);
				}
			}
			for (Thread thread : threads) {
				thread.join();
			}
			zipFile.close();
			images.sort(new PathSorter());
			this.images = images.toArray(new OptimizedImage[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean ready() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxPage() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public void closeBook() {
		// TODO Auto-generated method stub

	}

}
