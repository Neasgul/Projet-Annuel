package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.IOException;

/**
 * Created by Benoit on 06/05/2016.
 */
public class Sphinx_Thread implements Runnable{
    private Thread t;
    private String threadName;

    private SphinxEvent sphinx_callback;

    private Configuration mConfiguration;
    private PluginManager mPluginManager;
    private LiveSpeechRecognizer lmRecognizer;

    public Sphinx_Thread(SphinxEvent event, String Name) {
        this.threadName = Name;
        this.sphinx_callback = event;
    }

    @Override
    public void run() {

    }

    public void start() {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }


    public void start_recognition() {
        System.out.println(threadName + " start");
        lmRecognizer.startRecognition(true);

        while (true)
        {
            String result = lmRecognizer.getResult().getHypothesis();
            System.out.println(result);
            if (result.equals("exit"))
            {
                this.stop_recognition();
                sphinx_callback.onStop();
                break;
            }
        }
    }

    public void stop_recognition() {
        System.out.println(threadName + " stop");
        lmRecognizer.stopRecognition();

    }

    void Initialization() {
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

    interface SphinxEvent{
        void onStop();
    }
}
