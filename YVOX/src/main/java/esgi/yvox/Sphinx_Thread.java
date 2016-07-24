package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.fst.Export;
import esgi.yvox.controller.MainScene_Controller;
import esgi.yvox.plugins.PluginsLoader;
import esgi.yvox.sdk.LanguagePlugins;
import javafx.concurrent.Task;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Benoit on 06/05/2016.
 */
public class Sphinx_Thread extends Task{
    private SphinxEvent sphinx_callback;
    private Command_Manager command_manager;
    private static Configuration mConfiguration;
    private static LiveSpeechRecognizer lmRecognizer;
    private static Sphinx_Controller parent;
    private LanguagePlugins usedLanguage;
    private ArrayList<String> filelist;

    public Sphinx_Thread(Sphinx_Controller controller,SphinxEvent event) {
        this.sphinx_callback = event;
        this.parent = controller;
    }

    void initialization() {
        System.out.println("thread Initiazation");
        PluginsLoader pluginsLoader = new PluginsLoader();
        String jarName = "";
        try {
            LanguagePlugins[] allLanguagePlugins = pluginsLoader.loadAllLanguagePlugins();
            for (int i = 0; i < allLanguagePlugins.length; i++) {
                if (allLanguagePlugins[i].getName().compareTo(Main.ChoiceBoxValue) == 0){
                    usedLanguage = allLanguagePlugins[i];
                    jarName = pluginsLoader.getPluginNameJar().get(usedLanguage.getName()).toString();
                    break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        filelist = getFiles(jarName);
        mConfiguration = new Configuration();
        for (String s:filelist ) {
            String[] path = s.split("\\\\");
            s = path[path.length-3] +"\\\\"+ path[path.length-2] +"\\\\"+ path[path.length-1];
            if(path[path.length-1].equals(usedLanguage.getAcousticModelPath())){
                mConfiguration.setAcousticModelPath(s);
            }
            if(path[path.length-1].equals(usedLanguage.getDictionaryPath())){
                mConfiguration.setDictionaryPath(s);
            }
            if(path[path.length-1].equals(usedLanguage.getLanguageModelPath())){
                mConfiguration.setLanguageModelPath(s);
            }
        }
        System.out.println(mConfiguration.getAcousticModelPath());
        System.out.println(mConfiguration.getDictionaryPath());
        System.out.println(mConfiguration.getLanguageModelPath());

        command_manager = new Command_Manager(generateKeyWordMap());

        try {
            lmRecognizer = new LiveSpeechRecognizer(mConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    protected Object call() throws Exception {
        System.out.println("thread starting");
        if(mConfiguration == null && lmRecognizer == null){
            initialization();
        }
        lmRecognizer.startRecognition(true);

        while (!parent.AskToStop)
        {
            String result = lmRecognizer.getResult().getHypothesis();
            //sphinx_callback.onResult(result);
            updateMessage(result);
            System.out.println(result);
            if (command_manager.isCommandStop(result))
            {
                break;
            }else {
                Command current = command_manager.determindeCommand(result);
                if(current!=null) {
                    command_manager.executeCommand(current);
                }
            }
        }
        System.out.println("thread stoping");
        lmRecognizer.stopRecognition();
        sphinx_callback.onStop();
        System.out.println("Recognition finished correctly");
        return true;
    }

    HashMap<Integer, ArrayList<String>> generateKeyWordMap(){
        HashMap<Integer, ArrayList<String>> keyword_map = new HashMap<>();

        keyword_map.put(0,usedLanguage.StopKeyWordList());
        keyword_map.put(1,usedLanguage.ExecuteKeyWordList());
        keyword_map.put(2,usedLanguage.CreateKeyWordList());
        keyword_map.put(3,usedLanguage.DeleteKeyWordList());

        return keyword_map;
    }

    public ArrayList<String> getFiles(String jarName){
        ArrayList<String> files = new ArrayList<>();
        Path pathDir = Paths.get(System.getProperty("user.dir") + "/Plugins/" + jarName.substring(0, jarName.length()-4) + "/");
        try{
            DirectoryStream<Path> dirStr = Files.newDirectoryStream(pathDir);
            Iterator<Path> itrDir = dirStr.iterator();
            while (itrDir.hasNext()){
                Path pathFile = itrDir.next();
                if (pathFile.toString().substring(pathFile.toString().length() - 4).compareTo(".jar") != 0) {
                    files.add(pathFile.toString());
                }
            }
            dirStr.close();
        }catch (IOException ioEx){
            ioEx.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            return files;
        }
    }


    interface SphinxEvent{
        void onStop();
    }
}
