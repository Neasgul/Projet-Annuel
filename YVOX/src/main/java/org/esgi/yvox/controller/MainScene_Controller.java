package org.esgi.yvox.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Teddy on 29/04/2016.
 */
public class MainScene_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView mic;

    @FXML
    private ImageView img_top_logo;

    @FXML
    void onMicClick(ActionEvent event) {
        System.out.println("Start / stop vocale recognition");
        // TODO: 04/05/2016 implement it
    }

    @FXML
    void onHistoriqueClick(ActionEvent event) {
        System.out.println("Open History window");
        // TODO: 04/05/2016 Create a History window
    }

    @FXML
    void onPluginClick(ActionEvent event) {
        System.out.println("Open Plugins window");
        // TODO: 04/05/2016 Create a plugin window
    }

    @FXML
    void initialize() {
        assert mic != null : "fx:id=\"mic\" was not injected: check your FXML file 'main_scene.fxml'.";
        assert img_top_logo != null : "fx:id=\"img_top_logo\" was not injected: check your FXML file 'main_scene.fxml'.";
        System.out.println("Main Scene initialize");

    }
}
