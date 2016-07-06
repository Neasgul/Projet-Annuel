package esgi.yvox.controller;

import edu.cmu.sphinx.linguist.acoustic.tiedstate.HTK.Lab;
import esgi.yvox.plugins.PluginsLoader;
import esgi.yvox.sdk.PluginsInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import static java.nio.file.StandardOpenOption.*;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;

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

    @FXML
    private Accordion plugins_accordion;

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
        File file = new File("Plugins");
        file.mkdirs();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Jar Files", "*.jar"));
        main_window = (Stage) add_plugin.getScene().getWindow();
        try {
            File jar = fileChooser.showOpenDialog(main_window);
            Files.copy(jar.toURI().toURL().openStream(),
                    Paths.get(System.getProperty("user.dir"), "Plugins/"+jar.getName()));
            getPlugins();
        } catch (FileAlreadyExistsException faex){
            info_addPlugin.setText("Le plugin est déjà installé");
            info_addPlugin.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){ getPlugins();}

    ArrayList<String> getFiles(){
        ArrayList<String> files = new ArrayList<>();
        Path pathDir = Paths.get(System.getProperty("user.dir") + "/Plugins/");
        try{
            DirectoryStream<Path> dirStr = Files.newDirectoryStream(pathDir);
            Iterator<Path> itrDir = dirStr.iterator();
            while (itrDir.hasNext()){
                Path pathFile = itrDir.next();
                if (pathFile.toString().substring(pathFile.toString().indexOf(".jar")).compareTo(".jar") == 0){
                    files.add(pathFile.toString());
                }
            }
        }catch (IOException ioEx){
            ioEx.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            return files;
        }
    }

    void getPlugins(){
        ArrayList<String> files = getFiles();
        if (files.size() == 0){
            // TODO
            return;
        }

        PluginsLoader plugLoader = new PluginsLoader(files);
        try {
            ArrayList<PluginsInfo> allPlugins = plugLoader.loadAllPlugins();
            for (int i = 0; i < allPlugins.size(); i++) {
                BorderPane plugin_details = new BorderPane();
                Label plugin_description = new Label("Description :\n" + allPlugins.get(i).getDescription());
                Label plugin_version = new Label("Version :\n" + allPlugins.get(i).getVersion());
                Label plugin_author = new Label("Author : " + allPlugins.get(i).getAuthor());
                //Button plugin_delete = new Button("Delete");
                //plugin_details.setAlignment(plugin_delete, Pos.CENTER);
                plugin_description.setWrapText(true);
                plugin_description.setTextAlignment(TextAlignment.JUSTIFY);
                plugin_details.setAlignment(plugin_version, Pos.CENTER);
                plugin_details.setAlignment(plugin_author, Pos.CENTER);
                //plugin_details.setLeft(plugin_delete);
                plugin_details.setCenter(plugin_description);
                plugin_details.setRight(plugin_version);
                plugin_details.setBottom(plugin_author);
                TitledPane plugin_title = new TitledPane(allPlugins.get(i).getName(), plugin_details);
                plugins_accordion.getPanes().add(plugin_title);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
