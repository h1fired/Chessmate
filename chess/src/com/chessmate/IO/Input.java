package com.chessmate.IO;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.chessmate.utils.Position;



public class Input implements MouseListener, MouseMotionListener {

	private Position mousePosition; // зберігає позицію мишки (x, y)
	private boolean mouseClicked;
	private boolean mousePressed;
	
	public Input() {
		mousePosition = new Position(0, 0);
	}
	
	//Повертає позицію мишки в даний момент
	public Position getPosition() {
		return mousePosition;
	}
	
	//Перевірка на зажим мишки
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	//Перевірка на нажаття мишки
	public boolean isMouseClicked() {
		return mouseClicked;
	}
	
	//Очищення нажаття мишки
	public void clearMouseClick() {
		mouseClicked = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePosition = new Position((float)e.getPoint().getX(), (float)e.getPoint().getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClicked = true;
		mousePosition = new Position((float)e.getPoint().getX(), (float)e.getPoint().getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		mousePosition = new Position((float)e.getPoint().getX(), (float)e.getPoint().getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked = true;
		mousePressed = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
