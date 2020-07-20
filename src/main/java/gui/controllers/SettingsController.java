package gui.controllers;

import driver.ConfigurationManager;
import driver.ConfigurationManager.RequiredField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class SettingsController {

    @FXML
    Button saveButton;

    @FXML
    Button autoplayToggle;

    @FXML
    TextField durationField;

    @FXML
    Button shuffleToggle;

    @FXML
    TextField extensionsField;

    private ConfigurationManager configurationManager;

    public void startSettingsController(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;

        autoplayToggle.setText(configurationManager.getProperty(RequiredField.AUTOPLAY));
        durationField.setText(configurationManager.getProperty(RequiredField.DURATION));
        shuffleToggle.setText(configurationManager.getProperty(RequiredField.SHUFFLE));
        extensionsField.setText(configurationManager.getProperty(RequiredField.EXTENSIONS));
    }

    @FXML
    public void toggleAutoplay() {
        if (Boolean.parseBoolean(autoplayToggle.getText())) {
            autoplayToggle.setText("false");
        }
        else {
            autoplayToggle.setText("true");
        }
    }

    @FXML
    public void toggleShuffle() {
        if (Boolean.parseBoolean(shuffleToggle.getText())) {
            shuffleToggle.setText("false");
        }
        else {
            shuffleToggle.setText("true");
        }
    }

    public void saveConfigurations() {
        List<String> config = new ArrayList<>();

        config.add(autoplayToggle.getText());
        config.add(durationField.getText());
        config.add(shuffleToggle.getText());
        config.add(extensionsField.getText());

        try {
            configurationManager.savePropertyValues(config);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
