import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Created by Emilio Lopez on 2/25/2018.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage)
    throws Exception {
        // Need to get the File from the directory select and pass to the SlideShowDriver
        // Since it's a File, I need to pass the String path to the driver
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("gui/fxml/startup.fxml")));
        Scene scene = new Scene(pane);

        primaryStage.setTitle("Throne | Rejoice!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
