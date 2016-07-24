package esgi.yvox.controller;

import esgi.yvox.exception.PluginException;
import esgi.yvox.plugins.PluginsLoader;
import esgi.yvox.sdk.PluginsInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by ostro on 19/06/2016.
 */
public class Plugins_Controller {

    @FXML
    private Button home_button;

    @FXML
    private Button add_plugin;

    @FXML
    private Label info_Plugin;

    @FXML
    private Accordion plugins_accordion;

    @FXML
    private Label label_noPlugins;

    @FXML
    private ScrollPane sc_plugins;

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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Zip Files", "*.zip"));
        main_window = (Stage) add_plugin.getScene().getWindow();
        try {
            uncompressZipFile(fileChooser.showOpenDialog(main_window), new File("temp"));
            copyTempInPlugins();
            getPlugins();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){ getPlugins();}

    void getPlugins(){
        plugins_accordion.getPanes().clear();
        PluginsLoader plugLoader = new PluginsLoader();
        ArrayList<String> files = plugLoader.getFiles();

        if (files.size() == 0){
            sc_plugins.setVisible(false);
            label_noPlugins.setText("No Plugin detected");
            label_noPlugins.setVisible(true);
            return;
        }
        try {
            ArrayList<PluginsInfo> allPlugins = plugLoader.loadAllPlugins();
            for (int i = 0; i < allPlugins.size(); i++) {
                BorderPane plugin_details = new BorderPane();
                Label plugin_description = new Label("Description :\n" + allPlugins.get(i).getDescription());
                Label plugin_version = new Label("Version :\n" + allPlugins.get(i).getVersion());
                Label plugin_author = new Label("Author : " + allPlugins.get(i).getAuthor());
                Button plugin_delete = new Button("Delete");
                final int finalI = i;
                plugin_delete.setOnAction((event -> {
                    try {
                        String jarNameFile = plugLoader.getPluginNameJar().get(allPlugins.get(finalI).getName()).toString();
                        Path jarPath = Paths.get(System.getProperty("user.dir") + "/Plugins/" + jarNameFile.substring(0, jarNameFile.length()-4));
                        deleteAllFile(jarPath);
                        Files.delete(jarPath);
                        info_Plugin.setText("The plugin has been deleted !");
                        info_Plugin.setVisible(true);
                        getPlugins();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
                plugin_description.setWrapText(true);
                plugin_description.setTextAlignment(TextAlignment.JUSTIFY);
                plugin_details.setAlignment(plugin_delete, Pos.CENTER);
                plugin_details.setAlignment(plugin_version, Pos.CENTER);
                plugin_details.setAlignment(plugin_author, Pos.CENTER);
                plugin_details.setLeft(plugin_delete);
                plugin_details.setCenter(plugin_description);
                plugin_details.setRight(plugin_version);
                plugin_details.setBottom(plugin_author);
                plugin_details.setMargin(plugin_delete, new Insets(10));
                plugin_details.setMaxHeight(Double.MAX_VALUE);
                TitledPane plugin_title = new TitledPane(allPlugins.get(i).getName(), plugin_details);
                plugins_accordion.getPanes().add(plugin_title);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    void deleteAllFile(Path path) throws IOException {
        DirectoryStream<Path> dirStr = Files.newDirectoryStream(path);
        Iterator<Path> itrDir = dirStr.iterator();
        while (itrDir.hasNext()){
            Path pathFile = itrDir.next();
            if (pathFile.toFile().isDirectory()){
                deleteAllFile(pathFile);
                Files.delete(pathFile);
            }else {
                Files.delete(pathFile);
            }
        }
        dirStr.close();
    }

    boolean checkValidPlugin(File file) throws ClassNotFoundException, IOException, PluginException {
        File jar = file;
        URL fileUrl = jar.toURI().toURL();
        URLClassLoader loader = new URLClassLoader(new URL[]{fileUrl});
        JarFile jarFile = new JarFile(jar);
        Enumeration enumeration = jarFile.entries();
        boolean flag = false;

        outofloop:
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement().toString();

            if (string.length() > 5 && string.substring(string.length() - 6).compareTo(".class") == 0) {
                string = string.substring(0, string.length() - 6);
                string = string.replaceAll("/", ".");

                Class clazz = Class.forName(string, true, loader);
                Annotation[] clazzAnnotations = clazz.getAnnotations();
                for (int k = 0; k < clazzAnnotations.length; k++) {
                    if (clazzAnnotations[k].toString().contains("YVOXPlugin")) {
                        flag = true;
                        break outofloop;
                    }
                }
            }
        }
        jarFile.close();
        loader.close();

        return flag;
    }

    File uncompressZipFile(File zipFile, File contentDest) throws IOException, ClassNotFoundException, FileAlreadyExistsException{
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry ze;

        while ((ze = zis.getNextEntry()) != null) {
            if (ze.isDirectory()){
                File dir = new File(contentDest.getAbsolutePath() + File.separator + ze.getName());
                if (!dir.exists()){
                    dir.mkdir();
                }
            }else{
                File file = new File(contentDest.getAbsolutePath() + File.separator + ze.getName());
                if (!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(contentDest.getAbsolutePath() + File.separator + ze.getName());

                byte[] buffer = new byte[1024];
                int read;

                while ((read = zis.read(buffer)) > -1){
                    fos.write(buffer, 0, read);
                }
                fos.flush();
                fos.close();
            }
        }
        zis.closeEntry();
        zis.close();

        return contentDest;
    }

    void copyTempInPlugins() throws IOException {
        DirectoryStream<Path> dirStr = Files.newDirectoryStream(Paths.get(System.getProperty("user.dir") + "/temp"));
        Iterator<Path> itrDir = dirStr.iterator();

        ArrayList<Path> pathList = new ArrayList<>();
        try {
            String jarName = "";
            while (itrDir.hasNext()) {
                Path itrPath = itrDir.next();
                if (itrPath.toString().substring(itrPath.toString().length()-4, itrPath.toString().length()).compareTo(".jar") == 0){
                    if (!checkValidPlugin(itrPath.toFile())){
                        throw new PluginException("This file isn't a valid plugin !");
                    }
                    jarName = itrPath.toString().split("\\\\")[itrPath.toString().split("\\\\").length-1];
                    jarName = jarName.substring(0, jarName.length()-4);
                }
                pathList.add(itrPath);
            }

            File jarfile = new File(System.getProperty("user.dir") + "/Plugins/" + jarName);
            jarfile.mkdir();
            Iterator<Path> itrList = pathList.iterator();
            while (itrList.hasNext()) {
                Path pathListItem = itrList.next();
                URL pathToURL = pathListItem.toUri().toURL();
                Files.move(pathListItem, Paths.get(System.getProperty("user.dir") + "/Plugins/" + jarName + "/"
                        +  pathToURL.getFile().toString().split("/")[pathToURL.getFile().toString().split("/").length-1]), StandardCopyOption.REPLACE_EXISTING);
            }

            info_Plugin.setText("The plugin has been added !");
            info_Plugin.setVisible(true);
            if (label_noPlugins.isVisible()){
                label_noPlugins.setVisible(false);
                sc_plugins.setVisible(true);
            }
        } catch (PluginException ex) {
            info_Plugin.setText(ex.getMessage());
            info_Plugin.setVisible(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileAlreadyExistsException|DirectoryNotEmptyException faex){
            info_Plugin.setText("This plugin is already installed !");
            info_Plugin.setVisible(true);
            deleteAllFile(Paths.get(System.getProperty("user.dir") + "/temp"));
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            dirStr.close();
            Files.delete(Paths.get(System.getProperty("user.dir") + "/temp"));
        }
    }
}
