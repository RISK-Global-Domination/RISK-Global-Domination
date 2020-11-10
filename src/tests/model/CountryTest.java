package tests.model;

import game.risk.model.Country;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountryTest {

    @Test
    public void getName() {
        Country c = new Country("Canada", null);
        assertEquals("Canada", c.getName());
    }

    @Test
    public void getArmies() {
        Country c = new Country("Canada", null);
        c.updateArmies(5);
        assertEquals(5, c.getArmies());
    }
}