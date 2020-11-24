package tests.model;

import game.risk.model.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountryTest {

    private Country c;

    @Before
    public void setUp() throws Exception {
        c = new Country("Canada", null);
    }

    /**
     * Test the getName() function
     */
    @Test
    public void getName() {
        assertEquals("Canada", c.getName());
    }

    /**
     * Test getArmies() function
     */
    @Test
    public void getArmies() {
        c.updateArmies(5);
        assertEquals(5, c.getArmies());
    }
}