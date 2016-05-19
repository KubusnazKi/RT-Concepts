import java.util.LinkedList;
import java.util.Random;

public class Level {

	public int levelSize;
	public int numberOfRooms;
	public int minRoomSize;
	public int maxRoomSize;	
	Random RNG = new Random();
	int[][] level;

	public Level(int levelSize, int numberOfRooms, int minRoomSize, int maxRoomSize) {

		this.levelSize = levelSize;
		this.numberOfRooms = numberOfRooms;
		this.minRoomSize = minRoomSize;
		this.maxRoomSize = maxRoomSize;

		//Generate empty level grid
		level = new int[levelSize][levelSize];

	}

	public void addRooms() {

		//List of all rooms created
		LinkedList<Room> rooms = new LinkedList<Room>();

		//Create some rooms
		for (int i = 0; i < numberOfRooms; i++) {
			int length = RNG.nextInt(maxRoomSize - minRoomSize) + minRoomSize;
			int width = RNG.nextInt(maxRoomSize - minRoomSize) + minRoomSize;
			Room room = new Room(length, width);
			rooms.add(room);
		}

		//Add each room to the map
		for (Room nextRoom : rooms) {
			boolean isPlaced = false;
			int count = 0;
			while (!isPlaced && count < 100) {
				System.out.println("Attempt: " + count);
				isPlaced = placeRoomInMap(nextRoom, level);
				count++;
			}
			System.out.println("Placed room");
		}
		System.out.println("Done placing rooms");
	}

	private boolean placeRoomInMap(Room nextRoom, int[][] level) {

		//Randomize room origin (top left corner)
		int originX = RNG.nextInt(levelSize - (nextRoom.width));
		int originY = RNG.nextInt(levelSize - (nextRoom.length));
		System.out.println("Origin: " + originX + " " + originY);
		System.out.println("L W: " + nextRoom.width + " " + nextRoom.length);

		//Check if placing the room here would overlap with another room
		for (int i = 0; i < nextRoom.width; i++) {
			for (int j = 0; j < nextRoom.length; j++) {
				if (level[originY + j][originX + i] != 0) {
					System.out.println("TESTTESTTESTTESTTEST");
					return false; //Overlap
				}
			}
		}

		//Create walls
		for (int i = 0; i < nextRoom.width; i++) {
			level[originY][originX + i] = 1;
		}
		for (int i = 0; i < nextRoom.width; i++) {
			level[originY + nextRoom.length][originX + i] = 1;
		}
		for (int j = 0; j < nextRoom.length; j++) {
			level[originY + j][originX] = 1;
		}
		for (int j = 0; j <= nextRoom.length; j++) {
			level[originY + j][originX + nextRoom.width] = 1;
		}

		//Create floor
		for (int i = 1; i < nextRoom.width; i++) {
			for (int j = 1; j < nextRoom.length; j++) {
				level[originY + j][originX + i] = 2;
			}
		}

		return true;

	}

	public void printMap() {
		System.out.println("####~~~~~~~~~~~~~~~~~Map Legend~~~~~~~~~~~~~~~~~####");
		System.out.println("Blank Space : X   Wall : #   Floor : O   Doorway : &");
		System.out.println();

		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[0].length; j++) {
				if (level[i][j] == 0) {			//Void Space
					System.out.print("X");
				} else if (level[i][j] == 1) {	//Room wall
					System.out.print("#");
				} else if (level[i][j] == 2) {	//Room floor
					System.out.print("O");
				} else if (level[i][j] == 3) {	//Room door
					System.out.print("&");
				} else {
					System.out.println();
					System.out.println("Error: illogical level element");
					System.out.println();
					break;
				}
			}
			System.out.println();
		}
	}

}