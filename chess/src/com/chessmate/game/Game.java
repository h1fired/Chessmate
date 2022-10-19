package com.chessmate.game;

import java.awt.Graphics2D;

import com.chessmate.IO.Input;
import com.chessmate.display.Display;
import com.chessmate.ui.GamePart;
import com.chessmate.ui.Menu;
import com.chessmate.ui.Settings;
import com.chessmate.utils.Time;

public class Game implements Runnable{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String TITLE = "Chessmate";
	public static final int CLEAR_COLOR = 0xff000000;
	public static final int NUM_BUFFERS = 3;
	
	public static final float UPDATE_RATE = 60.0f;
	//public static final float FPS_REQUIRED = Time.SECOND / (UPDATE_RATE + 30);
	public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
	public static final long SLEEP_TIME = 1;
	
	public static final String ATLAS_FILENAME = "chess_texatlas.png";
	public static final int SINGLE_TEX_SCALE = 128;
	
	public static enum STATES{
		MENU,
		SETTINGS,
		GAME
	};
	public STATES GAME_STATE = STATES.GAME;
	
	private boolean running;
	private Thread gameThread;
	private Graphics2D graphics;
	
	private Input input;
	
	public Game() {
		running = false;
		input = new Input();
		Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS, input);
		graphics = Display.getGraphics();
		
		//ініціалізація текстур "партії"
		GamePart.init();
		
	}
	
	//Запуск гри
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	//Закриття гри
	public synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cleanUp();
	}
	
	//Запуск GameLoop
	public void run() {
		
		int fps = 0;
		int upd = 0;
		int updl = 0;
		
		long count = 0;
		
		float delta = 0;
		//float delta_fps = 0;
		
		long lastTime = Time.get();
		while(running) {
			
			long now = Time.get();
			long elapsedTime = now - lastTime;
			lastTime = now;
			
			count += elapsedTime;
			
			boolean render = false;
			delta += (elapsedTime / UPDATE_INTERVAL);
			// delta_fps += (elapsedTime / FPS_REQUIRED);
			while(delta > 1) {
				update();
				upd++;
				delta--;
				if(render) {
					updl++;
				}else {
					render = true;
				}
			}
			
			render();
			fps++;
			if(render) {
				
				//delta_fps--;
			}else {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(count >= Time.SECOND) {
				Display.setTitle(TITLE + " || fps: " + fps + " || upd:" + upd + " || updl: " + updl + " // mouse pos x: " + + input.getPosition().getX() + ", y: " + input.getPosition().getY());
				upd = 0;
				fps = 0;
				updl = 0;
				count = 0;
				
			}
				
		}
	}
	
	//Оновлення всіх обчислень
	private void update() {
		if(GAME_STATE == STATES.GAME) {
			GamePart.update();
		}
		
		
		
		handleMouseInput();
	}
	
	//Синхронізація викликів обробника подій мишки
	private void handleMouseInput() {
		
		
		
		input.clearMouseClick();
		
	}

	//Візуалізація гри
	private void render() {
		Display.clear();
		
		if(GAME_STATE == STATES.MENU) {
			Menu.render(Display.getGraphics());
		}else if(GAME_STATE == STATES.SETTINGS) {
			Settings.render(Display.getGraphics());
		}else if(GAME_STATE == STATES.GAME) {
			GamePart.render(graphics);
		}
		
		
		Display.swapBuffers();
	}
	
	//Закриття процесів зв'язаних з грою
	private void cleanUp() {
		Display.destroy();
	}
	
	

}
