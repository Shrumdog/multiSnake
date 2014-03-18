package multiSnake;


public class MapBuffer {
	//more static CONSTANTS for convenience	  
	private final static int SNAKE_SEGMENT = -1, WALL = -2;
	private int [][] screen;
	private int size;
	
	//The values in ScreenBuufer's screen array are either SNAKE_SEGMENT, WALL, 
	// or the index value where the point can be found in the freePool
	//  
	MapBuffer( int rows, int columns ) {  //THIS IS COMPLETE
		screen = new int[rows][columns]; 
		size = rows;
		//a two dimensional array is a "long" one-dimensional array but the the two
		//  indices [row] [col] internally do the arithmetic to find the correct
		//  associated value in the "hidden" long array
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
		//return whether the given point corresponds to a Snake Segment location
		return (screen[p.getRow()][p.getCol()] == SNAKE_SEGMENT);
	}

	public void makeSegment( Point p ) { 
		//set the given point's value to be a Snake Segment location
		screen[p.getRow()][p.getCol()] = SNAKE_SEGMENT;
	}

	public boolean isWall( Point p ) { 
		//return whether the given point corresponds to a Wall location
		return (screen[p.getRow()][p.getCol()] == WALL);
	}

	public boolean isOccupied( Point p ) { 
		//return whether this point corresponds to a Snake Segment or Wall location
		if(p.getRow() > size - 1 || p.getCol() > size - 1){ return true; }
		return (screen[p.getRow()][p.getCol()] == WALL || screen[p.getRow()][p.getCol()] == SNAKE_SEGMENT);
	}

	public int valueAt( Point p ) {
		//return the screen value at Point p
		return screen[p.getRow()][p.getCol()];
	}
	
	public void setValueAt( Point p, int n ) {
		//set the screen value at Point p to value n
		screen[p.getRow()][p.getCol()] = n;
	}

}