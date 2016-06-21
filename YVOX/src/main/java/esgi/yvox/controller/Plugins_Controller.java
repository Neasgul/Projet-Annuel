package esgi.yvox.controller;

import edu.cmu.sphinx.linguist.acoustic.tiedstate.HTK.Lab;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.nio.file.StandardOpenOption.*;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ostro on 19/06/2016.
 */
public class Plugins_Controller {

    @FXML
    private Button home_button;

    @FXML
    private Button add_plugin;

    @FXML
    private Label info_addPlugin;

    private Stage main_window;

    @FXML
    void onHomeClick(ActionEvent event) {
        System.out.println("Open Home window");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_scene.fxml"));
            Parent home_window = loader.load();

            main_window = (Stage) home_button.getScene().getWindow();
            Scene scene_historic = new Scene(home_window, main_window.getWidth(), main_window.getHeight());
            main_window.setScene(scene_historic);
            main_window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void onAddPlugin(ActionEvent event){
        System.out.println(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Jar Files", "*.jar"),
                new FileChooser.ExtensionFilter("All Files", "*.*")); // All files just for test
        main_window = (Stage) add_plugin.getScene().getWindow();
        try {
            Files.copy(fileChooser.showOpenDialog(main_window).toURI().toURL().openStream(),
                    Paths.get(System.getProperty("user.dir"), "src/main/java/esgi/yvox/plugins"));
        } catch (FileAlreadyExistsException faex){
            info_addPlugin.setText("Le plugin est déjà installé");
            info_addPlugin.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){ getPlugins();}

    void getPlugins(){

    }
}
