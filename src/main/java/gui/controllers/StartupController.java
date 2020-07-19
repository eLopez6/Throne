package gui.controllers;

import driver.ConfigurationManager;
import driver.ImageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

import static driver.ConfigurationManager.RequiredField.*;


/**
 * Created by Emilio Lopez on 3/28/2018.
 */
public class StartupController  {

    @FXML
    private Button selectDirectory;

    @FXML
    private Button settingsButton;

    @FXML
    public void startButtonClicked() throws Exception {

        // Slideshow setup
        ConfigurationManager config = ConfigurationManager.Builder.buildDefault();

        ImageManager imageManager = ImageManager.Builder.build(config.getExtensions(), config.getProperty("DIRECTORY", String.class));

        int duration = Integer.parseInt(config.getProperty(DURATION));
        boolean autoplay = Boolean.parseBoolean(config.getProperty(AUTOPLAY));

        if (Boolean.parseBoolean(config.getProperty(SHUFFLE))) {
            imageManager.shuffleImages();
        }

        // Controller setup
        FXMLLoader loader = new FXMLLoader(StartupController.class.getClassLoader().getResource("gui/fxml/slideshow.fxml"));
        Parent root = loader.load();
        SlideshowController slideshowController = loader.getController();

        slideshowController.setImageManager(imageManager);
        slideshowController.setDuration(duration);
        slideshowController.setAutoplay(autoplay);
        slideshowController.setUpImageViewSettings();
        slideshowController.setFirstImage();
        slideshowController.setUpTimer();


        Scene scene = new Scene(root);

        // Actually creating the stage
        Stage stage = new Stage();
        stage.setTitle("Slideshow!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        addHandlers(slideshowController, stage);

        stage.show();
    }

    @FXML
    public void selectDirectoryButtonClicked()
    throws Exception {
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(selectDirectory.getScene().getWindow());

        if (selectedDirectory == null) {    // No directory selected
            return;
        }

        ConfigurationManager manager = ConfigurationManager.Builder.buildDefault();
        manager.changePropertyValue("DIRECTORY", selectedDirectory.getAbsolutePath());
    }

    private void addHandlers(SlideshowController slideshowController, Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            KeyCode code = event.getCode();
            if (KeyCode.ESCAPE == code) {
                slideshowController.stopTime();
                stage.close();
            }
            switch (code) {
                case ESCAPE:
                    slideshowController.stopTime();
                    stage.close();
                    break;
                case RIGHT:
                case LEFT:
                    slideshowController.stopTime();
                    slideshowController.setUpTimer();
                    break;
            }
        });
    }
}
