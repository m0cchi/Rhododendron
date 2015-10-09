package io.mocchi.adaptor;

import io.mocchi.RhoUtil;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public abstract class Adaptor {
	final protected static ArrayList<String> EXTS = new ArrayList<>();
	/** adaptors list */
	final private static ArrayList<Class> adaptors = new ArrayList<>();
	/** file */
	protected String path;
	protected OptimizedImage[] images;
	protected Optimizer optimizer;

	/**
	 * 
	 * @param clazz
	 */
	protected static void register(Class<?> clazz) {
		if (Adaptor.class.isAssignableFrom(clazz)
				&& !Adaptor.adaptors.contains(clazz)) {
			Adaptor.adaptors.add(clazz);
		} else {
			throw new RuntimeException("register error clazz=>" + clazz);
		}
	}

	/**
	 * 
	 * @param path
	 *            book archive
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	public static Adaptor createAdaptor(String path)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		Class<Adaptor> adaptorClass = null;
		Adaptor adaptor = null;
		String fileExt = RhoUtil.getExt(path);
		// search adaptor
		for (Class clazz : Adaptor.adaptors) {
			java.lang.reflect.Field field = clazz.getDeclaredField("EXTS");
			ArrayList<String> exts = (ArrayList<String>) field.get(null);
			for (String ext : exts) {
				if (ext.equalsIgnoreCase(fileExt)) {
					adaptorClass = clazz;
					break;
				}
			}
		}
		// create adaptor
		if (adaptorClass != null) {
			adaptor = adaptorClass.newInstance();
			adaptor.setPath(path);
		}
		return adaptor;
	}

	public abstract OptimizedImage page(int pageNumber);

	public abstract void openBook();

	public abstract void closeBook();

	public abstract boolean ready();

	public abstract boolean canRead();

	public abstract int getMaxPage();

	public void init() {
		openBook();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public OptimizedImage[] getImages() {
		return images;
	}

	public Optimizer getOptimizer() {
		return optimizer;
	}

	public void setOptimizer(Optimizer optimizer) {
		this.optimizer = optimizer;
	}
	
	
	
}
