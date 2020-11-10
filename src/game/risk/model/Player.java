package game.risk.model;

import java.util.ArrayList;

/**
 *KEEPS RECORD OF EACH PLAYER’S OCCUPANCIES.
 *UPDATES THE NUMBER OF ARMIES.
 *ADDS COUNTRY IF IT IS CONQUERED, REMOVES COUNTRY IF IS OCCUPIED.
 *ADDS CONTINENT IF IT IS OCCUPIED.
 *RER
 */
public class Player {

	private String name;
	private int armies;
	private ArrayList<Country> occupiedCountries;
	private ArrayList<Continent> occupiedContinents;

	/**
	 * Constructor for Player, takes in the name of the player and the number of armies of the player.
	 * @param player
	 * @param armies
	 */
	public Player(String player, int armies) {
		
		this.name = player;
		this.armies = armies;
		this.occupiedContinents = new ArrayList<>();
		this.occupiedCountries = new ArrayList<>();
		
	}

	/**
	 * Updates the number of armies by adding the input "updator" to the current number of armies.
	 * @param updator
	 */
	public void armiesUpdator(int updator) {
		this.armies += updator;
	}

	/**
	 * When a country is conquered, it will be added the list of occupied countries.
	 * @param country
	 */
	public void conqueredCountry(Country country) {
		this.occupiedCountries.add(country);
	}

	/**
	 * When a country is lost, it will be removed from the list of occupied countries.
	 * @param country
	 */
	public void lostCountry(Country country) {
		this.occupiedCountries.remove(country);
	}

	/**
	 * When a continent is conquered, it will be added the list of occupied continents.
	 * @param continent
	 */
	public void conqueredContinent(Continent continent) {
		this.occupiedContinents.add(continent);
	}

	/**
	 * Gets the name of the player.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the number of armies
	 * @return
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * Get the list of occupied countries.
	 * @return
	 */
	public ArrayList<Country> getOccupiedCountries() {
		return occupiedCountries;
	}

	/**
	 * Gets the list of occupied continents.
	 * @return
	 */
	public ArrayList<Continent> getOccupiedContinents() {
		return occupiedContinents;
	}
	
}
