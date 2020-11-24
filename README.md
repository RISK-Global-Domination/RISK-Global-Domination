# RISK-Global-Domination
A simplified version of the classic strategy game RISK
 
 ### Group Members:
 * [Adityo Sarkar](https://github.com/theadityo)
 * [Jatin Gartan](https://github.com/JatinGartan)
 * [Lynn Mehyou](https://github.com/lynnmehyou)
 * [Tejash Patel](https://github.com/tejash3402)

### Moderator Teaching Assistant
* [Joe Samuel](https://github.com/joefsamuel)

### List of Deliverables
- README file, design decisions, unit tests, source code, documentation.

### Get Started
1. Clone the repo
2. Add JDK path to the project
3. Run the main function in Driver class

### User Manual
> refer to User_Manual in docs folder for detailed user manual with screenshots
1) Run the jar file
2) Input the number of players 
3) Add number of bots
4) Enter the player and bot's names
5) The game randomizes countries for each player
7) Game makes available certain bonus army for each player to place at the beginning of each turn; with more available based on amount of territories owned, entire continents controlled
8) Player should enter which country he wants to attack and which country he will attack from; can only attack a connected territory.
9) Step 8 is repeated until either there is no more troops left to attack (1 troop on each territory) or the player decides to stop
10) Player then enters Troupe Movement Phase; can only move armies from one location to another as many troops as needed, must keep at least 1 troop on territory
11) Repeat steps 7-11 for every player
12) When only one player remains they have won

## Design Decisions
The purpose of this milestone is to implement a GUI-based version of the Game using MVC design. We will have a working user interface where the user input is via mouse.

In Milestone 1, we already had the MVC model in our code, therefor what we needed to do for this Milestone is to add the JFrame. For the Game Controller, we decided to split the execution to 3 parts, as we would like to execute asking for the number of players, setting up and starting the game seperately. We added listeners to these events. In the GameView, we set up the game's frame by designing what we would appear to on the screen of the user. We also designed the buttons to generate the different events, for example when we select 2 players, it will ask for two player's names and play the game for 2 players. We also added test cases for the Model.

For the next milestone, we are suppose to add features such as bonus army placement for holding whole continents, troupe movement phase, reinforcement of armies proportional to the number of countries held and an "AI" player. We are suppose to make a smell free code, the next challenge would be implement the "AI" player.

## List of Contributions
- Tejash, Jatin added bonus army feature, ai player and troupe movement phase
- Lynn, Adityo worked on bugs and refactoring
- All members helped in writing the documentation including diagrams
- All members contributed in the regular meetings and recommanding ideas

## Known issues for milestone 3
1. Player name can be an empty string.
3. Attack Phase bug -> currently troops don't die, fix this
4. Dice rolls are properly implemented in model, however dice rolls are not shown to the user in the GUI.
5. The JOptionPanel for selecting country number spawns at different location. A good location needs to be found.
6. The view and controller file ended up a little long. It needs to break into smaller classes.

## Feedback implemented from milestone 2
1. Made the button text a bit bigger and bolder.
2. Select extra soldiers made a dropdown, so only valid input can be selected.
3. SetUp() and TearDown() methods created in unit test for common intializations.
4. Efforts were made so pull requests gets reviewed by atleast one team member.

# Roadmap Ahead
- Work on known issues of milestone 3
- Improve the Game Board design by adding World image and using graph for connecting countries
- Add Save/Load features and Custom Maps to the game.