package io.mocchi.frame;

import io.mocchi.Settings;
import io.mocchi.adaptor.Adaptor;
import io.mocchi.adaptor.Operation;
import io.mocchi.adaptor.OptimizedImage;
import io.mocchi.adaptor.Optimizer;
import io.mocchi.comp.ImagePanel;
import io.mocchi.comp.ImageViewKeyListener;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 7584062209990894301L;

	public MainFrame() {
		super(Settings.APP_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// full screen with frame
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle desktopBounds = graphicsEnvironment.getMaximumWindowBounds();
		setBounds(desktopBounds);
	}

	public void initContext() {
		removeAll();
		KeyListener[] listeners = getKeyListeners();
		if (listeners != null) {
			for (KeyListener listener : listeners) {
				removeKeyListener(listener);
			}
		}
		setTitle(Settings.APP_NAME);
	}

	public void openBook(String path) {
		ImagePanel panel = new ImagePanel();
		Adaptor adaptor;
		Operation.Action action = new Operation.Action() {
			@Override
			protected void setImage(OptimizedImage image) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						panel.setImage(image.getImage());
						panel.repaint();
						setTitle(Settings.APP_NAME + " - " + image.getPath());
					}
				});
			}

			@Override
			protected void select(OptimizedImage image) {
				setImage(image);
			}

			@Override
			protected void ready() {
				// TODO Auto-generated method stub
			}

			@Override
			protected void prev(OptimizedImage image) {
				setImage(image);
			}

			@Override
			protected void next(OptimizedImage image) {
				setImage(image);
			}

			@Override
			protected void message(String message) {
				// TODO Auto-generated method stub

			}
		};
		try {
			adaptor = Adaptor.createAdaptor(path);
			Operation operation = new Operation(adaptor, action);
			ImageViewKeyListener listener = new ImageViewKeyListener(operation);
			addKeyListener(listener);
			setContentPane(panel);
			Optimizer optimizer = new Optimizer(getWidth(), getHeight());
			optimizer.add(adaptor.getImages());
			optimizer.start();
			// first page

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
