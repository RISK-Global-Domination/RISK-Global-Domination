package game.risk.controller;

import game.risk.model.*;
import game.risk.view.GameView;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Executes the Game's status, allows the command of Forfeiting,
 * conquering a country with the number of armies you want to go with,
 * roll dixe, end a turn and get the winner of the game.
 *
 * @author Tejash, Jatin, Lynn, Adityo
 * @version 1.0
 */
public class GameController {

    private GameModel model;
    private GameView view;
    private Random random;
    private ArrayList<Player> players;

    /**
     * Constructor for GameController, takes in model and view.
     *
     * @param model
     * @param view
     */
    public GameController(GameModel model, GameView view) {

        this.model = model;
        this.view = view;
        this.random = new Random();

    }

    /**
     * Executes the game and shows the Welcome Screen
     */
    public void execute() {
        this.loadMenuItems();
        view.createPlayerCountScreen();
        view.addPlayerCountListener(new PlayerCountListener());
    }

    public void allocateRandomCountries() {

        // Shuffle Countries
        model.shuffleCountries();

        Random random = new Random();
        // Setting up random values..
        int numberOfCountries = model.getCountries().size();
        for (int i = 0; i < numberOfCountries; i++) {
            int index = i / (numberOfCountries / players.size());
            int decreaser;
            if (index == players.size()) {
                index--;
            }
            if (players.get(index).getArmies() != 0) {
                if (((i + 1) / (numberOfCountries / players.size())) != index) {
                    model.getCountries().get(i).setArmies(players.get(index).getArmies());
                    decreaser = players.get(index).getArmies();
                } else {
                    int count = 1 + random.nextInt(4);
                    if (count > players.get(index).getArmies()) {
                        count = players.get(index).getArmies();
                    }
                    decreaser = count;
                    model.getCountries().get(i).setArmies(count);
                }
                model.getCountries().get(i).setOccupant(players.get(index));
                players.get(index).armiesUpdator(-decreaser);
                players.get(index).conqueredCountry(model.getCountries().get(i));
            }
        }
    }

    public void startGame() {
        // starting of game.
        while (!isEnd()) {
            // each user turn.
            for (Player player : players) {
                // move to next player if player lost (has no countries left)
                if (player.getOccupiedCountries().size() == 0) {
                    continue;
                }
                view.completeReport(players);
                int to, from;
                // Human player
                if (player.getPlayerNumber() < (view.getPlayerCount() - view.getAiPlayerCount())) {
                    do {
                        view.takeTurn(player);
                        // Place Bonus Armies
                        int bonusArmy = Math.max(3, player.getOccupiedCountries().size() / 3 +
                                player.getOccupiedContinents().size() * 2);
                        to = view.toLand_BonusArmy(player, bonusArmy);
                        to = getFromIndex(player, to);
                        model.getCountries().get(to).updateArmies(bonusArmy);
                        view.completeReport(players);
                        // Attack : from
                        from = view.getCountryStartAttack(player);
                        from = getFromIndex(player, from);
                        // Attack : to
                        to = view.getCountryOnAttack(player, model.getCountries().get(from));
                        to = getToIndex(to, from);
                    } while (to == -1);

                    attack(player, to, from);
                    view.completeReport(players);

                    // Move Troupe Phase
                    view.moveTroupes();
                    moveTroupes(player);

                    // if game ended, break.
                    if (isEnd()) {
                        break;
                    }
                }
                // AI Player
                else {
                    do {
                        view.takeTurn(player);

                        int bonusArmy = Math.max(3, player.getOccupiedCountries().size() / 3 +
                                player.getOccupiedContinents().size() * 2);

                        to = view.aitoLand_BonusArmy(player, bonusArmy);
                        to = getFromIndex(player, to);
                        model.getCountries().get(to).updateArmies(bonusArmy);

                        from = view.aiGetCountryStartAttack(player);
                        from = getFromIndex(player, from);

                        to = view.aiGetCountryOnAttack(player, model.getCountries().get(from));
                        for (int i = 0; i < model.getCountries().size(); i++) {
                            if (model.getCountries().get(Math.abs(from)).getJoining().get(Math.abs(to)) == model.getCountries().get(Math.abs(i))) {
                                to = i;
                                break;
                            }
                        }
                    } while (to == -1);

                    // Attacking with dices
                    int toArmies = model.getCountries().get(to).getArmies();
                    int fromArmies = model.getCountries().get(from).getArmies() - 1;
                    int dices = Math.min(toArmies, fromArmies);
                    if (dices > 2) {
                        dices = 2;
                    }
                    if (toArmies == 0) {
                        // direct conquer..
                        conquerCountry(player, to, fromArmies);
                    } else {
                        int[] toDices = rollDices(dices);
                        int[] fromDices = rollDices(dices);
                        Arrays.sort(toDices);
                        Arrays.sort(fromDices);
                        int toWins = 0, fromWins = 0;
                        for (int i = 0; i < toDices.length; i++) {
                            if (toDices[i] > fromDices[i]) {
                                toWins++;
                            } else if (toDices[i] < fromDices[i]) {
                                fromWins++;
                            }
                        }
                        model.getCountries().get(to).updateArmies(-fromWins);
                        model.getCountries().get(from).updateArmies(-toWins);
                        // checking wins..
                        if (fromWins > toWins) {
                            // conquer..
                            conquerCountry(player, to, (fromArmies - toWins));
                            aiFortification(player);
                        } else if (fromWins < toWins) {
                            // conquer..
                            view.fail(player, model.getCountries().get(to));
                        }
                    }

                    view.moveTroupes();

                    int moveTroupesTo, moveTroupesFrom;

                    moveTroupesFrom = view.aiFromMove(player);
                    moveTroupesFrom = getFromIndex(player, moveTroupesFrom);

                    moveTroupesTo = view.aiGetMoveTroupes(player, model.getCountries().get(moveTroupesFrom));
                    moveTroupesTo = getToIndex(moveTroupesTo, moveTroupesFrom);

                    int soldiers = random.nextInt(Math.abs(model.getCountries().get(Math.abs(moveTroupesFrom)).getArmies() - 1));

                    JOptionPane.showMessageDialog(null, "AI Selected Number of Soldiers: " + soldiers,
                            "AI Selected Number of Soldiers", JOptionPane.PLAIN_MESSAGE, null);

                    model.getCountries().get(moveTroupesFrom).updateArmies(-soldiers);
                    model.getCountries().get(moveTroupesTo).updateArmies(soldiers);

                    // if is game ended.
                    if (isEnd()) {
                        break;
                    }
                }
            }
        }
        System.out.println("Winner: " + getWinner());
    }

    private int getToIndex(int to, int from) {
        for (int i = 0; i < model.getCountries().size(); i++) {
            if (model.getCountries().get(from).getJoining().get(to) == model.getCountries().get(i)) {
                to = i;
                break;
            }
        }
        return to;
    }

    private int getFromIndex(Player player, int from) {
        for (int i = 0; i < model.getCountries().size(); i++) {
            if (player.getOccupiedCountries().get(from) == model.getCountries().get(i)) {
                from = i;
                break;
            }
        }
        return from;
    }

    private void moveTroupes(Player player) {
        int moveTroupesTo, moveTroupesFrom;

        moveTroupesFrom = view.fromMove(player);
        moveTroupesFrom = getFromIndex(player, moveTroupesFrom);

        moveTroupesTo = view.getMoveTroupes(player, model.getCountries().get(moveTroupesFrom));
        moveTroupesTo = getToIndex(moveTroupesTo, moveTroupesFrom);

        int soldiers = view.numberOfSoliders(Math.abs(model.getCountries().get(Math.abs(moveTroupesFrom)).getArmies() - 1));
        if (soldiers <= (model.getCountries().get(moveTroupesFrom).getArmies() - 1)) {
            model.getCountries().get(moveTroupesFrom).updateArmies(-soldiers);
            model.getCountries().get(moveTroupesTo).updateArmies(soldiers);
        } else {
            view.errorFortification();
        }
    }

    private void attack(Player player, int to, int from) {
        // Attacking with dices
        int toArmies = model.getCountries().get(to).getArmies();
        int fromArmies = model.getCountries().get(from).getArmies() - 1;
        int dices = Math.min(toArmies, fromArmies);
        if (dices > 2) {
            dices = 2;
        }
        if (toArmies == 0) {
            // direct conquer..
            conquerCountry(player, to, fromArmies);
        } else {
            int[] toDices = rollDices(dices);
            int[] fromDices = rollDices(dices);
            Arrays.sort(toDices);
            Arrays.sort(fromDices);
            int toWins = 0, fromWins = 0;
            for (int i = 0; i < toDices.length; i++) {
                if (toDices[i] > fromDices[i]) {
                    toWins++;
                } else if (toDices[i] < fromDices[i]) {
                    fromWins++;
                }
            }
            model.getCountries().get(to).updateArmies(-fromWins);
            model.getCountries().get(from).updateArmies(-toWins);
            // checking wins..
            if (fromWins > toWins) {
                // conquer..
                conquerCountry(player, to, (fromArmies - toWins));
                fortification(player);
            } else if (fromWins < toWins) {
                // conquer..
                view.fail(player, model.getCountries().get(to));
            }
        }
    }

    private void fortification(Player player) {
        view.fortification();
        int from = view.fromMove(player);
        from = getFromIndex(player, from);
        int soldiers = view.numberOfSoliders(Math.abs(model.getCountries().get(Math.abs(from)).getArmies() - 1));
        if (soldiers <= (model.getCountries().get(from).getArmies() - 1)) {
            int to = view.toLand(player, soldiers);
            to = getFromIndex(player, to);
            model.getCountries().get(from).updateArmies(-soldiers);
            model.getCountries().get(to).updateArmies(soldiers);
        } else {
            view.errorFortification();
        }
    }

    private void aiFortification(Player player) {
        view.fortification();
        int from = view.aiFromMove(player);
        from = getFromIndex(player, from);

        int soldiers = random.nextInt(Math.abs(model.getCountries().get(Math.abs(from)).getArmies() - 1));

        JOptionPane.showMessageDialog(null, "AI Selected Number of Soldiers: " + soldiers,
                "AI Selected Number of Soldiers", JOptionPane.PLAIN_MESSAGE, null);

        int to = view.aiToLand(player, soldiers);
        to = getFromIndex(player, to);
        model.getCountries().get(from).updateArmies(-soldiers);
        model.getCountries().get(to).updateArmies(soldiers);
    }

    private void conquerCountry(Player player, int toIndex, int armies) {
        Player defeated = model.getCountries().get(toIndex).getOccupant();
        defeated.lostCountry(model.getCountries().get(toIndex));
        model.getCountries().get(toIndex).setOccupant(player);
        player.conqueredCountry(model.getCountries().get(toIndex));

        // new armies..
        model.getCountries().get(toIndex).setArmies(armies);
        view.conquered(player, model.getCountries().get(toIndex));
    }

    private int[] rollDices(int count) {
        int[] rolls = new int[count];
        for (int i = 0; i < count; i++) {
            rolls[i] = 1 + random.nextInt(6);
        }
        return rolls;
    }

    private boolean isEnd() {
        for (Player player : players) {
            if (player.getOccupiedCountries().size() == model.getCountries().size()) {
                return true;
            }
        }
        return false;
    }

    private String getWinner() {
        for (Player player : players) {
            if (player.getOccupiedCountries().size() == model.getCountries().size()) {
                return player.getName();
            }
        }
        return null;
    }

    /**
     * Load menu actions
     */
    private void loadMenuItems() {
        JsonHandler handler = new JsonHandler();
        view.setActionOnSaveMenuItem((event) -> {
            File file = chooseFile(true);
            if (file != null) {
                file = new File(file.getAbsolutePath() + ".json");
                final String json = handler.toJson(buildStatusSave());
                try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
                    out.print(json);
                    view.errorMessage("", "Game Saved");
                } catch (FileNotFoundException e) {
                    view.errorMessage("", "Unable to save file into: " + file.getAbsolutePath());
                }

            }
        });
        view.setActionOnLoadMenuItem((event) -> {
            final File file = chooseFile(false);
            if (file != null) {
                final String jsonSave = DataFetcher.readFileAsString(file);
                if (jsonSave != null && !jsonSave.trim().isEmpty()) {
                    buildGameState(handler.fromJson(jsonSave, SaveData.class));
                }
            }
        });
        view.setActionOnLoadMApMenuItem((event) -> {
            final File file = chooseFile(false);
            if (file != null) {
                final String jsonMap = DataFetcher.readFileAsString(file);
                if (jsonMap != null && !jsonMap.trim().isEmpty()) {
                    loadCustomMap(handler.fromJson(jsonMap, CustomMap.class));
                }
            }
        });
    }

    /*
        Load custom map
    */
    private void loadCustomMap(CustomMap fromJson) {
        if (fromJson.getContinents() == null || fromJson.getContinents().isEmpty()) {
            view.showMessageDialog("Invalid map, no Continents found");
            return;
        }
        if (fromJson.getCountries() == null || fromJson.getCountries().isEmpty()) {
            view.showMessageDialog("Invalid map, no Countries found");
            return;
        }
        // let's build countries
        HashMap<String, Country> processing = new HashMap<>();
        for (CustomMap.Country country : fromJson.getCountries()) {
            if (country.getJoining() == null || country.getJoining().isEmpty()) {
                view.showMessageDialog("Country: " + country.getName() + " have no joining");
                return;
            }
            // check if country already allocated
            Country found = processing.get(country.getName());
            if (found == null) {
                found = new Country(country.getName());
                processing.put(found.getName(), found);
            }
            // Add joining country to actual evaluation
            for (String countryName : country.getJoining()) {
                Country joined = processing.get(countryName);
                if (joined == null) {
                    joined = new Country(countryName);
                    processing.put(countryName, joined);
                }
                if (found.getJoining() == null) {
                    found.setJoining(new ArrayList<>());
                }
                found.getJoining().add(joined);
            }
        }
        // Let Build Cointinents
        ArrayList<Continent> finalContinent = new ArrayList<>();
        for (CustomMap.Continent continent : fromJson.getContinents()) {
            final Continent cntnt = new Continent(continent.getName(), new ArrayList<>());
            for (String country : continent.getCountries()) {
                final Country state = processing.get(country);
                if (state == null) {
                    view.errorMessage("", "Country: " + country + " present into continent: " + continent.getName() + " is invalid ");
                    return;
                }
                cntnt.getCountries().add(state);
            }
            finalContinent.add(cntnt);
        }
        model.customContinents(finalContinent);
        model.customCountries(new ArrayList<>(processing.values()));
        view.showMessageDialog("Map Loaded");
    }

    /*
      Convert game state into save data. For do it simply save player status
     */
    private SaveData buildStatusSave() {
        SaveData data = new SaveData();
        for (Player player : players) {
            SaveData.Player dataPlayer = new SaveData.Player(player.getName(), player.getPlayerNumber(), player.getArmies());
            if (player.getOccupiedContinents() != null && !player.getOccupiedContinents().isEmpty()) {
                dataPlayer.getOccupiedContinent().addAll(player.getOccupiedContinents().stream().map(Continent::getName).collect(Collectors.toList()));
            }
            if (player.getOccupiedCountries() != null && !player.getOccupiedCountries().isEmpty()) {
                dataPlayer.getOccupiedCountry().addAll(player.getOccupiedCountries().stream().map(c -> new SaveData.Country(c.getName(), c.getArmies())).collect(Collectors.toList()));
            }
            dataPlayer.setIa(player.isAi());
            data.getPlayers().add(dataPlayer);
        }
        return data;
    }


    /*
      Convert save data into game data.
     */
    private void buildGameState(SaveData data) {
        view.setPlayerCount(data.getPlayers().size());
        view.setAiPlayerCount(0);
        // convert countries as map
        Map<String, Country> countryMap =
                model.getCountries().stream().collect(Collectors.toMap(Country::getName, c -> c));
        final Map<String, Continent> continentMap =
                model.getContinents().stream().collect(Collectors.toMap(Continent::getName, ct -> ct));
        for (SaveData.Player player : data.getPlayers()) {
            if (players == null) {
                players = new ArrayList<>();
            }
            final Player riskPlayer = new Player(player.getPlayerName(), player.getArmies(), player.getPlayerNumber());
            riskPlayer.setAi(player.getIa());
            if (player.getIa()) {
                view.setAiPlayerCount(view.getAiPlayerCount() + 1);
            }
            players.add(riskPlayer);
            for (SaveData.Country cc : player.getOccupiedCountry()) {
                final Country country = countryMap.get(cc.getName());
                if (country == null) {
                    view.errorMessage("", "Invalid save data, maybe custom map?");
                    players = new ArrayList<>();
                    view.setPlayerCount(0);
                    view.setAiPlayerCount(0);
                    return;
                }
                country.setArmies(cc.getArmies());
                country.setOccupant(riskPlayer);
                riskPlayer.conqueredCountry(country);
            }
            for (String ct : player.getOccupiedContinent()) {
                final Continent continent = continentMap.get(ct);
                if (continent == null) {
                    view.errorMessage("", "Invalid save data, maybe custom map?");
                    players = new ArrayList<>();
                    view.setPlayerCount(0);
                    view.setAiPlayerCount(0);
                    return;
                }
                riskPlayer.conqueredContinent(continent);
            }
        }
        view.createGameScreen();
        startGame();
    }

    /**
     * Retrieve file from FileSystem
     *
     * @param save Show save button if true, open otherwise
     * @return File if found or null if action aborted
     */
    private File chooseFile(boolean save) {
        // Chooser Dialog
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setAcceptAllFileFilterUsed(false);
        j.setFileFilter(new FileFilter() {
            public String getDescription() {
                return "JSON file (.json)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".json") || filename.endsWith(".json");
                }
            }
        });
        // show dialog
        int r = save ? j.showSaveDialog(null) : j.showOpenDialog(null);
        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // set the label to the path of the selected file
            return j.getSelectedFile();
        }
        // if the user cancelled the operation
        else return null;
    }

    public class PlayerCountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            view.setPlayerCount(Integer.parseInt(actionEvent.getActionCommand()));
            view.createAICountScreen();
            view.addAICountListener(new aiCountListener());
        }
    }

    public class aiCountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            view.setAiPlayerCount(Integer.parseInt(actionEvent.getActionCommand()));
            view.createPlayerNameScreen();
            view.addOkListener(new okListener());
        }
    }

    public class okListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            players = new ArrayList<>();
            int playerCount = view.getPlayerCount();

            int armies = switch (playerCount) {
                case 2 -> 50;
                case 3 -> 35;
                case 4 -> 30;
                case 5 -> 25;
                case 6 -> 20;
                default -> throw new RuntimeException("Player count should be between 2 to 6.");
            };

            final int aiPlayerCount = view.getAiPlayerCount();

            for (int i = 0; i < playerCount; i++) {
                final Player e = new Player(view.getNames()[i].getText(), armies, i);
                if (i >= aiPlayerCount) {
                    e.setAi(true);
                }
                players.add(e);
            }

            view.createGameScreen();

            allocateRandomCountries();
            startGame();
        }
    }
}
