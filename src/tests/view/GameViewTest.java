package tests.view;

import game.risk.view.GameView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewTest {

    private GameView view;

    @Before
    public void setUp() throws Exception {
        view = new GameView();
    }

    /**
     * Test the view's getPlayerCount() function
     */
    @Test
    public void getPlayerCount() {
        view.setPlayerCount(3);
        assertEquals(3, view.getPlayerCount());
    }

    /**
     * Test view's getNames() methond by checking it is properly initialized and lenth is 0 at the beginning
     */
    @Test
    public void getNames() {
        view.createPlayerCountScreen();
        view.createPlayerNameScreen();
        assertEquals(0, view.getNames().length);
    }

    /**
     * Test the view's setPlayerCount() function
     */
    @Test
    public void setPlayerCount() {
        view.setPlayerCount(5);
        assertEquals(5, view.getPlayerCount());
    }
}