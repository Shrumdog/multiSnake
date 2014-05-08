package multiSnake;


public class MapBuffer {
	
	private final static int SNAKE_SEGMENT = -1, WALL = -2;
	private int [][] screen;
	private int size;
	
	MapBuffer( int rows, int columns ) {  //THIS IS COMPLETE
		screen = new int[rows][columns]; 
		size = rows;
		
		for( int i = 0; i < rows; i++ ) {
			screen[i][0] = WALL;
			screen[i][columns-1] = WALL;
		}
		for( int i = 0; i < columns; i++ ) { // corners will be marked twice!
			screen[0][i] = WALL;
			screen[rows-1][i] = WALL;
		}
	}

	public boolean isSegment( Point p ) { 
		return (screen[p.getRow()][p.getCol()] == SNAKE_SEGMENT);
	}

	public void makeSegment( Point p ) { 
		screen[p.getRow()][p.getCol()] = SNAKE_SEGMENT;
	}

	public boolean isWall( Point p ) { 
		return (screen[p.getRow()][p.getCol()] == WALL);
	}

	public boolean isOccupied( Point p ) { 
		if(p.getRow() > size - 1 || p.getCol() > size - 1){ return true; }
		return (screen[p.getRow()][p.getCol()] == WALL || screen[p.getRow()][p.getCol()] == SNAKE_SEGMENT);
	}

	public int valueAt( Point p ) {
		return screen[p.getRow()][p.getCol()];
	}
	
	public void setValueAt( Point p, int n ) {
		screen[p.getRow()][p.getCol()] = n;
	}

}