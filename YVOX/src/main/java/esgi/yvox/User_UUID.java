package esgi.yvox;

import java.io.FileReader;
import java.io.LineNumberReader;

/**
 * Created by ostro on 02/06/2016.
 */
public class User_UUID {

    private static String uuid;

    public static String getUuid() {
        return uuid;
    }

    public static void readUUID(){
        try{
            FileReader fileReader = new FileReader("token.txt");
            LineNumberReader ln = new LineNumberReader(fileReader);
            uuid = ln.readLine();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
