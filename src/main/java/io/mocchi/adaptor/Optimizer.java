package io.mocchi.adaptor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Optimizer extends Thread {
	private static final List<OptimizedImage> IMAGES = Collections
			.synchronizedList(new ArrayList<>());
	/** window */
	private int width, height;

	public Optimizer(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public static void add(OptimizedImage image) {
		IMAGES.add(image);
	}

	public void optimize(OptimizedImage image) {
		BufferedImage origin = image.getImage();
		double fraito = 1.0, sraito = 1.0;
		int w, h;
		w = origin.getWidth();
		h = origin.getHeight();
		// opt
		fraito = (double) width / w;
		if (h * fraito > height) {
			sraito = (double) height / (h * fraito);
		}
		w = (int) (w * fraito * sraito);
		h = (int) (h * fraito * sraito);

		BufferedImage newImage = new BufferedImage(w, h, image.getImage()
				.getType());
		newImage.getGraphics().drawImage(
				origin.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, w, h,
				null);
		origin = null;
		image.setImage(newImage);
	}

	public void optimize() {
		for (OptimizedImage optimizedImage : IMAGES) {
			optimize(optimizedImage);
		}
		IMAGES.clear();
	}

	@Override
	public void run() {
		optimize();
	}
}
