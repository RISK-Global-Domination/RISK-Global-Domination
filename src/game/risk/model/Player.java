package game.risk.model;

import java.util.ArrayList;

public class Player {

	private String name;
	private int armies;
	private ArrayList<Country> occupiedCountries;
	private ArrayList<Continent> occupiedContinents;
	
	public Player(String player, int armies) {
		
		this.name = player;
		this.armies = armies;
		this.occupiedContinents = new ArrayList<>();
		this.occupiedCountries = new ArrayList<>();
		
	}

	public void armiesUpdator(int updator) {
		this.armies += updator;
	}
	
	public void conqueredCountry(Country country) {
		this.occupiedCountries.add(country);
	}
	
	public void lostCountry(Country country) {
		this.occupiedCountries.remove(country);
	}
	
	public void conqueredContinent(Continent continent) {
		this.occupiedContinents.add(continent);
	}
	
	public String getName() {
		return name;
	}

	public int getArmies() {
		return armies;
	}

	public ArrayList<Country> getOccupiedCountries() {
		return occupiedCountries;
	}

	public ArrayList<Continent> getOccupiedContinents() {
		return occupiedContinents;
	}
	
}
