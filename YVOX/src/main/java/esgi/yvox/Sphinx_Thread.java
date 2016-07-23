package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import esgi.yvox.controller.MainScene_Controller;
import esgi.yvox.plugins.PluginsLoader;
import esgi.yvox.sdk.LanguagePlugins;
import javafx.concurrent.Task;

import java.io.IOException;

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

    public Sphinx_Thread(Sphinx_Controller controller,SphinxEvent event) {
        this.sphinx_callback = event;
        this.parent = controller;
    }

    void initialization() {
        System.out.println("thread Initiazation");
        PluginsLoader pluginsLoader = new PluginsLoader();
        try {
            LanguagePlugins[] allLanguagePlugins = pluginsLoader.loadAllLanguagePlugins();
            for (int i = 0; i < allLanguagePlugins.length; i++) {
                if (allLanguagePlugins[i].getName().compareTo(Main.ChoiceBoxValue) == 0){
                    usedLanguage = allLanguagePlugins[i];
                    break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        mConfiguration = new Configuration();
        mConfiguration.setAcousticModelPath(usedLanguage.getAcousticModelPath());
        mConfiguration.setDictionaryPath(usedLanguage.getDictionaryPath());
        mConfiguration.setLanguageModelPath(usedLanguage.getLanguageModelPath());
        command_manager = new Command_Manager();

        try {
            lmRecognizer = new LiveSpeechRecognizer(mConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
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

    interface SphinxEvent{
        void onStop();
    }
}
