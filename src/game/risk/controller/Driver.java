package game.risk.controller;

import game.risk.model.GameModel;
import game.risk.view.GameView;

/**
 * Driver Class - Contains the main function which creates
 * the view, model and controller objects and executes them.
 * @author Tejash
 * @version 1.0
 */
public class Driver {

    // main function
    public static void main(String[] args) {

        GameView view = new GameView();
        GameModel model = new GameModel();
        GameController controller = new GameController(model, view);
        controller.execute();

    }
}
