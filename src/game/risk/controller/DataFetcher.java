package game.risk.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import game.risk.model.Continent;
import game.risk.model.Country;

/**
 *FETCHES THE COUNTRIES AND CONTINENTS NAMES FROM THE TEXT FILES.
 */
public class DataFetcher {

	private ArrayList<Continent> continents;
	private ArrayList<Country> countries;
	
	public DataFetcher() {
		
		this.continents = new ArrayList<>();
		this.countries = new ArrayList<>();
		this.readCountries();
		this.readContinents();
		
	}
	
	public ArrayList<Continent> getContinents() {

		return continents;
	
	}

	public ArrayList<Country> getCountries() {

		return countries;
	
	}

	private void readCountries() {

		ArrayList<String> data = readFile("mapdata/countries.txt");
		for(String each: data) {
			countries.add(new Country(each));
		}
		// Adjacent..
		data = readFile("mapdata/joining.txt");
		for(String each: data) {
			
			String[] tokens = each.split(",");
			String country = tokens[0].trim();
			ArrayList<String> names = new ArrayList<>();
			for(int i = 1; i < tokens.length; i++) {
				names.add(tokens[i].trim());
			}
			for(int i = 0; i < countries.size(); i++) {
				if(country.equals(countries.get(i).getName())) {
					countries.get(i).setJoining(this.fetchCountriesFromNames(names));
					break;
				}
			}
			
		}
		
	}
	
	private ArrayList<Country> fetchCountriesFromNames(ArrayList<String> names){
		
		ArrayList<Country> list = new ArrayList<>();
		for(int i = 0; i < names.size(); i++) {
			for(int j = 0; j < countries.size(); j++) {
				if(names.get(i).equals(countries.get(j).getName())) {
					list.add(countries.get(j));
					break;
				}
			}
		}
		return list;
		
	}
	
	private void readContinents() {

		ArrayList<String> data = readFile("mapdata/continents.txt");
		for(String each: data) {
			
			String[] tokens = each.split(",");
			String continentName = tokens[0];
			ArrayList<String> names = new ArrayList<>();
			for(int i = 1; i < tokens.length; i++) {
				names.add(tokens[i].trim());
			}
			continents.add(new Continent(continentName,  
					this.fetchCountriesFromNames(names)));
			
		}
		
	}
	
	// Reading file..
	private ArrayList<String> readFile(String file) {
		
		ArrayList<String> list = new ArrayList<>();
		Scanner scan;
		try {
			scan = new Scanner(new File(file));
			while(scan.hasNextLine()) {
				list.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("MapData file not found!!");
		}
		return list;
		
	}
	
}
