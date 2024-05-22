package edu.ntnu.idatt2003.view;

/**
 * Represents a text renderer.
 * Contains constants for menu options and methods for showing the menu, entering a path and entering steps.
 * Goal: act as a view for a text renderer.
 *
 */

public class TextRenderer {

  public static final String READ_FILE = "1";
  public static final String WRITE_FILE = "2";
  public static final String RUN_ITERATIONS = "3";
  public static final String SHOW_CANVAS = "4";
  public static final String WRITE_NEW_DESCRIPTION = "5";
  public static final String EXIT = "5";

  public void showMenu() {
    System.out.println("Menu:");
    System.out.println("1. Read description from file");
    System.out.println("2. Write current description to file");
    System.out.println("3. Run ChaosGame a given number of steps");
    System.out.println("4. Show Canvas");
    System.out.println("5. Write New Description");
    System.out.println("6. Exit");
  }

  public void enterPath() {
    System.out.println("Enter path to file:");
  }

  public void enterSteps() {
    System.out.println("Enter number of steps:");
  }



}
