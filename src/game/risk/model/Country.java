package game.risk.model;

import java.util.ArrayList;

public class Country implements Comparable<Country> {

    // Attributes..
    private String name;
    private ArrayList<Country> joining;
    private int armies;
    private Player occupant;

    // Country..
    public Country(String name, ArrayList<Country> joining) {

        this.name = name;
        this.joining = joining;

    }

    // Country..
    public Country(String name) {

        this.name = name;
        this.joining = new ArrayList<>();

    }

    public void updateArmies(int updator) {
        this.armies += updator;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Country> getJoining() {
        return joining;
    }

    public int getArmies() {
        return armies;
    }

    public Player getOccupant() {
        return occupant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJoining(ArrayList<Country> joining) {
        this.joining = joining;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }

    public void setOccupant(Player occupant) {
        this.occupant = occupant;
    }

    @Override
    public int compareTo(Country o) {

        return this.name.compareTo(o.getName());

    }

}
