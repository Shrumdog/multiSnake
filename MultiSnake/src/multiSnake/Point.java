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
		  if(p.getRow() != getRow()) { return false; }
		  else if(p.getCol() != getCol()) { return false; }
		  else { return true; }
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
	  
	  public static Point toPoint(String point){
			point = point.trim();
			String[] pointInfo = point.split(",");
			String temp = pointInfo[0].replace("(", "");
			int row = Integer.parseInt(temp);
			temp = pointInfo[1];
			temp = (String) temp.subSequence(0, 2);
			int col = Integer.parseInt(temp);
			return new Point(row, col);
		}
}