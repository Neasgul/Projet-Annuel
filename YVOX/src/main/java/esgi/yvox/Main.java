package esgi.yvox;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Benoit on 17/03/2016.
 */
public class Main extends Application{

    private Configuration mConfiguration;
    private PluginManager mPluginManager;
    private LiveSpeechRecognizer lmRecognizer;

    boolean recognitionState;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("main_scene.fxml"));
        //Parent main_sc = loader.load();
        stage.setTitle("YVOX Voice Controller");
        stage.getIcons().add(new Image("file:img/logo_icone.png"));

        Rectangle2D mainscreen = Screen.getPrimary().getVisualBounds();
        stage.setWidth(mainscreen.getWidth() * 0.5);
        stage.setHeight(mainscreen.getHeight() * 0.75);

        StackPane sp = new StackPane();
        Image imgmic = new Image("file:img/mic.png");
        ImageView imgview = new ImageView(imgmic);
        imgview.addEventHandler(MouseEvent.MOUSE_PRESSED,img_click);
        sp.getChildren().add(imgview);
        Scene scene = new Scene(sp,stage.getWidth(),stage.getHeight());
        //Scene scene = new Scene(main_sc,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.show();
        sphinxConfiguration();
        PluginManager.getInstance();
    }

    void sphinxConfiguration() throws IOException {
        mConfiguration = new Configuration();

        mConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        mConfiguration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        mConfiguration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        lmRecognizer = new LiveSpeechRecognizer(mConfiguration);

    }

    void onVoiceRecognitionStart(){
        System.out.println("Speak ... and press the button");
        recognitionState = true;
        lmRecognizer.startRecognition(true);

        while (true)
        {
            String result = lmRecognizer.getResult().getHypothesis();
            System.out.println(result);
            if (result.equals("exit"))
            {
                onVoiceRecognitionStop();
            }
        }

    }

    void onVoiceRecognitionStop(){
        recognitionState = false;
        lmRecognizer.stopRecognition();
    }


    EventHandler<MouseEvent> img_click = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println("click event : "+ recognitionState);
            if (true == recognitionState){
                onVoiceRecognitionStop();
            }
            else{
                onVoiceRecognitionStart();
            }
        }
    };

}
