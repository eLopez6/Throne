import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


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
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/fxml/startup.fxml"));
        AnchorPane page = loader.load();
        Scene scene = new Scene(page);

        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("gui/images/icon.jpg")));    // replace l8r
        primaryStage.setTitle("Throne | Rejoice!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
