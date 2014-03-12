package multiSnake;

import java.awt.Color;
import java.awt.Graphics;

public class Snake {
	private SnakeSegment head, tail, iter;
	public Color color;
	public boolean isAlive;
	  
	  Snake() {
		  head = tail = iter = null;
		  isAlive = true;
		  color = Color.GRAY;
	  }
	  
	  Snake(Color c) {
		  head = tail = iter = null;
		  isAlive = true;
		  color = c;
	  }
	  
	  public void addToHead( SnakeSegment p ) {
		// p becomes the new head OF THE SNAKE
		  if(head != null) { head.setNext(p); }
		  if(tail == null) { tail = head; }
		  head = p;
	  }

	  public SnakeSegment rmTail(){ 
		//remove and return the tail OF THE SNAKE
		  SnakeSegment temp = tail;
		  tail = tail.getNext();
		  return temp;
	  }

	  public boolean empty() { //returns whether the snake body is empty; 
		return (head != null);  
	  }
	  public SnakeSegment getHead() { return head;} //returns the head of the SNAKE
	  public Color getColor() {return color;}
	  
	  // The following three methods are iterator methods.
	  // This allows for an external agent to run through
	  // the data structure WITHOUT KNOWING ITS UNDERLYING
	  // STRUCTURE
	  
	  public void start() {  //set iter to the "head" of  Snake
		  iter = tail;
	  }
	  
	  public SnakeSegment nextElement() {
	    SnakeSegment ws = iter;
	    iter = iter.getNext();
	    return ws;
	  }
	  
	  public boolean moreElements() { return iter != null; }
	  
	  public void draw(Graphics g, int scale)
	  {
		  g.setColor(color);
		  SnakeSegment seg = new SnakeSegment(head.getPoint());
		  start();
		  while(moreElements())
		  {
			  g.fillOval(seg.getCol()*scale, seg.getRow()*scale, scale, scale);
			  seg = nextElement();
		  }
	  }
	  
	  public void draw(Graphics g, int scale, Point trueCenter, Point falseCenter)
	  {
		  SnakeSegment seg = new SnakeSegment(head.getPoint());
		  start();
		  g.setColor(color.darker().darker());
		  Point segLoc = translate(seg.getPoint(), trueCenter, falseCenter, scale);
		  g.fillOval(segLoc.getCol(), segLoc.getRow(), scale, scale);
		  seg = nextElement();
		  g.setColor(color);
		  while(moreElements())
		  {
			  segLoc = translate(seg.getPoint(), trueCenter, falseCenter, scale);
			  g.fillOval(segLoc.getCol(), segLoc.getRow(), scale, scale);
			  seg = nextElement();
		  }
	  }
	  
	  private Point translate(Point segLoc, Point trueCenter, Point falseCenter, int scale)
	  {
		  int row = falseCenter.getRow() + scale*(segLoc.getRow() - trueCenter.getRow());
		  int col = falseCenter.getCol() + scale*(segLoc.getCol() - trueCenter.getCol());
		  return new Point(row, col);
	  }
}
