# RISK-Global-Domination
 A simplified version of the classic strategy game RISK
 
 ### Group Members:
 * [Adityo Sarkar](https://github.com/theadityo)
 * [Jatin Gartan](https://github.com/JatinGartan)
 * [Lynn Mehyou](https://github.com/lynnmehyou)
 * [Tejash Patel](https://github.com/tejash3402)

### Moderator Teaching Assistant
* [Joe Samuel](https://github.com/joefsamuel)

### Get Started
1. Clone the repo
2. Add JDK path to the project
3. Run the main function in Driver class

### User Manual
(refer to User_Manual in docs folder for detailed user manual with screenshots)
1) Run the jar file
2) Input the number of players 
3) Enter the player's names
4) The game randomizes countries for each player
5) Each player is given a certain number of troops at the start of the match based on amount of players
6) Each player takes a turn placing his or her troops on their territories
7) Game makes available a minimum of 2 troops for each player to place at the beginning of each turn; with more available based on amount of territories owned, entire continents controlled and any cards played 
8) Player should enter which country he wants to attack and which country he will attack from; can only attack a connected territory.
9) Step 8 is repeated until either there is no more troops left to attack (1 troop on each territory) or the player decides to stop
10) Player should then fortify territories based on need; can only move from one location to another as many troops as needed, must keep at least 1 troop on territory
11) Repeat steps 7-11 for every player
12) When only one player remains they have won

### Design Decisions
The purpose of this milestone is to implement a GUI-based version of the Game using MVC design. We will have a working user interface where the user input is via mouse.

In Milestone 1, we already had the MVC model in our code, therefor what we needed to do for this Milestone is to add the JFrame. For the Game Controller, we decided to split the execution to 3 parts, as we would like to execute asking for the number of players, setting up and starting the game seperately. We added listeners to these events. In the GameView, we set up the game's frame by designing what we would appear to on the screen of the user. We also designed the buttons to generate the different events, for example when we select 2 players, it will ask for two player's names and play the game for 2 players. We also added test cases for the Model.

For the next milestone, we are suppose to add features such as bonus army placement for holding whole continents, troupe movement phase, reinforcement of armies proportional to the number of countries held and an "AI" player. We are suppose to make a smell free code, the next challenge would be implement the "AI" player.

### List of Contributions
- Lynn, Adityo worked on the creating the mvc design
- Adityo helped in writing the .txt files for mapdata
- Tejash, Jatin worked on creating the GUI components
- Tejash, Jatin added JUnit tests
- All members contributed in the regular meetings and recommanding ideas
- All members helped in writing the documentation including diagrams

### Known issues for milestone 2
1. Player name can be an empty string.
2. In fortification: Select number of soldiers to move doesn't check for valid range.
3. Pass option is still to be implemented
4. Dice rolls are properly implemented in model, however dice rolls are not shown to the user in the GUI.
5. The JOptionPanel for selecting country number spawns at different location. A good location needs to be found.
6. The view and controller file ended up a little long. It needs to break into smaller classes.

### Feedback implemented from milestone 1
1. Created and added javadocs under documentation
2. Detailed design description was added.
3. List of contribution added in README
4. Github was used for peoject development including Github issues and pull requests.

### List of Deliverables
- README file, design decisions, unit tests, source code, documentation.

### Roadmap Ahead
- Add additional features like bonus army placement and AI player: We made modular code with strict mvc pattern that will help in implementing new features easily
- Add Save/Load features and custom maps: We creation separate .txt files for mapdata which will help ahead in easily implementation these features.
- Work on known issues of milestone 2
- Improve the Game Board design by adding World image and using graph for connecting countries
