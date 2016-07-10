package esgi.yvox.controller;

import esgi.yvox.Sphinx_Controller;
import esgi.yvox.Sphinx_Request;
import esgi.yvox.plugins.PluginsLoader;
import esgi.yvox.sdk.LanguagePlugins;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Teddy on 29/04/2016.
 */
public class MainScene_Controller{
    Sphinx_Request sphinx_request = new Sphinx_Request() {
        public void onRecognitionRequest() {
            Sphinx_Controller.getInstance().onRecognitionRequest();
        }
    };

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label result;

    @FXML
    private ImageView mic;

    @FXML
    private ImageView img_top_logo;

    @FXML
    private Button button_historic;

    @FXML
    private Button button_plugins;

    @FXML
    private ChoiceBox cb_Language;

    @FXML
    void onHistoriqueClick(ActionEvent event) {
        System.out.println("Open History window");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/historic_scene.fxml"));
            Parent hist_window = loader.load();

            Stage main_window = (Stage) button_historic.getScene().getWindow();
            hist_window.minWidth(main_window.getWidth());
            hist_window.minHeight(main_window.getHeight());
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/plugins_scene.fxml"));
            Parent home_window = loader.load();

            Stage main_window = (Stage) button_plugins.getScene().getWindow();
            Scene scene_historic = new Scene(home_window, main_window.getWidth(), main_window.getHeight());
            main_window.setScene(scene_historic);
            main_window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert mic != null : "fx:id=\"mic\" was not injected: check your FXML file 'main_scene.fxml'.";
        assert img_top_logo != null : "fx:id=\"img_top_logo\" was not injected: check your FXML file 'main_scene.fxml'.";
        System.out.println("Main Scene initialize");
        result.setText("Recognition not started");
        loadLanguage();

        mic.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // TODO: 04/05/2016 implement it
                sphinx_request.onRecognitionRequest();
                result.textProperty().bind(Sphinx_Controller.getInstance().getTask().messageProperty());
            }
        });

    }

    public void setResultText(String text){
        result.setText(text);
    }

    public void loadLanguage(){
        PluginsLoader pluginsLoader = new PluginsLoader();

        try{
            LanguagePlugins[] languagePlugins = pluginsLoader.loadAllLanguagePlugins();
            for (int i = 0; i < languagePlugins.length; i++) {
                System.out.println("Name : " + languagePlugins[i].getName());
                cb_Language.getItems().add(languagePlugins[i].getName());
            }
            cb_Language.setTooltip(new Tooltip("Select the language"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
