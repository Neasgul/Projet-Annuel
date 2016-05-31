package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import javafx.concurrent.Task;

import java.io.IOException;

/**
 * Created by Benoit on 06/05/2016.
 */
public class Sphinx_Thread extends Task{
    private SphinxEvent sphinx_callback;

    private static Configuration mConfiguration;
    private static LiveSpeechRecognizer lmRecognizer;
    private static Sphinx_Controller parent;

    public Sphinx_Thread(Sphinx_Controller controller,SphinxEvent event) {
        this.sphinx_callback = event;
        this.parent = controller;
    }

    void Initialization() {
        System.out.println("thread Initiazation");
        mConfiguration = new Configuration();

        mConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        mConfiguration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        mConfiguration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

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
            Initialization();
        }
        lmRecognizer.startRecognition(true);

        while (!parent.AskToStop)
        {
            String result = lmRecognizer.getResult().getHypothesis();
            //sphinx_callback.onResult(result);
            updateMessage(result);
            System.out.println(result);
            if (result.equals("exit") || result.equals("stop") || result.equals("cancel"))
            {
                break;
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
