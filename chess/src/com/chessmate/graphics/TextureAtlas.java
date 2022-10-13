package com.chessmate.graphics;

import java.awt.image.BufferedImage;

import com.chessmate.utils.ResourceLoader;

public class TextureAtlas {
	BufferedImage image;
	
	public TextureAtlas(String imageName) {
		image = ResourceLoader.loadImage(imageName);
	}
	
	public BufferedImage getAtlasImage() {
		return image;
	}
	
	public BufferedImage cut(int x, int y, int w, int h) {
		
		return image.getSubimage(x, y, w, h);
		
	}
}
