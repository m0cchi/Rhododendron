package io.mocchi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

public class Bootstrap {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		if (args.length == 0) {
			URL url = Init.class.getResource("Init.class");
			final String path = URLDecoder.decode(url.getFile(), "utf-8");

			ArrayList<String> target = new ArrayList<String>() {
				private static final long serialVersionUID = 1L;
				{
					add("java");
					add("-Xmx1024m");
					if (path.contains("!")) {
						add("-jar");
						add(path.substring(5, path.indexOf('!')));
					} else {
						add(path);
					}
					add("--run");
				}
			};
			ProcessBuilder builder = new ProcessBuilder(target);
			Process process = builder.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			int status = process.waitFor();
			System.out.println("status: " + status);
		} else {
			Init.main(args);
		}
	}

}
