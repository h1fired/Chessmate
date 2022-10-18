package com.chessmate.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.chessmate.game.Game;

public class Menu {

	public static void render(Graphics g) {
		
		Font font = new Font("arial", Font.BOLD, 50);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("MENU", Game.WIDTH / 2, 100);
		
	}
	
}
