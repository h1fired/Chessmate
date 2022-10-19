package com.chessmate.logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.chessmate.IO.Input;
import com.chessmate.ui.GamePart;
import com.chessmate.utils.Contains;

public class Figure {
	
	private BufferedImage image;
	private float scale;
	
	private boolean ACTIVE_ANIM;
	private boolean ACTIVE_OBJECT;
	
	private int beg_x;
	private int beg_y;
	private int contain_x;
	private int contain_y;
	private int shifted_x;
	private int shifted_y;
	
	private Contains contain;
	
	private int c;
	
	public Figure(BufferedImage image, float scale, float x, float y) {
		this.beg_x = (int)x;
		this.beg_y = (int)y;
		this.image = image;
		this.scale = scale;
		
		contain_x = beg_x;
		contain_y = beg_y;
		contain = new Contains(beg_x, beg_y, beg_x + (int)(image.getWidth() * scale), beg_y + (int)(image.getHeight() * scale));
		
		ACTIVE_ANIM = false;
	}
	
	//Візуалізація текстури
	public void render(Graphics2D g)  {
		g.drawImage(image, this.contain_x, + this.contain_y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale), null);
	}
	
	//Обчислення математичних функцій
	public void update() {
		dragAnimation();
	}
	
	//Переміщення фігури
	public void dragAnimation() {
		if(isContain() && Input.isMousePressed()) {
			ACTIVE_ANIM = true;
		}
		
		if(ACTIVE_ANIM && Input.isMousePressed() && ACTIVE_OBJECT) {
			
			if(c==0) {
				shifted_x = (int)Input.getPosition().getX() - contain.getX();
				shifted_y = (int)Input.getPosition().getY() - contain.getY();
			}
			c++;
			
			//Запис області фігури
			this.contain_x = (int) Input.getPosition().getX() - shifted_x;
			this.contain_y = (int) Input.getPosition().getY() - shifted_y;
			contain.setX(this.contain_x);
			contain.setY(this.contain_y);
			contain.setMaxX((int)(this.contain_x + image.getWidth() * scale));
			contain.setMaxY((int)(this.contain_y + image.getHeight() * scale));
			
		}
		
		if(Input.isMousePressed() == false) {
			mapFieldMove();
			c = 0;
			ACTIVE_ANIM = false;
		}
		
		if(Input.isMousePressed()) {
			//System.out.println("MOUSE POS = x: " + Input.getPosition().getX() + " || y: " + Input.getPosition().getY() + " || s: " + shifted_x);
		}
	}
	
	//Переміщення фігури в поле
	public void mapFieldMove() {
		for(int i = 0; i < 64; i++) {
			Contains mapField = GamePart.getMapContains(i);
			
			if(ACTIVE_OBJECT && Input.getPosition().getX() >= mapField.getX() && Input.getPosition().getX() <= mapField.getMaxX() && Input.getPosition().getY() >= mapField.getY() && Input.getPosition().getY() <= mapField.getMaxY()) {
				
				contain.setX(mapField.getX());
				contain.setY(mapField.getY());
				contain.setMaxX(mapField.getMaxX());
				contain.setMaxY(mapField.getMaxY());
				
				contain_x = mapField.getX();
				contain_y = mapField.getY();
				
				System.out.println("DRAGED = x: " + contain.getX() + " || y: " + contain.getY() + " || max-x: " + contain.getMaxX() + " || max-y: " + contain.getMaxY());
			}
			
		}
	}
	
	//Перевірка на нажаття мишки в області фігури
	public boolean isContain() {
		if(Input.getPosition().getX() >= contain.getX() && Input.getPosition().getX() <= contain.getMaxX() && Input.getPosition().getY() >= contain.getY() && Input.getPosition().getY() <= contain.getMaxY()) {
			return true;
		}else {
			return false;
		}
	}
	
	//Активність фігури
	public void setActive(boolean active) {
		ACTIVE_OBJECT = active;
	}
	
	
}
