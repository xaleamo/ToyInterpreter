package viewGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/program_selector.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root,500,800);
        scene.getStylesheets().add(getClass().getResource("/my_style.css").toExternalForm());

        mainStage.setScene(scene);
        mainStage.setTitle("Program selector");
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
