package game.risk.model;

import java.util.ArrayList;

/**
 * Model for Player.
 * Stores the name of the player, number of total armies player posses,
 * as well as list of occupied countries and continents.
 * @author Tejash
 * @version 1.0
 */
public class Player {

	private final String name;
	private int armies;
	private final int playerNumber;
	private ArrayList<Country> occupiedCountries;
	private ArrayList<Continent> occupiedContinents;

	/**
	 * Constructor for Player, takes in the name of the player and the number of armies of the player.
	 * @param player - name of the player
	 * @param armies - number of total armies player has
	 * @param playerNumber - unique player number (1, 2, etc.)
	 */
	public Player(String player, int armies, int playerNumber) {

		this.name = player;
		this.armies = armies;
		this.playerNumber = playerNumber;
		this.occupiedContinents = new ArrayList<>();
		this.occupiedCountries = new ArrayList<>();

	}

	/**
	 * Updates the number of armies by adding the input "updator" to the current number of armies.
	 * @param updator - number of armies to be added
	 */
	public void armiesUpdator(int updator) {
		this.armies += updator;
	}

	/**
	 * When a country is conquered, it will be added the list of occupied countries.
	 * @param country - the conquered country
	 */
	public void conqueredCountry(Country country) {
		this.occupiedCountries.add(country);
	}

	/**
	 * When a country is lost, it will be removed from the list of occupied countries.
	 * @param country - the lost country
	 */
	public void lostCountry(Country country) {
		this.occupiedCountries.remove(country);
	}

	/**
	 * When a continent is conquered, it will be added the list of occupied continents.
	 * @param continent - the conquered continent
	 */
	public void conqueredContinent(Continent continent) {
		this.occupiedContinents.add(continent);
	}

	/**
	 * Gets the name of the player.
	 * @return name - name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the number of armies
	 * @return armies - the number of armies player has
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * Get the list of occupied countries.
	 * @return occupiedCountries - list of countries
	 */
	public ArrayList<Country> getOccupiedCountries() {
		return occupiedCountries;
	}

	/**
	 * Gets the list of occupied continents.
	 * @return occupiedContinents - list of occupied continents
	 */
	public ArrayList<Continent> getOccupiedContinents() {
		return occupiedContinents;
	}

	/**
	 * Gets playerNumber/
	 * @return playerNumber - unique number associated to the player
	 */
	public int getPlayerNumber() 
	{
		return playerNumber;
	}
	
}
