package gui.controllers;

import driver.ConfigurationManager;
import driver.ImageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private ImageManager imageManager;

    private ConfigurationManager config;

    @FXML
    public void initialize() {
        try {
            config = ConfigurationManager.Builder.buildDefault();
            imageManager = ImageManager.Builder.build(config.getExtensions(), config.getProperty(DURATION));
        }
        catch (Exception e) {
            System.out.println("Error: configuration or image manager failed to build.");
        }
    }

    @FXML
    public void startButtonClicked() throws Exception {
        // Slideshow setup
        assert config != null;
        assert imageManager != null;

        int duration = config.getAutoplayDuration();
        boolean autoplay = config.isAutoplay();

        if (config.isShuffle()) {
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
        addSlideshowHandlers(slideshowController, stage);

        stage.show();
    }

    @FXML
    public void settingsButtonClicked() throws Exception {
        ConfigurationManager config = ConfigurationManager.Builder.buildDefault();

        // Controller setup
        FXMLLoader loader = new FXMLLoader(SettingsController.class.getClassLoader().getResource("gui/fxml/settings.fxml"));
        Parent root = loader.load();
        SettingsController settingsController = loader.getController();

        settingsController.startSettingsController(config);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Throne | Settings");
        stage.setScene(scene);

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

        assert config != null;
        if (imageManager == null) {
            imageManager = ImageManager.Builder.build(config.getExtensions(), config.getProperty("DIRECTORY", String.class));
        }
        if (imageManager.getIMAGES_LENGTH() < 1) {
            Alert directoryAlert = new Alert(Alert.AlertType.ERROR);
            directoryAlert.setContentText("The selected directory, " +
                    config.getProperty("DIRECTORY", String.class) + "does not contain any files with the " +
                    "configured extensions: " + config.getExtensions());
            directoryAlert.show();
            imageManager = null;
        }
    }

    private void addSlideshowHandlers(SlideshowController slideshowController, Stage stage) {
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
