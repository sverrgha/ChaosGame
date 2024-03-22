package edu.ntnu.idatt2003.model;

import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChaosGameFileHandlerTest {
  private static ChaosGameFileHandler fileHandler;
  private static String affine2dTextFilePath;
  private static String juliaTextFilePath;
  private static ChaosGameDescription affine2DDescription;
  private static ChaosGameDescription juliaDescription;

  @BeforeAll
  static void setUp() {
    fileHandler = new ChaosGameFileHandler();
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
            List.of(new JuliaTransform(new Complex(-0.74543, 0.11301), 1))
    );
  }
  @AfterEach
  void cleanUp() {
    try {
        Files.deleteIfExists(Paths.get(("src/test/resources/Affine2DExampleOutput.txt")));
        Files.deleteIfExists(Paths.get(("src/test/resources/JuliaExampleOutput.txt")));
        } catch (Exception e) {
        fail(e.getMessage());
    }
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("Test readFromfile with Affine2DTransform")
    void testReadFromFileWithAffine2D() {
      try {
        assertEquals(affine2DDescription.toString(), fileHandler.readFromFile(affine2dTextFilePath).toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }

    @Test
    @DisplayName("Test readFromfile with JuliaTransform")
    void testReadFromFileWithJulia() {
      try {
        assertEquals(juliaDescription.toString(), fileHandler.readFromFile(juliaTextFilePath).toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }
    @Test
    @DisplayName("Test writeToFile with Affine2DTransform")
    void testWriteToFileWithAffine2D() {
      try {
        fileHandler.writeToFile(affine2DDescription, "src/test/resources/Affine2DExampleOutput.txt");
        assertEquals(affine2DDescription.toString(), fileHandler.readFromFile("src/test/resources/Affine2DExampleOutput.txt").toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }
    @Test
    @DisplayName("Test writeToFile with JuliaTransform")
    void testWriteToFileWithJulia() {
      try {
        fileHandler.writeToFile(juliaDescription, "src/test/resources/JuliaExampleOutput.txt");
        assertEquals(juliaDescription.toString(), fileHandler.readFromFile("src/test/resources/JuliaExampleOutput.txt").toString());
      } catch (FileNotFoundException e) {
        fail(e.getMessage());
      }
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Test readChaosGameDescription with non-existing file")
    void testReadChaosGameDescriptionWithNonExistingFile() {
      assertThrows(FileNotFoundException.class, () -> fileHandler.readFromFile("non-existing-file.txt"));
    }
  }
}
