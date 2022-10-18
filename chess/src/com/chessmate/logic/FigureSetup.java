package com.chessmate.logic;

public class FigureSetup {

	private static int[][] chessmap = {
		{15, 14, 13, 12, 11, 13, 14, 15},
		{16, 16, 16, 16, 16, 16, 16, 16},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{26, 26, 26, 26, 26, 26, 26, 26},
		{25, 24, 23, 22, 21, 23, 24, 25}
	};
	
	public static int getArrValue(int y, int x) {
		
		if((int)(chessmap[x][y] / 10) == 1) {
			
			return chessmap[x][y] % 10 - 1;
			
		}else if((int)(chessmap[x][y] / 10) == 2) {
			
			return chessmap[x][y] % 10 - 1 + 6;
			
		}
		
		return -1;
		
	}
	
}
