package multiSnake;

public class Point {
	  private int row, col;

	  public Point( int x, int y )
	  {
	    row = x;
	    col = y;
	  }
	  
	  public Point(Point other)
	  {
	    row = other.row;
	    col = other.col;
	  }

	  public void setRow( int x ) { row = x; } 
	  public int getRow() { return row; } 
	  public void setCol( int x ) { col = x; } 
	  public int getCol() { return col; } 
	  public boolean equals( Point p ) { 
		  if (p != null){
			  if(p.getRow() != getRow()) { return false; }
			  else if(p.getCol() != getCol()) { return false; }
			  else { return true; }
		  } return false;
	  }
	  @Override
	  public int hashCode()
	  {
		  return toString().hashCode();
	  }
	  
	  @Override
	  public String toString()
	  {
		  return "("+row+","+col+") ";
	  }
}