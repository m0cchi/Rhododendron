package io.mocchi.comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 3543262776343265082L;
	private BufferedImage img;

	public void setImage(String path) throws IOException {
		setImage(new FileInputStream(new File(path)));
	}

	public void setImage(InputStream is) throws IOException {
		setImage(ImageIO.read(is));
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public void repaint() {
		super.repaint();
		revalidate();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(getX(), getY(), getWidth(), getHeight());
		if (img == null) {
			return;
		}
		int w, h, x, y, ww, wh;
		double fraito = 1.0, sraito = 1.0;
		ww = getWidth();
		wh = getHeight();
		w = img.getWidth();
		h = img.getHeight();
		// opt
		fraito = (double) ww / w;
		if (h * fraito > wh) {
			sraito = (double) wh / (h * fraito);
		}

		w = (int) (w * fraito * sraito);
		h = (int) (h * fraito * sraito);
		x = (ww - w) / 2;
		y = (wh - h) / 2;
		g2.drawImage(img, x, y, w, h, this);
	}
}
