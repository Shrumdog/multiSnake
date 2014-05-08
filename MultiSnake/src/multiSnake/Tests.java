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
		
//		forTheSakeOfCoverage();
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
		System.out.println("Snake Test");
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
	public void segmentTest()
	{
		SnakeSegment seg1 = new SnakeSegment(10, 15);
		SnakeSegment seg2 = new SnakeSegment(seg1.getPoint());
		
		assertNotEquals(seg1, seg2);
		assertEquals(seg1.getRow(), seg2.getRow());
		assertEquals(seg1.getCol(), seg2.getCol());
		assertEquals(seg1.toString(), seg2.toString());
	}
	
	@Test
	public void pointTest()
	{
		int row = 10;
		int col = 15;
		SnakeSegment segment = new SnakeSegment(row, col);
		Point point1 = new Point(segment.getPoint());
		Point point2 = new Point(row, col);
		
		assertNotEquals(point1, point2);
		assertNotEquals(point2, segment);
		assertNotEquals(segment, point1);
		
		assertEquals(point1.getRow(), point2.getRow());
		assertEquals(point2.getRow(), segment.getRow());
		assertEquals(segment.getRow(), point1.getRow());
		
		assertEquals(point1.getCol(), point2.getCol());
		assertEquals(point2.getCol(), segment.getCol());
		assertEquals(segment.getCol(), point1.getCol());
		
		assertEquals(point1.toString(), point2.toString());
		assertEquals(point2.toString(), segment.toString());
		assertEquals(segment.toString(), point1.toString());
		
		point2.setCol(row);
		point2.setRow(col);
		assertEquals(point2.toString(), new Point(col, row).toString());
	}
	
	@Test
	public void playerTest()
	{
		System.out.println("Player Test");
		
		assertEquals(player.getTrueMap(), map);
		assertEquals(player.getVisualMap(), screen);
		
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
		System.out.println("Snake Iterate Test");
		SnakeSegment iterSeg = snake.start(null);
		SnakeSegment headSeg = snake.getHead();
		iterSeg = snake.nextElement(iterSeg);
		
		Point headPnt = headSeg.getPoint();
		Point iterPnt = iterSeg.getPoint();
		while(!headPnt.equals(iterPnt))
		{
			assertTrue(snake.moreElements(iterSeg));
			iterSeg = snake.nextElement(iterSeg);
			iterPnt = iterSeg.getPoint();
		}
		
		iterSeg = snake.nextElement(iterSeg);
		assertFalse(snake.moreElements(iterSeg));
	}
	
//	@Test
//	public void moveTest()
//	{
//		assertEquals(1, map.getSnakes().size());
//		SnakeSegment headSeg = snake.getHead();
//		SnakeSegment neckSeg = getNeck();
//		Point headPnt = headSeg.getPoint();
//		Point neckPnt = neckSeg.getPoint();
//		int vert = Math.abs(headPnt.getRow() - neckPnt.getRow()) * 2;
//		int hori = neckPnt.getCol() - headPnt.getCol() + 2;
//		hori = hori == 2 ? hori + 1 : hori;
//		
//		assertTrue(move(vert));
//		assertTrue(move((vert + 2) % 4));
//		
//		assertTrue(move(hori));
//		assertTrue(move((hori + 2) % 4));
//		assertTrue(move((vert + 2) % 4));
//		assertTrue(move((hori + 2) % 4));
//		assertFalse(move((hori + 2) % 4));
//		assertFalse(move(-1));
//	}
	
	@Test
	public void moveTest()
	{
		System.out.println("Move Test");
		assertEquals(1, map.getSnakes().size());
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment neckSeg = getNeck();
		Point headPnt = headSeg.getPoint();
		Point neckPnt = neckSeg.getPoint();
		
		int vert = headPnt.getRow() - neckPnt.getRow();
		int hori = neckPnt.getCol() - headPnt.getCol();
		Direction vDir = vert >= 0 ? Direction.SOUTH : Direction.NORTH;
		Direction hDir = hori >= 0 ? Direction.EAST : Direction.WEST;
		
		assertTrue(move(vDir));
		vDir = oppositeDirection(vDir);
		assertTrue(move(vDir));
		
		assertTrue(move(hDir));
		hDir = oppositeDirection(hDir);
		assertTrue(move(hDir));
		assertTrue(move(vDir));
		assertTrue(move(hDir));
		assertFalse(move(map.dir));
	}
	
//	@Test
//	public void wallDeathTest()
//	{
//		assertEquals(1, map.getSnakes().size());
//		SnakeSegment headSeg = snake.getHead();
//		SnakeSegment neckSeg = getNeck();
//		Point headPnt = headSeg.getPoint();
//		Point neckPnt = neckSeg.getPoint();
//		int vert = headPnt.getRow() - neckPnt.getRow();
//		int dir = vert != 1 ? map.UP : map.DOWN;
//		
//		while(snake.isAlive)
//		{
//			move(dir);
//		}
//		assertFalse(move(dir));
//	}
	
	@Test
	public void wallDeathTest()
	{
		System.out.println("Wall Death Test");
		assertEquals(1, map.getSnakes().size());
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment neckSeg = getNeck();
		Point headPnt = headSeg.getPoint();
		Point neckPnt = neckSeg.getPoint();
		
		int vert = headPnt.getRow() - neckPnt.getRow();
		Direction dir = vert >= 0 ? Direction.SOUTH : Direction.NORTH;
		
		while(snake.isAlive)
		{
			System.out.println("while... "+dir);
			move(dir);
		}
		dir = rotateClockwise(dir);
		assertFalse(move(dir));
	}
	
	@Test
	public void segmentDeathTest()
	{
		System.out.println("Segment Death Test");
		assertEquals(1, map.getSnakes().size());
		SnakeSegment headSeg = snake.getHead();
		SnakeSegment neckSeg = getNeck();
		Point headPnt = headSeg.getPoint();
		Point neckPnt = neckSeg.getPoint();
		
		int vert = headPnt.getRow() - neckPnt.getRow();
		Direction dir = vert != 1 ? Direction.NORTH : Direction.SOUTH;
		
		while(snake.isAlive)
		{
			move(dir);
			dir = rotateClockwise(dir);
		}
		assertFalse(move(dir));
	}
	
//	private SnakeSegment getNeck()
//	{
//		snake.start(null);
//		SnakeSegment headSeg = snake.getHead();
//		SnakeSegment neck = snake.nextElement();
//		SnakeSegment nextSeg = neck.getNext();
//		
//		Point headPnt = headSeg.getPoint();
//		Point nextPnt = nextSeg.getPoint();
//		while(!headPnt.equals(nextPnt))
//		{
//			neck = snake.nextElement();
//			nextSeg = neck.getNext();
//			nextPnt = nextSeg.getPoint();
//		}
//		
//		return neck;
//	}
	
	private SnakeSegment getNeck()
	{
		SnakeSegment neck = snake.start(null);
		SnakeSegment headSeg = snake.getHead();
		neck = snake.nextElement(neck);
		SnakeSegment nextSeg = neck.getNext();
		
		Point headPnt = headSeg.getPoint();
		Point nextPnt = nextSeg.getPoint();
		while(!headPnt.equals(nextPnt))
		{
			neck = snake.nextElement(neck);
			nextSeg = neck.getNext();
			nextPnt = nextSeg.getPoint();
		}
		
		return neck;
	}
	
	private boolean move(Direction direct)
	{
		System.out.println("Moving "+map.setDirection(direct.getDir().substring(0, 1).toLowerCase())+"\tRequested "+direct.getDir().substring(0, 1).toLowerCase());
		return snake.isAlive = map.move();
	}
	
	private Direction rotateClockwise(Direction direct)
	{
		System.out.print("Rotating... Input: "+direct);
		switch(direct){
			case NORTH:
				System.out.println("\tOutput: EAST");
				return Direction.EAST;
			case EAST:
				System.out.println("\tOutput: SOUTH");
				return Direction.SOUTH;
			case SOUTH:
				System.out.println("\tOutput: WEST");
				return Direction.WEST;
			case WEST:
				System.out.println("\tOutput: NORTH");
				return Direction.NORTH;
			default:
				System.out.println("\tOutput: default");
				return direct;
		}
	}
	
	private Direction oppositeDirection(Direction direct)
	{
		System.out.print("Opposite... Input: "+direct);
		switch(direct){
			case NORTH:
				System.out.println("\tOutput: SOUTH");
				return Direction.SOUTH;
			case EAST:
				System.out.println("\tOutput: WEST");
				return Direction.WEST;
			case SOUTH:
				System.out.println("\tOutput: NORTH");
				return Direction.NORTH;
			case WEST:
				System.out.println("\tOutput: EAST");
				return Direction.EAST;
			default:
				System.out.println("\tOutput: default");
				return direct;
		}
	}
	
//	private void forTheSakeOfCoverage()
//	{
//		new GUI();
//		SnakeOptions o = new SnakeOptions(player);
//		player.setName(o.getName());
//		player.getTrueMap();
//	}
}