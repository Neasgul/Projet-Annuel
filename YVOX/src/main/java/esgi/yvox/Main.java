package esgi.yvox;

import esgi.yvox.annotation.UUID;
import esgi.yvox.annotation.UUID_Processor;
import esgi.yvox.controller.MainScene_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.LineNumberReader;

/**
 * Created by Benoit on 17/03/2016.
 */
public class Main extends Application{

    public static String ChoiceBoxValue = "";

    @UUID
    public static void main(String[] args) {
        UUID_Processor uuid_processor = new UUID_Processor(Main.class);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        User_UUID.readUUID();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_scene.fxml"));
        Parent main_sc = loader.load();
        MainScene_Controller ms_Controller = loader.getController();
        ChoiceBoxValue = (String) ms_Controller.getChoiceBoxValue();
        stage.setTitle("YVOX Voice Controller");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo_icone_transparent.png")));

        Rectangle2D mainscreen = Screen.getPrimary().getVisualBounds();
        stage.setWidth(mainscreen.getWidth() * 0.5);
        stage.setHeight(mainscreen.getHeight() * 0.75);

        Scene scene = new Scene(main_sc,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
