package edu.ntnu.idatt2003.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a factory for handling descriptions.
 * Contains a variety of descriptions, and the method to switch between descriptions.
 * Includes an enum.
 * Goal: Create pre-defined ChaosGameDescriptions.
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

  public static ChaosGameDescription get(DescriptionTypeEnum descriptionType) {
    return switch (descriptionType) {
      case SIERPINSKI_TRIANGLE -> sierpinskiTriangle();
      case BARNSLEY_FERN -> barnsleyFern();
      case JULIA -> juliaTransformation();
      case LEVY_C_CURVE -> levyCurve();
      case DRAGON_CURVE -> dragonCurve();

    };
  }

  /**
   * Enumerates the types of fractal descriptions that can be generated. Each enum constant
   * corresponds to a specific fractal pattern, which the factory can produce.
   */

  public enum DescriptionTypeEnum {
    SIERPINSKI_TRIANGLE,
    BARNSLEY_FERN,
    JULIA,
    LEVY_C_CURVE,
    DRAGON_CURVE
  }

  /**
   * A static method that generates a ChaosGameDescription for a pre-defined
   * Affine transformation, which is called a Sierpinski Triangle.
   *
   * @return a description for sierpinski triangle
   */

  private static ChaosGameDescription sierpinskiTriangle() {
    List<Transform2D> transformations = new ArrayList<>();
    transformations.add(new AffineTransform2D(
            new Matrix2x2(0.5, 0.0, 0.0, 0.5),
            new Vector2d(0.0, 0.0))
    );
    transformations.add(new AffineTransform2D(
            new Matrix2x2(0.5, 0.0, 0.0, 0.5),
            new Vector2d(.25, .5))
    );
    transformations.add(new AffineTransform2D(
            new Matrix2x2(0.5, 0.0, 0.0, 0.5),
            new Vector2d(.5, 0))
    );

    return new ChaosGameDescription(
            new Vector2d(0, 0),
            new Vector2d(1, 1),
            transformations
    );

  }

  /**
   * A static method that generates a ChaosGameDescription for a pre-defined
   * Affine transformation, which is called a Barnsley Fern.
   *
   * @return a description of the barnsley fern transformation.
   */

  private static ChaosGameDescription barnsleyFern() {
    List<Transform2D> transformations = new ArrayList<>();
    transformations.add(new AffineTransform2D(
            new Matrix2x2(0, 0, 0, 0.16),
            new Vector2d(0, 0))
    );
    transformations.add(new AffineTransform2D(
            new Matrix2x2(0.85, 0.04, -0.04, 0.85),
            new Vector2d(0, 1.6))
    );
    transformations.add(new AffineTransform2D(
            new Matrix2x2(0.2, -0.26, 0.23, 0.22),
            new Vector2d(0, 0.16))
    );
    transformations.add(new AffineTransform2D(
            new Matrix2x2(-.15, .28, .26, .24),
            new Vector2d(0, 0.44))
    );

    return new ChaosGameDescription(
            new Vector2d(-2.5, 0),
            new Vector2d(2.5, 10),
            transformations
    );
  }

  /**
   * A static method that generates a ChaosGameDescription for a pre-defined
   * Julia transformation.
   *
   * @return a description based on a julia transformation.
   */
  private static ChaosGameDescription juliaTransformation() {
    return new ChaosGameDescription(
            new Vector2d(-1.6, -1),
            new Vector2d(1.6, 1),
            List.of(
                    new JuliaTransform(new Complex(-0.74543, 0.11301), 1),
                    new JuliaTransform(new Complex(-0.74543, 0.11301), -1)
            ));
  }

  private static ChaosGameDescription levyCurve() {
    List<Transform2D> transformations = new ArrayList<>();
    transformations.add(new AffineTransform2D(
        new Matrix2x2(0.5, -0.5, 0.5, 0.5),
        new Vector2d(0, 0))
    );
    transformations.add(new AffineTransform2D(
        new Matrix2x2(0.5, 0.5, -0.5, 0.5),
        new Vector2d(0.5, 0.5))
    );

    return new ChaosGameDescription(
        new Vector2d(-1, -0.5),
        new Vector2d(2, 1.5),
        transformations
    );
  }

  private static ChaosGameDescription dragonCurve() {
    List<Transform2D> transformations = new ArrayList<>();
    transformations.add(new AffineTransform2D(
        new Matrix2x2(0.824074, 0.281482, -0.212346, 0.864198),
        new Vector2d(-1.882290, -0.110607))
    );
    transformations.add(new AffineTransform2D(
        new Matrix2x2(0.088272, 0.520988, -0.463889, -0.377778),
        new Vector2d(0.785360, 8.095795))
    );

    return new ChaosGameDescription(
        new Vector2d(-7, 0),
        new Vector2d(6, 11),
        transformations
    );
  }

    /**
     * A static method that reads a fractal from a file and returns a ChaosGameDescription.
     *
     * @param pathToFile the path to the file containing the fractal description
     * @return a ChaosGameDescription based on the fractal description in the file
     */
  private static ChaosGameDescription readFractal(String pathToFile)
          throws FileNotFoundException{
    try {
      return new ChaosGameFileHandler().readFromFile(pathToFile);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + pathToFile + " not found." + e.getMessage());
    }
  }

  /**
   * A static method for returning a ChaosGameDescription based on the given transformation name.
   *
   * @param transformationName the name of the custom transformation
   * @return the ChaosGameDescription corresponding to the given transformation name
   */

  public static ChaosGameDescription getCustom(String transformationName)
          throws FileNotFoundException{
    try {
      String filePath = "src/main/resources/transformations/" + transformationName + ".txt";
      return readFractal(filePath);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + e.getMessage());
    }

  }



}


