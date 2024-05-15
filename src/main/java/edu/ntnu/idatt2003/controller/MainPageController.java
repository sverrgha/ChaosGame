package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.view.MainPageView;
import edu.ntnu.idatt2003.view.MainPageView.TransformationType;
import java.util.List;

/**
 * The controller class for the main page of the ChaosGame application.
 * This class is responsible for initializing the game and the view,
 * and handling events from the view.
 */
public class MainPageController {
  private ChaosGame game;
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
    this.view.render(game.getCanvas());
  }

  /**
   * Get the view of the main page.
   *
   * @return the view of the main page.
   */
  public MainPageView getView() {
    return view;
  }

  public void runSteps(int steps) {
    if (steps < 0) {
      game.getCanvas().clear();
    } else {
      game.runSteps(steps);
    }
    view.render(game.getCanvas());
  }

  public void changeTransformation(ChaosGameDescriptionFactory.descriptionTypeEnum descriptionType) {
    this.game = new ChaosGame(ChaosGameDescriptionFactory
            .get(descriptionType), 600, 600);
    view.render(game.getCanvas());
  }

  public void addNewTransformation(Vector2d minCoords, Vector2d maxCoords,
      List<Transform2D> transform, TransformationType transformationType, String TransformationName) {
    ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
    ChaosGameDescription newChaosGameDescription = new ChaosGameDescription(minCoords, maxCoords, transform);

    //switch (transformationType) {
      //case JULIA -> chaosGameFileHandler.writeToFile(newChaosGameDescription, "src/main/resources/" + TransformationName);
      //case AFFINE -> chaosGameFileHandler.writeToFile(newChaosGameDescription, "src/main/resources/" + TransformationName);
    //method must define affine or julia
  }

}



