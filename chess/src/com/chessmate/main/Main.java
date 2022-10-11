package com.chessmate.main;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import com.chessmate.display.Display;

public class Main {

	public static void main(String[] args) {
		Display.create(1280, 720, "Chessmate", 0xff000000, 3);
		
		Timer t = new Timer(1000 / 60, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Display.clear();
				Display.render();
				Display.swapBuffers();
			}
		});
		
		t.setRepeats(true);
		t.start();
	}

}
