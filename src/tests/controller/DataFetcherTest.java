package tests.controller;

import game.risk.controller.DataFetcher;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataFetcherTest {

    /**
     * Test that getContinents fetches exactly 6 continents from the .txt file
     */
    @Test
    public void getContinents() {
        DataFetcher df = new DataFetcher();
        assertEquals(6, df.getContinents().size());
    }

    /**
     * Test that getCountries fetches exactly 42 countries from the .txt file
     */
    @Test
    public void getCountries() {
        DataFetcher df = new DataFetcher();
        assertEquals(42, df.getCountries().size());
    }
}