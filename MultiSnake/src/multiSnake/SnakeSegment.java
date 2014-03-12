package multiSnake;

public class SnakeSegment {
  private Point p;
  private SnakeSegment next;

  public SnakeSegment( Point pp ) { 
    p = pp;
    next = null;
  }
  
  public SnakeSegment( int row, int col ) {
    p = new Point( row, col );
    next = null;
  }

  public Point getPoint() { return p; }
  public void setNext( SnakeSegment ws ) { next = ws; }
  public SnakeSegment getNext() { return next; }
  public int getRow() { return p.getRow(); } //return the row location of this SnakeSegment 
  public int getCol() { return p.getCol(); } //return the column location of this SnakeSegment 
}