package io.mocchi.frame;

import io.mocchi.Settings;
import io.mocchi.adaptor.Adaptor;
import io.mocchi.adaptor.Operation;
import io.mocchi.adaptor.OptimizedImage;
import io.mocchi.adaptor.Optimizer;
import io.mocchi.comp.ImagePanel;
import io.mocchi.comp.ImageViewKeyListener;
import io.mocchi.comp.UtilPanel;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 7584062209990894301L;
	private Container defaultContent;
	@SuppressWarnings("serial")
	public MainFrame() {
		super(Settings.APP_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.defaultContent = getContentPane();
		// full screen with frame
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle desktopBounds = graphicsEnvironment.getMaximumWindowBounds();
		setBounds(desktopBounds);
		this.defaultContent.add(new UtilPanel() {
			@Override
			public void postDo() {
				this.getFileOpenButton().addActionListener(
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								JFileChooser filechooser = new JFileChooser();

								int selected = filechooser
										.showOpenDialog(MainFrame.this);
								if (selected == JFileChooser.APPROVE_OPTION) {
									String path = filechooser.getSelectedFile()
											.getAbsolutePath();
									if (path != null && !path.isEmpty()) {
										System.out.println("path:" + path);
										initContext();
										MainFrame.this.openBook(path);
										MainFrame.this.revalidate();
									}else{
										System.out.println("faild path");
									}
								}else{
									System.out.println("faild select");
								}

							}
						});
			}
		});
	}

	public void initContext() {
		this.defaultContent.removeAll();
		KeyListener[] listeners = getKeyListeners();
		if (listeners != null) {
			for (KeyListener listener : listeners) {
				removeKeyListener(listener);
			}
		}
		setTitle(Settings.APP_NAME);
		revalidate();
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
			Optimizer optimizer = new Optimizer(getWidth(), getHeight());
			adaptor.setOptimizer(optimizer);
			Operation operation = new Operation(adaptor, action);
			ImageViewKeyListener listener = new ImageViewKeyListener(operation);
			addKeyListener(listener);
			this.defaultContent.add(panel);

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
