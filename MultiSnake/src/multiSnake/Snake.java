package multiSnake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Snake {
	private SnakeSegment head, tail;
	public Color color;
	public boolean isAlive;
	public int length;
	//private Point p = new Point(0,0);

	public Snake() {
		head = tail = null;
		isAlive = true;
		color = Color.GRAY;
	}

	public Snake(Color c) {
		head = tail = null;
		isAlive = true;
		color = c;
	}

	public void addToHead( SnakeSegment p ) {
		if(head != null) { head.setNext(p); }
		if(tail == null) { tail = head; }
		head = p;
	}

	public SnakeSegment rmTail(){
		SnakeSegment temp = tail;
		tail = tail.getNext();
		return temp;
	}

	@Override
	public String toString(){
		SnakeSegment curSeg = null;
		curSeg = start(curSeg);
		String result = "";
		while(moreElements(curSeg)){
			result += curSeg.toString();
			curSeg = nextElement(curSeg);
		}
		return result;
	}

	//	public ArrayList<Point> toArray(){
	//		ArrayList<Point> ret = new ArrayList<Point>();
	//		ret.add(this.start().getPoint());
	//		while(this.moreElements()){
	//			ret.add(nextElement().getPoint());
	//		}
	//		return ret;
	//	}

	public boolean empty() {
		return (head != null);
	}
	public SnakeSegment getHead() { return head;}
	public Color getColor() {return color;}

	public SnakeSegment start(SnakeSegment seg) {
		seg = tail;
		return seg;
	}

	public SnakeSegment nextElement(SnakeSegment seg) {
		seg = seg.getNext();
		return seg;
	}

	public boolean moreElements(SnakeSegment seg) { return seg != null; }

	public void draw(Graphics g, int scale)
	{
		if(isAlive){

			g.setColor(color);
			SnakeSegment seg = new SnakeSegment(head.getPoint());
			seg = start(seg);
			while(moreElements(seg)){
				g.fillOval(seg.getCol()*scale, seg.getRow()*scale, scale, scale);
				seg = nextElement(seg);
			}
		}
	}

	public void drawSnake(Graphics g, int scale, Point trueCenter, Point falseCenter)
	{
		if(isAlive){
			drawSegments(g, scale, trueCenter, falseCenter);
		}
	}
	
	public void drawSegments(Graphics g, int scale, Point trueCenter, Point falseCenter){
		SnakeSegment seg = new SnakeSegment(head.getPoint());
		int count = 0;
		g.setColor(color.darker().darker());
		Point segLoc = translate(seg.getPoint(), trueCenter, falseCenter, scale);
		g.fillOval(segLoc.getCol(), segLoc.getRow(), scale, scale);
		seg = start(seg);
		g.setColor(color);

		while(moreElements(seg)){
			count++;
			segLoc = translate(seg.getPoint(), trueCenter, falseCenter, scale);
			g.fillOval(segLoc.getCol(), segLoc.getRow(), scale, scale);
			seg = nextElement(seg);
		}
		this.length = count;
	}

	private Point translate(Point segLoc, Point trueCenter, Point falseCenter, int scale)
	{
		int row = falseCenter.getRow() + scale*(segLoc.getRow() - trueCenter.getRow());
		int col = falseCenter.getCol() + scale*(segLoc.getCol() - trueCenter.getCol());
		return new Point(row, col);
	}

	public boolean contains(Point p) {
		SnakeSegment it = null;
		it = start(it);
		while(moreElements(it)){
			if(it.getPoint().equals(p)){return true;}
			it = nextElement(it);
		}
		return false;
	}
	
	
}