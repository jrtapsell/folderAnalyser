package uk.co.jrtapsell.file_analyser.jfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.co.jrtapsell.file_analyser.common.Controller;
import uk.co.jrtapsell.file_analyser.common.Model;

public class JFXMain extends Application {

  public static void main(final String... args) {
    launch(args);
  }

  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout.fxml"));
    final VBox loadedRoot = loader.load();
    final JFXView view = loader.getController();
    final Model model = new Model();
    final Controller controller = new Controller(view, model);
    final Scene value = new Scene(loadedRoot, 600, 400);
    value.getStylesheets().add(getClass().getResource("/index.css").toExternalForm());
    primaryStage.setScene(value);
    primaryStage.setTitle("File Analyser");
    primaryStage.setMaximized(true);
    primaryStage.show();
  }
}