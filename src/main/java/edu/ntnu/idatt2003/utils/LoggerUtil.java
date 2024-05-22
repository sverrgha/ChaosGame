package edu.ntnu.idatt2003.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class for setting up a Logger.
 */
public class LoggerUtil {

  /**
   * Sets up a logger for the specified class name.
   *
   * @param className the name of the class for which the logger is being set up
   * @return the configured Logger instance
   */
  public static Logger setupLogger(String className) {
    Logger logger = Logger.getLogger(className);
    try {
      File logDirectory = new File("logs");
      if (!logDirectory.exists() && !logDirectory.mkdirs()) {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(logDirectory));
        System.err.println("Failed to create log directory.");
      }
      FileHandler fileHandler = new FileHandler("logs/application.log", false);
      fileHandler.setFormatter(new SimpleFormatter());
      fileHandler.setLevel(Level.WARNING);
      logger.addHandler(fileHandler);
      logger.setLevel(Level.ALL);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return logger;
  }
}
