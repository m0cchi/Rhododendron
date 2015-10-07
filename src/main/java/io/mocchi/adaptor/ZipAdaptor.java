package io.mocchi.adaptor;

public class ZipAdaptor extends Adaptor {
	protected static String[] EXTS = new String[] { "zip" };
	static {
		Adaptor.register(ZipAdaptor.class);
	}

}
