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

> For milestone 4: refer to Step 1 (Custom Map), Step 11 (Save Game), and Step 12 (Load Game)
0) Optional step: Load a custom map
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
13) Save the game by going to File->Save Game

## Design Decisions
The purpose of this milestone is to have additional features to our game. When a player holds a hole continent, he should be receiving more armies when reinforcement happens at the beggining of the player's turn. The number of bonus armies depend on which continent you have occupied. The reinforcement happens before the attack. After the attack, the player can choose to move his troup from a country to another country connected to it by being occupied by the same player. The player should keep at least one troupe in each of his conquered countires. He can only to the movement once. An "AI" player is also implemented where any number of in the game can be assigned to be an "AI" player.

For milestone 4, the following design decisions were made: 
- Added Menu in View
- Added JChooser for save/open file into GameController
- Added ReadStringFromAFile into DataFetcher
- Added method for save state of game in GameController
- Added method for load state of game in GameController\
- Added JsonHandler convert Object to/from String
- Added Gson (Google Library for json) library in lib folder
- Added class SaveData with contain

## List of Contributions
- Lynn, Tejash added the save/load game feature and custom maps feature
- Jatin, Adityo worked on bugs and refactoring
- All members helped in writing the documentation including diagrams
- All members contributed in the regular meetings and recommanding ideas

## Known issues for milestone 4
1. Player name can be an empty string.
2. For save game, user have to stop action (dialog) and save after
3. Load will start the game from player 1, because no turn is stored currently
4. Custom Map don't check countries number

## Feedback implemented from milestone 3
1. Button text color changed from white to green!

# Roadmap Ahead
- Work on known issues of milestone 4
- Continue improving the game after course is over and add additional features like a graph for representing countries and much more!
