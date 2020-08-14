package AdventurePractice;

import  sun.audio.*;
import java.applet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AdventureMainCLI {
	private String scrollingText = "";
	private Scanner input = new Scanner(System.in);
	private Random rand = new Random();

	
	public void music() throws IOException {
		String fileName = "trenda_one.wav";
		File file = new File(fileName);
		
			try {
				InputStream in = new FileInputStream(fileName);
				AudioStream as = new AudioStream(in);
				AudioPlayer.player.start(as);
			}
			catch (FileNotFoundException e) {
				e.getMessage();
			}
	}
	
	
	
	public void scrollTheTextMediumSpeed(String scrollingText) throws InterruptedException {
		for (int i = 0; i < scrollingText.length() - 1; i++) {
			Thread.sleep(25);
			System.out.print(scrollingText.substring(i, i + 1));

		}
		System.out.println("\n");
	}

	public void scrollTheTextSlowSpeed(String scrollingText) throws InterruptedException {
		for (int i = 0; i < scrollingText.length() - 1; i++) {
			Thread.sleep(100);
			System.out.print(scrollingText.substring(i, i + 1));

		}
		System.out.println("\n");
	}

	public void scrollTheTextFastSpeed(String scrollingText) throws InterruptedException {
		for (int i = 0; i < scrollingText.length() - 1; i++) {
			Thread.sleep(10);
			System.out.print(scrollingText.substring(i, i + 1));

		}
		System.out.println("\n");
	}

	public void clrscr() {

		// Clears Screen in java
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}

	public void controls() {
		System.out.println("\n");
		System.out.println("------------------------------------------------------------------- ");
		System.out.println("\n");

		System.out.println("Controls: \n");
		System.out.println("Move through the map using 'N', 'S', 'E', or 'W' on keyboard.\n"
				+ "Also try commands such as 'go south' or 'head east'.\n"
				+ "Type 'inventory' when not in battle to show money collected "
				+ "and \n\tspecial items you have picked up on your journey\n"
				+ "Press 'Q' when not in battle to quit.\n");

		System.out.println("\n");
		System.out.println("------------------------------------------------------------------- ");
		System.out.println("\n");
	}

	public void cantGoThatWay() throws InterruptedException {

		List<String> msg = new ArrayList<String>();
		msg.add("\t*This doesn't look like the way out* ");
		msg.add("\t*You trip over some rocks as you try to find your way ");
		msg.add("\t*Your boot is stuck in the mud, try a different way* ");
		msg.add("\t*An impasse* ");
		msg.add("\t*Not this way, try a different way* ");
		msg.add("\t*This way doesn't look promising* ");
		msg.add("\t*Something tells you that way isn't going to work, try another* ");
		msg.add("\t*Your way is blocked by an impassible obsticle* ");
		msg.add("\t*You won't make any progress going that way* ");
		msg.add("\t*That way is not going to get you to your starship any faster* ");
		msg.add("\t*You might be lost, this isn't the right way* ");
		msg.add("\t*Nah* ");
		msg.add("\t*Nope* ");
		msg.add("\t*Don't go that way* ");

		int randomNumber = rand.nextInt(msg.size());
		System.out.println("\n");

		scrollingText = msg.get(randomNumber);
		scrollTheTextFastSpeed(scrollingText);

		System.out.println("\n");

	}

	public void inventoryDisplay(Hero hero) {
//		Set inventoryKeySet = hero.getInventory().keySet();
		System.out.println("------------------------------------------------------------------- ");
		System.out.println("\nInventory: ");

		List<String> inventoryList = hero.getInventory();
		for (String item : inventoryList) {
			System.out.println("-" + item);
		}
		if (hero.getTreasury() > 1) {
			System.out.println("-" + hero.getTreasury() + " silver pieces");
		}
		else {
			System.out.println("-" + hero.getTreasury() + " silver piece");
		}
		

		System.out.println("\n(Press enter to continue game)");
		System.out.println("------------------------------------------------------------------- ");
		input.nextLine();
	}

	public void printTitleScreen(Scanner input) throws InterruptedException {
		scrollingText = "       Welcome to...                                      ";
		scrollTheTextSlowSpeed(scrollingText);
		
		String cauven = "   _____              _    _  __      __  ______   _   _ \r\n" + 
				"  / ____|     /\\     | |  | | \\ \\    / / |  ____| | \\ | |\r\n" + 
				" | |         /  \\    | |  | |  \\ \\  / /  | |__    |  \\| |\r\n" + 
				" | |        / /\\ \\   | |  | |   \\ \\/ /   |  __|   | . ` |\r\n" + 
				" | |____   / ____ \\  | |__| |    \\  /    | |____  | |\\  |\r\n" + 
				"  \\_____| /_/    \\_\\  \\____/      \\/     |______| |_| \\_|\r\n" + 
				"                                                         ";
		scrollTheTextMediumSpeed(cauven);
		System.out.println("\n\n\n");
		String scrollText = "\tA Zoudar Story ";
		scrollTheTextSlowSpeed(scrollText);
		scrollingText = "\n\n\n";
		scrollTheTextSlowSpeed(scrollingText);
		
		System.out.println("\tPress enter to continue");
		input.nextLine();
		controls();
		System.out.println("\tPress enter to continue");
		input.nextLine();
		clrscr();
	}

	public void printChapterOne() throws InterruptedException {
		String scrollText = "   _____ _                 _               ____             \r\n"
				+ "  / ____| |               | |             / __ \\            \r\n"
				+ " | |    | |__   __ _ _ __ | |_ ___ _ __  | |  | |_ __   ___ \r\n"
				+ " | |    | '_ \\ / _` | '_ \\| __/ _ \\ '__| | |  | | '_ \\ / _ \\\r\n"
				+ " | |____| | | | (_| | |_) | ||  __/ |    | |__| | | | |  __/\r\n"
				+ "  \\_____|_| |_|\\__,_| .__/ \\__\\___|_|     \\____/|_| |_|\\___|\r\n"
				+ "                    | |                                     \r\n"
				+ "                    |_|                                     ";
		scrollTheTextFastSpeed(scrollText);
		System.out.println("\n\n\n\n");
	}

	public void printChapterTwo() throws InterruptedException {
		String scrollText = "   _____ _                 _              _______            \r\n"
				+ "  / ____| |               | |            |__   __|           \r\n"
				+ " | |    | |__   __ _ _ __ | |_ ___ _ __     | __      _____  \r\n"
				+ " | |    | '_ \\ / _` | '_ \\| __/ _ | '__|    | \\ \\ /\\ / / _ \\ \r\n"
				+ " | |____| | | | (_| | |_) | ||  __| |       | |\\ V  V | (_) |\r\n"
				+ "  \\_____|_| |_|\\__,_| .__/ \\__\\___|_|       |_| \\_/\\_/ \\___/ \r\n"
				+ "                    | |                                      \r\n"
				+ "                    |_|                                      ";
		scrollTheTextFastSpeed(scrollText);
		System.out.println("\n\n\n\n");
	}

	
	public void backStory() throws InterruptedException {
		String scrollingText = "Every celestial body has its first order threats. On this moon, Cauven, it's the birds.\n"
				+ "Or maybe the trees. A dozen species of bird vie for the top of the food chain. A hundred species of\n"
				+ "tree have developed poisonous defenses. The flightless creatures here are small, quick, and burrowing.\n"
				+ "Small and quick to escape the birds, burrowing to avoid the trees. The birds survive mainly off each other.\n"
				+ "All you know is that you need to watch out for things quick and things still.\n\n"
				+ "Because nature impedes technology here, today's mission calls for primitive skills. No one wanted it.\n"
				+ "But you and Pete have wanted to see Cauven for months. Ever since Doghoof reappeared here and resuscitated\n"
				+ "the grolan trade, and blindness in the solar system had increased threefold.\n ";
		scrollTheTextFastSpeed(scrollingText);
		
		System.out.println("\tPress enter");
		input.nextLine();
		
		scrollingText = "Most analog missions come with directions written on paper, landmark navigation. But this one calls for\n"
				+ "collection. Tod birds are the size of a knuckle. Their miniscule feathers are coated with an oil that is\n"
				+ "rumored to have some success in restoring partial vision after grolan attacks. Tods nest in the overstory of\n"
				+ "grolan forests. Grolans are towering, spindly giants that sway in wind like kelp in water. It is the pulp\n"
				+ "of the grolan tree that Doghoof has exploited and weaponized to sell to any buyers with a disdain for soldiers\n"
				+ "of the Rame Gallery--like you and Pete.\n ";
		scrollTheTextFastSpeed(scrollingText);
		
		System.out.println("\tPress enter");
		input.nextLine();
		
		scrollingText = "Thus, the collection is twofold: collect as many tod birds as possible, and collect any intel on\n"
				+ "Doghoof's activity in these forests.\n ";
		scrollTheTextFastSpeed(scrollingText);
		
		System.out.println("\tPress enter to begin");
		input.nextLine();
		clrscr();
	}
	
	public void gameOver(Stats stats) throws InterruptedException {
		// show stats and end game
		scrollingText = "You have perished on your journey to find your starship... ";
		scrollTheTextSlowSpeed(scrollingText);
		System.out.println("Here are your stats: ");

		displayStats(stats);
	}

	public void quitSequence(Scanner input, Stats stats) {
		while (true) {
			System.out.println("------------------------------------------------------------------- ");
			System.out.println("Are you sure you want to quit? (y/n)");
			String quitInput = input.nextLine().toUpperCase();
			if (quitInput.equals("Y")) {
				System.out.println("Here are your stats: ");
				displayStats(stats);
			} else if (quitInput.equals("N")) {
				System.out.println("Get back in there, champ.");
				System.out.println("------------------------------------------------------------------- ");
				break;

			} else {
				System.out.println("Invalid command");
			}
		}
	}

	public void displayStats(Stats stats) {
		// Create an Empty List of Student, And add few objects to the List

		// Print the list objects in tabular format.
		System.out.println("----------------------------------------------------------------- ");
		System.out.printf("|%-15s %11s %-30s %s", " ENEMIES VANQUISHED", "|", "PLACES DISCOVERED", "|");
		System.out.println();
		System.out.println("----------------------------------------------------------------- ");

		System.out.format("|%-15s %15s %-30s %s", " " + stats.getenemiesKilled(), "|",
				stats.getplacesDiscovered() + " (out of x)", "|");
		System.out.println();

		System.out.println("----------------------------------------------------------------- ");
		System.out.println("\n\n\nThanks for playing!");
		input.close();
		System.exit(0);
	}

	public void run() throws InterruptedException, IOException {

		Key key = new Key();
		Hero hero = new Hero(100, 50, "Pete Tren");
		GameMap newGame = new GameMap();

		Stats stats = new Stats();

		music();
		printTitleScreen(input);
		backStory();
		printChapterOne();

		newGame.moveAround(input, hero, stats, key);
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		AdventureMainCLI cli = new AdventureMainCLI();
		cli.run();

	}

}