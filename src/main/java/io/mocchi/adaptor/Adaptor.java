package io.mocchi.adaptor;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public abstract class Adaptor {
	protected static String[] EXTS = new String[0];
	/** adaptors list */
	final private static ArrayList<Class> adaptors = new ArrayList<>();
	/** file */
	private String path;

	private static String getExt(String path) {
		if (path == null)
			return null;
		int point = path.lastIndexOf(".");
		if (point != -1) {
			return path.substring(point + 1);
		}
		return null;
	}

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
		String fileExt = Adaptor.getExt(path);
		// search adaptor
		for (Class clazz : Adaptor.adaptors) {
			java.lang.reflect.Field field = clazz.getDeclaredField("EXTS");
			String[] exts = (String[]) field.get(null);
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
