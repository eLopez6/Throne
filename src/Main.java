import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import driver.*;
import gui.*;

/**
 * Created by Emilio Lopez on 2/25/2018.
 */
public class Main extends Application {

    private static final int SPACING = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Throne - Semblance");

        Button loadFolderButton = new Button();
        loadFolderButton.setText("Select folder");

        Button changeSettingsButton = new Button();
        changeSettingsButton.setText("Change settings");

        Button startShowButton = new Button();
        startShowButton.setText("Start Throne");

        HBox buttonBox = new HBox(SPACING);
        buttonBox.getChildren().addAll(loadFolderButton, changeSettingsButton, startShowButton);


        primaryStage.show();
    }
}
