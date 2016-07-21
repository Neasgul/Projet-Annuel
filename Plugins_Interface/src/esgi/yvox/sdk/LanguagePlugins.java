package esgi.yvox.sdk;


import java.util.ArrayList;

/**
 * Created by ostro on 16/06/2016.
 */
public interface LanguagePlugins extends PluginsInfo {

    ArrayList<String> StopKeyWordList();
    ArrayList<String> ExecuteKeyWordList();
    ArrayList<String> CreateKeyWordList();
    ArrayList<String> DeleteKeyWordList();

    String getAcousticModelPath();
    String getDictionaryPath();
    String getLanguageModelPath();


}
