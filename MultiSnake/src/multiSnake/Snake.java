package multiSnake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Snake {
private SnakeSegment head, tail, iter;
public Color color;
public boolean isAlive;

public Snake() {
head = tail = iter = null;
isAlive = true;
color = Color.GRAY;
}

public Snake(Color c) {
head = tail = iter = null;
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
SnakeSegment curSeg = start();
String result = "";
while(moreElements()){
result += curSeg.toString();
curSeg = nextElement();
}
return result;
}

public ArrayList<Point> toArray(){
ArrayList<Point> ret = new ArrayList<Point>();
ret.add(this.start().getPoint());
while(this.moreElements()){
ret.add(nextElement().getPoint());
}
return ret;
}

public boolean empty() {
return (head != null);
}
public SnakeSegment getHead() { return head;}
public Color getColor() {return color;}

public SnakeSegment start() {
iter = tail;
return iter;
}

public SnakeSegment nextElement() {
SnakeSegment ws = iter;
iter = iter.getNext();
return ws;
}

public boolean moreElements() { return iter != null; }

public void draw(Graphics g, int scale)
{
if(isAlive)
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
}

public void draw(Graphics g, int scale, Point trueCenter, Point falseCenter)
{
if(isAlive)
{
SnakeSegment seg = new SnakeSegment(head.getPoint());
start();
int count = 1;
g.setColor(color.darker().darker());
Point segLoc = translate(seg.getPoint(), trueCenter, falseCenter, scale);
g.fillOval(segLoc.getCol(), segLoc.getRow(), scale, scale);
if(moreElements()){
seg = nextElement();
}
g.setColor(color);
while(moreElements())
{
count++;
segLoc = translate(seg.getPoint(), trueCenter, falseCenter, scale);
g.fillOval(segLoc.getCol(), segLoc.getRow(), scale, scale);
seg = nextElement();
}
g.setColor(Color.black);
g.drawString("Snake length: "+count, 8, 12);
}
}

private Point translate(Point segLoc, Point trueCenter, Point falseCenter, int scale)
{
int row = falseCenter.getRow() + scale*(segLoc.getRow() - trueCenter.getRow());
int col = falseCenter.getCol() + scale*(segLoc.getCol() - trueCenter.getCol());
return new Point(row, col);
}
}