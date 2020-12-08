package game.risk.model;

import java.util.ArrayList;
import java.util.List;

/*
 Simplified object for saving purpose
 */
public class SaveData {

    private List<Player> players;

    public List<Player> getPlayers() {
        if (players == null) {
            players = new ArrayList<>();
        }
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public static class Country {
        private String name;
        private int armies;

        public Country(String name, int armies) {
            this.name = name;
            this.armies = armies;
        }

        public Country() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getArmies() {
            return armies;
        }

        public void setArmies(int armies) {
            this.armies = armies;
        }
    }

    public static class Player {
        private String playerName;
        private int playerNumber;
        private int armies;
        private List<Country> occupiedCountry;
        private List<String> occupiedContinent;
        private boolean ia = false;

        public Player() {
        }

        public Player(String playerName, int playerNumber, int armies) {
            this.playerName = playerName;
            this.playerNumber = playerNumber;
            this.armies = armies;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getPlayerNumber() {
            return playerNumber;
        }

        public void setPlayerNumber(int playerNumber) {
            this.playerNumber = playerNumber;
        }

        public int getArmies() {
            return armies;
        }

        public void setArmies(int armies) {
            this.armies = armies;
        }

        public List<Country> getOccupiedCountry() {
            if (occupiedCountry == null) {
                occupiedCountry = new ArrayList<>();
            }
            return occupiedCountry;
        }

        public void setOccupiedCountry(List<Country> occupiedCountry) {
            this.occupiedCountry = occupiedCountry;
        }

        public List<String> getOccupiedContinent() {
            if (occupiedContinent == null) {
                occupiedContinent = new ArrayList<>();
            }
            return occupiedContinent;
        }

        public void setOccupiedContinent(List<String> occupiedContinent) {
            this.occupiedContinent = occupiedContinent;
        }

        public boolean getIa() {
            return ia;
        }

        public void setIa(boolean ia) {
            this.ia = ia;
        }
    }
}
