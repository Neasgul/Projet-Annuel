package esgi.yvox;

import edu.cmu.sphinx.api.Microphone;

/**
 * Created by Benoit on 24/07/2016.
 */
public class MaxSpeechSourceProvider {
    private static final Microphone mic = new Microphone(16000, 16, true, false);

    Microphone getMicrophone() {
        return mic;
    }
}
