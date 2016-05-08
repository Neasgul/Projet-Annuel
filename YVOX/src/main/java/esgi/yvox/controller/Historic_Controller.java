package esgi.yvox.controller;

import com.sun.deploy.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;

/**
 * Created by Teddy on 04/05/2016.
 */
public class Historic_Controller {

    @FXML
    private Button button_home;

    @FXML
    private GridPane gp_Historic;

    @FXML
    private Label lbl_noHistoric;

    @FXML
    void onHomeClick(ActionEvent event) {
        System.out.println("Open Home window");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main_scene.fxml"));
            Parent home_window = loader.load();

            Stage main_window = (Stage) button_home.getScene().getWindow();
            Scene scene_historic = new Scene(home_window, main_window.getWidth(), main_window.getHeight());
            main_window.setScene(scene_historic);
            main_window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        getHistoric();
    }

    void getHistoric(){
        HttpURLConnection historic_connection = null;
        try{
            URL historic_url = new URL("http://localhost:8888/CommandLog");
            historic_connection = (HttpURLConnection) historic_url.openConnection();
            historic_connection.setRequestMethod("GET");

            BufferedReader buffer = new BufferedReader(new InputStreamReader(historic_connection.getInputStream()));
            String bufferline = "";
            String res = "";
            JSONArray jsonArray = new JSONArray();
            JSONParser jsonParser = new JSONParser();
            while ((bufferline = buffer.readLine()) != null) {
                res = bufferline;
                res = res.substring(1, res.length()-1).replace("\\\"","\"");
                Object object = jsonParser.parse(res);
                jsonArray = (JSONArray) object;
            }
            buffer.close();
            if (res == ""){
                lbl_noHistoric.setVisible(true);
                gp_Historic.setVisible(false);
                return;
            }
            int count = (res.length() - res.replace("},","").length()) / 2;
            Object objLevel = "level";
            Object objMessage = "message";
            Object objTimestamp = "timestamp";
            for (int i = 0; i <= count; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Object resLevel = jsonObject.get(objLevel);
                Object resMessage = jsonObject.get(objMessage);
                Object resTimestamp = jsonObject.get(objTimestamp);
                Label lbl_Level = new Label(resLevel.toString());
                Label lbl_Message = new Label(resMessage.toString());
                resTimestamp = resTimestamp.toString().substring(0, resTimestamp.toString().indexOf(".")).replace("T", " ");
                Label lbl_Timestamp = new Label(resTimestamp.toString());
                gp_Historic.setHalignment(lbl_Level, HPos.CENTER);
                gp_Historic.setHalignment(lbl_Message, HPos.CENTER);
                lbl_Timestamp.setWrapText(true);
                lbl_Timestamp.setTextAlignment(TextAlignment.CENTER);
                gp_Historic.add(lbl_Level, 0, i+1);
                gp_Historic.add(lbl_Message, 1, i+1);
                gp_Historic.add(lbl_Timestamp, 2, i+1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            historic_connection.disconnect();
        }
    }
}
