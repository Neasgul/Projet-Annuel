package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.Context;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.decoder.adaptation.ClusteredDensityFileData;
import edu.cmu.sphinx.decoder.adaptation.Stats;
import edu.cmu.sphinx.decoder.adaptation.Transform;
import edu.cmu.sphinx.linguist.acoustic.tiedstate.Sphinx3Loader;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;

import java.io.IOException;

/**
 * Created by Benoit on 24/07/2016.
 */
public class MaxAbstractSpeechRecognizer {
    protected final Context context;
    protected final Recognizer recognizer;

    protected ClusteredDensityFileData clusters;

    protected final MaxSpeechSourceProvider speechSourceProvider;

    /**
     * Constructs recognizer object using provided configuration.
     * @param configuration initial configuration
     * @throws IOException if IO went wrong
     */
    public MaxAbstractSpeechRecognizer(Configuration configuration)
            throws IOException
    {
        this(new Context(configuration));
    }

    protected MaxAbstractSpeechRecognizer(Context context) throws IOException {
        this.context = context;
        recognizer = context.getInstance(Recognizer.class);
        speechSourceProvider = new MaxSpeechSourceProvider();
    }

    public SpeechResult getResult() {
        Result result = this.recognizer.recognize();
        return null == result?null:new SpeechResult(result);
    }

    public Stats createStats(int numClasses) {
        this.clusters = new ClusteredDensityFileData(this.context.getLoader(), numClasses);
        return new Stats(this.context.getLoader(), this.clusters);
    }

    public void setTransform(Transform transform) {
        if(this.clusters != null && transform != null) {
            this.context.getLoader().update(transform, this.clusters);
        }

    }

    public void loadTransform(String path, int numClass) throws Exception {
        this.clusters = new ClusteredDensityFileData(this.context.getLoader(), numClass);
        Transform transform = new Transform((Sphinx3Loader)this.context.getLoader(), numClass);
        transform.load(path);
        this.context.getLoader().update(transform, this.clusters);
    }
}
