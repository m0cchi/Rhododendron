package io.mocchi.adaptor;

import io.mocchi.PathSorter;
import io.mocchi.RhoUtil;
import io.mocchi.Settings;
import io.mocchi.sort.ZipEntrySorter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private ZipEntry[] entries;
	private ZipFile archive;

	static {
		Adaptor.register(ZipAdaptor.class);
	}

	protected OptimizedImage resad(int index) {
		if (images[index] == null) {

		}

		return images[index];
	}

	private void removeCache(int index) {
		if (index > 0 && index < this.images.length) {
			images[index] = null;
		}
	}

	@Override
	protected OptimizedImage read(int index) {
		removeCache(index - Settings.cacheSize);
		removeCache(index + Settings.cacheSize);
		if (images[index] == null) {
			InputStream is;
			try {
				is = archive.getInputStream(this.entries[index]);
				BufferedInputStream bis = new BufferedInputStream(is);
				images[index] = new OptimizedImage(ImageIO.read(bis),
						this.entries[index].getName());
				if (this.optimizer != null) {
					this.optimizer.optimize(images[index]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images[index];
	}

	@Override
	public OptimizedImage page(int pageNumber) {
		return read(pageNumber);
	}

	public void openBook(int count) {
		File file = new File(this.path);
		try {
			archive = new ZipFile(file);
			ArrayList<ZipEntry> entries = new ArrayList<>();
			size = 0;
			for (Enumeration<? extends ZipEntry> e = archive.entries(); e
					.hasMoreElements();) {
				ZipEntry entry = e.nextElement();
				if (entry.isDirectory())
					continue;
				if (RhoUtil.isSupportedFormats(entry.getName())
						&& !RhoUtil.isHiddenPath(entry.getName())) {
					size++;
					entries.add(entry);
				}
			}
			Collections.sort(entries, new ZipEntrySorter());
			this.entries = entries.toArray(new ZipEntry[0]);
			this.images = new OptimizedImage[this.entries.length];
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void openBook() {
		openBook(0);
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

	@Override
	public void finalize() {
		try {
			if (archive != null) {
				archive.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
