package gui.controllers;

import driver.ImageManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SlideshowController {
    private ImageManager imageManager;
    private int duration;
    private boolean autoplay;
    private Timeline timer;

    @FXML
    private ImageView img;


    void setUpTimer() {
        timer = new Timeline(new KeyFrame(
                Duration.seconds((double)duration),
                arg -> nextImage()));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void nextImage() {
        img.setImage(imageManager.nextImage());
    }

    @FXML
    void updateImage(KeyEvent event) {
        if (KeyCode.RIGHT == event.getCode()) {
            img.setImage(imageManager.nextImage());
        }
        else if (KeyCode.LEFT == event.getCode()) {
            img.setImage(imageManager.previousImage());
        }
    }

    void setImageManager(ImageManager manager) {
        imageManager = manager;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }

    void setAutoplay(boolean autoplay) {
        this.autoplay = autoplay;
    }

    void setFirstImage() {
        img.setImage(imageManager.firstImage());
    }


}
