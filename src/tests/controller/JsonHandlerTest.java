package tests.controller;

import game.risk.controller.JsonHandler;
import game.risk.model.SaveData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JsonHandlerTest {


    private JsonHandler jsonHandler;

    @Before
    public void setUp() throws Exception {
        jsonHandler = new JsonHandler();
    }

    /**
     * Test json conversion
     */
    @Test
    public void convertFromJson() {
        final String json = "{\"players\":[{\"playerName\":\"Bob\",\"playerNumber\":0,\"armies\":0,\"occupiedCountry\":[{\"name\":\"Indonesia\",\"armies\":4},{\"name\":\"North Africa\",\"armies\":3},{\"name\":\"China\",\"armies\":3},{\"name\":\"Central America\",\"armies\":3},{\"name\":\"Venezuela\",\"armies\":2},{\"name\":\"Argentina\",\"armies\":1},{\"name\":\"Egypt\",\"armies\":1},{\"name\":\"Ukraine\",\"armies\":1},{\"name\":\"Madagascar\",\"armies\":2},{\"name\":\"Alaska\",\"armies\":4},{\"name\":\"Japan\",\"armies\":4},{\"name\":\"Kamchatka\",\"armies\":1},{\"name\":\"Middle East\",\"armies\":2},{\"name\":\"Congo\",\"armies\":2},{\"name\":\"Ural\",\"armies\":2},{\"name\":\"East Africa\",\"armies\":2},{\"name\":\"Yakutsk\",\"armies\":2},{\"name\":\"Quebec\",\"armies\":4},{\"name\":\"Western Europe\",\"armies\":3},{\"name\":\"Irkutsk\",\"armies\":2},{\"name\":\"Southern Europe\",\"armies\":2}],\"ia\":false},{\"playerName\":\"Risko\",\"playerNumber\":1,\"armies\":0,\"occupiedCountry\":[{\"name\":\"Ontario\",\"armies\":1},{\"name\":\"New Guinea\",\"armies\":1},{\"name\":\"Northwest Territory\",\"armies\":4},{\"name\":\"Scandinavia\",\"armies\":1},{\"name\":\"Western Australia\",\"armies\":1},{\"name\":\"Brazil\",\"armies\":1},{\"name\":\"Eastern Australia\",\"armies\":3},{\"name\":\"Western United States\",\"armies\":2},{\"name\":\"Siam\",\"armies\":4},{\"name\":\"Eastern United States\",\"armies\":2},{\"name\":\"Afghanistan\",\"armies\":1},{\"name\":\"Alberta\",\"armies\":1},{\"name\":\"Great Britain\",\"armies\":3},{\"name\":\"Mongolia\",\"armies\":3},{\"name\":\"Iceland\",\"armies\":2},{\"name\":\"India\",\"armies\":2},{\"name\":\"Siberia\",\"armies\":3},{\"name\":\"Northern Europe\",\"armies\":2},{\"name\":\"South Africa\",\"armies\":2},{\"name\":\"Greenland\",\"armies\":1},{\"name\":\"LotR\",\"armies\":10}],\"ia\":true}]}";
        final SaveData saveData = jsonHandler.fromJson(json, SaveData.class);
        Assert.assertNotNull(saveData);
        Assert.assertNotNull(saveData.getPlayers());
        Assert.assertEquals(2, saveData.getPlayers().size());
        Assert.assertFalse(saveData.getPlayers().get(0).getIa());
        Assert.assertEquals("Bob", saveData.getPlayers().get(0).getPlayerName());
        Assert.assertEquals(0, saveData.getPlayers().get(0).getPlayerNumber());
        Assert.assertNotNull( saveData.getPlayers().get(0).getOccupiedCountry());
        Assert.assertTrue(saveData.getPlayers().get(1).getIa());
        Assert.assertEquals("Risko", saveData.getPlayers().get(1).getPlayerName());
        Assert.assertEquals(1, saveData.getPlayers().get(1).getPlayerNumber());
        Assert.assertNotNull( saveData.getPlayers().get(1).getOccupiedCountry());
    }
    /**
     * Test json conversion
     */
    @Test
    public void ConvertToJson() {

        SaveData data = new SaveData();
        data.getPlayers().add(new SaveData.Player("test_name", 1, 10));
        final String json = jsonHandler.toJson(data);
        Assert.assertNotNull(json);
        Assert.assertTrue(json.contains("test_name"));
        Assert.assertTrue(json.contains("1"));
        Assert.assertTrue(json.contains("10"));
    }

}
