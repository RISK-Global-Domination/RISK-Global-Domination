package game.risk.views;

import java.util.ArrayList;
import java.util.Scanner;

import game.risk.models.Country;
import game.risk.models.Player;

/**
 *RETURN THE REPORT OF THE GAME, FETCHES PLAYERS, THE COUNTRIES ATTACKED AND ERRORS.
 */
public class GameView {

	// Attributes...
	private Scanner scan;
	
	public GameView() {
		
		scan = new Scanner(System.in);
		System.out.println("*** RISK GAME ***");
		
	}

	// Fetching players..
	public ArrayList<Player> fetchPlayers() {
		
		ArrayList<Player> players = new ArrayList<>();
		System.out.print("How many players (2/3/4/5/6): ");
		int playerCount = Integer.parseInt(scan.nextLine());
		int armies = 0;
		switch(playerCount) {
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
		for(int i = 0; i < playerCount; i++) {
			
			System.out.print("Enter player "+(i+1)+" name: ");
			String name = scan.nextLine();
			players.add(new Player(name, armies));
			
		}
		return players;
		
	}
	

	public void completeReport(ArrayList<Player> players) {
	
		for(Player player: players) {
			System.out.println(player.getName()+" Armies");
			int count = 1;
			for(Country country: player.getOccupiedCountries()) {
				System.out.printf("\t%d: %-25s %s\n", count, country.getName(), "(Armies: "+country.getArmies()+")");
				count++;
			}
		}
		
	}

	// Doing turn.
	public void takeTurn(Player player) {
		
		System.out.println("************************************************");
		System.out.println(">>> "+player.getName()+"'s TURN <<<");
		System.out.println("************************************************");
		
	}
	
	public int getCountryStartAttack(Player player) {
		
		return this.getCountryNumber(player, "Select number which country will attack: ");
		
	}
	
	public int fromMove(Player player) {
		
		return this.getCountryNumber(player, "Select number from which country you want to move soliders: ");
		
	}
	
	public int toLand(Player player, int number) {
		
		return this.getCountryNumber(player, "You have "+number+" extra soliders\n"
				+ "Select number which country you want to land soliders: ");
		
	}
	
	public int getCountryNumber(Player player, String message) {
		
		int number = 0;
		while(true) {
			int count = 1;
			for(Country country: player.getOccupiedCountries()) {
				System.out.printf("\t%d: %-25s %s\n", count, country.getName(), "(Armies: "+country.getArmies()+")");
				count++;
			}
			if(count != 1) {
				System.out.print(message);
				number = Integer.parseInt(scan.nextLine());
				if(number >= 1 && number < count) {
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
		while(true) {
			int count = 1;
			for(Country country: attacking.getJoining()) {
				if(country.getOccupant() != player) {
					System.out.printf("\t%d: %-25s %s\n", count, country.getName(), "(Armies: "+country.getArmies()+")");
					count++;
				}
			}
			if(count != 1) {
				System.out.print("Select number where to attack: ");
				number = Integer.parseInt(scan.nextLine());
				if(number >= 1 && number < count) {
					int index = 0;
					count = 1;
					for(Country country: attacking.getJoining()) {
						if(count == number) {
							number = index;
							break;
						}
						if(country.getOccupant() != player) {
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
		System.out.println(player.getName()+" conquered "+country.getName());
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
		System.out.println(player.getName()+" failed to conquered "+country.getName());
		System.out.println("********************* FAILED *****************");
		
	}
	
}
