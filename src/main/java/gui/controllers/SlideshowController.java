package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SlideshowController {

    private int currentImageIndex = 0;

    @FXML
    private ImageView img;


    public void nextImage(String path) {
        img.setImage(new Image(path));
    }


}
