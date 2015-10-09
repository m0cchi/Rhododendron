package io.mocchi.comp;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public abstract class UtilPanel extends JPanel {
	private JButton fileOpenButton;

	public static void setUIFont(FontUIResource fontUIResource) {
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				FontUIResource resource = (FontUIResource) value;
				Font font = new Font(fontUIResource.getFontName(),
						resource.getStyle(), fontUIResource.getSize());
				UIManager.put(key, new FontUIResource(font));
			}
		}
	}

	public UtilPanel() {
		super();
		fileOpenButton = new JButton("FileOpen");
		setLayout(new GridLayout(1, 1));
		setUIFont(new FontUIResource(new Font("Arial", 0, 30)));
		setBorder(BorderFactory.createTitledBorder("Menu"));
		add(fileOpenButton);
		postDo();
	}

	public abstract void postDo();

	public JButton getFileOpenButton() {
		return fileOpenButton;
	}
}
