package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the game state by providing methods to save and load the game state from a file.
 */
public class GameStateManager {

  private static final String SERIALIZED_GAME_PATH = "src/main/resources/savedGameState.ser";
  private static final Logger LOGGER = Logger.getLogger(GameStateManager.class.getName());

  /**
   * Saves the current game state to a serialized file.
   *
   * @param game The current game state to be saved.
   */
  public static void saveGameState(ChaosGame game) {
    LOGGER.log(Level.INFO, "Saving game state.");
    try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream(SERIALIZED_GAME_PATH))) {
      oos.writeObject(game);
      LOGGER.log(Level.INFO, "Game state saved successfully in {0}", SERIALIZED_GAME_PATH);
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Failed to save game state.", e);
    }
  }

  /**
   * Loads the game state from a serialized file. If the file does not exist or loading fails,
   * a new game state is created.
   *
   * @return The loaded game state, or a new game state if the file does not exist or loading fails.
   */
  public static ChaosGame loadGameState() {
    LOGGER.log(Level.INFO, "Loading game state.");
    File file = new File(SERIALIZED_GAME_PATH);
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        ChaosGame loadedGame = (ChaosGame) ois.readObject();
        LOGGER.log(Level.INFO, "Game state loaded successfully.");
        return loadedGame;
      } catch (IOException | ClassNotFoundException e) {
        LOGGER.log(Level.WARNING, "Failed to load game state. Creating new game.", e);
      }
    } else {
      LOGGER.log(Level.WARNING, "No saved game state found. Creating new game.");
    }
    return createNewGame();
  }

  /**
   * Creates a new game state with default settings.
   *
   * @return The newly created game state.
   */
  private static ChaosGame createNewGame() {
    ChaosGame newGame = new ChaosGame(
        ChaosGameDescriptionFactory.get(
            ChaosGameDescriptionFactory.DescriptionTypeEnum.SIERPINSKI_TRIANGLE),
        650, 650);
    newGame.setDescriptionName(
        ChaosGameDescriptionFactory.DescriptionTypeEnum.SIERPINSKI_TRIANGLE.toString());
    return newGame;
  }
}

