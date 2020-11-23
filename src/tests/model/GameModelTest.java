package tests.model;

import game.risk.model.GameModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {

    /**
     * Test that the model's getCountries retuen "Alaska" as the first country from the file
     */
    @Test
    public void getCountries() {
        GameModel gm = new GameModel();
        assertEquals("Alaska", gm.getCountries().get(0).getName());
    }

    /**
     * Test that the model's getContinents retuen "North America" as the first country from the file
     */
    @Test
    public void getContinents() {
        GameModel gm = new GameModel();
        assertEquals("North America", gm.getContinents().get(0).getName());
    }
}