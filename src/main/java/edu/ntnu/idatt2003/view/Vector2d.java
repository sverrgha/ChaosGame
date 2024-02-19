package edu.ntnu.idatt2003.view;

public class Vector2d {
  double x0;
  double x1;

    public Vector2d(double x0, double x1) {
        this.x0 = x0;
        this.x1 = x1;
    }

    public double getX0() {
        return x0;
    }

    public double getX1() {
        return x1;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x0 + other.x0, x1 + other.x1);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x0 - other.x0, x1 - other.x1);
    }
}
