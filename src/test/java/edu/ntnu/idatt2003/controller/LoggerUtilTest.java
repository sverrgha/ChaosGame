package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.utils.LoggerUtil;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test class for the LoggerUtil class.
 * This class contains tests for verifying the configuration and functionality of the LoggerUtil.
 */
public class LoggerUtilTest {

  private static final String LOG_FILE_PATH = "src/test/resources";
  private static Logger logger;

  /**
   * Sets up the test environment before each test.
   * Initializes the logger and ensures that the log file is deleted before each test.
   */
  @BeforeEach
  public void setUp() {
    File logFile = new File(LOG_FILE_PATH);
    if (logFile.exists()) {
      logFile.delete();
    }
    logger = LoggerUtil.setupLogger(LoggerUtilTest.class.getName());
  }

  /**
   * Tests that the logger is configured correctly.
   * Verifies that the logger is not null, the log file is created, the logger has a FileHandler,
   * and the logger level is set to ALL.
   */
  @Test
  @DisplayName("Logger is configured correctly")
  public void testLoggerConfiguration() {
    assertNotNull(logger);

    File logFile = new File(LOG_FILE_PATH);
    assertTrue(logFile.exists(), "Log file should exist");

    boolean hasFileHandler = false;
    for (var handler : logger.getHandlers()) {
      if (handler instanceof FileHandler) {
        hasFileHandler = true;
        break;
      }
    }
    assertTrue(hasFileHandler, "Logger should have a FileHandler");

    assertEquals(Level.ALL, logger.getLevel(), "Logger level should be ALL");
  }

  /**
   * Cleans up the test environment after each test.
   * Deletes the log file to ensure a clean state for the next test.
   */
  @AfterEach
  public void tearDown() {
    File logFile = new File(LOG_FILE_PATH);
    if (logFile.exists()) {
      logFile.delete();
    }
  }
}