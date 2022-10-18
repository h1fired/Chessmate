package com.chessmate.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.chessmate.game.Game;
import com.chessmate.graphics.Sprite;
import com.chessmate.graphics.SpriteSheet;
import com.chessmate.graphics.TextureAtlas;
import com.chessmate.logic.FigureSetup;

public class GamePart {
	
	//tmp
	
	private static float delta_x = 0;
	
	
	
	//tmp end
	
	private static int x = 50;
	private static int y = 50;
	private static int mapSize = 8;
	
	private static float SCALE = 0.5f;
	private static float FIGURE_SCALE = 0.3f;
	
	//sprites
	// Поле
	private static TextureAtlas atlas;
	private static SpriteSheet sheet;
	private static Sprite spriteWhite;
	private static Sprite spriteBlack;
	
	// Фігури
	private static SpriteSheet sheetFigure;
	private static Sprite spriteFigure[];
	
	//tmp
	private static Sprite spriteTestContain;
	//tmp end
	
	//sprites end
	
	private static char[] coordsLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
	private static Font coordsString = new Font("arial", Font.BOLD, 12);
	
	public static void init() {
		
		//chessmap
		atlas = new TextureAtlas("chess_texatlas.png");
		sheet = new SpriteSheet(atlas.cut(Game.SINGLE_TEX_SCALE * 6, 0, Game.SINGLE_TEX_SCALE, Game.SINGLE_TEX_SCALE * 2));
		spriteWhite = new Sprite(sheet.getSprite(0), SCALE);
		spriteBlack = new Sprite(sheet.getSprite(1), SCALE);
		
		//figures
		sheetFigure = new SpriteSheet(atlas.cut(0, 0, Game.SINGLE_TEX_SCALE * 6, Game.SINGLE_TEX_SCALE * 2));
		spriteFigure = new Sprite[12];
		for(int i = 0; i < spriteFigure.length; i++) {
			spriteFigure[i] = new Sprite(sheetFigure.getSprite(i), SCALE);
		}
		
		//testContain
		spriteTestContain = new Sprite(sheetFigure.getSprite(0), SCALE);
		
	}
	
	public static void render(Graphics2D g) {
		chessMap(g, x, y);
		figureSetup(g, x, y);
		coordinates(g, x, y);
		
		spriteTestContain.render(g, x, 300);
	}
	
	public static void update() {
		delta_x += 0.01f;
		x = (int)(Math.sin(delta_x) * 200);
		
		//test
		System.out.println(spriteTestContain.isContain());
	}
	
	
	public static void chessMap(Graphics2D g, int x, int y) {
		
		boolean fieldShift = false;
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				
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
			}
		}
	}
	
	public static void figureSetup(Graphics2D g, int x, int y) {
		
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				
				
				
				if(FigureSetup.getArrValue(i, j) != -1) {
					spriteFigure[FigureSetup.getArrValue(i, j)].render(g, ((Game.SINGLE_TEX_SCALE * SCALE) * i + x), ((Game.SINGLE_TEX_SCALE * SCALE) * j + y));
				}
				
			}
		}
		
	}
	
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
