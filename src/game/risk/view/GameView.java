package game.risk.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import game.risk.model.Country;
import game.risk.model.Player;

/**
 * Return the report of the game, fetches players, the countries attacked and errors.
 * @author Tejash, Lynn, Jatin, Adityo
 * @version 1.0
 */
public class GameView {

    private int playerCount, aiPlayerCount;

    // components - screen 1
    private final JFrame gameFrame;
    private final Container con;
    private JPanel gameTfPanel;
    private JTextArea gameTextField;
    private JPanel buttonGrid;
    private JButton[] buttons;

    //components - aicount screen
    private JPanel aiPanel;
    private JTextArea aiTextField;
    private JPanel aiButtonGrid;
    private JButton[] aiButtons;


    // components - screen 3
    private JPanel mainTextPanel;
    private JTextArea mainTextArea;
    private JPanel inputPanel;
    private JTextField[] names;
    private JPanel okButtonPanel;
    private JButton ok;

    // fonts
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 70);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);

    // components - 4
    private JPanel topPanel;
    private JLabel topLabel;
    private JPanel middlePanel;
    private JPanel bottomPanel2;
    private JTextArea[] middleTextArea;
    private JPanel bottomPanel1;
    private JTextArea bottomLabel;
    private JTextArea bottomLabel2;
    private JTextArea bottomLabelResult;

    // font colors
    private final Color background = Color.BLACK;
    private final Color fontsMain = Color.CYAN;
    private final Color fontsSecondary = Color.WHITE;
    private JPanel bottomPanel3;
    private Paint Paint;


    // constructor
    public GameView() {

        // Attributes...
        gameFrame = new JFrame("RISK: Global Domination");
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        con = gameFrame.getContentPane();
        con.setBackground(background);

        gameFrame.setSize(1200, 800);
        gameFrame.setVisible(true);
    }

    // Screen 1 : Count Player Number
    public void createPlayerCountScreen() {

        gameTfPanel = new JPanel();
        gameTfPanel.setBounds(100, 25, 1000, 425);
        gameTfPanel.setBackground(background);
        gameTfPanel.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("risk_logo.png");
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        gameTextField = new JTextArea("Welcome to RISK Game!\nHow many players?");
        gameTextField.setBackground(background);
        gameTextField.setForeground(fontsMain);
        gameTextField.setEditable(false);
        gameTextField.setFont(titleFont);

        buttonGrid = new JPanel();
        buttonGrid.setBounds(300, 550, 600, 75);
        buttonGrid.setBackground(background);
        buttonGrid.setLayout(new GridLayout(1, 5));

        buttons = new JButton[5];

        for (int i = 0; i < buttons.length; i++) {
            JButton b = new JButton(i + 2 + "");
            b.setBackground(background);
            b.setForeground(fontsSecondary);
            b.setFont(normalFont);
            b.setFocusPainted(false);

            // b.addActionListener(new GameController.PlayerCountListener());
            b.setActionCommand(i + 2 + "");
            buttonGrid.add(b);
            buttons[i] = b;
        }

        gameTfPanel.add(iconLabel, BorderLayout.NORTH);
        gameTfPanel.add(gameTextField, BorderLayout.SOUTH);
        con.add(buttonGrid);
        con.add(gameTfPanel);
        gameFrame.revalidate();
    }

    //screen 2: Count number of AI Players
    public void createAICountScreen() {
        gameTfPanel.setVisible(false);
        buttonGrid.setVisible(false);

        aiPanel = new JPanel();
        aiPanel.setBounds(100, 150, 1000, 250);
        aiPanel.setBackground(background);

        aiTextField = new JTextArea("\nHow many AI players?");
        aiTextField.setBorder(BorderFactory.createEmptyBorder());
        aiTextField.setBackground(background);
        aiTextField.setForeground(fontsMain);
        aiTextField.setEditable(false);
        aiTextField.setFont(titleFont);

        aiButtonGrid = new JPanel();
        aiButtonGrid.setBounds(300, 550, 600, 75);
        aiButtonGrid.setBackground(background);
        aiButtonGrid.setLayout(new GridLayout(1, getPlayerCount() - 1));

        aiButtons = new JButton[getPlayerCount()];

        for (int i = 0; i < aiButtons.length; i++) {
            JButton b = new JButton(i + "");
            b.setBackground(background);
            b.setForeground(fontsSecondary);
            b.setFont(normalFont);
            b.setFocusPainted(false);

            b.setActionCommand(i + "");
            aiButtonGrid.add(b);
            aiButtons[i] = b;
        }

        aiPanel.add(aiTextField);
        con.add(aiButtonGrid);
        con.add(aiPanel);
    }

    // screen 3: Get Names of Players
    public void createPlayerNameScreen() {
        aiPanel.setVisible(false);
        aiButtonGrid.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 1000, 200);
        mainTextPanel.setBackground(background);
        con.add(mainTextPanel);

        mainTextArea = new JTextArea("Enter player names and press OK");
        mainTextArea.setBounds(100, 100, 1000, 250);
        mainTextArea.setBackground(background);
        mainTextArea.setForeground(fontsMain);
        mainTextArea.setFont(titleFont);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        inputPanel = new JPanel();
        inputPanel.setBounds(250, 300, 500, 150);
        inputPanel.setBackground(background);
        inputPanel.setLayout(new GridLayout(playerCount, 2));
        con.add(inputPanel);

        names = new JTextField[playerCount];

        for (int i = 0; i < playerCount; i++) {
            JLabel pLabel;
            if (i < (getPlayerCount() - getAiPlayerCount())) {
                pLabel = new JLabel("Player " + (i + 1) + "'s Name: ");
            } else {
                pLabel = new JLabel("Bot " + (i + 1 - getAiPlayerCount()) + "'s Name: ");
            }
            pLabel.setFont(normalFont);
            pLabel.setBackground(background);
            pLabel.setForeground(fontsMain);

            JTextField pTf = new JTextField();
            pTf.setBounds(250, 300 + (i * 50), 100, 50);
            pTf.setSize(50, 50);
            pTf.setFont(normalFont);
            pTf.setBackground(background);
            pTf.setForeground(fontsSecondary);
            names[i] = pTf;

            inputPanel.add(pLabel);
            inputPanel.add(pTf);
        }

        okButtonPanel = new

                JPanel();
        okButtonPanel.setBounds(250, 550, 300, 100);
        okButtonPanel.setBackground(background);
        con.add(okButtonPanel);

        ok = new

                JButton("OK");
        ok.setBackground(background);
        ok.setForeground(fontsMain);
        ok.setFont(titleFont);

        okButtonPanel.add(ok);

    }

    // screen 4: Main game screen
    public void createGameScreen() {

        mainTextPanel.setVisible(false);
        inputPanel.setVisible(false);
        okButtonPanel.setVisible(false);

        topPanel = new JPanel();
        topPanel.setBounds(200, 10, 800, 100);
        topPanel.setBackground(background);
        con.add(topPanel);

        topLabel = new JLabel("Welcome to RISK Board!");
        topLabel.setFont(titleFont);
        topLabel.setBackground(background);
        topLabel.setForeground(fontsMain);
        topPanel.add(topLabel);

        middlePanel = new JPanel();
        middlePanel.setBounds(50, 110, 1050, 375);
        middlePanel.setBackground(background);
        middlePanel.setLayout(new GridLayout(1, playerCount));
        Border tb = BorderFactory.createTitledBorder("Game");
        middlePanel.setBorder(BorderFactory.createTitledBorder(tb, "Game Board", 0, 0, null, fontsMain));
        con.add(middlePanel);

        middleTextArea = new JTextArea[playerCount];

        for (int i = 0; i < playerCount; i++) {
            JTextArea middleTextArea = new JTextArea("This is the main board");
            middleTextArea.setBackground(background);
            middleTextArea.setForeground(fontsSecondary);
            middleTextArea.setLineWrap(false);
            this.middleTextArea[i] = middleTextArea;
            middlePanel.add(middleTextArea);
        }

        bottomPanel1 = new JPanel();
        bottomPanel1.setBounds(50, 580, 650, 175);
        bottomPanel1.setBackground(background);
        //Border tb3 = BorderFactory.createTitledBorder("Game");
        //bottomPanel1.setBorder(BorderFactory.createTitledBorder(tb3, "Game Status", 0, 0, null, fontsMain));

        con.add(bottomPanel1);

        bottomLabel = new JTextArea("Game Status :");
        bottomLabel.setFont(normalFont);
        bottomLabel.setBackground(background);
        bottomLabel.setForeground(fontsMain);

        bottomPanel3 = new JPanel();
        bottomPanel3.setBounds(50, 480, 625, 100);
        bottomPanel3.setBackground(background);
        //Border tb2 = BorderFactory.createTitledBorder("Game");
        //bottomPanel3.setBorder(BorderFactory.createTitledBorder(tb2, "Current Phase", 0, 0, null, fontsMain));

        con.add(bottomPanel3);

        bottomLabelResult = new JTextArea("");
        bottomLabelResult.setFont(normalFont);
        bottomLabelResult.setBackground(background);
        bottomLabelResult.setForeground(fontsSecondary);

        bottomPanel1.add(bottomLabel);
        bottomPanel3.add(bottomLabelResult);

        bottomPanel2 = new JPanel();
        bottomPanel2.setBounds(675, 510, 400, 450);
        bottomPanel2.setBackground(background);
        con.add(bottomPanel2);

        bottomLabel2 = new JTextArea();
        bottomLabel2.setBackground(background);
        bottomLabel2.setForeground(fontsSecondary);
        bottomPanel2.add(bottomLabel2);
    }

    public void completeReport(ArrayList<Player> players) {

        int i = 0;
        for (Player player : players) {
            String report = "";
            report += player.getName() + "'s Armies \n";
            int count = 1;
            for (Country country : player.getOccupiedCountries()) {
                report += "  " + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                count++;
            }
            middleTextArea[i].setText(report);
            i++;
        }
    }

    // Doing turn.
    public void takeTurn(Player player) {

        topLabel.setText(">>> " + player.getName() + "'s TURN <<<");

    }

    public int getCountryStartAttack(Player player) {

        return this.getCountryNumber(player, "Select number which country will attack: ");

    }

    public int aiGetCountryStartAttack(Player player) {
        return this.aiGetCountryNumber(player, player.getName() + " has selected this country to attack from");
    }

    public int fromMove(Player player) {

        return this.getCountryNumber(player, "Select number from which country you want \n to move soldiers: ");

    }

    public int aiFromMove(Player player) {
        return this.aiGetCountryNumber(player, "AI selected to move from country number: ");
    }

    public int toLand_BonusArmy(Player player, int number) {

        return this.getCountryNumber(player, "You have " + number + " bonus soldiers \n (for holding " +
                player.getOccupiedCountries().size() + " countries & " + player.getOccupiedContinents().size() + " continents)\n"
                + "Select number which country you want to land soldiers: ");
    }

    public int toLand(Player player, int number) {

        return this.getCountryNumber(player, "You have " + number + " extra soldiers\n"
                + "Select number which country you want to land soldiers: ");
    }

    public int aitoLand_BonusArmy(Player player, int number) {

        return this.aiGetCountryNumber(player, player.getName() + " has " + number + " bonus soldiers \n (Countries=" +
                player.getOccupiedCountries().size() + "& continents=" + player.getOccupiedContinents().size() + ")");
    }

    public int aiToLand(Player player, int number) {
        return this.aiGetCountryNumber(player, player.getName() + " has " + number + " extra soldiers");
    }

    public int getCountryNumber(Player player, String message) {

        int number;
        while (true) {
            int count = 1;
            String status = "";
            for (Country country : player.getOccupiedCountries()) {
                status += "  " + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                count++;
            }
            bottomLabel2.setText(status);
            if (count != 1) {
                bottomLabel.setText(message);

                int[] a = IntStream.range(1, count).toArray();
                Object[] options = new Object[a.length];
                // copy elements from object array to integer array
                for (int i = 0; i < a.length; i++) {
                    options[i] = a[i];
                }
                number = (int) (JOptionPane.showInputDialog(bottomPanel1, "Select country number",
                        "Select Country Number", JOptionPane.PLAIN_MESSAGE, null, options, options[0]));

                if (number >= 1 && number < count) {
                    number--;
                    break;
                } else {
                    bottomLabel.setText("Please select correct number!!");
                }
            } else {
                bottomLabel.setText("Sorry! You don't hold any country.");
                number = -1;
                break;
            }
        }
        return number;

    }

    public int aiGetCountryNumber(Player player, String message) {
        int number;

        while (true) {
            int count = 1;
            String status = "";
            for (Country country : player.getOccupiedCountries()) {
                status += "  " + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                count++;
            }
            bottomLabel2.setText(status);
            if (count != 1) {
                bottomLabel.setText(message);

                Random r = new Random();
                int rand = r.nextInt(count);

                if (rand == 0)
                    rand++;

                number = rand;

                JOptionPane.showMessageDialog(bottomPanel1, "AI selected country number:" + number,
                        "AI Selected Country", JOptionPane.PLAIN_MESSAGE, null);

                number--;
                break;

            } else {
                bottomLabel.setText("Sorry! You don't hold any country.");
                number = -1;
                break;
            }
        }

        return number;
    }

    public int getCountryOnAttack(Player player, Country attacking) {

        int number;
        while (true) {
            int count = 1;
            String status = "";
            for (Country country : attacking.getJoining()) {
                if (country.getOccupant() != player) {
                    status += "  " + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                    count++;
                }
            }
            bottomLabel2.setText(status);
            if (count != 1) {
                bottomLabelResult.setText("");
                bottomLabel.setText("Select number where to attack: ");

                int[] a = IntStream.range(1, count).toArray();

                Object[] options = new Object[a.length];

                // copy elements from object array to integer array
                for (int i = 0; i < a.length; i++) {
                    options[i] = a[i];
                }

                number = (int) (JOptionPane.showInputDialog(null, "Select country number",
                        "Select country number", JOptionPane.PLAIN_MESSAGE, null, options, options[0]));

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
                    bottomLabelResult.setText("Please select correct number!!");
                }
            } else {
                System.out.println("Sorry! There is no any enemy country near you, Try Again.");
                bottomLabelResult.setText("Sorry! There is no any enemy country near you,\n Try Again.");
                number = -1;
                break;
            }

        }
        return number;
    }

    public int aiGetCountryOnAttack(Player player, Country attacking) {
        int number;

        while (true) {
            int count = 1;
            String status = "";

            for (Country country : attacking.getJoining()) {
                if (country.getOccupant() != player) {
                    status += "  " + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                    count++;
                }
            }
            bottomLabel2.setText(status);

            if (count != 1) {
                bottomLabel.setText("AI selected to attack: ");

                Random r = new Random();
                int rand = r.nextInt(count);

                if (rand == 0)
                    rand++;

                number = rand;

                JOptionPane.showMessageDialog(null, "AI selected country number:" + number,
                        "AI Selected Country", JOptionPane.PLAIN_MESSAGE, null);

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
                System.out.println("Sorry! There is no any enemy country near you, Try Again.");
                bottomLabelResult.setText("Sorry! There is no any enemy country near you,\n Try Again.");
                number = -1;
                break;
            }

        }

        return number;
    }

    public int getMoveTroupes(Player player, Country movingTo) {

        int number;
        while (true) {
            int count = 1;
            String status = "";
            for (Country country : movingTo.getJoining()) {
                if (country.getOccupant() == player) {
                    status += "  " + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                    count++;
                }
            }
            bottomLabel2.setText(status);
            if (count != 1) {
                bottomLabelResult.setText("");
                bottomLabel.setText("Select number where to move troupes: ");

                int[] a = IntStream.range(1, count).toArray();

                Object[] options = new Object[a.length];

                // copy elements from object array to integer array
                for (int i = 0; i < a.length; i++) {
                    options[i] = a[i];
                }

                number = (int) (JOptionPane.showInputDialog(null, "Select country number",
                        "Select country number", JOptionPane.PLAIN_MESSAGE, null, options, options[0]));

                if (number >= 1 && number < count) {
                    int index = 0;
                    count = 1;
                    for (Country country : movingTo.getJoining()) {
                        if (count == number) {
                            number = index;
                            break;
                        }
                        if (country.getOccupant() == player) {
                            count++;
                        }
                        index++;
                    }
                    break;
                } else {
                    System.out.println("Please select correct number!!");
                    bottomLabelResult.setText("Please select correct number!!");

                }
            } else {
                System.out.println("Sorry! There is no any enemy country near you, Try Again.");
                bottomLabelResult.setText("Sorry! There is no any enemy country near you,\n Try Again.");
                number = -1;
                break;
            }

        }
        return number;
    }

    public int aiGetMoveTroupes(Player player, Country movingTo) {
        int number = 0;

        while (true) {
            int count = 1;
            String status = "";

            for (Country country : movingTo.getJoining()) {
                if (country.getOccupant() == player) {
                    status += "  " + count + ": " + country.getName() + "(Armies: " + country.getArmies() + ")\n";
                    count++;
                }
            }
            bottomLabel2.setText(status);

            if (count != 1) {
                bottomLabel.setText("AI selected to move troupes to: ");

                Random r = new Random();
                int rand = r.nextInt(count);

                if (rand == 0)
                    rand++;

                number = rand;

                JOptionPane.showMessageDialog(null, "AI selected country number:" + number,
                        "AI Selected Country", JOptionPane.PLAIN_MESSAGE, null);

                int index = 0;
                count = 1;
                for (Country country : movingTo.getJoining()) {
                    if (count == number) {
                        number = index;
                        break;
                    }
                    if (country.getOccupant() == player) {
                        count++;
                    }
                    index++;
                }
                break;
            } else {
                System.out.println("Sorry! There is no any enemy country near you, Try Again.");
                bottomLabelResult.setText("Sorry! There is no any enemy country near you,\n Try Again.");
                number = -1;
                break;
            }

        }

        return number;
    }

    public int numberOfSoliders() {

        return Integer.parseInt(JOptionPane.showInputDialog("Select Soldiers Number", JOptionPane.PLAIN_MESSAGE));

    }

    public void conquered(Player player, Country country) {

//        System.out.println("************************************************");
//        System.out.println("********************* CONQURED *****************");
//        System.out.println(player.getName() + " conquered " + country.getName());
//        System.out.println("************************************************");

        bottomLabelResult.setText(player.getName() + " conquered " + country.getName() + "!!!");

    }

    public void fortification() {

//        System.out.println("****************** FORTIFICATION ***************");
        topLabel.setText("FORTIFICATION");

    }

    public void errorFortification() {

//        System.out.println("You don't have enough soldiers to move.");
        bottomLabelResult.setText("");
        bottomLabel.setText("You don't have enough soldiers to move.");

    }

    public void moveTroupes() {
        topLabel.setText("MOVE TROUPES PHASE");
    }

    public void fail(Player player, Country country) {

//        System.out.println("********************* FAILED *****************");
//        System    .out.println(player.getName() + " failed to conquered " + country.getName());
//        System.out.println("********************* FAILED *****************");
        bottomLabelResult.setText(player.getName() + " failed to conquered " + country.getName() + "!!!");

    }

    public void addPlayerCountListener(ActionListener al) {
        for (JButton button : buttons) {
            button.addActionListener(al);
        }
    }

    public void addAICountListener(ActionListener al) {
        for (JButton button : aiButtons) {
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

    public int getAiPlayerCount() {
        return aiPlayerCount;
    }

    public void setAiPlayerCount(int aiPlayerCount) {
        this.aiPlayerCount = aiPlayerCount;
    }

}
