package AdventurePractice;

import java.util.ArrayList;
import java.util.List;

public class Stats {
	private int enemiesKilled;
	private List<String> places = new ArrayList<String>();
	private int placesDiscovered;

	// Default Constructor
	public Stats() {

	}

	// Parameterized Constructor
	public Stats(int enemiesKilled, int placesDiscovered) {
		this.enemiesKilled = enemiesKilled;

		this.placesDiscovered = placesDiscovered;

	}

	public int getenemiesKilled() {
		return enemiesKilled;
	}

	public void setenemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}

	public int getplacesDiscovered() {
		int counter = 0;
		for(String place : this.places) {
			counter++;
		}
		placesDiscovered = counter;
		return placesDiscovered;
	}

	public void setplacesDiscovered(int places) {
		this.placesDiscovered = places;
	}

	public List<String> getPlaces() {
		return places;
	}

	public void setPlaces(String place) {
		this.places.add(place);
	}

}
