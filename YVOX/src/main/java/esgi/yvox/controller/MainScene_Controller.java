package esgi.yvox.controller;

import esgi.yvox.Main;
import esgi.yvox.Sphinx_Controller;
import esgi.yvox.Sphinx_Request;
import esgi.yvox.plugins.PluginsLoader;
import esgi.yvox.sdk.LanguagePlugins;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
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
                if (Main.ChoiceBoxValue != null) {
                    sphinx_request.onRecognitionRequest();
                    result.textProperty().bind(Sphinx_Controller.getInstance().getTask().messageProperty());
                    result.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            switch (newValue){
                                case "Recognition started":
                                    mic.setImage(new Image(getClass().getResourceAsStream("/img/mic_on.png")));
                                    break;
                                case "Recognition stopped":
                                    mic.setImage(new Image(getClass().getResourceAsStream("/img/mic.png")));
                                    break;
                                default:

                            }
                        }
                    });
                }else {
                    Stage secondaryStage = new Stage();
                    BorderPane root = new BorderPane();
                    Button exit = new Button("Ok");
                    exit.setOnAction(event1 -> {
                        secondaryStage.close();
                    });
                    Label text = new Label("Select a language");
                    text.setTextAlignment(TextAlignment.CENTER);
                    root.setAlignment(exit, Pos.CENTER);
                    root.setCenter(text);
                    root.setBottom(exit);
                    root.setPadding(new Insets(0,0,5,0));
                    Scene popup = new Scene(root, 300, 75);
                    secondaryStage.setScene(popup);
                    secondaryStage.setTitle("No language found");
                    secondaryStage.showAndWait();
                }
            }
        });

    }

    public Object getChoiceBoxValue(){ return cb_Language.getValue();}

    public void setResultText(String text){
        result.setText(text);
    }

    public void loadLanguage(){
        PluginsLoader pluginsLoader = new PluginsLoader();

        try{
            LanguagePlugins[] languagePlugins = pluginsLoader.loadAllLanguagePlugins();
            for (int i = 0; i < languagePlugins.length; i++) {
                cb_Language.getItems().add(languagePlugins[i].getName());
            }
            cb_Language.setTooltip(new Tooltip("Select the language"));
            cb_Language.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    Main.ChoiceBoxValue = (String) cb_Language.getItems().get(newValue.intValue());
                    System.out.println("Change : " + Main.ChoiceBoxValue);
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
