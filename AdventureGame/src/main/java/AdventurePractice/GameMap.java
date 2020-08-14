package AdventurePractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameMap {
	private int locationID;
	private String locationDescription;
	private Map<Integer, GameMap> location = new HashMap<>();
	private Map<String, Integer> exits = new HashMap<>();
	private Map<String, Map> exitToNewMap = new HashMap<>();
	private Map<Integer, GameMap> locationTwo = new HashMap<>();
	private Map<String, String> vocabMap = new HashMap<>();
	private Scanner input = new Scanner(System.in);

	private int beginningLocId = 1;

	public GameMap() {
		this.location = getLocationMap();
		this.locationTwo = getLocationMapTwo();
		this.vocabMap = vocabMap();

	}

	public GameMap(int locationID, String locationDescription) {
		this.locationID = locationID;
		this.locationDescription = locationDescription;
		addExits("Q", 0);
	}

	public Map<String, String> getVocabMap() {
		return vocabMap;
	}

	public void addExternalExitMap(String direction, Map locationTwo) {
		exitToNewMap.put(direction, location);
	}

	public Map<Integer, GameMap> getLocation() {
		return location;
	}

	public void setLocation(Map<Integer, GameMap> location) {
		this.location = location;
	}

	public Map<String, Map> getExitToNewMap() {
		return exitToNewMap;
	}

	public void setExitToNewMap(Map<String, Map> exitToNewMap) {
		this.exitToNewMap = exitToNewMap;
	}

	public Map<String, Integer> getExits() {
		return exits;
	}

	public void setExits(Map<String, Integer> exits) {
		this.exits = exits;
	}

	public Map<Integer, GameMap> getNewGameMap() {
		return location;
	}

	public void addExits(String direction, int location) {
		exits.put(direction, location);
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public Map<Integer, GameMap> getLocationMap() {
		// location map is populated with location id and location object
		// each new Location will also contain exits, initialized in constructor
		location.put(0, new GameMap(0, ""));
		location.put(1,
				new GameMap(1, "You look out around the clearing. You have landed in an ink tree forest.\n"
						+ "Ink trees secrete a black sap. When the sap dries, it crumbles into a feathery\n"
						+ "powder that causes cardiac arrest if inhaled. You make a note not to touch any\n"
						+ "sap, whether flowing or dry."));
		location.put(2, new GameMap(2, "You are at the edge of the forest. Ink tree leaves are fanlike, and as\n"
									+ "broad as sails. They are dark green, almost oil-black. The dark chlorophyll\n"
									+ "is heavy with lead and interferes with your communications.  Lost men have\n"
									+ "died of thirst here, found with fully functioning comms in their hands,\n"
									+ "the signal hindered by the ink tree leaves. You must not lose track of your ship."));
		location.put(3,
				new GameMap(3, "3"));
		location.put(4, new GameMap(4, "4"));
		location.put(5,
				new GameMap(5, "5"));
		location.put(6, new GameMap(6, "6"));
		location.put(7, new GameMap(7, "7"));
		location.put(8, new GameMap(8, "8"));
		location.put(9, new GameMap(9, "9"));
		location.put(10, new GameMap(10, "10"));
		location.put(11, new GameMap(11, "11"));
		location.put(12, new GameMap(12, "12"));
		location.put(13, new GameMap(13, "13"));
		location.put(14, new GameMap(14, "Up in the canopy, the nest of a Forvan bird shakes in the wind.\n"
										+ "Forvans build their nests out of dry ivy and the toxic sap of\n"
										+ "the ink tree. The nests are the size of a grown man."));
		location.put(15, new GameMap(15, "15"));
		location.put(16, new GameMap(16, "16"));
		location.put(17, new GameMap(17, "17"));
		location.put(18, new GameMap(18, "18"));
		location.put(19, new GameMap(19, "19"));
		location.put(20, new GameMap(20, "20"));
		location.put(21, new GameMap(21, "21"));
		location.put(22, new GameMap(22, "22"));
		location.put(23, new GameMap(23, "23"));
		location.put(24, new GameMap(24, "24"));
		location.put(25, new GameMap(25, "25"));
		location.put(26, new GameMap(26, "26"));
		location.put(27, new GameMap(27, "27"));
		location.put(28, new GameMap(28, "28"));

		location.put(999, new GameMap(999, "Map1"));

		// adds exits to each location in location map

		location.get(1).addExits("W", 2);

		location.get(2).addExits("W", 3);
		location.get(2).addExits("E", 1);

		location.get(3).addExits("N", 4);
		location.get(3).addExits("E", 2);

		location.get(4).addExits("N", 5);
		location.get(4).addExits("S", 3);

		location.get(5).addExits("S", 4);
		location.get(5).addExits("N", 7);
		location.get(5).addExits("W", 6);
		location.get(5).addExits("E", 8);

		location.get(6).addExits("E", 5);

		location.get(7).addExits("S", 5);

		location.get(8).addExits("W", 5);
		location.get(8).addExits("N", 10);
		location.get(8).addExits("S", 9);

		location.get(9).addExits("N", 8);

		location.get(10).addExits("S", 8);
		location.get(10).addExits("E", 11);

		location.get(11).addExits("W", 10);
		location.get(11).addExits("S", 12);

		location.get(12).addExits("N", 11);
		location.get(12).addExits("S", 13);

		location.get(13).addExits("N", 12);
		location.get(13).addExits("S", 14);
		location.get(13).addExits("E", 16);
		location.get(13).addExits("W", 15);

		location.get(14).addExits("N", 13);
		location.get(14).addExits("E", 26);

		location.get(15).addExits("E", 13);

		location.get(16).addExits("W", 13);
		location.get(16).addExits("N", 17);

		location.get(17).addExits("W", 16);
		location.get(17).addExits("S", 18);

		location.get(18).addExits("N", 17);
		location.get(18).addExits("S", 19);
		location.get(18).addExits("E", 21);

		location.get(19).addExits("N", 18);
		location.get(19).addExits("S", 20);

		location.get(20).addExits("N", 19);

		location.get(21).addExits("S", 18);
		location.get(21).addExits("N", 22);

		location.get(22).addExits("N", 23);
		location.get(22).addExits("S", 21);

		location.get(23).addExits("N", 24);
		location.get(23).addExits("S", 22);

		location.get(24).addExits("W", 25);
		location.get(24).addExits("S", 23);

		location.get(25).addExits("E", 24);

		location.get(26).addExits("N", 27);
		location.get(26).addExits("S", 17);
		location.get(26).addExits("E", 14);

		location.get(27).addExits("W", 28);
		location.get(27).addExits("S", 26);

		location.get(28).addExits("E", 27);

		// exits to location two
		location.get(28).addExternalExitMap("N", locationTwo);
		return location;
	}

	public Map<Integer, GameMap> getLocationMapTwo() {
		// location map is populated with location id and location object
		// each new Location will also contain exits, initialized in constructor
		locationTwo.put(0, new GameMap(0, ""));
		locationTwo.put(1, new GameMap(1, "Congratulations, you've made it out of the forest!"));
		locationTwo.put(2, new GameMap(2, "You see an abandoned house with broken windows"));
		locationTwo.put(3, new GameMap(3, "You've found an old canoe with the paddles laying beside it"));
		locationTwo.put(4, new GameMap(4, "Here you see many wildflowers growing"));
		locationTwo.put(5, new GameMap(5, "Oh no, another dark forest"));

		// adds exits to each location in location map

		// from home
		locationTwo.get(1).addExits("W", 2);
		locationTwo.get(1).addExits("Q", 0);
		// from barn
		locationTwo.get(2).addExits("S", 3);
		locationTwo.get(2).addExits("N", 5);
		locationTwo.get(2).addExits("W", 4);
		locationTwo.get(2).addExits("E", 1);
		locationTwo.get(2).addExits("Q", 0);
		// FROM STREAM
		locationTwo.get(3).addExits("N", 2);
		locationTwo.get(3).addExits("W", 4);
		locationTwo.get(3).addExits("Q", 0);
		// FROM HILL
		locationTwo.get(4).addExits("N", 5);
		locationTwo.get(4).addExits("Q", 0);
		// FROM FOREST
		locationTwo.get(5).addExits("S", 2);
		locationTwo.get(5).addExits("Q", 0);
		return locationTwo;
	}

	public Map<String, String> vocabMap() {
		// acceptable words for user input when asked for new location
		vocabMap.put("QUIT", "Q");
		vocabMap.put("NORTH", "N");
		vocabMap.put("SOUTH", "S");
		vocabMap.put("EAST", "E");
		vocabMap.put("WEST", "W");
		return vocabMap;

	}

	public void moveAround(Scanner input, Hero hero, Stats stats, Key key) throws InterruptedException {
		Enemy enemy = new Enemy();
		AdventureMainCLI cli = new AdventureMainCLI();
		GameMap gameMap = new GameMap();
		chooseLandingLocation(stats);
		// initializes the first map
		Map<Integer, GameMap> theMap = location;
		GameMap currentLocation = theMap.get(beginningLocId);

		while (true) {
			// add current description to places discovered stats
			if (!stats.getPlaces().contains(currentLocation.getLocationDescription())) {
				stats.setPlaces(currentLocation.getLocationDescription());
			}

			// placement of key to get to chapter two
			if (currentLocation.getLocationDescription()
					.equals("You find yourself at the top of a large hill. You see the river to your east \"\r\n"
							+ "										+ \"\\nand a bridge a bit north of your location. As you survey the landscape, \"\r\n"
							+ "										+ \"\\nyou see multiple paths down the hill..")) {
				key.keyInMap(hero);

			}

			System.out.print("\n");
			System.out.println(currentLocation.getLocationDescription());
			System.out.print("\n");

			// this will show available locations on map
//			if (currentLocation.getLocationID() == 5) {
//				getExitToNewMapLocations(currentLocation);
//			} else {
//				getStringOfNextLocations(currentLocation);
//			}

			// user enters which direction they want to go
			System.out.print("Enter action>>> ");

			// take the value from key of available exits, use as location for next
			// currentLocation
			String inputForNextLocation = input.nextLine().toUpperCase();

			String[] inputForNextLocationArr = null;
			String direction = "";
			// this checks for a sentence to see if it has the word of the
			// direction in it, checks against vocab map
			if (inputForNextLocation.equals("Q")) {
				cli.quitSequence(input, stats);
				continue;
			}
			if (inputForNextLocation.equals("INVENTORY")) {
				cli.inventoryDisplay(hero);
				continue;
			}
			if (inputForNextLocation.length() > 1) {
				inputForNextLocationArr = inputForNextLocation.split(" ");
				for (String word : inputForNextLocationArr) {
					if (gameMap.getVocabMap().containsKey(word)) {
						direction = gameMap.getVocabMap().get(word);
					}
				}

			} else {
				direction = inputForNextLocation;
			}
			if (currentLocation.getExits().containsKey(direction)) {
				beginningLocId = currentLocation.getExits().get(direction);
			}
			// only locations with values corresponding to exits that lead to other maps
			// will satisfy this boolean
			else if (currentLocation.exitToNewMap.containsKey(direction)) {
				// initializes the second map

				// ******** instead of calling each individual map and chapter, make methods for
				// next
				// chapter, next map-- maybe have a list and a counter that pulls at
				// index each time this cycles through-- this will make the code below
				// less brittle

				theMap = gameMap.getLocationMapTwo();
				beginningLocId = 1;
				cli.clrscr();
				cli.printChapterTwo();
			} else {
				cli.cantGoThatWay();
			}

			currentLocation = theMap.get(beginningLocId);

			enemy.randomEnemyGenerator(hero, stats);

		}
	}

	public void chooseLandingLocation(Stats stats) throws InterruptedException {
		AdventureMainCLI cli = new AdventureMainCLI();
		List<String> wood = new ArrayList<>();
		List<String> bald = new ArrayList<>();
		wood.add("CLEARING");
		wood.add("WOODED");
		wood.add("VALLEY");
		wood.add("CLEAR");
		wood.add("WOOD");
		wood.add("WOODS");
		bald.add("GRASSY");
		bald.add("BALD");
		bald.add("MOUNTAIN");
		bald.add("GRASS");
		bald.add("MOUNT");
		boolean flag = true;
		while (flag) {
			
			String scrollingText = "You guide the craft down through the atmosphere.\n"
					+ "You see a clearing in a wooded valley.\n"
					+ "You also see a grassy bald on a mountain.\n"
					+ "\nWhere do you land? ";
			
			cli.scrollTheTextMediumSpeed(scrollingText);

			String userInput = input.nextLine().toUpperCase();
			String[] landingLocationArr = userInput.split(" ");

			for (String landingLocation : landingLocationArr) {
				if (wood.contains(landingLocation)) {
					beginningLocId = 1;
					
					scrollingText = "You land in the clearing.\n"
							+ "Your co-pilot, and oldest friend, Pete, does a quick scan from the cockpit.\n"
							+ "No other ships within a hundred miles. ";
					cli.scrollTheTextMediumSpeed(scrollingText);
					flag = false;
					break;
				}

				else if (bald.contains(landingLocation)) {
					beginningLocId = 20;
					
					scrollingText = "You land on the bald of the mountain.\n"
							+ "Your co-pilot, and oldest friend, Pete, does a quick scan from the cockpit.\n"
							+ "No other ships within a hundred miles. ";
					cli.scrollTheTextMediumSpeed(scrollingText);
					flag = false;
					break;
				}

				else if (landingLocation.equals("Q")) {
					cli.quitSequence(input, stats);
				}

			}

		}

	}

}
