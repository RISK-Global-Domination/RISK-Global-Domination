package game.risk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

import game.risk.model.GameModel;
import game.risk.model.Player;
import game.risk.view.GameView;

/**
 * EXECUTES THE GAME'S STATUS, ALLOWS THE COMMAND OF FORFEITING,
 * CONQUERING A COUNTRY WITH THE NUMBER OF ARMIES YOU WANT TO GO WITH,
 * ROLL DICE, END A TURN AND GET THE WINNER OF THE GAME.
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
        view.createPlayerCountScreen();
        view.addPlayerCountListener(new PlayerCountListener());
    }

    public void execute2() {

        // Shuffle Countries
        model.shuffleCountries();

        Random random = new Random();
        // Setting up random values..
        for (int i = 0; i < model.getCountries().size(); i++) {
            int index = i / (42 / players.size());
            int decreaser;
            if (index == players.size()) {
                index--;
            }
            if (players.get(index).getArmies() != 0) {
                if (((i + 1) / (42 / players.size())) != index) {
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

    public void execute3() {
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

                        int bonusArmy = Math.max(3, player.getOccupiedCountries().size()/3 +
                                player.getOccupiedContinents().size()*2);

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

                        int bonusArmy = Math.max(3, player.getOccupiedCountries().size()/3 +
                                player.getOccupiedContinents().size()*2);

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

        int soldiers = view.numberOfSoliders();
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
        int soldiers = view.numberOfSoliders();
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
            if (player.getOccupiedCountries().size() == 42) {
                return true;
            }
        }
        return false;
    }

    private String getWinner() {
        for (Player player : players) {
            if (player.getOccupiedCountries().size() == 42) {
                return player.getName();
            }
        }
        return null;
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

            for (int i = 0; i < playerCount; i++) {
                players.add(new Player(view.getNames()[i].getText(), armies, i));
            }

            view.createGameScreen();

            execute2();
            execute3();
        }
    }
}
