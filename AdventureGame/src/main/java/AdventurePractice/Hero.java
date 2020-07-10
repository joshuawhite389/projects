package AdventurePractice;

import java.util.ArrayList;
import java.util.List;


public class Hero {
	private int maxHealth;
	private int attackDamage;
	private String name;
	private int numOfHealthPotions;
	private int healthPotionHealAmount;
	private int bravery; // number of time you can run away
	private int healthPotionDropChance; // percentage
	private List<String> inventory = new ArrayList<>();
	private int treasury;

	public Hero(int maxHealth, int attackDamage, String name) {
		this.maxHealth = maxHealth;
		this.attackDamage = attackDamage;
		this.name = name;
		this.numOfHealthPotions = 3;
		this.healthPotionHealAmount = 25;
		this.bravery = 3;
		this.healthPotionDropChance = 50;
		this.treasury = 1;
	}

	public Hero() {

	}

	public int getHealthPotionDropChance() {
		return healthPotionDropChance;
	}

	public void setHealthPotionDropChance(int healthPotionDropChance) {
		this.healthPotionDropChance = healthPotionDropChance;
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

	public int getNumOfHealthPotions() {
		return numOfHealthPotions;
	}

	public void setNumOfHealthPotions(int numOfHealthPotions) {
		this.numOfHealthPotions = numOfHealthPotions;
	}

	public int getHealthPotionHealAmount() {
		return healthPotionHealAmount;
	}

	public void setHealthPotionHealAmount(int healthPotionHealAmount) {
		this.healthPotionHealAmount = healthPotionHealAmount;
	}

	public int getBravery() {
		return bravery;
	}

	public void setBravery(int bravery) {
		this.bravery = bravery;
	}

	public List<String> getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory.add(inventory);
	}

	public int getTreasury() {
		return treasury;
	}

	public void setTreasury(int treasury) {
		this.treasury = treasury;
	}

}
