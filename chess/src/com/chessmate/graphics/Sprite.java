package com.chessmate.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.chessmate.utils.Contains;

public class Sprite{

	private BufferedImage image;
	
	private float scale;
	private int x;
	private int y;
	
	private Contains contain;
	
	public Sprite(BufferedImage image, float scale) {
		this.image = image;
		this.scale = scale;
		this.contain = new Contains(0, 0, 0 + (int)(image.getWidth() * scale), 0 + (int)(image.getHeight() * scale));
	}
	
	//Візуалізація текстури
	public void render(Graphics2D g, float x, float y)  {
		this.x = (int)x;
		this.y = (int)y;
		
		this.contain = new Contains(this.x, this.y, this.x + (int)(image.getWidth() * scale), this.y + (int)(image.getHeight() * scale));
		g.drawImage(image, this.x, + this.y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale), null);
	}
	
	//Вивід в консоль області фігури
	public void printContain() {
		System.out.println("CONTAIN = x: " + contain.getX() + " || y: " + contain.getY() + " || maxX: " + contain.getMaxX() + " || maxY: " + contain.getMaxY());
	}
	
}
