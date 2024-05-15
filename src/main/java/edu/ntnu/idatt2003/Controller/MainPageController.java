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

  public ChaosGame getGame() {
    return game;
  }

  public void runSteps(int steps) {
      game.runSteps(steps);
  }

  public void changeTransformation(ChaosGameDescriptionFactory.descriptionTypeEnum descriptionType) {
    game.changeTransformation(descriptionType);
  }

}
