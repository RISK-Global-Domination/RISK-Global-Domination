package game.risk.controller;

import java.util.ArrayList;
import java.util.Collections;

import game.risk.model.Continent;
import game.risk.model.Country;

/**
 *GAME'S MODEL
 */
public class GameModel {

	private ArrayList<Country> countries;
	private ArrayList<Continent> continents;
	private DataFetcher fetcher;
	
	public GameModel() {
		
		fetcher = new DataFetcher();
		this.continents = fetcher.getContinents();
		this.countries = fetcher.getCountries();
		
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public ArrayList<Continent> getContinents() {
		return continents;
	}

	public DataFetcher getFetcher() {
		return fetcher;
	}
	
	public void shuffleCountries() {
		Collections.shuffle(countries);
	}
	
}
