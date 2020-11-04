package game.risk.controllers;

import game.risk.views.GameView;

public class Driver {

	// 
	public static void main(String[] args) {
		
		//
		GameView view = new GameView();
		GameModel model = new GameModel();
		GameController controller = new GameController(model, view);
		controller.execute();
		
	}
	
}
