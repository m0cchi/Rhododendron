package io.mocchi;

import io.mocchi.adaptor.OptimizedImage;

import java.util.Comparator;

public class PathSorter implements Comparator<OptimizedImage> {

	public int compare(String o1, String o2) {
		char[] s1 = o1.toCharArray();
		char[] s2 = o2.toCharArray();
		for (int i = 0; i < s1.length && i < s2.length; i++) {
			if (s1[i] == s2[i])
				continue;
			return s1[i] > s2[i] ? 1 : -1;
		}
		return s1.length == s2.length ? 0 : s1.length > s2.length ? 1 : -1;
	}

	@Override
	public int compare(OptimizedImage o1, OptimizedImage o2) {
		return o1 == null ? -1 : o2 == null ? 1 : compare(o1.getPath(),
				o2.getPath());
	}
}
