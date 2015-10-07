package io.mocchi;

public class RhoUtil {
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
