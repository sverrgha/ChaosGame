package edu.ntnu.idatt2003;

import edu.ntnu.idatt2003.model.AffineTransform2D;
import edu.ntnu.idatt2003.model.ChaosGame;
import edu.ntnu.idatt2003.model.ChaosGameDescription;
import edu.ntnu.idatt2003.model.Matrix2x2;
import edu.ntnu.idatt2003.model.Transform2D;
import edu.ntnu.idatt2003.model.Vector2d;
import edu.ntnu.idatt2003.view.UI;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    Vector2d min = new Vector2d(0, 0);
    Vector2d max = new Vector2d(1, 1);
    Transform2D sierpinski1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2d(0, 0));
    Transform2D sierpinski2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2d(0.5, 0));
    Transform2D sierpinski3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2d(0.25, 0.5));
    List<Transform2D> transform2DList = new ArrayList<>();
    transform2DList.add(sierpinski1);
    transform2DList.add(sierpinski2);
    transform2DList.add(sierpinski3);


    ChaosGameDescription description = new ChaosGameDescription(min, max, transform2DList);
    ChaosGame game = new ChaosGame(description, 100, 100);
    game.runSteps(10000);
    game.getCanvas().showCanvas();

    UI ui = new UI();
    ui.start();
    }

  }
