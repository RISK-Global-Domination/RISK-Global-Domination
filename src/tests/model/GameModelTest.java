package tests.model;

import game.risk.model.GameModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {

    private GameModel gm;

    @Before
    public void setUp() throws Exception {
        gm = new GameModel();
    }

    /**
     * Test that the model's getCountries retuen "Alaska" as the first country from the file
     */
    @Test
    public void getCountries() {
        assertEquals("Alaska", gm.getCountries().get(0).getName());
    }

    /**
     * Test that the model's getContinents retuen "North America" as the first country from the file
     */
    @Test
    public void getContinents() {
        assertEquals("North America", gm.getContinents().get(0).getName());
    }
}