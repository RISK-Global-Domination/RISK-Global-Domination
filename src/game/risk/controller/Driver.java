package game.risk.controller;

import game.risk.view.GameView;

/**
 * GAME'S DRIVER
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
