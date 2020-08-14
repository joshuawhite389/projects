package AdventurePractice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Enemy {
	Random rand = new Random();
	private int maxHealth;
	private int attackDamage;
	private String name;
	private Scanner input = new Scanner(System.in);

	public Enemy(int maxHealth, int attackDamage, String name) {
		this.maxHealth = rand.nextInt(maxHealth);
		this.attackDamage = attackDamage;
		this.name = name;
	}

	public Enemy() {

	}

	public int getHealth() {
		return maxHealth;
	}

	public void setHealth(int health) {
		this.maxHealth = health;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Enemy> listOfEnemies() {
		Enemy snorg = new Enemy(75, 25, "Snorg");
		Enemy quonel = new Enemy(75, 25, "Quonel");
		Enemy shearkazyx = new Enemy(75, 25, "Shearkazyx");
		Enemy trenlur = new Enemy(75, 25, "Trenlur");
		List<Enemy> listOfEnemies = new ArrayList<Enemy>();
		listOfEnemies.add(snorg);
		listOfEnemies.add(quonel);
		listOfEnemies.add(shearkazyx);
		listOfEnemies.add(trenlur);
		return listOfEnemies;
	}

	public Enemy getAnEnemy() {
		Random rand = new Random();
		int randInt = rand.nextInt(listOfEnemies().size());
		Enemy enemy = listOfEnemies().get(randInt);
		return enemy;
	}

	public void enemyAppears(Hero hero, Stats stats) throws InterruptedException {
		AdventureMainCLI cli = new AdventureMainCLI();
		Enemy enemy = getAnEnemy();
		int enemyHealth = enemy.getHealth() + 1;
		String enemyName = enemy.getName();

		String userInput = "";

		boolean running = true;
		mainLoop: while (running) {
			String scrollingText = "------------------------------------------------------------------- ";
			cli.scrollTheTextMediumSpeed(scrollingText);
			System.out.println("\n\t# " + enemyName + " has appeared #\n");
			while (enemyHealth > 0) {
				System.out.println("\tYour HP: " + hero.getHealth());
				System.out.println("\t" + enemyName + "'s HP: " + enemyHealth);
				System.out.println("\n\tActions:");
				System.out.println("\t1. Attack");
				System.out.println("\t2. Drink health potion");
				System.out.println("\t3. Run");

				userInput = input.nextLine();
				if (userInput.equals("1")) {
					int damageDealt = rand.nextInt(hero.getAttackDamage() + 1);
					int damageTaken = rand.nextInt(enemy.getAttackDamage() + 1);

					enemyHealth -= damageDealt;
					if (enemyHealth >= 1) {
						hero.setHealth(hero.getHealth() - damageTaken);
						scrollingText = "\t> Your blaster hits the " + enemyName + " for " + damageDealt + " damage. ";
						cli.scrollTheTextMediumSpeed(scrollingText);
						scrollingText = "\t> The " + enemyName + " attacks you for " + damageTaken + " damage. ";
						cli.scrollTheTextMediumSpeed(scrollingText);
						scrollingText = "*********************************************** ";
						cli.scrollTheTextFastSpeed(scrollingText);
					} else {
						scrollingText = "\t> Your blaster hits the " + enemyName + " for " + damageDealt + " damage. ";
						cli.scrollTheTextMediumSpeed(scrollingText);
						scrollingText = "*********************************************** ";
						cli.scrollTheTextFastSpeed(scrollingText);
					}

					if (hero.getHealth() < 1) {
						cli.gameOver(stats);
					}
				} else if (userInput.equals("2")) {
					if (hero.getNumOfHealthPotions() > 0 && hero.getHealth() < 100) {

						// ******* make this heal up to 100 only **************

						if (hero.getHealth() >= 75) {

							hero.setNumOfHealthPotions(hero.getNumOfHealthPotions() - 1);
							scrollingText = "\t> Good stuff, you heal yourself for " + (100 - hero.getHealth())
									+ " HP. ";
							cli.scrollTheTextMediumSpeed(scrollingText);
							hero.setHealth(100);
							scrollingText = "\n\t> You now have " + hero.getHealth() + " HP. " + "\n\t> You have "
									+ hero.getNumOfHealthPotions() + " health potions left.\n ";
							cli.scrollTheTextMediumSpeed(scrollingText);
						} else {
							hero.setHealth(hero.getHealth() + hero.getHealthPotionHealAmount());
							hero.setNumOfHealthPotions(hero.getNumOfHealthPotions() - 1);
							scrollingText = "\t> Good stuff, you heal yourself for " + hero.getHealthPotionHealAmount()
									+ " HP." + "\n\t> You now have " + hero.getHealth() + " HP. " + "\n\t> You have "
									+ hero.getNumOfHealthPotions() + " health potions left.\n ";
							cli.scrollTheTextMediumSpeed(scrollingText);
						}

					} else if (hero.getNumOfHealthPotions() == 0) {
						System.out.println(
								"\t> You have no health potions left! Fight this guy and see if he drops one.\n ");
					} else if (hero.getHealth() == 100) {
						System.out.println("\t>Your health is already full.\n ");
						System.out.println("*********************************************** ");
					}

				} else if (userInput.equals("3")) {
					if (hero.getBravery() > 0) {
						scrollingText = "\tYou run away from the " + enemyName + "! ";
						cli.scrollTheTextMediumSpeed(scrollingText);
						hero.setBravery(hero.getBravery() - 1);
						System.out.println("\t(Your bravery decreased)");
						scrollingText = "------------------------------------------------------------------- ";
						cli.scrollTheTextMediumSpeed(scrollingText);
						break mainLoop;
					} else {
						scrollingText = "You've run away too many times, you must stay and fight! ";
						cli.scrollTheTextMediumSpeed(scrollingText);

					}

				} else {
					System.out.println("\n\t*Invalid command!*\n ");

				}
			}

			scrollingText = "------------------------------------------------------------------- ";
			cli.scrollTheTextFastSpeed(scrollingText);
			scrollingText = " # " + enemyName + " was defeated! # ";
			cli.scrollTheTextMediumSpeed(scrollingText);
			scrollingText = " # You have " + hero.getHealth() + " HP left. # ";
			cli.scrollTheTextMediumSpeed(scrollingText);

			if (rand.nextInt(100) < hero.getHealthPotionDropChance()) {
				if (hero.getNumOfHealthPotions() < 3) {
					hero.setNumOfHealthPotions((hero.getNumOfHealthPotions() + 1));
					scrollingText = " # The " + enemyName + " dropped a health potion! # ";
					cli.scrollTheTextMediumSpeed(scrollingText);
					scrollingText = " # You now have " + hero.getNumOfHealthPotions() + " health potions(s). # ";
					cli.scrollTheTextMediumSpeed(scrollingText);
				} else {
					scrollingText = " # The " + enemyName
							+ " dropped a health potion, but you can't carry any more. # ";
					cli.scrollTheTextMediumSpeed(scrollingText);
				}

			}
			if (hero.getBravery() < 3) {
				hero.setBravery(hero.getBravery() + 1);
				scrollingText = " # Your bravery increased! # ";
				cli.scrollTheTextMediumSpeed(scrollingText);
			}
			// random tod bird drop
			int todBirdDrop = rand.nextInt(3);
			hero.setTreasury(hero.getTreasury() + todBirdDrop);
			if (todBirdDrop > 1) {
				scrollingText = " #  As you inspect the " + enemyName + ", you find  " + todBirdDrop
						+ " tod birds, passengers of the larger bird, have fallen to the ground. You put them in your pack. # ";
				cli.scrollTheTextFastSpeed(scrollingText);
			} else if (todBirdDrop > 0) {
				scrollingText = " # As you inspect the " + enemyName + ", you find " + todBirdDrop + " tod bird, a passenger of the larger bird"
						+ ", has fallen to the ground. You put it in your pack. # ";
				cli.scrollTheTextFastSpeed(scrollingText);
			}
			scrollingText = "------------------------------------------------------------------- ";
			cli.scrollTheTextFastSpeed(scrollingText);

			// add enemies killed to stats
			stats.setenemiesKilled(stats.getenemiesKilled() + 1);

			break mainLoop;
		}
	}

	public void randomEnemyGenerator(Hero hero, Stats stat) throws InterruptedException {
		Random rand = new Random();
		if (rand.nextInt(100) < 20) {
			enemyAppears(hero, stat);
		}
	}

}
