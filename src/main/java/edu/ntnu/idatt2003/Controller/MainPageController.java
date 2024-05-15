package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.view.MainPageView;

/**
 * The controller class for the main page of the ChaosGame application.
 * This class is responsible for initializing the game and the view,
 * and handling events from the view.
 */
public class MainPageController {
  private final ChaosGame game;
  private final MainPageView view;

  /**
   * The constructor for the MainPageController class.
   * The constructor initializes the game and the view,
   * and renders the view.
   */
  public MainPageController() {
    this.game = new ChaosGame(ChaosGameDescriptionFactory
            .get(ChaosGameDescriptionFactory.descriptionTypeEnum.SIERPINSKI_TRIANGLE),
            600, 600);
    this.view = new MainPageView(this);
    this.game.registerObserver(view);
    this.view.render();
  }

  /**
   * Get the view of the main page.
   *
   * @return the view of the main page.
   */
  public MainPageView getView() {
    return view;
  }

  /**
   * Get the game of the main page.
   *
   * @return the game of the main page.
   */
  public ChaosGame getGame() {
    return game;
  }

  /**
   * Run the chaos game simulation for the specified number of steps. If
   * the number of steps is negative, the canvas will be cleared.
   *
   * @param steps The number of steps to run the simulation.
   */
  public void runSteps(int steps) {
    game.runSteps(steps);
  }

  /**
   * Change the transformation-type of the chaos game.
   *
   * @param descriptionType The type of fractal description to retrieve.
   */
  public void changeTransformation(ChaosGameDescriptionFactory
                                           .descriptionTypeEnum descriptionType) {
    game.changeTransformation(descriptionType);
  }

}
