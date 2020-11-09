package game.risk.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import game.risk.controller.GameController;
import game.risk.model.Country;
import game.risk.model.Player;

import javax.swing.*;

/**
 * RETURN THE REPORT OF THE GAME, FETCHES PLAYERS, THE COUNTRIES ATTACKED AND ERRORS.
 */
public class GameView {

    // Attributes...
    private Scanner scan;
    private int playerCount;

    // components - screen 1
    private JFrame gameFrame;
    private Container con;
    private JPanel gameTfPanel;
    private JTextField gameTextField;
    private JPanel buttonGrid;
    private JButton[] buttons;

    // components - screen 2
    private JPanel mainTextPanel;
    private JTextArea mainTextArea;
    private JPanel inputPanel;
    private JTextField[] names;
    private JPanel okButtonPanel;
    private JButton ok;

    // fonts
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 70);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);

    private JPanel topPanel;
    private JLabel topLabel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JTextArea middleTextArea;

    // constructor
    public GameView() {

        scan = new Scanner(System.in);
        gameFrame = new JFrame("RISK: Global Domination");
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        con = gameFrame.getContentPane();
        con.setBackground(Color.WHITE);

        gameFrame.setSize(1200, 800);
        gameFrame.setVisible(true);
    }

    // Screen 1 : Count Player Number
    public void createPlayerCountScreen() {

        gameTfPanel = new JPanel();
        gameTfPanel.setBounds(100, 250, 1000, 150);
        gameTfPanel.setBackground(Color.WHITE);

        gameTextField = new JTextField("How many players (2/3/4/5/6): ");
        gameTextField.setBorder(BorderFactory.createEmptyBorder());
        gameTextField.setBackground(Color.WHITE);
        gameTextField.setForeground(Color.BLUE);
        gameTextField.setEditable(false);
        gameTextField.setFont(titleFont);

        buttonGrid = new JPanel();
        buttonGrid.setBounds(300, 550, 600, 75);
        buttonGrid.setBackground(Color.WHITE);
        buttonGrid.setLayout(new GridLayout(1, 5));

        buttons = new JButton[5];

        for (int i = 0; i < buttons.length; i++) {
            JButton b = new JButton(i + 2 + "");
            b.setBackground(Color.WHITE);
            b.setForeground(Color.BLUE);
            b.setFont(normalFont);
            b.setFocusPainted(false);

            // b.addActionListener(new GameController.PlayerCountListener());
            b.setActionCommand(i + 2 + "");
            buttonGrid.add(b);
            buttons[i] = b;
        }

        gameTfPanel.add(gameTextField);
        con.add(buttonGrid);
        con.add(gameTfPanel);
        gameFrame.revalidate();
    }

    // screen 2: Get Names of Players
    public void createPlayerNameScreen() {
        gameTfPanel.setVisible(false);
        buttonGrid.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.WHITE);
        con.add(mainTextPanel);

        mainTextArea = new JTextArea("Enter player names and press OK");
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.gray);
        mainTextArea.setForeground(Color.BLUE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        inputPanel = new JPanel();
        inputPanel.setBounds(250, 350, 300, 150);
        inputPanel.setBackground(Color.GRAY);
        inputPanel.setLayout(new GridLayout(playerCount, 2));
        con.add(inputPanel);

        names = new JTextField[playerCount];

        for (int i = 0; i < playerCount; i++) {
            JLabel pLabel = new JLabel("Player " + (i + 1));
            pLabel.setFont(normalFont);
            pLabel.setBackground(Color.CYAN);

            JTextField pTf = new JTextField();
            pTf.setBounds(250, 350 + (i * 50), 100, 50);
            pTf.setSize(50, 50);
            pTf.setFont(normalFont);
            pTf.setBackground(Color.DARK_GRAY);
            pTf.setForeground(Color.YELLOW);
            names[i] = pTf;

            inputPanel.add(pLabel);
            inputPanel.add(pTf);
        }

        okButtonPanel = new JPanel();
        okButtonPanel.setBounds(250, 650, 300, 50);
        okButtonPanel.setBackground(Color.GRAY);
        con.add(okButtonPanel);

        ok = new JButton("OK");
        ok.setBackground(Color.pink);

        okButtonPanel.add(ok);

    }

    // screen 3: Main game screen

    /**
     * *******************************
     * **********GAME SCREEN**********
     * *******************************
     */
    public void createGameScreen() {

        mainTextPanel.setVisible(false);
        inputPanel.setVisible(false);
        okButtonPanel.setVisible(false);

        topPanel = new JPanel();
        topPanel.setBounds(200, 100, 800, 50);
        topPanel.setBackground(Color.GRAY);
        con.add(topPanel);

        // to-do : get whose's turn
        topLabel = new JLabel("Player x's turn");
        topLabel.setFont(normalFont);
        topLabel.setBackground(Color.CYAN);
        topPanel.add(topLabel);

        middlePanel = new JPanel();
        middlePanel.setBounds(200, 200, 800, 400);
        middlePanel.setBackground(Color.GRAY);
        con.add(middlePanel);

        middleTextArea = new JTextArea("Player x's turn");
        middleTextArea.setBounds(200, 200, 800, 400);
        middleTextArea.setBackground(Color.GRAY);
        middlePanel.setForeground(Color.BLUE);
        // middlePanel.setFont(normalFont);
        middleTextArea.setLineWrap(true);
        middlePanel.add(middleTextArea);

        bottomPanel = new JPanel();
        bottomPanel.setBounds(200, 650, 800, 100);
        bottomPanel.setBackground(Color.GRAY);
        con.add(bottomPanel);

//		for(int i = 0; i < playerCount; i++) {
//			JLabel pLabel = new JLabel("Player " + i + 1);
//			pLabel.setFont(normalFont);
//			pLabel.setBackground(Color.CYAN);
//
//			JTextField pTf = new JTextField();
//			pTf.setBounds(250, 350 + (i*50), 100, 50);
//			pTf.setSize(50, 50);
//			pTf.setFont(normalFont);
//			pTf.setBackground(Color.DARK_GRAY);
//			pTf.setForeground(Color.YELLOW);
//			names[i] = pTf;
//
//			inputPanel.add(pLabel);
//			inputPanel.add(pTf);
//		}
    }

    public void completeReport(ArrayList<Player> players) {

        String report = "";

        for (Player player : players) {
            report += player.getName() + " Armies \n";
            int count = 1;
            for (Country country : player.getOccupiedCountries()) {
                report += "\t" + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                count++;
            }
        }

        middleTextArea.setText(report);

    }

    // Doing turn.
    public void takeTurn(Player player) {

        topLabel.setText(">>> " + player.getName() + "'s TURN <<<");

    }

    public int getCountryStartAttack(Player player) {

        return this.getCountryNumber(player, "Select number which country will attack: ");

    }

    public int fromMove(Player player) {

        return this.getCountryNumber(player, "Select number from which country you want to move soliders: ");

    }

    public int toLand(Player player, int number) {

        return this.getCountryNumber(player, "You have " + number + " extra soliders\n"
                + "Select number which country you want to land soliders: ");

    }

    public int getCountryNumber(Player player, String message) {

        int number = 0;
        while (true) {
            int count = 1;
            for (Country country : player.getOccupiedCountries()) {
                System.out.printf("\t%d: %-25s %s\n", count, country.getName(), "(Armies: " + country.getArmies() + ")");
                count++;
            }
            if (count != 1) {
                System.out.print(message);
                number = Integer.parseInt(scan.nextLine());
                if (number >= 1 && number < count) {
                    number--;
                    break;
                } else {
                    System.out.println("Please select correct number!!");
                }
            } else {
                System.out.println("Sorry! You don't hold any country.");
                number = -1;
                break;
            }
        }
        return number;

    }

    public int getCountryOnAttack(Player player, Country attacking) {

        int number = 0;
        while (true) {
            int count = 1;
            for (Country country : attacking.getJoining()) {
                if (country.getOccupant() != player) {
                    System.out.printf("\t%d: %-25s %s\n", count, country.getName(), "(Armies: " + country.getArmies() + ")");
                    count++;
                }
            }
            if (count != 1) {
                System.out.print("Select number where to attack: ");
               number = Integer.parseInt(scan.nextLine());
                if (number >= 1 && number < count) {
                    int index = 0;
                    count = 1;
                    for (Country country : attacking.getJoining()) {
                        if (count == number) {
                            number = index;
                            break;
                        }
                        if (country.getOccupant() != player) {
                            count++;
                        }
                        index++;
                    }
                    break;
                } else {
                    System.out.println("Please select correct number!!");
                }
            } else {
                System.out.println("Sorry! There is no any enemy country near you, Try Again.");
                number = -1;
                break;
            }

        }
        return number;
    }

    public int numberOfSoliders() {

        System.out.print("\nSelect number of soliders: ");

        return Integer.parseInt(scan.nextLine());

    }

    public void conquered(Player player, Country country) {

        System.out.println("************************************************");
        System.out.println("********************* CONQURED *****************");
        System.out.println(player.getName() + " conquered " + country.getName());
        System.out.println("************************************************");

    }

    public void fortification() {

        System.out.println("****************** FORTIFICATION ***************");

    }

    public void errorFortification() {

        System.out.println("You don't have enough soliders to move.");

    }

    public void fail(Player player, Country country) {

        System.out.println("********************* FAILED *****************");
        System.out.println(player.getName() + " failed to conquered " + country.getName());
        System.out.println("********************* FAILED *****************");

    }

    public void addPlayerCountListener(ActionListener al) {
        for (JButton button : buttons) {
            button.addActionListener(al);
        }
    }

    public void addOkListener(ActionListener al) {
        ok.addActionListener(al);
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public JTextField[] getNames() {
        return names;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
