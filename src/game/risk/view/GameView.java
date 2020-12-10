package game.risk.view;

import game.risk.model.Country;
import game.risk.model.Player;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Return the report of the game, fetches players, the countries attacked and errors.
 *
 * @author Tejash, Lynn, Jatin, Adityo
 * @version 1.0
 */
public class GameView {

    // components - screen 1 (Welcome screen - choose number of players)
    private final JFrame gameFrame;
    private final Container container;
    // font colors
    private final Color background = Color.BLACK;
    private final Color fontsMain = Color.CYAN;
    private final Color fontsButton = Color.GREEN;
    private final Color fontsSecondary = Color.WHITE;
    // Menu Top Bar
    private final JMenuBar menuBar;
    // fonts
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 70);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);
    private JMenuItem saveMenuItem;
    private JMenuItem loadMenuAction;
    private JMenuItem loadCustomMap;
    private int playerCount, aiPlayerCount;
    private JPanel gameTfPanel;
    private JTextArea gameTextField;
    private JPanel buttonGrid;
    private JButton[] buttons;
    //components - aicount screen - (Choose number of ai players)
    private JPanel aiPanel;
    private JTextArea aiTextField;
    private JPanel aiButtonGrid;
    private JButton[] aiButtons;
    // components - screen 3 - (Add names of players and bots)
    private JPanel mainTextPanel;
    private JTextArea mainTextArea;
    private JPanel inputPanel;
    private JTextField[] names;
    private JPanel okButtonPanel;
    private JButton ok;
    // components - 4 - main game screen (shows game board, who's turn, status of the game)
    private JPanel topPanel;
    private JLabel topLabel;
    private JPanel middlePanel;
    private JPanel bottomPanelRight;
    private JTextArea[] middleTextArea;
    private JPanel statusPanel2;
    private JTextArea statusLabel2;
    private JTextArea bottomLabelRight;
    private JTextArea statusLabel;
    private JPanel statusPanel;

    /**
     * Constructor - GameView
     */
    public GameView() {

        gameFrame = new JFrame("RISK: Global Domination");
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = gameFrame.getContentPane();
        container.setBackground(background);
        gameFrame.setSize(1200, 800);
        gameFrame.setVisible(true);

        //Create the menu bar.
        menuBar = new JMenuBar();
        buildMenuBar();
        gameFrame.setJMenuBar(menuBar);

    }

    private void buildMenuBar() {

        // Build the file menu
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Main Menu");
        menuBar.add(menu);

        // Save Menu Point
        saveMenuItem = new JMenuItem("Save Game",
                KeyEvent.VK_T);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        saveMenuItem.getAccessibleContext().setAccessibleDescription(
                "Save current game");
        menu.add(saveMenuItem);

        // Save Action
        loadMenuAction = new JMenuItem("Load Game",
                KeyEvent.VK_T);
        loadMenuAction.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        loadMenuAction.getAccessibleContext().setAccessibleDescription(
                "Load a saved game");
        menu.add(loadMenuAction);
        menu.addSeparator();
        // Load Custom Map
        loadCustomMap = new JMenuItem("Load Custom Map",
                KeyEvent.VK_T);
        loadCustomMap.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        loadCustomMap.getAccessibleContext().setAccessibleDescription(
                "Load a custom map");
        menu.add(loadCustomMap);
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
            b.setForeground(fontsButton);
            b.setFont(titleFont);
            b.setFocusPainted(false);
            b.setActionCommand(i + 2 + "");
            buttonGrid.add(b);
            buttons[i] = b;
        }

        gameTfPanel.add(iconLabel, BorderLayout.NORTH);
        gameTfPanel.add(gameTextField, BorderLayout.SOUTH);
        container.add(buttonGrid);
        container.add(gameTfPanel);
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
            b.setForeground(fontsButton);
            b.setFont(titleFont);
            b.setFocusPainted(false);

            b.setActionCommand(i + "");
            aiButtonGrid.add(b);
            aiButtons[i] = b;
        }

        aiPanel.add(aiTextField);
        container.add(aiButtonGrid);
        container.add(aiPanel);
    }

    // screen 3: Get Names of Players
    public void createPlayerNameScreen() {
        aiPanel.setVisible(false);
        aiButtonGrid.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 1000, 200);
        mainTextPanel.setBackground(background);
        container.add(mainTextPanel);

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
        container.add(inputPanel);

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

        okButtonPanel = new JPanel();
        okButtonPanel.setBounds(250, 550, 300, 100);
        okButtonPanel.setBackground(background);
        container.add(okButtonPanel);

        ok = new JButton("OK");
        ok.setBackground(background);
        ok.setForeground(fontsButton);
        ok.setFont(titleFont);

        okButtonPanel.add(ok);

    }

    // screen 4: Main game screen
    public void createGameScreen() {

        if (mainTextPanel != null) mainTextPanel.setVisible(false);
        if (inputPanel != null) inputPanel.setVisible(false);
        if (okButtonPanel != null) okButtonPanel.setVisible(false);

        if (gameTfPanel != null) gameTfPanel.setVisible(false);
        if (buttonGrid != null) buttonGrid.setVisible(false);

        if (aiPanel != null) aiPanel.setVisible(false);
        if (aiButtonGrid != null) aiButtonGrid.setVisible(false);

        topPanel = new JPanel();
        topPanel.setBounds(200, 10, 800, 100);
        topPanel.setBackground(background);
        container.add(topPanel);

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
        container.add(middlePanel);

        middleTextArea = new JTextArea[playerCount];

        for (int i = 0; i < playerCount; i++) {
            JTextArea middleTextArea = new JTextArea("This is the main board");
            middleTextArea.setBackground(background);
            middleTextArea.setForeground(fontsSecondary);
            middleTextArea.setLineWrap(false);
            this.middleTextArea[i] = middleTextArea;
            middlePanel.add(middleTextArea);
        }

        statusPanel2 = new JPanel();
        statusPanel2.setBounds(50, 580, 650, 175);
        statusPanel2.setBackground(background);

        container.add(statusPanel2);

        statusLabel2 = new JTextArea("Game Status :");
        statusLabel2.setFont(normalFont);
        statusLabel2.setBackground(background);
        statusLabel2.setForeground(fontsMain);

        statusPanel = new JPanel();
        statusPanel.setBounds(50, 480, 625, 100);
        statusPanel.setBackground(background);

        container.add(statusPanel);

        statusLabel = new JTextArea("");
        statusLabel.setFont(normalFont);
        statusLabel.setBackground(background);
        statusLabel.setForeground(fontsSecondary);

        statusPanel2.add(statusLabel2);
        statusPanel.add(statusLabel);

        bottomPanelRight = new JPanel();
        bottomPanelRight.setBounds(675, 510, 400, 450);
        bottomPanelRight.setBackground(background);
        container.add(bottomPanelRight);

        bottomLabelRight = new JTextArea();
        bottomLabelRight.setBackground(background);
        bottomLabelRight.setForeground(fontsSecondary);
        bottomPanelRight.add(bottomLabelRight);
    }

    /**
     * Prints the game report onto the Game Board section in GUI
     *
     * @param players - list of players
     */
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
                player.getOccupiedCountries().size() + " countries & " + player.getOccupiedContinents().size() +
                " continents)\n" + "Select number which country you want to land soldiers: ");
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
            bottomLabelRight.setText(status);
            if (count != 1) {
                statusLabel2.setText(message);

                int[] a = IntStream.range(1, count).toArray();
                Object[] options = new Object[a.length];
                // copy elements from object array to integer array
                for (int i = 0; i < a.length; i++) {
                    options[i] = a[i];
                }
                number = (int) (JOptionPane.showInputDialog(statusPanel2, "Select country number",
                        "Select Country Number", JOptionPane.PLAIN_MESSAGE, null, options, options[0]));

                if (number >= 1 && number < count) {
                    number--;
                    break;
                } else {
                    statusLabel2.setText("Please select correct number!!");
                }
            } else {
                statusLabel2.setText("Sorry! You don't hold any country.");
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
            bottomLabelRight.setText(status);
            if (count != 1) {
                statusLabel2.setText(message);

                Random r = new Random();
                int rand = r.nextInt(count);

                if (rand == 0)
                    rand++;
                number = rand;

                JOptionPane.showMessageDialog(statusPanel2, "AI selected country number:" + number,
                        "AI Selected Country", JOptionPane.PLAIN_MESSAGE, null);
                number--;
                break;
            } else {
                statusLabel2.setText("Sorry! You don't hold any country.");
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
            bottomLabelRight.setText(status);
            if (count != 1) {
                statusLabel.setText("");
                statusLabel2.setText("Select number where to attack: ");

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
                    statusLabel.setText("Please select correct number!!");
                }
            } else {
                System.out.println("Sorry! There is no any enemy country near you, Try Again.");
                statusLabel.setText("Sorry! There is no any enemy country near you,\n Try Again.");
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
            bottomLabelRight.setText(status);

            if (count != 1) {
                statusLabel2.setText("AI selected to attack: ");

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
                statusLabel.setText("Sorry! There is no any enemy country near you,\n Try Again.");
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
            bottomLabelRight.setText(status);
            if (count != 1) {
                statusLabel.setText("");
                statusLabel2.setText("Select number where to move troupes: ");

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
                    statusLabel.setText("Please select correct number!!");
                }
            } else {
                System.out.println("Sorry! There is no any enemy country near you, Try Again.");
                statusLabel.setText("Sorry! There is no any enemy country near you,\n Try Again.");
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
            bottomLabelRight.setText(status);

            if (count != 1) {
                statusLabel2.setText("AI selected to move troupes to: ");

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
                statusLabel.setText("Sorry! There is no any enemy country near you,\n Try Again.");
                number = -1;
                break;
            }
        }
        return number;
    }

    public int numberOfSoliders(int maxSoldiers) {

        int[] a = IntStream.range(1, maxSoldiers).toArray();

        Object[] options = new Object[a.length];

        // copy elements from object array to integer array
        for (int i = 0; i < a.length; i++) {
            options[i] = a[i];
        }

        return (int) (JOptionPane.showInputDialog(null, "How many soldiers to move?",
                "Select number of soldiers", JOptionPane.PLAIN_MESSAGE, null, options, options[0]));
    }

    public void conquered(Player player, Country country) {
        statusLabel.setText(player.getName() + " conquered " + country.getName() + "!!!");
    }

    public void fortification() {
        topLabel.setText("FORTIFICATION");
    }

    public void errorFortification() {
        statusLabel.setText("");
        statusLabel2.setText("You don't have enough soldiers to move.");
    }

    public void errorMessage(String textLabel1, String textLabel2) {
        statusLabel.setText(textLabel1);
        statusLabel2.setText(textLabel2);
    }

    public void moveTroupes() {
        topLabel.setText("MOVE TROUPES PHASE");
    }

    public void fail(Player player, Country country) {
        statusLabel.setText(player.getName() + " failed to conquered " + country.getName() + "!!!");
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

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public JTextField[] getNames() {
        return names;
    }

    public int getAiPlayerCount() {
        return aiPlayerCount;
    }

    public void setAiPlayerCount(int aiPlayerCount) {
        this.aiPlayerCount = aiPlayerCount;
    }

    public void setActionOnSaveMenuItem(ActionListener function) {
        if (saveMenuItem != null) {
            saveMenuItem.addActionListener(function);
        }
    }

    public void setActionOnLoadMenuItem(ActionListener function) {
        if (loadMenuAction != null) {
            loadMenuAction.addActionListener(function);
        }
    }

    public void setActionOnLoadMApMenuItem(ActionListener function) {
        if (loadCustomMap != null) {
            loadCustomMap.addActionListener(function);
        }
    }

    public void showMessageDialog(String message){
        JOptionPane.showMessageDialog(gameFrame, message);
    }

}
