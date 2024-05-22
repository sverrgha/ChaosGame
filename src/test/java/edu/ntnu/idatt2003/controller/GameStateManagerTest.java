package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescriptionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GameStateManager class.
 * This class contains tests for verifying the functionality of the GameStateManager.
 */
public class GameStateManagerTest {

  @TempDir
  Path tempDir;

  private Path tempFilePath;
  private static final Logger LOGGER = Logger.getLogger(GameStateManager.class.getName());
  private ChaosGame game;

  @BeforeEach
  public void setUp() {
    tempFilePath = tempDir.resolve("savedGameStateTest.ser");

    game = new ChaosGame(
        ChaosGameDescriptionFactory.get(
            ChaosGameDescriptionFactory.DescriptionTypeEnum.SIERPINSKI_TRIANGLE),
        650, 650);
    game.setDescriptionName(
        ChaosGameDescriptionFactory.DescriptionTypeEnum.SIERPINSKI_TRIANGLE.toString());
  }

  @Nested
  @DisplayName("Positive Tests")
  class positiveTest {

    /**
     * Tests that the game state is saved correctly.
     */
    @Test
    @DisplayName("Save game state")
    public void testSaveGameState() {
      GameStateManager.saveGameState(game);

      assertTrue(Files.exists(tempFilePath), "Saved game state file should exist");
    }

    /**
     * Tests that the game state is loaded correctly.
     */
    @Test
    @DisplayName("Load game state")
    public void testLoadGameState() {
      GameStateManager.saveGameState(game);

      ChaosGame loadedGame = GameStateManager.loadGameState();
      assertNotNull(loadedGame, "Loaded game state should not be null");
      assertEquals(game.getDescriptionName(), loadedGame.getDescriptionName(),
          "Loaded game state should match saved state");
    }

    /**
     * Tests that a new game state is created when no saved game state exists.
     */
    @Test
    @DisplayName("Load new game state when no saved state exists")
    public void testLoadNewGameStateWhenNoSavedStateExists() {
      assertNotNull(game, "New game state should not be null");
      assertEquals("SIERPINSKI_TRIANGLE", game.getDescriptionName(),
          "New game state should have default description");
    }


  }

  @Nested
  @DisplayName("Negative tests")
  class negativeTests {

  /**
   * Tests that an IOException is handled gracefully when saving the game state.
   */
  @Test
  @DisplayName("Handle IOException during save")
  public void testHandleIOExceptionDuringSave() {
    tempFilePath.toFile().setReadOnly();

    assertDoesNotThrow(() -> GameStateManager.saveGameState(game),
        "Saving game state should not throw an exception");
  }

  /**
   * Tests that an IOException is handled gracefully when loading the game state.
   */
  @Test
  @DisplayName("Handle IOException during load")
  public void testHandleIOExceptionDuringLoad() {
    try {
      Files.write(tempFilePath, new byte[]{0, 1, 2, 3});
    } catch (IOException e) {
      fail("Unexpected exception writing to temp file: " + e.getMessage());
    }

    ChaosGame loadedGame = assertDoesNotThrow(() -> GameStateManager.loadGameState(),
        "Loading game state should not throw an exception");
    assertNotNull(loadedGame, "Loaded game state should not be null despite IOException");
  }
}

}
