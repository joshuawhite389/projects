package AdventurePractice;

import java.util.Scanner;

public class Key {

	private int keyId;
	private String keyName;
	private boolean keyFound;
	private Scanner input = new Scanner(System.in);

	public Key() {

	}

	public Key(int keyId, String keyName) {
		this.keyId = keyId;
		this.keyName = keyName;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public boolean isKeyFound() {
		return keyFound;
	}

	public void setKeyFound(boolean keyFound) {
		this.keyFound = keyFound;
	}

	public void keyInMap(Hero hero) {

		String userInput = "";
		if (keyFound == false) {

			System.out.println("------------------------------------------------------------------- ");
			System.out.println("You see an old Trendan military foot locker surrounded by brush and dead leaves.\n\n"
					+ "You walk over to it and try to open it.\n\n" + "It doesn't budge.\n\n");
			while (keyFound == false) {
				System.out.println("You want to open it, but how?\n");
				System.out.println("1. Find a rock and smash the latch");
				System.out.println("2. Use your knife to pry it open");
				System.out.println("3. Use a health potion to lubricate the hinges");
				userInput = input.nextLine();
				if (userInput.equals("1")) {
					System.out.println("\n\t>The rock breaks apart in your hand, the latch remains intact.\n");
				}
				if (userInput.equals("2")) {
					System.out.println("\n\t>You chip your knife trying to open the locker, but it remains shut.\n");
				}
				if (userInput.equals("3")) {

					if (hero.getNumOfHealthPotions() > 0) {
						hero.setNumOfHealthPotions(hero.getNumOfHealthPotions() - 1);
						hero.setInventory("An old rusty key");
						System.out.println("\n\t>You got it open! There's an old rusty key inside.  You take it.\n");
						System.out.println("\n\t>After using a health potion to open the locker, you have "
								+ hero.getNumOfHealthPotions() + " left.");
						System.out.println("------------------------------------------------------------------- ");
						keyFound = true;
					} else {
						System.out.println(
								"\n\t>You don't have enough health potions. Defeat some enemies and try to find some.");
						break;
					}

				}
			}

		} else {
			System.out.println("\nYou see that old foot locker that you got the key out of.");
		}
	}

}
