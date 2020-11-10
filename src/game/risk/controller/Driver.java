package game.risk.controller;

import game.risk.view.GameView;

/**
 * Opens and runs through the game sequence.
 * @param args
 */
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
