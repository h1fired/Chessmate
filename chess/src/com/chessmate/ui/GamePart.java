package com.chessmate.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.chessmate.IO.Input;
import com.chessmate.display.Display;
import com.chessmate.game.Game;
import com.chessmate.graphics.Sprite;
import com.chessmate.graphics.SpriteSheet;
import com.chessmate.graphics.TextureAtlas;
import com.chessmate.logic.Figure;
import com.chessmate.logic.FigureSetup;
import com.chessmate.utils.Contains;

public class GamePart {
	
	//tmp
	private static float delta_x = 0;
	//tmp end
	
	private static int x = 50;
	private static int y = 50;
	private static int mapSize = 8;
	private static float SCALE = 0.5f;
	
	private static TextureAtlas atlas;
	private static SpriteSheet sheet;
	private static Sprite spriteWhite;
	private static Sprite spriteBlack;
	private static Contains mapContains[];
	private static boolean mapCreated = false;
	
	private static SpriteSheet sheetFigure;
	private static Figure spriteFigure[];
	private static int c = 0;
	
	private static char[] coordsLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
	private static Font coordsString = new Font("arial", Font.BOLD, 12);
	
	//Ініціалізація текстур
	public static void init() {
		// Шахматна дошка
		atlas = new TextureAtlas("chess_texatlas.png");
		sheet = new SpriteSheet(atlas.cut(Game.SINGLE_TEX_SCALE * 6, 0, Game.SINGLE_TEX_SCALE, Game.SINGLE_TEX_SCALE * 2));
		spriteWhite = new Sprite(sheet.getSprite(0), SCALE);
		spriteBlack = new Sprite(sheet.getSprite(1), SCALE);
		mapContains = new Contains[mapSize * mapSize];
		
		// Фігури
		sheetFigure = new SpriteSheet(atlas.cut(0, 0, Game.SINGLE_TEX_SCALE * 6, Game.SINGLE_TEX_SCALE * 2));
		spriteFigure = new Figure[32];
		
		int c = 0;
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				if(FigureSetup.getArrValue(i, j) != -1) {
					spriteFigure[c] = new Figure(sheetFigure.getSprite(FigureSetup.getArrValue(i, j)), SCALE, ((Game.SINGLE_TEX_SCALE * SCALE) * i + x), ((Game.SINGLE_TEX_SCALE * SCALE) * j + y));
					c++;
				}
			}
		}
		
	}
	
	public static void render(Graphics2D g) {
		Display.clear();
		
		chessMap(g, x, y);
		figureSetup(g, x, y);
		coordinates(g, x, y);
		
		Display.swapBuffers();
	}
	
	public static void update() {
		delta_x += 0.01f;
		//x = (int)(Math.sin(delta_x) * 200);
		
		if(mapCreated) {
			activeObject();
		}
	}
	
	//Встановлення активної фігури
	public static void activeObject() {
		for(int i = 0; i < spriteFigure.length; i++) {
			spriteFigure[i].update();
		}
		
		if(Input.isMousePressed() && c == 0) {
			
			for(int i = 0; i < spriteFigure.length; i++) {
				spriteFigure[i].setActive(false);
			}
			
			for(int i = 0; i < spriteFigure.length; i++) {
				if(spriteFigure[i].isContain()) {
					spriteFigure[i].setActive(true);
					break;
				}
			}
			c++;
		}
		
		if(!Input.isMousePressed()) {
			c = 0;
		}
	}
	
	
	
	//Візуалізація шахматної дошки
	public static void chessMap(Graphics2D g, int x, int y) {
		boolean fieldShift = false;
		int c_map = 0;
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				
				//Візуалізація поля шахматів
				if(fieldShift) {
					if(i % 2 == 0) {
						spriteWhite.render(g, ((Game.SINGLE_TEX_SCALE * SCALE) * i + x), ((Game.SINGLE_TEX_SCALE * SCALE) * j + y));
					}else {
						spriteBlack.render(g, ((Game.SINGLE_TEX_SCALE * SCALE) * i + x), ((Game.SINGLE_TEX_SCALE * SCALE) * j + y));
					}
					fieldShift = false;
				}else {
					if(i % 2 == 0) {
						spriteBlack.render(g, ((Game.SINGLE_TEX_SCALE * SCALE) * i + x), ((Game.SINGLE_TEX_SCALE * SCALE) * j + y));
					}else {
						spriteWhite.render(g, ((Game.SINGLE_TEX_SCALE * SCALE) * i + x), ((Game.SINGLE_TEX_SCALE * SCALE) * j + y));
					}
					fieldShift = true;
				}
				
				//Координатні поля
				int c_x = (int)((Game.SINGLE_TEX_SCALE * SCALE) * i + x);
				int c_y = (int)((Game.SINGLE_TEX_SCALE * SCALE) * j + y);
				int c_x_max = c_x + (int)(Game.SINGLE_TEX_SCALE * SCALE);
				int c_y_max = c_y + (int)(Game.SINGLE_TEX_SCALE * SCALE);
				
				mapContains[c_map] = new Contains(c_x, c_y, c_x_max, c_y_max);
				c_map++;
				
				mapCreated = true;
			}
		}
	}
	
	//Отримання координатного поля
	public static Contains getMapContains(int index) {
		return mapContains[index];
	}
	
	//Візуалізація фігур
	public static void figureSetup(Graphics2D g, int x, int y) {
		int c = 0;
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				
				if(FigureSetup.getArrValue(i, j) != -1) {
					spriteFigure[c].render(g);
					c++;
				}
			}
		}
		
	}
	
	//Візуалізація координат
	public static void coordinates(Graphics2D g, int x, int y) {
		g.setFont(coordsString);
		g.setColor(Color.white);
		for(int i = 0; i < mapSize; i++) {
			g.drawString(String.valueOf(coordsLetters[i]), x + (Game.SINGLE_TEX_SCALE * SCALE / 2 - 5) + (i * Game.SINGLE_TEX_SCALE * SCALE), y + 15 + (Game.SINGLE_TEX_SCALE * mapSize * SCALE));
		}
		for(int i = 0; i < mapSize; i++) {
			String coordNum = Integer.toString(mapSize - i);
			g.drawString(coordNum, x + (Game.SINGLE_TEX_SCALE * SCALE * mapSize) + 15, y + (i * Game.SINGLE_TEX_SCALE * SCALE) + (Game.SINGLE_TEX_SCALE * SCALE / 2) + 10);
		}
	}
}
