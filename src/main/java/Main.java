import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import driver.*;
//import gui.controllers.*;

import java.io.File;
import java.util.List;

import static driver.ConfigurationManager.RequiredField.*;

/**
 * Created by Emilio Lopez on 2/25/2018.
 */
public class Main extends Application {

    public static void main(String[] args)
    throws Exception {
        String propertiesPath = "../";

        ConfigurationManager config = ConfigurationManager.Builder.build(propertiesPath);

        ImageAggregator aggregator = null;
        if (args.length > 1) {
            aggregator = ImageAggregator.Builder.build(config.getExtensions(EXTENSIONS), args[0]);
        }
        else {
            aggregator = ImageAggregator.Builder.build(config.getExtensions(EXTENSIONS), config.getProperty("DIRECTORY", String.class));
        }


        // See ideal method in ConfigurationManager.java
//        List<File> images = (config.getProperty(SHUFFLE, Boolean.class)) ? aggregator.shuffledImages() : aggregator.getImages();
//        int duration = config.getProperty(DURATION, Integer.class);
//        boolean autoplay = config.getProperty(AUTOPLAY, Boolean.class);

        int duration = Integer.parseInt(config.getProperty(DURATION));
        boolean autoplay = Boolean.parseBoolean(config.getProperty(AUTOPLAY));

        List<File> images;
        boolean shuffle;
        if ((shuffle = Boolean.parseBoolean(config.getProperty(SHUFFLE)))) {
            images = aggregator.shuffledImages();
        } else {
            images = aggregator.getImages();


            launch(args);
        }


    }

    @Override
    public void start (Stage primaryStage)
    throws Exception {
        // Need to get the File from the directory select and pass to the SlideShowDriver
        // Since it's a File, I need to pass the String path to the driver
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/startup.fxml"));
        AnchorPane page = loader.load();
        Scene scene = new Scene(page);

        primaryStage.setTitle("Throne | Rejoice!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
