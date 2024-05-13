package edu.ntnu.idatt2003.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a factory for handling descriptions.
 * Contains a variety of descriptions, and the method to switch between descriptions.
 * Includes an enum.
 * Goal: switch between descriptions.
 *
 */


public class ChaosGameDescriptionFactory {

  public static ChaosGameDescription get(descriptionTypeEnum descriptionType) {
    return switch (descriptionType) {
      case SIERPINSKI_TRIANGLE -> sierpinskiTriangle();
      case BARNSLEY_FERN -> barnsleyFern();
      case JULIA -> juliaTransformation();
    };
  }
  public enum descriptionTypeEnum {
    SIERPINSKI_TRIANGLE,
    BARNSLEY_FERN,
    JULIA,
  }

  /**
   * A static method for the sierpinskiTriangle.
   *
   * @return a description for sierpinski triangle
   */

  private static ChaosGameDescription sierpinskiTriangle() {
    return transformations("src/test/resources/Affine2DExample.txt");
  }

  /**
   * A static method for barnsley fern transformations.
   *
   * @return a description of the barnsley fern transformation.
   */

  private static ChaosGameDescription barnsleyFern() {
    return transformations("src/test/resources/BarnsleyFern.txt");
  }

  /**
   * A static method for the julia transformation.
   *
   * @return a description based on a julia transformation.
   */

  private static ChaosGameDescription juliaTransformation() {
    return transformations("src/test/resources/JuliaExample.txt");
  }

  private static ChaosGameDescription transformations(String pathToFile) {
    try {
      return new ChaosGameFileHandler().readFromFile(pathToFile);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File " + pathToFile + " not found." + e.getMessage());
    }
  }


}


