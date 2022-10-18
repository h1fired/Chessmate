package com.chessmate.graphics;

public class Contains {

	private int x;
	private int y;
	private int max_x;
	private int max_y;
	
	public Contains(int x, int y, int max_x, int max_y) {
		this.x = x;
		this.y = y;
		this.max_x = max_x;
		this.max_y = max_y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getMaxX() {
		return max_x;
	}
	
	public int getMaxY() {
		return max_y;
	}

	
}
