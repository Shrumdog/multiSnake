package multiSnake;

import static org.junit.Assert.*;
import gui.GUI;
import gui.VisualMap;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class Tests
{
	private MasterMap map;
	private VisualMap screen;
	private Player player;
	private Snake snake;
	
	@Before
	public void setup()
	{
		map = new MasterMap();
		screen = new VisualMap(map);
		player = new Player(screen);
		
		forTheSakeOfCoverage();
		player.startPlaying();
		
		player.connectListener();
		snake = map.getMySnake();
		snake.color = Color.RED;
		map.addSnake(snake);
		map.setPlayerSnake(snake);
	}
	
	@Test
	public void snakeTest()
	{
		Color c = Color.BLACK;
		Snake derp = new Snake(c);
		assertEquals(c, derp.getColor());
		assertFalse(derp.empty());
		map.addSnake(derp);
		assertTrue(derp.empty());
		assertEquals(2, map.getSnakes().size());
		screen.removeSnake(derp);
	}
	
	@Test
	public void playerTest()
	{
		Color c = Color.YELLOW;
		player.setColor(c);
		assertEquals(c, snake.getColor());
		player.stopPlaying();
		assertEquals(1, map.getSnakes().size());
		player.resumePlay();
		assertEquals(1, map.getSnakes().size());
	}
	
	@Test
	public void snakeIterateTest()
	{
		snake.start();
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment iterSeg = snake.nextElement();
		
		Point headPnt = headSeg.getPoint();
		Point iterPnt = iterSeg.getPoint();
		while(!headPnt.equals(iterPnt))
		{
			assertTrue(snake.moreElements());
			iterSeg = snake.nextElement();
			iterPnt = iterSeg.getPoint();
		}
		
		assertFalse(snake.moreElements());
	}
	
	@Test
	public void moveTest()
	{
		assertEquals(1, map.getSnakes().size());
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment neckSeg = getNeck();
		Point headPnt = headSeg.getPoint();
		Point neckPnt = neckSeg.getPoint();
		int vert = Math.abs(headPnt.getRow() - neckPnt.getRow()) * 2;
		int hori = neckPnt.getCol() - headPnt.getCol() + 2;
		hori = hori == 2 ? hori + 1 : hori;
		
		assertTrue(move(vert));
		assertTrue(move((vert + 2) % 4));
		
		assertTrue(move(hori));
		assertTrue(move((hori + 2) % 4));
		assertTrue(move((vert + 2) % 4));
		assertTrue(move((hori + 2) % 4));
		assertFalse(move((hori + 2) % 4));
		assertFalse(move(-1));
	}
	
	@Test
	public void wallDeathTest()
	{
		assertEquals(1, map.getSnakes().size());
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment neckSeg = getNeck();
		Point headPnt = headSeg.getPoint();
		Point neckPnt = neckSeg.getPoint();
		int vert = headPnt.getRow() - neckPnt.getRow();
		int dir = vert != 1 ? map.UP : map.DOWN;
		
		while(snake.isAlive)
		{
			move(dir);
		}
		assertFalse(move(dir));
	}
	
	@Test
	public void segmentDeathTest()
	{
		assertEquals(1, map.getSnakes().size());
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment neckSeg = getNeck();
		Point headPnt = headSeg.getPoint();
		Point neckPnt = neckSeg.getPoint();
		int vert = headPnt.getRow() - neckPnt.getRow();
		int dir = vert != 1 ? map.UP : map.DOWN;
		
		while(snake.isAlive)
		{
			move(dir);
			dir = (dir + 1) % 4;
		}
		assertFalse(move(dir));
	}
	
	private SnakeSegment getNeck()
	{
		snake.start();
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment neck = snake.nextElement();
		SnakeSegment nextSeg = neck.getNext();
		
		Point headPnt = headSeg.getPoint();
		Point nextPnt = nextSeg.getPoint();
		while(!headPnt.equals(nextPnt))
		{
			neck = snake.nextElement();
			nextSeg = neck.getNext();
			nextPnt = nextSeg.getPoint();
		}
		
		return neck;
	}
	
	private boolean move(int direct)
	{
		return snake.isAlive = map.move(direct);
	}
	
	private void forTheSakeOfCoverage()
	{
		new GUI();
		SnakeOptions o = new SnakeOptions(player);
		player.setName(o.getName());
		player.getTrueMap();
	}
}