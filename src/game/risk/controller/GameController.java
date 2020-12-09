package game.risk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

import game.risk.model.GameModel;
import game.risk.model.Player;
import game.risk.view.GameView;

/**
 * Executes the Game's status, allows the command of Forfeiting,
 * conquering a country with the number of armies you want to go with,
 * roll dixe, end a turn and get the winner of the game.
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
        //view.createPlayerCountScreen();
        //view.addPlayerCountListener(new PlayerCountListener());
        
        view.createMainMenuScreen();
        view.addMainMenuListener(new MainMenuListener());
    }

    public void allocateRandomCountries() {

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

    public void startGame(GameModel model, ArrayList<Player> players) {
        // starting of game.
        while (!isEnd(players)) {
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
                        to = getFromIndex(player, to, model);
                        model.getCountries().get(to).updateArmies(bonusArmy);
                        view.completeReport(players);
                        // Attack : from
                        from = view.getCountryStartAttack(player);
                        from = getFromIndex(player, from, model);
                        // Attack : to
                        to = view.getCountryOnAttack(player, model.getCountries().get(from));
                        to = getToIndex(to, from, model);
                    } while (to == -1);

                    attack(player, to, from, model);
                    view.completeReport(players);

                    // Move Troupe Phase
                    view.moveTroupes();
                    moveTroupes(player, model);

                    // if game ended, break.
                    if (isEnd(players)) {
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
                        to = getFromIndex(player, to, model);
                        model.getCountries().get(to).updateArmies(bonusArmy);

                        from = view.aiGetCountryStartAttack(player);
                        from = getFromIndex(player, from, model);

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
                        conquerCountry(player, to, fromArmies,model);
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
                            conquerCountry(player, to, (fromArmies - toWins), model);
                            aiFortification(player, model);
                        } else if (fromWins < toWins) {
                            // conquer..
                            view.fail(player, model.getCountries().get(to));
                        }
                    }

                    view.moveTroupes();

                    int moveTroupesTo, moveTroupesFrom;

                    moveTroupesFrom = view.aiFromMove(player);
                    moveTroupesFrom = getFromIndex(player, moveTroupesFrom, model);

                    moveTroupesTo = view.aiGetMoveTroupes(player, model.getCountries().get(moveTroupesFrom));
                    moveTroupesTo = getToIndex(moveTroupesTo, moveTroupesFrom, model);

                    int soldiers = random.nextInt(Math.abs(model.getCountries().get(Math.abs(moveTroupesFrom)).getArmies() - 1));

                    JOptionPane.showMessageDialog(null, "AI Selected Number of Soldiers: " + soldiers,
                            "AI Selected Number of Soldiers", JOptionPane.PLAIN_MESSAGE, null);

                    model.getCountries().get(moveTroupesFrom).updateArmies(-soldiers);
                    model.getCountries().get(moveTroupesTo).updateArmies(soldiers);

                    // if is game ended.
                    if (isEnd(players)) {
                        break;
                    }
                }
                
                if(player.getPlayerNumber() == (view.getPlayerCount() - 1))
                {
                	save();
                }
            }
        }
        System.out.println("Winner: " + getWinner(players));
    }
    
    private void save()
    {
    	if(view.askSave() == JOptionPane.YES_OPTION)
    	{
    		String filename = view.save();
        	
        	ArrayList<Object> save = new ArrayList<Object>();
        	save.add(model);
        	save.add(players);
        	
        	try
        	{
        		File file = new File("saves/" + filename + ".txt");
        		FileOutputStream fos = new FileOutputStream(file);
        		ObjectOutputStream out = new ObjectOutputStream(fos);
        		
        		out.writeObject(save);
        		
        		out.close();
        		fos.close();
        	}
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
    	}
    }
    
    private void loadFile(int fileNumber)
    {
    	GameModel model;
    	ArrayList<Player> players;
    	
    	ArrayList<Object> load = new ArrayList<Object>();
    	
    	File f = new File("saves/");
    	
    	File[] files = f.listFiles();
    	
    	String fileName = files[fileNumber - 1].getName();
    	
    	try
    	{
    		FileInputStream fis = new FileInputStream(new File("saves/" + fileName));
    		ObjectInputStream in = new ObjectInputStream(fis);
    		
    		load = (ArrayList<Object>)in.readObject();
    		
    		in.close();
    		fis.close();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    	catch(ClassNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	
    	model = (GameModel)load.get(0);
    	players = (ArrayList<Player>)load.get(1);
    	
    	view.setPlayerCount(players.size());
    	
    	int count = 0;
    	for(Player player: players)
    	{
    		if(player.getPlayerType().equals("AI"))
    			count++;
    	}
    	
    	System.out.println(count);
    	view.setAiPlayerCount(count);
    	
    	view.createGameScreen();
    	startGame(model, players);
    }

    private int getToIndex(int to, int from, GameModel model) {
        for (int i = 0; i < model.getCountries().size(); i++) {
            if (model.getCountries().get(from).getJoining().get(to) == model.getCountries().get(i)) {
                to = i;
                break;
            }
        }
        return to;
    }

    private int getFromIndex(Player player, int from, GameModel model) {
        for (int i = 0; i < model.getCountries().size(); i++) {
            if (player.getOccupiedCountries().get(from) == model.getCountries().get(i)) {
                from = i;
                break;
            }
        }
        return from;
    }

    private void moveTroupes(Player player, GameModel model) {
        int moveTroupesTo, moveTroupesFrom;

        moveTroupesFrom = view.fromMove(player);
        moveTroupesFrom = getFromIndex(player, moveTroupesFrom, model);

        moveTroupesTo = view.getMoveTroupes(player, model.getCountries().get(moveTroupesFrom));
        moveTroupesTo = getToIndex(moveTroupesTo, moveTroupesFrom, model);

        int soldiers = view.numberOfSoliders(Math.abs(model.getCountries().get(Math.abs(moveTroupesFrom)).getArmies() - 1));
        if (soldiers <= (model.getCountries().get(moveTroupesFrom).getArmies() - 1)) {
            model.getCountries().get(moveTroupesFrom).updateArmies(-soldiers);
            model.getCountries().get(moveTroupesTo).updateArmies(soldiers);
        } else {
            view.errorFortification();
        }
    }

    private void attack(Player player, int to, int from, GameModel model) {
        // Attacking with dices
        int toArmies = model.getCountries().get(to).getArmies();
        int fromArmies = model.getCountries().get(from).getArmies() - 1;
        int dices = Math.min(toArmies, fromArmies);
        if (dices > 2) {
            dices = 2;
        }
        if (toArmies == 0) {
            // direct conquer..
            conquerCountry(player, to, fromArmies, model);
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
                conquerCountry(player, to, (fromArmies - toWins), model);
                fortification(player, model);
            } else if (fromWins < toWins) {
                // conquer..
                view.fail(player, model.getCountries().get(to));
            }
        }
    }

    private void fortification(Player player, GameModel model) {
        view.fortification();
        int from = view.fromMove(player);
        from = getFromIndex(player, from, model);
        int soldiers = view.numberOfSoliders(Math.abs(model.getCountries().get(Math.abs(from)).getArmies() - 1));
        if (soldiers <= (model.getCountries().get(from).getArmies() - 1)) {
            int to = view.toLand(player, soldiers);
            to = getFromIndex(player, to, model);
            model.getCountries().get(from).updateArmies(-soldiers);
            model.getCountries().get(to).updateArmies(soldiers);
        } else {
            view.errorFortification();
        }
    }

    private void aiFortification(Player player, GameModel model) {
        view.fortification();
        int from = view.aiFromMove(player);
        from = getFromIndex(player, from, model);

        int soldiers = random.nextInt(Math.abs(model.getCountries().get(Math.abs(from)).getArmies() - 1));

        JOptionPane.showMessageDialog(null, "AI Selected Number of Soldiers: " + soldiers,
                "AI Selected Number of Soldiers", JOptionPane.PLAIN_MESSAGE, null);

        int to = view.aiToLand(player, soldiers);
        to = getFromIndex(player, to, model);
        model.getCountries().get(from).updateArmies(-soldiers);
        model.getCountries().get(to).updateArmies(soldiers);
    }

    private void conquerCountry(Player player, int toIndex, int armies, GameModel model) {
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

    private boolean isEnd(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.getOccupiedCountries().size() == 42) {
                return true;
            }
        }
        return false;
    }

    private String getWinner(ArrayList<Player> players) {
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
            	if(i < (view.getPlayerCount() - view.getAiPlayerCount()))
            		players.add(new Player(view.getNames()[i].getText(), armies, i, "Human"));
            	else
            		players.add(new Player(view.getNames()[i].getText(), armies, i, "AI"));
            }
            
            view.setInvisible1();
            view.createGameScreen();

            allocateRandomCountries();
            startGame(model, players);
        }
    }
    
    public class MainMenuListener implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent actionEvent)
    	{
    		if(actionEvent.getActionCommand().equals("Play"))
    		{
	    		view.createPlayerCountScreen();
	    		view.addPlayerCountListener(new PlayerCountListener());
    		}
    		else if(actionEvent.getActionCommand().equals("Load"))
    		{
    			File f = new File("saves/");
    			
    			File[] files = f.listFiles();
    			
    			if(files.length == 0)
    			{
    				view.setFilesCount(0);
    				view.noSavedGamesTrue();
    				view.createMainMenuScreen();
    			}
    			else
    			{
    				view.setFilesCount(files.length);
    			
    				int fileNumber = view.createLoadScreen();
    				
    				loadFile(fileNumber);
    			}
    			
    		}
    	}
    }
}