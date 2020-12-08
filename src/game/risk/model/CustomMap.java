package game.risk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
       Class needed for custom Maps
 */
public class CustomMap {

    private List<Country> countries;
    private List<Continent> continents;

    public CustomMap() {
    }

    public List<Continent> getContinents() {
        if (continents == null){
            continents = new ArrayList<>();
        }
        return continents;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    public List<Country> getCountries() {
        if (countries == null) {
            countries = new ArrayList<>();
        }
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public static class Continent {
        private String name;
        private List<String> countries;

        public Continent() {
        }

        public Continent(String name, String... countries) {
            this.name = name;
            if (countries != null) {
                this.countries = Arrays.asList(countries);
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
            this.countries = countries;
        }
    }

    public static class Country {
        private String name;
        private List<String> joining;

        public Country(String name, String... joining) {
            this.name = name;
            if (joining != null) {
                this.joining = Arrays.asList(joining);
            }
        }

        public Country() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getJoining() {
            return joining;
        }

        public void setJoining(List<String> joining) {
            this.joining = joining;
        }
    }
}
