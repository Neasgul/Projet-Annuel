package esgi.yvox;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Teddy on 29/04/2016.
 */
public class Controller {
    private Main yvox;

    @FXML
    private ImageView mic;

    @FXML
    public void initialize(){
        System.out.println("initalize");
        yvox = new Main();
        //mic.addEventHandler(MouseEvent.MOUSE_PRESSED, yvox.img_click);
    }
}
