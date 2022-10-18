package com.chessmate.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.chessmate.animation.Animation;
import com.chessmate.game.Game;

public class Sprite extends Animation{

	private BufferedImage image;
	private float scale;
	
	private int x;
	private int y;
	private static Contains contain;
	
	
	public Sprite(BufferedImage image, float scale) {
			
		this.image = image;
		this.scale = scale;
		
		
	}
	
	public void render(Graphics2D g, float x, float y)  {

		contain = new Contains((int)x, (int)y, (int)x + (int)(image.getWidth() * scale), (int)y + (int)(image.getHeight() * scale));
		g.drawImage(image, (int)x, (int)y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale), null);
		
	}
	
	public boolean isContain() {
		
		if(Game.MOUSE_X >= contain.getX() && Game.MOUSE_X <= contain.getMaxX() && Game.MOUSE_Y >= contain.getY() && Game.MOUSE_Y <= contain.getMaxY()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void printContain() {
		System.out.println("CONTAIN = x: " + contain.getX() + " || y: " + contain.getY() + " || maxX: " + contain.getMaxX() + " || maxY: " + contain.getMaxY());
	}
	
}
