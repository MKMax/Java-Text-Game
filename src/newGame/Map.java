package newGame;

import java.util.ArrayList;
import java.util.List;

public class Map {
	
	private final int DUNGEON_LEFT_MAX = 0; // old 10
	private final int DUNGEON_TOP = 0; // old 3
	private final int DUNGEON_RIGHT_MAX = 69; // old 79
	private final int DUNGEON_BOTTOM = 20; // old 18
	private int curX;
	private int curY;

	private List<Room> rooms;
	private List<Hallway> hallways;
	private List<Tile> tiles;

	public class Room
	{
		public int Xsize = MainGame.random.nextInt(11) + 5;
		public int Ysize = MainGame.random.nextInt(11) + 5;
		// min size are 4 by 4, and max size are 15 by 15
		public int X = curX;
		public int Y = curY;
		// the x and y cords are found in the top left corner
	}
	
	public class Hallway
	{
		int length;
		char direction; // R = right, L = left, U = up, D = down
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
		curX = MainGame.random.nextInt(69) + 1;
		curY = MainGame.random.nextInt(15) + 1;
		rooms = new ArrayList<>();
		hallways = new ArrayList<>();
		tiles = new ArrayList<>();
		int curRoom = 0;
		int curHall = 0;
		int curTile = 0;
		int limit = MainGame.random.nextInt(20) + 10;
		// room build
		int retries = 0;
		build: for(int x = 0; x < limit; x++)
		{
			if(retries < 100)
			{
				curX = MainGame.random.nextInt(69) + 1;
				curY = MainGame.random.nextInt(15) + 1;
				Room r = new Room();
				rooms.add(r);
				if(r.Xsize + curX - 1 < DUNGEON_RIGHT_MAX && r.Ysize + curY - 1 < DUNGEON_BOTTOM)
				{
					int X = r.X;
					int Y = r.Y;
					int XS = r.Xsize;
					int YS = r.Ysize;
					
						//Anything in the way?for(; X < r.X + r.Xsize - 1; X++)
						{
							if(MainGame.csi.peekChar(X, Y) == 'X' || MainGame.csi.peekChar(X, Y + YS - 1) == 'X' || MainGame.csi.peekChar(X, Y) == '.' || MainGame.csi.peekChar(X, Y + YS - 1) == '.')
							{
								x--;
								rooms.remove(curRoom);
								retries++;
								continue build;
							}
						}
						for(X = r.X; Y < r.Y + r.Ysize - 1; Y++)
						{
							if(MainGame.csi.peekChar(X, Y) == 'X' || MainGame.csi.peekChar(X + XS - 1, Y) == 'X' || MainGame.csi.peekChar(X, Y) == '.' || MainGame.csi.peekChar(X + XS - 1, Y) == '.')
							{
								x--;
								rooms.remove(curRoom);
								retries++;
								continue build;
							}
						}
						
						for(int CountA = 1; CountA <= r.Xsize; CountA++)
						{
							for(int CountB = 1; CountB <= r.Ysize; CountB++)
							{
								if(MainGame.csi.peekChar(CountA - 1 + r.X, CountB - 1 + r.Y) == 'X' || MainGame.csi.peekChar(CountA - 1 + r.X, CountB - 1 + r.Y) == '.')
								{
									x--;
									rooms.remove(curRoom);
									retries++;
									continue build;
								}
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
					curRoom++;
				}else{
					x--;
					rooms.remove(curRoom);
				}
			}else{
				limit = rooms.size();
			}
		}
		
		//Hallway build
		build: for(int x = 0; x < limit; x++)
		{
			Hallway h = new Hallway();
			hallways.add(h);
			h.direction = 'R';
			Room r = rooms.get(x);
			int HallwayHeight = MainGame.random.nextInt(r.Ysize - 2) + r.Y + 1;
			
			//right----------------------------------------------------
			for(h.length = r.X + r.Xsize - 1;; h.length++)
			{
				// hits dungeon boundary
				if(h.length + 1 >= DUNGEON_RIGHT_MAX)
				{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, "X");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
					break;
				}
				// hits other hallway
				else if(MainGame.csi.peekChar(h.length + 1, HallwayHeight) == '.')
				{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, ".");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight, ".");
					MainGame.csi.print(h.length + 1, HallwayHeight + 1, "X");
					break;
				}
				//directly hits a room
				else if(MainGame.csi.peekChar(h.length + 1, HallwayHeight) == 'X'
						&& MainGame.csi.peekChar(h.length + 1, HallwayHeight + 1) == 'X'
						&& MainGame.csi.peekChar(h.length + 1, HallwayHeight - 1) == 'X')
				{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, ".");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight, ".");
					MainGame.csi.print(h.length + 1, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 2, HallwayHeight, ".");
					break;
				}
				// hits a room in two spots
				else if(MainGame.csi.peekChar(h.length + 1, HallwayHeight + 1) == 'X'
						&& MainGame.csi.peekChar(h.length + 1, HallwayHeight) == 'X')
				{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, ".");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight, ".");
					MainGame.csi.print(h.length + 1, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 2, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 2, HallwayHeight, ".");
					MainGame.csi.print(h.length + 2, HallwayHeight + 1, ".");
					MainGame.csi.print(h.length + 3, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight + 1, ".");
					break;
				}else if(MainGame.csi.peekChar(h.length + 1, HallwayHeight - 1) == 'X'
						&& MainGame.csi.peekChar(h.length + 1, HallwayHeight) == 'X')
				{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, ".");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight, ".");
					MainGame.csi.print(h.length + 1, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 2, HallwayHeight - 1, ".");
					MainGame.csi.print(h.length + 2, HallwayHeight, ".");
					MainGame.csi.print(h.length + 2, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight - 1, ".");
					MainGame.csi.print(h.length + 3, HallwayHeight, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight + 1, "X");
					break;
				}
				// hits a room in one spot
				else if(MainGame.csi.peekChar(h.length + 1, HallwayHeight + 1) == 'X')
				{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, ".");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight, ".");
					MainGame.csi.print(h.length + 1, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 2, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 2, HallwayHeight, ".");
					MainGame.csi.print(h.length + 2, HallwayHeight + 1, ".");
					MainGame.csi.print(h.length + 3, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight + 1, "X");
					break;
				}else if(MainGame.csi.peekChar(h.length + 1, HallwayHeight - 1) == 'X')
				{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, ".");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 1, HallwayHeight, ".");
					MainGame.csi.print(h.length + 1, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 2, HallwayHeight - 1, ".");
					MainGame.csi.print(h.length + 2, HallwayHeight, ".");
					MainGame.csi.print(h.length + 2, HallwayHeight + 1, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight, "X");
					MainGame.csi.print(h.length + 3, HallwayHeight + 1, "X");
					break;
				}
				//standard hallway segment
				else{
					MainGame.csi.print(h.length, HallwayHeight - 1, "X");
					MainGame.csi.print(h.length, HallwayHeight, ".");
					MainGame.csi.print(h.length, HallwayHeight + 1, "X");
				}
			}
			int HallwayX = MainGame.random.nextInt(r.Xsize - 2) + r.X + 1;
			//Down----------------------------------------------------
			for(h.length = r.Y + r.Ysize - 1;; h.length++)
			{
				// hits dungeon boundary
				if(h.length + 1 >= DUNGEON_BOTTOM)
				{
					MainGame.csi.print(HallwayX - 1, h.length, "X");
					MainGame.csi.print(HallwayX, h.length, "X");
					MainGame.csi.print(HallwayX + 1, h.length, "X");
					break;
				}
				//directly hits a room
				else if(MainGame.csi.peekChar(HallwayX, h.length + 1) == 'X'
						&& MainGame.csi.peekChar(HallwayX + 1, h.length + 1) == 'X'
						&& MainGame.csi.peekChar(HallwayX - 1, h.length + 1) == 'X'
						&& MainGame.csi.peekChar(HallwayX, h.length + 2) == '.')
				{
					MainGame.csi.print(HallwayX - 1, h.length, "X");
					MainGame.csi.print(HallwayX, h.length, ".");
					MainGame.csi.print(HallwayX + 1, h.length, "X");
					MainGame.csi.print(HallwayX - 1, h.length + 1, "X");
					MainGame.csi.print(HallwayX, h.length + 1, ".");
					MainGame.csi.print(HallwayX + 1, h.length + 1, "X");
					MainGame.csi.print(HallwayX, h.length + 2, ".");
					break;
				}
				// hits a room in two spots
				else if(MainGame.csi.peekChar(HallwayX + 1, h.length + 1) == 'X'
						&& MainGame.csi.peekChar(HallwayX, h.length + 1) == 'X')
				{
					if(MainGame.csi.peekChar(HallwayX + 2, h.length + 2) != ' ')
					{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX, h.length + 1, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 2, "X");
						MainGame.csi.print(HallwayX, h.length + 2, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 2, ".");
						MainGame.csi.print(HallwayX - 1, h.length - 3, "X");
						MainGame.csi.print(HallwayX, h.length + 3, "X");
						MainGame.csi.print(HallwayX + 1, h.length + 3, ".");
						break;
					}
					else{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
					}
				}else if(MainGame.csi.peekChar(HallwayX - 1, h.length + 1) == 'X'
						&& MainGame.csi.peekChar(HallwayX, h.length + 1) == 'X')
				{
					if(MainGame.csi.peekChar(HallwayX - 2, h.length + 2) != ' ')
					{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX, h.length + 1, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 2, ".");
						MainGame.csi.print(HallwayX, h.length + 2, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 2, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 3, ".");
						MainGame.csi.print(HallwayX, h.length + 3, "X");
						MainGame.csi.print(HallwayX + 1, h.length + 3, "X");
						break;
					}
					else{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
					}
				}
				// hits a room in one spot
				else if(MainGame.csi.peekChar(HallwayX + 1, h.length + 1) == 'X')
				{
					if(MainGame.csi.peekChar(HallwayX + 2, h.length + 2) != ' ')
					{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX, h.length + 1, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 2, "X");
						MainGame.csi.print(HallwayX, h.length + 2, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 2, ".");
						MainGame.csi.print(HallwayX - 1, h.length + 3, "X");
						MainGame.csi.print(HallwayX, h.length + 3, "X");
						MainGame.csi.print(HallwayX + 1, h.length + 3, "X");
						if(MainGame.csi.peekChar(HallwayX + 2, h.length + 3) == ' ')
						{
							MainGame.csi.print(HallwayX + 2, h.length + 3, "X");
						}
						break;
					}
					else{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
					}
				}else if(MainGame.csi.peekChar(HallwayX - 1, h.length + 1) == 'X')
				{
					if(MainGame.csi.peekChar(HallwayX - 2, h.length + 2) != ' ')
					{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX, h.length + 1, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 1, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 2, ".");
						MainGame.csi.print(HallwayX, h.length + 2, ".");
						MainGame.csi.print(HallwayX + 1, h.length + 2, "X");
						MainGame.csi.print(HallwayX - 1, h.length + 3, "X");
						MainGame.csi.print(HallwayX, h.length + 3, "X");
						MainGame.csi.print(HallwayX + 1, h.length + 3, "X");
						if(MainGame.csi.peekChar(HallwayX - 2, h.length + 3) == ' ')
						{
							MainGame.csi.print(HallwayX - 2, h.length + 3, "X");
						}
						break;
					}
					else{
						MainGame.csi.print(HallwayX - 1, h.length, "X");
						MainGame.csi.print(HallwayX, h.length, ".");
						MainGame.csi.print(HallwayX + 1, h.length, "X");
					}
				}
				// hits other hallway
				else if(MainGame.csi.peekChar(HallwayX, h.length + 1) == '.')
				{
					MainGame.csi.print(HallwayX - 1, h.length, "X");
					MainGame.csi.print(HallwayX, h.length, ".");
					MainGame.csi.print(HallwayX + 1, h.length, "X");
					MainGame.csi.print(HallwayX - 1, h.length + 1, "X");
					MainGame.csi.print(HallwayX, h.length + 1, ".");
					MainGame.csi.print(HallwayX + 1, h.length + 1, "X");
					break;
				}
				//standard hallway segment
				else{
					MainGame.csi.print(HallwayX - 1, h.length, "X");
					MainGame.csi.print(HallwayX, h.length, ".");
					MainGame.csi.print(HallwayX + 1, h.length, "X");
				}
			}
		}
	}
}
