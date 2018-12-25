package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


/**
 * Created by Emilio Lopez on 3/28/2018.
 */
public class StartupController  {

    @FXML
    private ImageView REJOICE;

    @FXML
    private Button selectDirectory;

    @FXML
    private Button settingsButton;

    @FXML
    public void startButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(StartupController.class.getClassLoader().getResource("gui/fxml/slideshow.fxml"));
        AnchorPane page = loader.load();
        Scene scene = new Scene(page);

        Stage stage = new Stage();
        stage.setTitle("Slideshow!");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void selectDirectoryButtonClicked() throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(selectDirectory.getScene().getWindow());

        System.out.println(selectedDirectory.getAbsolutePath());
    }
}
