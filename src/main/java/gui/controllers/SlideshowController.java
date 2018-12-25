package gui.controllers;

import driver.ImageManager;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SlideshowController {

    private ImageManager imageManager;

    private int duration;

    private boolean autoplay;

    @FXML
    private ImageView img;


    void setImageManager(ImageManager manager) {
        imageManager = manager;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }

    void setAutoplay(boolean autoplay) {
        this.autoplay = autoplay;
    }


}
