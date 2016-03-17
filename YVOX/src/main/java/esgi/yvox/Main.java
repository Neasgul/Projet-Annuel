package esgi.yvox;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Created by Benoit on 17/03/2016.
 */
public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("YVOX Voice Controller : start state");

        stage.show();
    }

    void sphinxConfiguration(){

    }
}
