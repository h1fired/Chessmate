package com.chessmate.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {

	private BufferedImage image;
	private float scale;
	
	public Sprite(BufferedImage image, float scale) {
			
		this.image = image;
		this.scale = scale;
	}
	
	public void render(Graphics2D g, float x, float y)  {
		
		g.drawImage(image, (int)x, (int)y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale), null);
		
	}
	
}
