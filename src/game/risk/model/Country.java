package game.risk.model;

import java.util.ArrayList;

/**
 * Model for Country.
 * Stores the name of the Countries, joining (its adjacent countries),
 * occupant (player occupying the country), and the number of armies held.
 * @author Tejash, Lynn
 * @version 1.0
 */
public class Country implements Comparable<Country> {

    private String name;
    private ArrayList<Country> joining;
    private int armies;
    private Player occupant;

	/**
	 * Constructor for country
	 * @param name - name of the country
	 * @param joining - list of connected countries
	 */
    public Country(String name, ArrayList<Country> joining) {

        this.name = name;
        this.joining = joining;

    }

	/**
	 * Constructor for country. Takes a name.
	 * @param name - name of the country
	 */
    public Country(String name) {
        this.name = name;
        this.joining = new ArrayList<>();
    }

	/**
	 * Updates the number of armies by the number of armies that should be added
     * and adding it to the armies already existing.
	 * @param updator - number of armies added
	 */
    public void updateArmies(int updator) {
        this.armies += updator;
    }

	/**
	 * Gets the name of the country
	 * @return name - name of the country
	 */
    public String getName() {
        return name;
    }

	/**
	 * Gets the list of countries joining
	 * @return joining - list of connected countries
	 */
    public ArrayList<Country> getJoining() {
        return joining;
    }

	/**
	 * Gets the number of armies
	 * @return armies - number of armies
	 */
    public int getArmies() {
        return armies;
    }

	/**
	 * Gets the occupant player
	 * @return occupant - the player occupying the country
	 */
    public Player getOccupant() {
        return occupant;
    }

	/**
	 * Sets the name of the country
	 * @param name - name of the country
	 */
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * Sets the joining of list of countries
	 * @param joining - list of adjacent countries
	 */
    public void setJoining(ArrayList<Country> joining) {
        this.joining = joining;
    }

    /**
     * Setter for armies
     * @param armies - the number of armies country has
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }

    /**
     * Setter for occupant
     * @param occupant - the occupant of the country
     */
    public void setOccupant(Player occupant) {
        this.occupant = occupant;
    }

    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.getName());
    }

}
