package newGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
	
	private final int DUNGEON_LEFT_MAX = 0; // old 10
	private final int DUNGEON_TOP = 0; // old 3
	private final int DUNGEON_RIGHT_MAX = 69; // old 79
	private final int DUNGEON_BOTTOM = 15; // old 18
	private int curX;
	private int curY;
	private Random rand = new Random();
	
	public class Room
	{
		public int Xsize = rand.nextInt(11) + 5;
		public int Ysize = rand.nextInt(11) + 5;
		// min size are 4 by 4, and max size are 15 by 15
		public int X = curX;
		public int Y = curY;
		// the x and y cords are found in the top left corner
	}
	
	public class Hallway
	{
		public int length;
	}
	
	public class Tile
	{
		public char TileType; //X: wall, _: empty, |: door, $: treasure, P: player, M: monster
		public char TileVisibility; //V: visible, P: seen before, but out of sight, I: haven't seen before
		public int TileX;
		public int TileY;
	}
	
	public Map()
	{
		curX = rand.nextInt(69) + 1;
		curY = rand.nextInt(15) + 1;
		List<Room> rooms = new ArrayList<>();
		List<Hallway> hallways = new ArrayList<>();
		List<Tile> tiles = new ArrayList<>();
		int curRoom = 0;
		int curHall = 0;
		int curTile = 0;
		int limit = rand.nextInt(8) + 3;

		
		// room build
		build: for(int x = 0; x < limit; x++)
		{
			Room r = new Room();
			rooms.add(r);
			if(r.Xsize + curX - 1 < DUNGEON_RIGHT_MAX && r.Ysize + curY - 1 < DUNGEON_BOTTOM)
			{
				int X = r.X;
				int Y = r.Y;
				int XS = r.Xsize;
				int YS = r.Ysize;
				
				//Anything in the way?
				for(; X < r.X + r.Xsize - 1; X++)
				{
					if(MainGame.csi.peekChar(X, Y) == 'X' || MainGame.csi.peekChar(X, Y + YS - 1) == 'X' || MainGame.csi.peekChar(X, Y) == '.' || MainGame.csi.peekChar(X, Y + YS - 1) == '.')
					{
						x--;
						continue build;
					}
				}
				for(X = r.X; Y < r.Y + r.Ysize - 1; Y++)
				{
					if(MainGame.csi.peekChar(X, Y) == 'X' || MainGame.csi.peekChar(X + XS - 1, Y) == 'X' || MainGame.csi.peekChar(X, Y) == '.' || MainGame.csi.peekChar(X + XS - 1, Y) == '.')
					{
						x--;
						continue build;
					}
				}
				
				
				//build
				Y = r.Y;
				for(X = r.X; X < r.X + r.Xsize - 1; X++)
				{
					MainGame.csi.print(X, Y, "X");
					for(YS = r.Ysize - 1; YS > 1; YS--)
					{
						MainGame.csi.print(X, Y + YS - 1, ".");
					}
					MainGame.csi.print(X, Y + r.Ysize - 1, "X");
				}
				for(X = r.X; Y <= r.Y + r.Ysize - 1; Y++)
				{
					MainGame.csi.print(X, Y, "X");
					MainGame.csi.print(X + XS - 1, Y, "X");
				}
			}else{
				curX = rand.nextInt(69) + 1;
				curY = rand.nextInt(15) + 1;
				x--;
			}
		}
		
		//Hallway build
		build: for(int x = 0; x < limit; x++)
		{
			
		}
	}
}