
package edu.ntnu.idatt2003.launcher;

import edu.ntnu.idatt2003.view.CommandLineInterface;

/**
 * Launcher for the command line interface
 * To read or write from file write src/main/resources/fractals/filename(with .txt)
 */

public class CLILauncher {
  public static void main(String[] args) {
    CommandLineInterface menuDrivenCommandLine = new CommandLineInterface();
    menuDrivenCommandLine.start();
  }
}
