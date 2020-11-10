# RISK-Global-Domination
 A simplified version of the classic strategy game RISK
 
 ### Group Members:
 * [Adityo Sarkar](https://github.com/theadityo)
 * [Jatin Gartan](https://github.com/JatinGartan)
 * [Lynn Mehyou](https://github.com/lynnmehyou)
 * [Tejash Patel](https://github.com/tejash3402)

### Moderator Teaching Assistant
* [Joe Samuel](https://github.com/joefsamuel)

### User Manual
1) Run the jar file
2) Input the number of players 
3) Enter the player's names
4) The game randomizes countries for each player
5) Each player is given a certain number of troops at the start of the match based on amount of players
6) Each player takes a turn placing his or her troops on their territories
7) Game makes available a minimum of 3 troops for each player to place at the beginning of each turn; with more available based on amount of territories owned, entire continents controlled and any cards played 
8) Player should enter which country he wants to attack and which country he will attack from; can only attack a connected territory.
9) Step 8 is repeated until either there is no more troops left to attack (1 troop on each territory) or the player decides to stop
10) If a player conquers a territory in their turn, they receive a card
11) Player should then fortify territories based on need; can only move from one location to another as many troops as needed, must keep at least 1 troop on territory
12) Repeat steps 7-11 for every player
13) When only one player remains they have won

### Design Decisions
We mostly used the ArrayList in the whole program to keep track of the data like list of countries, continents and players. 

For the execution of program, 3 text files we created, which contains the name of countries, their adjacent countries and a 
text file which has all continents and countries within it. The program will ask for the 
number of players and their names and then start the game. It will iterate through all 
players for their turn. It will give 2 extra soldiers in each turn and user can place 
them in any country. The program will ask for country from which we have to generate 
attack and the country on which we have to attack, then it will drop 1 soldier in own 
country and attack with remaining soldiers. 

If attacker have the more wins than it will successfully conquer the country and 
start the process of fortification. It can move soldiers one time in a single turn. 
It will keep going until 1 player defeated all other players and conquered the 
whole world.

### Known issues for milestone 2
1. Player name can be an empty string.
2. In fortification: Select number of soldiers to move doesn't check for valid range.
3. Dice rolls are properly implemented in model, however dice rolls are not shown to the user in the GUI.
4. The JOptionPanel for selecting country number spawns at different location. A good location needs to be found.
