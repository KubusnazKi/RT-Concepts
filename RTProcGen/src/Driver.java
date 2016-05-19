public class Driver {

	public static void main(String[] args) {

		//Generate empty level
		Level level = new Level(150, 50, 20, 40);

		//Generate rooms
		level.addRooms();

		//Generate pathways

		//Print map to console
		level.printMap();
	}

}
