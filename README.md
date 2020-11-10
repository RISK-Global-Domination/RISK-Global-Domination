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
The purpose of this milestone is to implement a GUI-based version of the Game using MVC design. We will have a working user interface where the user input is via mouse. 

In Milestone 1, we already had the MVC model in our code, therefor what we needed to do for this Milestone is to add the JFrame. For the Game Controller, we decided to split the execution to 3 parts, as we would like to execute asking for the number of players,  setting up and starting the game seperately. We added listeners to these events. In the GameView, we set up the game's frame by designing what we would appear to on the screen of the user. We also designed the buttons to generate the different events, for example when we select 2 players, it will ask for two player's names and play the game for 2 players. We also added test cases for the Model. 

For the next milestone, we are suppose to add features such as bonus army placement for holding whole continents, troupe movement phase, reinforcement of armies proportional to the number of countries held and an "AI" player. We are suppose to make a smell free code, the next challenge would be implement the "AI" player.
