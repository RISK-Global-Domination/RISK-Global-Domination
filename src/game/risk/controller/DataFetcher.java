package game.risk.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import game.risk.model.Continent;
import game.risk.model.Country;

/**
 * DataFetcher - Fetches the countries and continent names from the text files
 * @author Tejash
 * @version 1.0
 */
public class DataFetcher {

    private ArrayList<Continent> continents;
    private ArrayList<Country> countries;

    /**
     * Constructor for DataFetcher.
     */
    public DataFetcher() {

        this.continents = new ArrayList<>();
        this.countries = new ArrayList<>();
        this.readCountries();
        this.readContinents();

    }

    /**
     * Gets a list of continents.
     * @return continents - The list of continents
     */
    public ArrayList<Continent> getContinents() {
        return continents;
    }

    /**
     * Gets the list of countries.
     * @return countries - The list of countries
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /**
     * Reads the countries from the text file counties.txt
     * Reads the joining from text file joining.txt and
     * sets the joining (adjacent countries) for each country
     */
    private void readCountries() {

        ArrayList<String> data = readFile("mapdata/countries.txt");
        for (String each : data) {
            countries.add(new Country(each));
        }

        data = readFile("mapdata/joining.txt");

        // Add neighbour countries as per the joining file
        for (String each : data) {
            String[] tokens = each.split(",");
            String country = tokens[0].trim();
            ArrayList<String> names = new ArrayList<>();

            for (int i = 1; i < tokens.length; i++) {
                names.add(tokens[i].trim());
            }

            for (int i = 0; i < countries.size(); i++) {
                if (country.equals(countries.get(i).getName())) {
                    countries.get(i).setJoining(this.fetchCountriesFromNames(names));
                    break;
                }
            }
        }
    }

    /**
     * Converts the arraylist of string of country names to arraylist of country object
     * @param names - list of strings of country names
     * @return list - list of Countries
     */
    private ArrayList<Country> fetchCountriesFromNames(ArrayList<String> names) {
        ArrayList<Country> list = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            for (int j = 0; j < countries.size(); j++) {
                if (names.get(i).equals(countries.get(j).getName())) {
                    list.add(countries.get(j));
                    break;
                }
            }
        }
        return list;
    }

    /**
     * Reads the continents from the text file continents.txt
     */
    private void readContinents() {

        ArrayList<String> data = readFile("mapdata/continents.txt");
        for (String each : data) {
            String[] tokens = each.split(",");
            String continentName = tokens[0];
            ArrayList<String> names = new ArrayList<>();
            for (int i = 1; i < tokens.length; i++) {
                names.add(tokens[i].trim());
            }
            continents.add(new Continent(continentName,
                    this.fetchCountriesFromNames(names)));
        }
    }

    /**
     * Reads the file and stores it in a arraylist of String
     *
     * @param file - the path of the file to be read
     * @return list - ArrayList of Strings from the file
     */
    private ArrayList<String> readFile(String file) {

        ArrayList<String> list = new ArrayList<>();
        Scanner scan;
        try {
            scan = new Scanner(new File(file));
            while (scan.hasNextLine()) {
                list.add(scan.nextLine());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("MapData file not found!!");
        }
        return list;

    }
}
