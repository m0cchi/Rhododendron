package io.mocchi.adaptor;

import java.awt.image.BufferedImage;

public class OptimizedImage {
	private BufferedImage image;
	private String path;
	public OptimizedImage(BufferedImage image, String path) {
		this.image = image;
		this.path = path;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

}
