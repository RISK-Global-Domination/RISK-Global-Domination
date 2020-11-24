package tests.controller;

import game.risk.controller.DataFetcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataFetcherTest {

    private DataFetcher df;

    @Before
    public void setUp() throws Exception {
        df = new DataFetcher();
    }

    @After
    public void tearDown() throws Exception { }

    /**
     * Test that getContinents fetches exactly 6 continents from the .txt file
     */
    @Test
    public void getContinents() {
        assertEquals(6, df.getContinents().size());
    }

    /**
     * Test that getCountries fetches exactly 42 countries from the .txt file
     */
    @Test
    public void getCountries() {
        assertEquals(42, df.getCountries().size());
    }
}