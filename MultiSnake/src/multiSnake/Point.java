package multiSnake;

public class Point {  // represents a block/location on the screen/image
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
	    //return whether Point p is the same as this Point
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
		  return "("+row+", "+col+")";
	  }
	  
	  public Point toPoint(String point){
		  String[] pointInfo = point.split("");
		  int row = Integer.parseInt(pointInfo[1]);
		  int col = Integer.parseInt(pointInfo[3]);
		  return new Point(row, col);
	  }
}

/*
public class Point
{
	  private double row, col;

	  public Point(double x, double y)
	  {
	    row = x;
	    col = y;
	  }
	  
	  public Point(Point other)
	  {
	    row = other.row;
	    col = other.col;
	  }
	  
	  public Point translatedFrom(double row, double col)
	  {
		  Point result = new Point(this);
		  result.row += row;
		  result.col += col;
		  return result;
	  }
	  
	  public Point moved(double r, double theta)
	  {
		  Point result = new Point(this);
		  result.row += r * Math.cos(theta);
		  result.col += r * Math.sin(theta);
		  return result;
	  }
	  
	  @Override
	  public String toString()
	  {
		  return "("+row+", "+col+")";
	  }
	  
	  @Override
	  public boolean equals(Object other)
	  {
		  if(other instanceof Point)
		  {
			  Point that = (Point)other;
			  return row == that.row && col == that.col;
		  }
		  return false;
	  }
	  
	  @Override
	  public int hashCode()
	  {
		  return toString().hashCode();
	  }
	  
	  public static void main(String[] args)
	  {
		  Point p = new Point(3, 4);
		  System.out.println(p);
		  Point q = new Point(3, 4);
		  System.out.println(q);
		  if(p.equals(q))
		  {
			  System.out.println("equal!");
		  }
		  else
		  {
			  System.out.println("not equal?!");
		  }
		  
		  ArrayList<Point> moves = new ArrayList<Point>();
		  moves.add(new Point(0, 0));
		  for(Direction d: new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
		  {
			  moves.add(d.moved(moves.get(moves.size() - 1)));
		  }
		  for(Point m: moves)
		  {
			  System.out.println("m: "+m);
		  }
	  }
}
*/