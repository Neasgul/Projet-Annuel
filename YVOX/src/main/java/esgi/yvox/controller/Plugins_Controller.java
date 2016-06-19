package esgi.yvox.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by ostro on 19/06/2016.
 */
public class Plugins_Controller {

    @FXML
    private Button home_button;

    @FXML
    private Button historic_button;

    @FXML
    void onHomeClick(ActionEvent event) {
        System.out.println("Open Home window");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_scene.fxml"));
            Parent home_window = loader.load();

            Stage main_window = (Stage) home_button.getScene().getWindow();
            Scene scene_historic = new Scene(home_window, main_window.getWidth(), main_window.getHeight());
            main_window.setScene(scene_historic);
            main_window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void onHistoriqueClick(ActionEvent event){
        System.out.println("Open History window");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/historic_scene.fxml"));
            Parent hist_window = loader.load();

            Stage main_window = (Stage) historic_button.getScene().getWindow();
            hist_window.minWidth(main_window.getWidth());
            hist_window.minHeight(main_window.getHeight());
            Scene scene_historic = new Scene(hist_window, main_window.getWidth(), main_window.getHeight());
            main_window.setScene(scene_historic);
            main_window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
