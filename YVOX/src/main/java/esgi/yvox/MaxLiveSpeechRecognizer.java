package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.Microphone;
import edu.cmu.sphinx.frontend.util.StreamDataSource;

import java.io.IOException;

/**
 * Created by Benoit on 24/07/2016.
 */
public class MaxLiveSpeechRecognizer extends MaxAbstractSpeechRecognizer {

    private final Microphone microphone;

    /**
     * Constructs new live recognition object.
     *
     * @param configuration common configuration
     * @throws IOException if model IO went wrong
     */
    public MaxLiveSpeechRecognizer(Configuration configuration) throws IOException {
        super(configuration);
        microphone = speechSourceProvider.getMicrophone();
        context.getInstance(StreamDataSource.class)
                .setInputStream(microphone.getStream());
    }
    public void startRecognition(boolean clear) {
        this.recognizer.allocate();
        this.microphone.startRecording();
    }

    public void stopRecognition() {
        this.microphone.stopRecording();
        this.recognizer.deallocate();
    }
}
