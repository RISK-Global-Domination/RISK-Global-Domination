package game.risk.model;

import java.util.ArrayList;

/**
 * Model for Continents.
 * Continent contains the list of countries it has.
 * @author Tejash
 * @version 1.0
 */
public class Continent {

    private String name;
    private ArrayList<Country> countries;

    /**
     * Constructor
     * @param name - name of the continent
     * @param countries - list of countries in the continent
     */
    public Continent(String name, ArrayList<Country> countries) {

        this.name = name;
        this.countries = countries;

    }

    /**
     * Gets the name of the continent
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get an array list of the countries for the continent
     * @return name
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a list of names of countries fo the continent
     * @param countries
     */
    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

}
