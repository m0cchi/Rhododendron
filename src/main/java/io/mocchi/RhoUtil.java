package io.mocchi;

import java.util.Arrays;

public class RhoUtil {

	public static boolean isSupportedFormats(String path){
		return Arrays.asList(Settings.FORMATS).contains(RhoUtil.getExt(path.toLowerCase()));
	}
	
	public static String getExt(String path) {
		if (path == null)
			return null;
		int point = path.lastIndexOf(".");
		if (point != -1) {
			return path.substring(point + 1);
		}
		return null;
	}
}
