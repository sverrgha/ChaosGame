package edu.ntnu.idatt2003.model;

import java.io.FileNotFoundException;


/**
 * Represents a factory for handling descriptions.
 * Contains a variety of descriptions, and the method to switch between descriptions.
 * Includes an enum.
 * Goal: switch between descriptions.
 *
 */


public class ChaosGameDescriptionFactory {

  /**
   * Retrieves a ChaosGameDescription based on the specified type of fractal description.
   * This method acts as a factory, selecting the appropriate method to generate the fractal
   * description based on the enum value provided.
   *
   * @param descriptionType the type of fractal description to retrieve. This enum specifies which
   *                        fractal pattern description is to be generated.
   * @return a ChaosGameDescription object corresponding to the specified fractal type.
   */

  public static ChaosGameDescription get(descriptionTypeEnum descriptionType) {
    return switch (descriptionType) {
      case SIERPINSKI_TRIANGLE -> sierpinskiTriangle();
      case BARNSLEY_FERN -> barnsleyFern();
      case JULIA -> juliaTransformation();
      case CUSTOM -> customTransformation();
    };
  }

  /**
   * Enumerates the types of fractal descriptions that can be generated. Each enum constant
   * corresponds to a specific fractal pattern, which the factory can produce.
   */

  public enum descriptionTypeEnum {
    SIERPINSKI_TRIANGLE,
    BARNSLEY_FERN,
    JULIA,
    CUSTOM
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

  private static ChaosGameDescription customTransformation() {
    //method to write to file
    //return transformations("path to an file")
    return null;
  }


  private static ChaosGameDescription transformations(String pathToFile) {
    try {
      return new ChaosGameFileHandler().readFromFile(pathToFile);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File " + pathToFile + " not found." + e.getMessage());
    }
  }


}


