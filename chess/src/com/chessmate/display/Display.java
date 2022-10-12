package com.chessmate.display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.JFrame;

public abstract class Display {
	
	private static boolean created = false; //Статус вікна гри (створений чи ні)
	private static JFrame window;
	private static Canvas content;
	
	private static BufferedImage buffer;
	private static int[] bufferData;
	private static Graphics bufferGraphics;
	private static int clearColor;
	
	private static BufferStrategy bufferStrategy;
	
	public static void create(int width, int height, String title, int _clearColor, int numBuffers) {
		//Перевірка чи запущена гра, якщо ні - виходить з функції
		if(created) { 
			return;
		}
		
		//Створення вікна гри
		window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content = new Canvas();
		
		Dimension size = new Dimension(width, height);
		content.setPreferredSize(size);
		
		window.setResizable(false);
		window.getContentPane().add(content);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		//Буфер для графіки цілого вікна 
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
		bufferGraphics = buffer.getGraphics();
		((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		clearColor = _clearColor;
		
		//Мульти буферизація
		content.createBufferStrategy(numBuffers);
		bufferStrategy = content.getBufferStrategy();
		
		created = true;
	}
	
	//Очищення буфера
	public static void clear() {
		Arrays.fill(bufferData, clearColor);
	}
	
	// Заміна стандартного буфера на створений buffer
	public static void swapBuffers() {
		Graphics g = bufferStrategy.getDrawGraphics();
		g.drawImage(buffer, 0, 0, null);
		bufferStrategy.show();
	}
	
	public static Graphics2D getGraphics() {
		return (Graphics2D) bufferGraphics;
	}
	
	public static void destroy() {
		if(!created)
			return;
		
		window.dispose();
	}
	
	public static void setTitle(String title) {
		window.setTitle(title);
	}
	
}
