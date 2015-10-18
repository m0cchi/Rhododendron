package io.mocchi.sort;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

public class ZipEntrySorter implements Comparator<ZipEntry> {

	public int compare(int n1, int n2) {
		return n1 == n2 ? 0 : n1 > n2 ? 1 : -1;
	}

	public int compare(String o1, String o2) {
		Pattern number = Pattern.compile("[0-9]+");
		String n1 = null;
		String n2 = null;
		int t1 = -1;
		int t2 = -1;
		Matcher matcher = number.matcher(o1);
		while (matcher.find()) {
			n1 = matcher.group();
		}
		matcher = number.matcher(o2);
		while (matcher.find()) {
			n2 = matcher.group();
		}

		try {
			t1 = Integer.parseInt(n1);
		} catch (Throwable e) {
		}
		try {
			t2 = Integer.parseInt(n2);
		} catch (Throwable e) {
		}

		return compare(t1, t2);
	}

	@Override
	public int compare(ZipEntry o1, ZipEntry o2) {
		return o1 == null ? -1 : o2 == null ? 1 : compare(o1.getName(),
				o2.getName());
	}
}
