package game.risk.model;

import java.util.ArrayList;

/**
 * GETS AND SETS THE CONTINENTS AND COUNTRIES FOR EACH CONTINENT.
 */
public class Continent {

    private String name;
    private ArrayList<Country> countries;

    /**
     * Constructor
     * @param name
     * @param countries
     */
    public Continent(String name, ArrayList<Country> countries) {

        this.name = name;
        this.countries = countries;

    }

    /**
     * Gets the name of the continent.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get an array list of the countries for the continent.
     * @return
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a list of names of countries fo the continent.
     * @param countries
     */
    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

}
