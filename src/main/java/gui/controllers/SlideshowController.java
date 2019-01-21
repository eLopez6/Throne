package gui.controllers;

import driver.ImageManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.util.Duration;

public class SlideshowController {
    private ImageManager imageManager;
    private int duration;
    private boolean autoplay;
    private Timeline timer;

    private double screenWidth;
    private double screenHeight;

    @FXML
    private ImageView imageView;


    void setUpTimer() {
        if (autoplay) {
            timer = new Timeline(new KeyFrame(
                    Duration.seconds((double) duration),
                    arg -> scaleAndCenterImage(imageManager.nextImage())));
            timer.setCycleCount(Animation.INDEFINITE);
            timer.play();
        }
    }

    private void nextImage() {
        imageView.setImage(imageManager.nextImage());
    }

    @FXML
    void updateImage(KeyEvent event) {
        Image image = null;

        imageView.setFitHeight(0);
        imageView.setFitWidth(0);
        imageView.setX(0);
        imageView.setY(0);

        if (KeyCode.RIGHT == event.getCode()) {
            image = imageManager.nextImage();
        }
        else if (KeyCode.LEFT == event.getCode()) {
            image = imageManager.previousImage();
        }

        if (image == null) {
            return;
        }

        scaleAndCenterImage(image);
    }

    private void scaleAndCenterImage(Image image) {
        double q;
        double r;
        if (image.getWidth() > screenWidth || image.getHeight() > screenHeight) {
            double[] scaledImageDimensions = scaleDimensions(image);
            double[] centeredImageDimensions = centerDimensions(scaledImageDimensions);
            q = centeredImageDimensions[0];
            r = centeredImageDimensions[1];

            imageView.setFitWidth(scaledImageDimensions[0]);
            imageView.setFitHeight(scaledImageDimensions[1]);
        }
        else {
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
            q = ((screenWidth - imageView.getFitWidth()) / 2);
            r = ((screenHeight - imageView.getFitHeight()) / 2);
        }

        // center image

        imageView.setX(q);
        imageView.setY(r);

        imageView.setImage(image);
    }

    void setFirstImage() {
        scaleAndCenterImage(imageManager.firstImage());
    }

    private double[] scaleDimensions(Image image) {
        // Add support for different orientations of monitors
        double[] dimensions = {0, 0};
        double aspectRatio = (image.getWidth() / image.getHeight());

        if (isVertical(image)) {
            dimensions[1] = screenHeight;
            dimensions[0] = screenHeight * aspectRatio;
        }
        else {
            dimensions[0] = screenWidth;
            dimensions[1] = screenWidth / aspectRatio;
        }

        return dimensions;
    }

    private double[] centerDimensions(double[] dimensions) {
        double[] centeredDimensions = {0, 0};

        centeredDimensions[0] = (screenWidth - dimensions[0]) / 2;
        centeredDimensions[1] = (screenHeight - dimensions[1]) / 2;

        return centeredDimensions;
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

    void setUpImageViewSettings() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        screenWidth = primaryScreenBounds.getWidth();
        screenHeight = primaryScreenBounds.getHeight();

        imageView.setFitHeight(0);
        imageView.setFitWidth(0);
        imageView.setX(0);
        imageView.setY(0);
    }

    private boolean isVertical(Image image) {
        return (image.getWidth() / image.getHeight()) <= 1;
    }

}
