package io.mocchi.frame;

import io.mocchi.comp.ImagePanel;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 7584062209990894301L;

	public MainFrame() {
		super("Rhododendron");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// full screen with frame
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle desktopBounds = graphicsEnvironment.getMaximumWindowBounds();
		setBounds(desktopBounds);
		ImagePanel panel = new ImagePanel();
		setContentPane(panel);
		try {
			panel.setImage("/Users/mocchi/Desktop/img.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
