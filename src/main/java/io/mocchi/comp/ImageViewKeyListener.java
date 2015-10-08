package io.mocchi.comp;

import io.mocchi.adaptor.Operation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ImageViewKeyListener implements KeyListener {
	private Operation operation;
	public ImageViewKeyListener(Operation operation) {
		this.operation = operation;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			operation.next();
			break;
		case KeyEvent.VK_RIGHT:
			operation.prev();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
