package game.risk.controller;

import java.util.ArrayList;
import java.util.Collections;

import game.risk.model.Continent;
import game.risk.model.Country;

/**
 * GAME'S MODEL
 */
public class GameModel {

    private ArrayList<Country> countries;
    private ArrayList<Continent> continents;
    private DataFetcher fetcher;

    /**
     * Constructor of GameModel.
     */
    public GameModel() {

        fetcher = new DataFetcher();
        this.continents = fetcher.getContinents();
        this.countries = fetcher.getCountries();

    }

    /**
     * Gets the list of countries.
     * @return
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /**
     * Gets the list of continents.
     * @return
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

}
