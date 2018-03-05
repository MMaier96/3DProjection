
package application;

import application.gui.Application;

public class Main {

  public static void main(String... args) {
    Application app = new Application();
    app.setArguments(args);
    app.startApplication();
  }
}
