package edu.ntnu.idatt2003.model;

import edu.ntnu.idatt2003.enums.descriptionTypeEnum;
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
    };
  }

  /**
   * A static method for the sierpinskiTriangle.
   *
   * @return a description for sierpinski triangle
   */

  private static ChaosGameDescription sierpinskiTriangle() {
    Vector2d min = new Vector2d(0, 0);
    Vector2d max = new Vector2d(1, 1);
    Transform2D sierpinski1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
        new Vector2d(0, 0));
    Transform2D sierpinski2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
        new Vector2d(0.5, 0));
    Transform2D sierpinski3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
        new Vector2d(0.25, 0.5));
    List<Transform2D> transform2DList = new ArrayList<>();
    transform2DList.add(sierpinski1);
    transform2DList.add(sierpinski2);
    transform2DList.add(sierpinski3);
    ChaosGameDescription description = new ChaosGameDescription(min, max, transform2DList);
    return description;
  }

  /**
   * A static method for barnsley fern transformations.
   *
   * @return a description of the barnsley fern transformation.
   */

  private static ChaosGameDescription barnsleyFern() {
    Vector2d min = new Vector2d(-2.5, 0);
    Vector2d max = new Vector2d(2.5, 10);

    Transform2D fern1 = new AffineTransform2D(new Matrix2x2(0, 0, 0, 0.16), new Vector2d(0, 0));
    Transform2D fern2 = new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85),
        new Vector2d(0, 1.6));
    Transform2D fern3 = new AffineTransform2D(new Matrix2x2(0.2, -0.26, 0.23, 0.22),
        new Vector2d(0, 1.6));
    Transform2D fern4 = new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24),
        new Vector2d(0, 0.44));

    List<Transform2D> transform2DList = new ArrayList<>();
    transform2DList.add(fern1);
    transform2DList.add(fern2);
    transform2DList.add(fern3);
    transform2DList.add(fern4);

    ChaosGameDescription description = new ChaosGameDescription(min, max, transform2DList);
    return description;

  }
}
