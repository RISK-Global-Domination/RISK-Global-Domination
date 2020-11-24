package tests.model;

import game.risk.model.Player;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player p;

    @Before
    public void setUp() throws Exception {
        p = new Player("John", 5 , 1);
    }

    @org.junit.Test
    public void getName() {
        assertEquals("John" ,p.getName());
    }



    @org.junit.Test
    public void getArmies() {
        assertEquals(5 ,p.getArmies());
    }
}