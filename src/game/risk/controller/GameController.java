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
 * EXECUTES THE GAME'S STATUS, ALLOWS THE COMMAND OF FORFITING,
 * CONQUERING A COUNTRY WITH THE NUMBER OF ARMIES YOU WANT TO GO WITH,
 * ROLL DICE, END A TURN AND GET THE WINNER OF THE GAME.
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
     * Executes the game.
     */
    public void execute() {

        // Random allocation.
        model.shuffleCountries();
        view.createPlayerCountScreen();
        view.addPlayerCountListener(new PlayerCountListener());
    }

    public void execute2() {

        Random random = new Random();
        // Setting up random values..
        for (int i = 0; i < model.getCountries().size(); i++) {
            int index = i / (42 / players.size());
            int decreaser = 0;
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

                if (player.getOccupiedCountries().size() == 0) {
                    continue;
                }
                //
                view.completeReport(players);

                // turn.
                int to = 0;
                int from = 0;
                
                if(player.getPlayerNumber() < (view.getPlayerCount() - view.getAiPlayerCount()))
                {
                	do {

                        view.takeTurn(player);
                        
                        
                        //
                        to = view.toLand(player, 2);
                        for (int i = 0; i < model.getCountries().size(); i++) {
                            if (player.getOccupiedCountries().get(to) == model.getCountries().get(i)) {
                                to = i;
                                break;
                            }
                        }
                        model.getCountries().get(to).updateArmies(2);
                        //
                        from = view.getCountryStartAttack(player);
                        for (int i = 0; i < model.getCountries().size(); i++) {
                            if (player.getOccupiedCountries().get(from) == model.getCountries().get(i)) {
                                from = i;
                                break;
                            }
                        }
                        to = view.getCountryOnAttack(player, model.getCountries().get(from));
                        for (int i = 0; i < model.getCountries().size(); i++) {
                            if (model.getCountries().get(from).getJoining().get(to) == model.getCountries().get(i)) {
                                to = i;
                                break;
                            }
                        }

                    } while (to == -1);

                    // Attacking...

                    /***********************************
                     **************** DICES ************
                     ***********************************/
                    int toArmies = model.getCountries().get(to).getArmies();
                    int fromArmies = model.getCountries().get(from).getArmies() - 1;
                    int dices = (toArmies < fromArmies) ? toArmies : fromArmies;
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
                    
                    view.moveTroupes();
                    
                    int moveTroupesTo = 0, moveTroupesFrom = 0;
                    
                    moveTroupesFrom = view.fromMove(player);
                    for (int i = 0; i < model.getCountries().size(); i++) {
                        if (player.getOccupiedCountries().get(moveTroupesFrom) == model.getCountries().get(i)) {
                            moveTroupesFrom = i;
                            break;
                        }
                    }
                    
                    moveTroupesTo = view.getMoveTroupes(player, model.getCountries().get(moveTroupesFrom));
                    for (int i = 0; i < model.getCountries().size(); i++) {
                        if (model.getCountries().get(moveTroupesFrom).getJoining().get(moveTroupesTo) == model.getCountries().get(i)) {
                        	moveTroupesTo = i;
                            break;
                        }
                    }
                    
                    int soldiers = view.numberOfSoliders();
                    if (soldiers <= (model.getCountries().get(moveTroupesFrom).getArmies() - 1)) 
                    {
                        model.getCountries().get(moveTroupesFrom).updateArmies(-soldiers);
                        model.getCountries().get(moveTroupesTo).updateArmies(soldiers);
                    } else {
                        view.errorFortification();
                    }

                    // if is game ended.
                    if (isEnd()) {
                        break;
                    }
                }
                
                else
                {
                	do
                	{
                		view.takeTurn(player);
                		
                		to = view.aiToLand(player, 2);
                		for (int i = 0; i < model.getCountries().size(); i++) {
                            if (player.getOccupiedCountries().get(to) == model.getCountries().get(i)) {
                                to = i;
                                break;
                            }
                        }
                        model.getCountries().get(to).updateArmies(2);
                        
                        from = view.aiGetCountryStartAttack(player);
                        for (int i = 0; i < model.getCountries().size(); i++) {
                            if (player.getOccupiedCountries().get(from) == model.getCountries().get(i)) {
                                from = i;
                                break;
                            }
                        }
                        
                        to = view.aiGetCountryOnAttack(player, model.getCountries().get(from));
                        for (int i = 0; i < model.getCountries().size(); i++) {
                            if (model.getCountries().get(Math.abs(from)).getJoining().get(Math.abs(to)) == model.getCountries().get(Math.abs(i))) {
                                to = i;
                                break;
                            }
                        }
                	} while(to == -1);
                	
                	// Attacking...

                    /***********************************
                     **************** DICES ************
                     ***********************************/
                    int toArmies = model.getCountries().get(to).getArmies();
                    int fromArmies = model.getCountries().get(from).getArmies() - 1;
                    int dices = (toArmies < fromArmies) ? toArmies : fromArmies;
                    if (dices > 2) {
                        dices = 2;
                    }
                    
                    if (toArmies == 0) {

                        // direct conquer..
                        conquerCountry(player, to, fromArmies);

                    }
                    else
                    {
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
                    
                    int moveTroupesTo = 0, moveTroupesFrom = 0;
                    
                    moveTroupesFrom = view.aiFromMove(player);
                    for (int i = 0; i < model.getCountries().size(); i++) {
                        if (player.getOccupiedCountries().get(moveTroupesFrom) == model.getCountries().get(i)) {
                            moveTroupesFrom = i;
                            break;
                        }
                    }
                    
                    moveTroupesTo = view.aiGetMoveTroupes(player, model.getCountries().get(moveTroupesFrom));
                    for (int i = 0; i < model.getCountries().size(); i++) {
                        if (model.getCountries().get(moveTroupesFrom).getJoining().get(moveTroupesTo) == model.getCountries().get(i)) {
                        	moveTroupesTo = i;
                            break;
                        }
                    }
                    
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

    //
    private void fortification(Player player) {

        view.fortification();
        int from = view.fromMove(player);
        for (int i = 0; i < model.getCountries().size(); i++) {
            if (player.getOccupiedCountries().get(from) == model.getCountries().get(i)) {
                from = i;
                break;
            }
        }
        int soldiers = view.numberOfSoliders();
        if (soldiers <= (model.getCountries().get(from).getArmies() - 1)) {
            int to = view.toLand(player, soldiers);
            for (int i = 0; i < model.getCountries().size(); i++) {
                if (player.getOccupiedCountries().get(to) == model.getCountries().get(i)) {
                    to = i;
                    break;
                }
            }
            model.getCountries().get(from).updateArmies(-soldiers);
            model.getCountries().get(to).updateArmies(soldiers);
        } else {
            view.errorFortification();
        }

    }
    
    private void aiFortification(Player player)
    {
    	view.fortification();
    	int from = view.aiFromMove(player);
    	for (int i = 0; i < model.getCountries().size(); i++) {
            if (player.getOccupiedCountries().get(from) == model.getCountries().get(i)) {
                from = i;
                break;
            }
        }
    	
    	int soldiers = random.nextInt(Math.abs(model.getCountries().get(Math.abs(from)).getArmies() - 1));
    	
    	JOptionPane.showMessageDialog(null, "AI Selected Number of Soldiers: " + soldiers,
                "AI Selected Number of Soldiers", JOptionPane.PLAIN_MESSAGE, null);
    	
    	int to = view.aiToLand(player, soldiers);
    	for (int i = 0; i < model.getCountries().size(); i++) {
            if (player.getOccupiedCountries().get(to) == model.getCountries().get(i)) {
                to = i;
                break;
            }
        }
        model.getCountries().get(from).updateArmies(-soldiers);
        model.getCountries().get(to).updateArmies(soldiers);
    }

    // conquer..
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
            //view.createPlayerNameScreen();
            //view.addOkListener(new okListener());
            view.createAICountScreen();
            view.addAICountListener(new aiCountListener());
        }
    }

    public class okListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            players = new ArrayList<>();
            int playerCount = view.getPlayerCount();

            int armies = 0;
            switch (playerCount) {
                case 2:
                    armies = 50;
                    break;
                case 3:
                    armies = 35;
                    break;
                case 4:
                    armies = 30;
                    break;
                case 5:
                    armies = 25;
                    break;
                case 6:
                    armies = 20;
                    break;
                default:
                    throw new RuntimeException("Player count should be between 2 to 6.");
            }

            for (int i = 0; i < playerCount; i++) {
                players.add(new Player(view.getNames()[i].getText(), armies, i));
            }

            view.createGameScreen();

            execute2();
            execute3();
        }
    }
    
    public class aiCountListener implements ActionListener
    {

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			// TODO Auto-generated method stub
			view.setAiPlayerCount(Integer.parseInt(arg0.getActionCommand()));
			view.createPlayerNameScreen();
			view.addOkListener(new okListener());
		}
    	
    }
}
