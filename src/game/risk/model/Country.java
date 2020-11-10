package game.risk.model;

import java.util.ArrayList;

/**
 *GETS AND SETS COUNTRIES, JOINING AND OCCUPANT. SETS, GETS AND UPDATES ARMIES.
 */
public class Country implements Comparable<Country> {

    // Attributes..
    private String name;
    private ArrayList<Country> joining;
    private int armies;
    private Player occupant;

	/**
	 * Constructor for country.
	 * Takes a name and a joining.
	 * @param name
	 * @param joining
	 */
    public Country(String name, ArrayList<Country> joining) {

        this.name = name;
        this.joining = joining;

    }

	/**
	 * Constructor for country.
	 * Takes a name.
	 * @param name
	 */
    public Country(String name) {

        this.name = name;
        this.joining = new ArrayList<>();

    }

	/**
	 * Updates the number of armies by the number of armies that should be added and adding it to the armies already existing.
	 * @param updator
	 */
    public void updateArmies(int updator) {
        this.armies += updator;
    }

	/**
	 * Gets the name of the country.
	 * @return
	 */
    public String getName() {
        return name;
    }

	/**
	 * Gets the list of countries joining.
	 * @return
	 */
    public ArrayList<Country> getJoining() {
        return joining;
    }

	/**
	 * Gets the number of armies.
	 * @return
	 */
    public int getArmies() {
        return armies;
    }

	/**
	 * Gets the occupant player.
	 * @return
	 */
    public Player getOccupant() {
        return occupant;
    }

	/**
	 * Sets the name of the country.
	 * @param name
	 */
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * Sets the joining of list of countries.
	 * @param joining
	 */
    public void setJoining(ArrayList<Country> joining) {
        this.joining = joining;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }

    public void setOccupant(Player occupant) {
        this.occupant = occupant;
    }

    @Override
    public int compareTo(Country o) {

        return this.name.compareTo(o.getName());

    }

}
