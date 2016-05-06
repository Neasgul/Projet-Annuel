package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import esgi.yvox.controller.MainScene_Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Benoit on 17/03/2016.
 */
public class Main extends Application{



    boolean recognitionState;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main_scene.fxml"));
        Parent main_sc = loader.load();
        stage.setTitle("YVOX Voice Controller");
        stage.getIcons().add(new Image("file:img/logo_icone.png"));

        Rectangle2D mainscreen = Screen.getPrimary().getVisualBounds();
        stage.setWidth(mainscreen.getWidth() * 0.5);
        stage.setHeight(mainscreen.getHeight() * 0.75);

        Scene scene = new Scene(main_sc,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.show();
        PluginManager.getInstance();
    }
}
