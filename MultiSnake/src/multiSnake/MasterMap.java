
package multiSnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

public class MasterMap
{
	private final int SIZE = 125;
	public Direction dir;
	private int effectiveSize, munchieVal = 1, score, growthVal;
	private MapBuffer field; // size determined by numPlayers
	private ArrayList<Snake> snakes;
	private ArrayList<Point> freePool;
	private ArrayList<Point> effectivePool;
	private ConcurrentHashMap<Color, Point> munchieOwners;
	private Random rand;
	private boolean snakeMade = false;
	private Snake player;
	private int prevDir;

	public MasterMap() {
		dir = Direction.SOUTH;
		score = growthVal = 0;
		effectiveSize = 20;
		field = new MapBuffer(SIZE, SIZE);
		snakes = new ArrayList<Snake>();
		freePool = new ArrayList<Point>(SIZE * SIZE);
		effectivePool = new ArrayList<Point>(effectiveSize * effectiveSize);
		munchieOwners = new ConcurrentHashMap<Color, Point>();
		rand = new Random();
		prevDir = 4;

		for (int i = 1; i < SIZE; i++)
			// declare everything free
			for (int j = 1; j < SIZE; j++)
				makeFree(new Point(i, j));
	}

	public Direction setDirection(String s){
		if(s.equals("n") && !dir.getDir().equals("SOUTH")) dir = Direction.NORTH;
		else if(s.equals("s") && !dir.getDir().equals("NORTH")) dir = Direction.SOUTH;
		else if(s.equals("e") && !dir.getDir().equals("WEST")) dir = Direction.EAST;
		else if(s.equals("w") && !dir.getDir().equals("EAST")) dir = Direction.WEST;
		return dir;
	}

	public void addSnake(Snake snake){
		if(!snake.color.equals(new Color(128, 128, 128))){
			if(munchieOwners.containsKey(snake.getColor())){
				for(Snake s : snakes){
					if(s.color.equals(snake.color)){
						updateSnake(s, snake);
						snakes.remove(s);
						snakes.add(snake);
					}
				}
			}
			else{
				snakes.add(snake);
				if (snakes.size() < 7){effectiveSize += 15;}
				makeEffect();
				spawnSnake(snake);
				spawnMunchie(snake);
			}
		}
	}
	private void updateSnake(Snake old, Snake nu){
		ArrayList<Point> oldArray = old.toArray();
		ArrayList<Point> nuArray = nu.toArray();
		for(Point p: oldArray){
			if(nuArray.contains(p))
				nuArray.remove(p);
			else
				freeUp(p);
		}
		for(Point p: nuArray){
			occupy(p);
		}
	}



	private void makeEffect()
	{
		effectivePool.clear();
		for (int x = (SIZE - effectiveSize) / 2; x <= ((SIZE - effectiveSize) / 2)
				+ effectiveSize; x++)
		{
			for (int y = (SIZE - effectiveSize) / 2; y <= ((SIZE - effectiveSize) / 2)
					+ effectiveSize; y++)
			{
				effect(new Point(x, y));
			}
		}
	}

	public void removeSnake(Snake snake)
	{
		Point m = munchieOwners.remove(snake.getColor());;
		snakes.remove(snake);
	}

	private void spawnSnake(Snake snake)
	{
		Point p = generateLoc();
		while (!effectivePool.contains(p))
		{
			p = generateLoc();
		}
		int direction = rand.nextInt(2);

		if (direction == 0)
		{
			for (int i = 0; i < 7; i++)
			{
				makeSnakeSegment(new Point(p.getRow(), p.getCol() + i), snake);
			}
		} else
		{
			for (int i = 0; i < 7; i++)
			{
				makeSnakeSegment(new Point(p.getRow() + i, p.getCol()), snake);
			}
		}
	}

	public void spawnMunchie(Snake snake)
	{
		Point p = generateLoc();
		while (!effectivePool.contains(p))
		{
			p = generateLoc();
		}

		munchieOwners.put(snake.getColor(), p);
		System.out.println(p);
	}

	private void freeUp(Point p){
		if (inEffect(p)){
			effect(p);
		} else {
			makeFree(p);
		}
	}

	private void occupy(Point p){
		if (inEffect(p)){
			swapEffect(effectivePool.get(effectivePool.size() - 1), field.valueAt(p));
			effectivePool.remove(effectivePool.size() - 1);
		} else{
			swapFree(freePool.get(freePool.size() - 1), field.valueAt(p));
			freePool.remove(freePool.size() - 1);
		}
	}

	private void makeFree(Point p)
	{
		freePool.add(p);
		field.setValueAt(p, freePool.size() - 1);
	}

	private void effect(Point p)
	{
		effectivePool.add(p);
		field.setValueAt(p, effectivePool.size() - 1);
	}

	private void swapFree(Point p, int i)
	{
		freePool.set(i, p);
		field.setValueAt(p, i);
	}

	private void swapEffect(Point p, int i)
	{
		if (i > effectivePool.size() - 1)
		{
			swapFree(p, i);
			return;
		}
		effectivePool.set(i, p);
		field.setValueAt(p, i);
	}

	private void makeSnakeSegment(Point p, Snake snake)
	{
		SnakeSegment seg = new SnakeSegment(p);
		snake.addToHead(seg);
		field.makeSegment(p);
	}

	private Point generateLoc()
	{
		return effectivePool.get(getAnIdxValue(effectivePool.size() - 1));
	}

	public boolean move() { // did the worm survive the move?
			if(player.isAlive)
			{
				SnakeSegment head = player.getHead();
				Point go = dir.move(head.getPoint());
				return finalizeMove(go);
			}
	return false;
	}

	public boolean finalizeMove(Point p)
	{
		Point munchieLocation = munchieOwners.get(player.getColor());
		if (field.isOccupied(p)){return false;}
		else if (contains(p)){
			handleMunchie(p, munchieLocation);
		}

		if (growthVal > 0){
			grow(p);
		} else if (growthVal < 0) {
			shrink(p);
		} else {
			carryOn(p);
		}

		makeSnakeSegment(p, player);
		return true;
	}

	private void handleMunchie(Point p, Point munch){
		if (p.equals(munch)){
			score += munchieVal;
			growthVal += munchieVal;
		} else {
			score -= munchieVal;
			growthVal -= munchieVal;
		}
		newMunchieLocation(p, player);
	}

	private void grow(Point p){
		if (inEffect(p)){
			swapEffect(effectivePool.get(effectivePool.size() - 1), field.valueAt(p));
			effectivePool.remove(effectivePool.size() - 1);
		} else{
			swapFree(freePool.get(freePool.size() - 1), field.valueAt(p));
			freePool.remove(freePool.size() - 1);
		}
		growthVal--;
	}

	private void shrink(Point p){
		SnakeSegment remove = player.rmTail();
		Point temp = player.rmTail().getPoint();
		if (inEffect(p)) {
			effectiveShrinker(remove, temp, p);
		} else {
			freeShrinker(remove, temp, p);
		}
		growthVal++;
	}

	private void effectiveShrinker(SnakeSegment removed, Point temp, Point p){
		effectivePool.add(removed.getPoint());
		field.setValueAt(removed.getPoint(), effectivePool.size() - 1);
		swapEffect(temp, field.valueAt(p));
	}

	private void freeShrinker(SnakeSegment removed, Point temp, Point p){
		freePool.add(removed.getPoint());
		field.setValueAt(removed.getPoint(), freePool.size() - 1);
		swapFree(temp, field.valueAt(p));
	}

	private void carryOn(Point p){
		Point temp = player.rmTail().getPoint();
		if (inEffect(p)) {
			swapEffect(temp, field.valueAt(p));
		} else {
			swapFree(temp, field.valueAt(p));
		}
	}

	private boolean contains(Point p) {
		for(Color c: munchieOwners.keySet()){
			if(munchieOwners.get(c).equals(p)){return true;}
		}
		return false;
	}

	public int getAnIdxValue(int max)
	{ // return a value in 0..(max-1)
			return Math.abs(rand.nextInt(max - 1));
	}

	public void draw(Graphics g, int length)
	{
		int scale = length / SIZE;
		Color c;
		Point m;
		for (Snake s : snakes)
		{
			c = s.getColor();
			m = munchieOwners.get(c);
			g.setColor(c);
			g.fillRect(m.getCol() * scale, m.getRow() * scale, scale, scale);
			s.draw(g, scale);
		}
	}

	public void draw(Graphics g, int coor, Snake focusSnake)
	{
		int scale = 10;
		Point falseCenter = new Point(coor, coor);
		focusSnake.getHead();
		Point trueCenter = new Point(focusSnake.getHead().getPoint());
		drawEdges(g, trueCenter, coor);
		Color c;
		Point m;
		for (Snake s : snakes)
		{
			c = s.getColor();
			m = munchieOwners.get(c);
			m = translate(m, trueCenter, falseCenter, scale);
			g.setColor(c);
			g.fillRect(m.getCol(), m.getRow(), scale, scale);
			s.draw(g, scale, trueCenter, falseCenter);
			snakeMade = true;
		}
	}

	private void drawEdges(Graphics g, Point center, int length)
	{
		drawBackground(g, center, length);
		g.setColor(Color.BLACK);
		int midRow = center.getRow();
		int midCol = center.getCol();
		if (midRow * 10 - length <= 0)
		{
			int dist = length - midRow * 10;
			g.fillRect(0, 0, length * 2, dist);
		} else if (midRow * 10 + length >= SIZE * 10)
		{
			int dist = (midRow * 10 + length) - SIZE * 10;
			g.fillRect(0, length * 2 - dist, length * 2, dist);
		}

		if (midCol * 10 - length <= 0)
		{
			int dist = length - midCol * 10;
			g.fillRect(0, 0, dist, length * 2);
		} else if (midCol * 10 + length >= SIZE * 10)
		{
			int dist = (midCol * 10 + length) - SIZE * 10;
			g.fillRect(length * 2 - dist, 0, dist, length * 2);
		}
	}

	private void drawBackground(Graphics g, Point center, int length)
	{
		Color dirt = Color.getHSBColor(0.1f, 0.5f, 0.9f).darker();
		for(int i = 0 - center.getCol(); i < length*2; i+= 15)
			for(int j = 0 - center.getRow(); j < length*2; j+= 15)
				g.fillRect(i*10, j*10, 5, 5);
	}

	private void newMunchieLocation(Point point, Snake s)
	{
		for (Color c : munchieOwners.keySet())
		{
			if(point.equals(munchieOwners.get(c))){
				int idx = getAnIdxValue(effectivePool.size() - 1);
				munchieOwners.put(c, effectivePool.get(idx));
			}
		}
	}

	private boolean inEffect(Point p)
	{
		if (p.getRow() >= ((SIZE - effectiveSize) / 2)
				&& p.getRow() <= ((SIZE - effectiveSize) / 2 + effectiveSize))
		{
			if (p.getCol() >= ((SIZE - effectiveSize) / 2)
					&& p.getCol() <= ((SIZE - effectiveSize) / 2 + effectiveSize))
			{
				return true;
			}
		}
		return false;
	}

	public int getSize()
	{
		return SIZE;
	}

	public Snake getMySnake()
	{
		return player;
	}

	public ArrayList<Snake> getSnakes()
	{
		return snakes;
	}

	public ArrayList<Point> getFreePool()
	{
		return freePool;
	}

	public boolean getSnakeMade()
	{
		return snakeMade;
	}

	public void changeSnakeMade(boolean bool)
	{
		snakeMade = bool;
	}

	public void swapMunchie (Snake snake, Point newMunchie) { 
		munchieOwners.remove(snake.getColor()); 
		munchieOwners.put(snake.getColor(), newMunchie); }

	public ConcurrentHashMap<Color, Point> getMunchieOwners()
	{
		return munchieOwners;
	}

	private Point translate(Point munchLoc, Point trueCenter,
			Point falseCenter, int scale)
	{
		int row = falseCenter.getRow() + scale
				* (munchLoc.getRow() - trueCenter.getRow());
		int col = falseCenter.getCol() + scale
				* (munchLoc.getCol() - trueCenter.getCol());
		return new Point(row, col);
	}

	public void setPlayerSnake(Snake s)
	{
		player = s;
	}
}