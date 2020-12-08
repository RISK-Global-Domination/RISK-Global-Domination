package game.risk.model;

import game.risk.controller.DataFetcher;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Model for the RISK game
 * It stores the list of countries, and the list of continents
 *
 * @author Tejash, Lynn
 * @version 1.0
 */
public class GameModel {

    private final DataFetcher fetcher;
    private ArrayList<Country> countries;
    private ArrayList<Continent> continents;

    /**
     * Constructor
     */
    public GameModel() {

        fetcher = new DataFetcher();
        this.continents = fetcher.getContinents();
        this.countries = fetcher.getCountries();

    }

    /**
     * Gets the list of countries.
     *
     * @return countries - list of all the countries in the game
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /**
     * Gets the list of continents.
     *
     * @return continents - list of all continents in the game
     */
    public ArrayList<Continent> getContinents() {
        return continents;
    }

    public DataFetcher getFetcher() {
        return fetcher;
    }

    /**
     * Shuffles the country list.
     */
    public void shuffleCountries() {
        Collections.shuffle(countries);
    }

    public void customCountries(ArrayList<Country> cntr) {
        this.countries = cntr;
    }

    public void customContinents(ArrayList<Continent> cntnt) {
        this.continents = cntnt;
    }

}
