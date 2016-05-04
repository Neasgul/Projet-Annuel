package esgi.yvox.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private Button button_historic;

    @FXML
    void onMicClick(ActionEvent event) {
        System.out.println("Start / stop vocale recognition");
        // TODO: 04/05/2016 implement it
    }

    @FXML
    void onHistoriqueClick(ActionEvent event) {
        System.out.println("Open History window");
        // TODO: 04/05/2016 Create a History window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/historic_scene.fxml"));
            Parent hist_window = loader.load();

            Stage main_window = (Stage) button_historic.getScene().getWindow();
            Scene scene_historic = new Scene(hist_window, main_window.getWidth(), main_window.getHeight());
            main_window.setScene(scene_historic);
            main_window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
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
