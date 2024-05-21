package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ChaosGameFileHandler, covering both positive and negative test cases.
 */
class ChaosGameFileHandlerTest {
  private static String affine2dTextFilePath;
  private static String juliaTextFilePath;
  private static ChaosGameDescription affine2DDescription;
  private static ChaosGameDescription juliaDescription;

  /**
   * Sets up the test environment before all tests.
   * Initializes the ChaosGameFileHandler, file paths, and ChaosGameDescription objects.
   */
  @BeforeAll
  static void setUp() {
    affine2dTextFilePath = "src/test/resources/Affine2DExample.txt";
    List<Transform2D> affine2DTransform = List.of(
        new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2d(0, 0)),
        new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2d(0.25, 0.5)),
        new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2d(0.5, 0))
    );
    affine2DDescription = new ChaosGameDescription(
        new Vector2d(0, 0), new Vector2d(1, 1), affine2DTransform);
    juliaTextFilePath = "src/test/resources/JuliaExample.txt";
    juliaDescription = new ChaosGameDescription(
        new Vector2d(-1.6, -1), new Vector2d(1.6, 1),
        List.of(new JuliaTransform(new Complex(-0.74543, 0.11301), 1),
            new JuliaTransform(new Complex(-0.74543, 0.11301), 1)));
  }

  /**
   * Cleans up the test environment after each test.
   * Deletes any output files created during the tests.
   */
  @AfterEach
  void cleanUp() {
    try {
      Files.deleteIfExists(Paths.get("src/test/resources/Affine2DExampleOutput.txt"));
      Files.deleteIfExists(Paths.get("src/test/resources/JuliaExampleOutput.txt"));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Positive test cases for ChaosGameFileHandler.
   */
  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    /**
     * Tests reading from a file with Affine2D transformations.
     */
    @Test
    @DisplayName("Test readFromFile with Affine2DTransform")
    void testReadFromFileWithAffine2D() {
      try {
        assertEquals(affine2DDescription.toString(), ChaosGameFileHandler.readFromFile(affine2dTextFilePath).toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }

    /**
     * Tests reading from a file with Julia transformations.
     */
    @Test
    @DisplayName("Test readFromFile with JuliaTransform")
    void testReadFromFileWithJulia() {
      try {
        assertEquals(juliaDescription.toString(), ChaosGameFileHandler.readFromFile(juliaTextFilePath).toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }

    /**
     * Tests writing to a file with Affine2D transformations and then reading from it.
     */
    @Test
    @DisplayName("Test writeToFile with Affine2DTransform")
    void testWriteToFileWithAffine2D() {
      try {
        ChaosGameFileHandler.writeToFile(affine2DDescription, "src/test/resources/Affine2DExampleOutput.txt");
        assertEquals(affine2DDescription.toString(), ChaosGameFileHandler.readFromFile("src/test/resources/Affine2DExampleOutput.txt").toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }

    /**
     * Tests writing to a file with Julia transformations and then reading from it.
     */
    @Test
    @DisplayName("Test writeToFile with JuliaTransform")
    void testWriteToFileWithJulia() {
      try {
        ChaosGameFileHandler.writeToFile(juliaDescription,
                "src/test/resources/JuliaExampleOutput.txt");
        assertEquals(juliaDescription.toString(), ChaosGameFileHandler
                .readFromFile("src/test/resources/JuliaExampleOutput.txt").toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }
  }

  /**
   * Negative test cases for ChaosGameFileHandler.
   */
  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    /**
     * Tests reading from a non-existing file.
     * Verifies that a FileNotFoundException is thrown.
     */
    @Test
    @DisplayName("Test readChaosGameDescription with non-existing file")
    void testReadChaosGameDescriptionWithNonExistingFile() {
      assertThrows(FileNotFoundException.class, () -> ChaosGameFileHandler
              .readFromFile("non-existing-file.txt"));
    }

    /**
     * Test reading from a file with an unknown transformation type.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    @DisplayName("Test readChaosGameDescription with unknown transformation type")
    void testReadChaosGameDescriptionWithUnknownTransformationType() {
      assertThrows(IllegalArgumentException.class, () -> ChaosGameFileHandler
              .readFromFile("src/test/resources/invalidNameExample.txt"));
    }

    /**
     * Tests reading from a file with an invalid number of arguments.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    @DisplayName("Test readChaosGameDescription with invalid number of arguments")
    void testReadChaosGameDescriptionWithInvalidNumberOfArguments() {
      assertThrows(InputMismatchException.class, () -> ChaosGameFileHandler
              .readFromFile("src/test/resources/invalidFormatExample.txt"));
    }
  }
}
