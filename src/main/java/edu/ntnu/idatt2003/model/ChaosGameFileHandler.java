package edu.ntnu.idatt2003.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A class that handles reading and writing ChaosGameDescriptions to and from files.
 * The class can read from a file and create a ChaosGameDescription object from the file.
 * The class can also write a ChaosGameDescription object to a file.
 * The class uses a delimiter filter to read the file.
 */
public class ChaosGameFileHandler {

  /**
   * Checks if the file exists and reads a ChaosGameDescription using the other
   * readFromFile, if the path is valid.
   *
   * @param path the path to the file.
   * @return The ChaosGameDescription read from the file.
   * @throws FileNotFoundException If the file is not found.
   */
  public ChaosGameDescription readFromFile(String path)
          throws FileNotFoundException {
    try {
      Files.exists(Paths.get(path));
      return readFromFile(new File(path));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File '" + path + "' not found.");
    }
  }

  /**
   * Reads a ChaosGameDescription from a file.
   * Delimiter filter is co-created using ChatGPT.
   *
   * @param file the file to read.
   * @return The ChaosGameDescription read from the file.
   * @throws FileNotFoundException If the file is not found.
   * @throws InputMismatchException If the file has invalid input.
   */
  public ChaosGameDescription readFromFile(File file)
          throws InputMismatchException, FileNotFoundException {
    try (Scanner scanner = new Scanner(file)) {
      scanner.useDelimiter("\\s|,\\s*");
      scanner.useLocale(Locale.ENGLISH);
      String transformType = scanner.nextLine().trim().toLowerCase().split(" ")[0];

      Vector2d minCoords = new Vector2d(readDouble(scanner), readDouble(scanner));
      Vector2d maxCoords = new Vector2d(readDouble(scanner), readDouble(scanner));

      List<Transform2D> transform = parseTransformations(scanner, transformType);

      return new ChaosGameDescription(minCoords, maxCoords, transform);
    } catch (NoSuchElementException e) {
      throw new InputMismatchException("Invalid format in file: " + file.getPath()
              + ". Please try again.");
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File '" + file.getName() + "' not found.");
    }
  }

  private double readDouble(Scanner scanner) {
    double value = scanner.nextDouble();
    if (scanner.hasNextLine() && !scanner.hasNextDouble()) {
      scanner.nextLine();
    }
    return value;
  }


  private List<Transform2D> parseTransformations(Scanner scanner, String transformType) {
    return switch (transformType) {
      case "affine2d" -> parseAffine2DTransformation(scanner);
      case "julia" -> parseJuliaTransformations(scanner);
      default -> throw new IllegalArgumentException("Unknown transformation type: "
              + transformType);
    };
  }

  private List<Transform2D> parseAffine2DTransformation(Scanner scanner) {
    List<Transform2D> transform = new ArrayList<>();
    Matrix2x2 matrix;
    Vector2d vector;
    while (scanner.hasNextLine()) {
      matrix = new Matrix2x2(readDouble(scanner), readDouble(scanner),
              readDouble(scanner), readDouble(scanner));
      vector = new Vector2d(readDouble(scanner), readDouble(scanner));
      transform.add(new AffineTransform2D(matrix, vector));
    }
    return transform;
  }

  private List<Transform2D> parseJuliaTransformations(Scanner scanner) {
    List<Transform2D> transform = new ArrayList<>();
    Complex complex = new Complex(readDouble(scanner), readDouble(scanner));
    transform.add(new JuliaTransform(complex, -1));
    transform.add(new JuliaTransform(complex, 1));
    return transform;
  }

  public void writeToFile(ChaosGameDescription chaosGameDescription, String path) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      writer.write(chaosGameDescription.toString());
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to file '" + path + "'.");
    }
  }

}
