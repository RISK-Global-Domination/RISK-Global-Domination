package tests.model;

import game.risk.model.Player;
import org.junit.Assert;

import static org.junit.Assert.*;

public class PlayerTest {

    @org.junit.Test
    public void getName() {
        Player p = new Player("John", 5 , 1);
        assertEquals("John" ,p.getName());
    }

    @org.junit.Test
    public void getArmies() {
        Player p = new Player("John", 5, 1);
        assertEquals(5 ,p.getArmies());
    }
}