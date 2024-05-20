package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ChaosGame, covering both positive and negative test cases.
 */
public class ChaosGameTest {

  private ChaosGame chaosGame;
  private ChaosGameDescription description;
  private TestObserver observer;

  /**
   * Sets up the test environment before each test.
   * Initializes the ChaosGame with a description and registers an observer.
   */
  @BeforeEach
  public void setUp() {

    AffineTransform2D transform1 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(1, 1));
    AffineTransform2D transform2 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(-1, -1));

    description = new ChaosGameDescription(new Vector2d(0, 0), new Vector2d(10, 10), Arrays.asList(transform1, transform2));

    chaosGame = new ChaosGame(description, 10, 10);

    observer = new TestObserver();
    chaosGame.registerObserver(observer);
  }

  /**
   * Positive test cases for ChaosGame.
   */
  @Nested
  @DisplayName("Positive Tests")
  class PositiveTests {

    /**
     * Tests the constructor of ChaosGame.
     * Verifies that the ChaosGame object is properly initialized.
     */
    @Test
    @DisplayName("Constructor Test")
    public void testConstructor() {
      assertNotNull(chaosGame);
      assertEquals(10, chaosGame.getCanvas().getWidth());
      assertEquals(10, chaosGame.getCanvas().getHeight());
    }

    /**
     * Tests the getCanvas() method of ChaosGame.
     * Verifies that the canvas is correctly retrieved.
     */
    @Test
    @DisplayName("Get Canvas Test")
    public void testGetCanvas() {
      assertNotNull(chaosGame.getCanvas());
    }

    /**
     * Tests the getTotalSteps() method of ChaosGame.
     * Verifies that the total steps are initially zero.
     */
    @Test
    @DisplayName("Get Total Steps Test")
    public void testGetTotalSteps() {
      assertEquals(0, chaosGame.getTotalSteps());
    }

    /**
     * Tests running steps and updating the total steps.
     * Verifies that the total steps are updated correctly.
     */
    @Test
    @DisplayName("Run Steps and Update Total Test")
    public void testRunStepsAndUpdateTotal() {
      chaosGame.runStepsAndUpdateTotal(5);
      assertEquals(5, chaosGame.getTotalSteps());
    }

    /**
     * Tests running steps without updating the total steps.
     * Verifies that the total steps remain unchanged.
     */
    @Test
    @DisplayName("Run Steps without Updating Total Test")
    public void testRunStepsWithoutUpdatingTotal() {
      chaosGame.runStepsWithoutUpdatingTotal(5);
      assertEquals(0, chaosGame.getTotalSteps());
    }

    /**
     * Tests changing the transformation of ChaosGame.
     * Verifies that the new description is correctly set.
     */
    @Test
    @DisplayName("Change Transformation Test")
    public void testChangeTransformation() {
      AffineTransform2D newTransform1 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(2, 2));
      AffineTransform2D newTransform2 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(-2, -2));
      ChaosGameDescription newDescription = new ChaosGameDescription(new Vector2d(0, 0), new Vector2d(10, 10), Arrays.asList(newTransform1, newTransform2));

      chaosGame.changeCustomTransformation(newDescription);

      assertEquals(newDescription, chaosGame.getDescription());
    }

    /**
     * Tests changing to a custom transformation of ChaosGame.
     * Verifies that the custom description is correctly set.
     */
    @Test
    @DisplayName("Change Custom Transformation Test")
    public void testChangeCustomTransformation() {
      AffineTransform2D customTransform1 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(3, 3));
      AffineTransform2D customTransform2 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(-3, -3));
      ChaosGameDescription customDescription = new ChaosGameDescription(new Vector2d(0, 0), new Vector2d(10, 10), Arrays.asList(customTransform1, customTransform2));

      chaosGame.changeCustomTransformation(customDescription);

      assertEquals(customDescription, chaosGame.getDescription());
    }

    /**
     * Tests setting a new description in ChaosGame.
     * Verifies that the new description is correctly set.
     */
    @Test
    @DisplayName("Set Description Test")
    public void testSetDescription() {
      AffineTransform2D newTransform1 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(4, 4));
      AffineTransform2D newTransform2 = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2d(-4, -4));
      ChaosGameDescription newDescription = new ChaosGameDescription(new Vector2d(0, 0), new Vector2d(10, 10), Arrays.asList(newTransform1, newTransform2));

      chaosGame.setDescription(newDescription);

      assertEquals(newDescription, chaosGame.getDescription());
    }

    /**
     * Tests the getDescription() method of ChaosGame.
     * Verifies that the description is correctly retrieved.
     */
    @Test
    @DisplayName("Get Description Test")
    public void testGetDescription() {
      assertEquals(description, chaosGame.getDescription());
    }

    /**
     * Tests registering an observer in ChaosGame.
     * Verifies that the observer is registered and notified.
     */
    @Test
    @DisplayName("Register Observer Test")
    public void testRegisterObserver() {
      TestObserver newObserver = new TestObserver();
      chaosGame.registerObserver(newObserver);
      chaosGame.notifyObservers();
      assertTrue(newObserver.isUpdated());
    }

    /**
     * Tests notifying observers in ChaosGame.
     * Verifies that the observers are notified.
     */
    @Test
    @DisplayName("Notify Observers Test")
    public void testNotifyObservers() {
      chaosGame.notifyObservers();
      assertTrue(observer.isUpdated());
    }
  }

  /**
   * Negative test cases for ChaosGame.
   */
  @Nested
  @DisplayName("Negative Tests")
  class NegativeTests {

    /**
     * Tests removing an observer from ChaosGame.
     * Verifies that the observer is not notified after removal.
     */
    @Test
    @DisplayName("Remove Observer Test")
    public void testRemoveObserver() {
      chaosGame.removeObserver(observer);
      chaosGame.notifyObservers();
      assertFalse(observer.isUpdated());
    }
  }

  /**
   * A simple implementation of ChaosGameObserver for testing purposes.
   */
  private static class TestObserver implements ChaosGameObserver {
    private boolean updated = false;

    @Override
    public void update() {
      updated = true;
    }

    public boolean isUpdated() {
      return updated;
    }
  }
}