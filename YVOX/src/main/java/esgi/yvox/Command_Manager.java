package esgi.yvox;

/**
 * Created by Benoit on 01/07/2016.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static javafx.scene.input.KeyCode.T;

/**
 * Indexing :
 *      - 0 = stop
 *      - 1 = execute
 *      - 2 = create
 *      - 3 = delete
 */
public class Command_Manager {

    HashMap<Integer, ArrayList<String>> keyword_list = new HashMap<>();


    public Command_Manager() {
        ArrayList<String> words = new ArrayList<>();
        words.add("exit");
        words.add("stop");
        words.add("cancel");
        keyword_list.put(0, words);

        words = new ArrayList<>();
        words.add("execute");
        words.add("open");
        words.add("start");
        keyword_list.put(1, words);

        words = new ArrayList<>();
        words.add("create");
        words.add("make");
        //words.add("cancel");
        keyword_list.put(2, words);

        words = new ArrayList<>();
        words.add("delete");
        words.add("remove");
        words.add("suppress");
        keyword_list.put(3, words);

    }



    public boolean isCommandStop(String result){
        for (String command : keyword_list.get(0)){
            if(result.contains(command)){
                return true;
            }
        }
        return false;
    }

    public boolean isCommandOpen(String result){
        for (String command : keyword_list.get(0)){
            if(result.contains(command)){
                return true;
            }
        }
        return false;
    }

    public boolean isCommandDelete(String result){
        for (String command : keyword_list.get(0)){
            if(result.contains(command)){
                return true;
            }
        }
        return false;
    }

    public boolean isCommandCreate(String result){
        for (String command : keyword_list.get(0)){
            if(result.contains(command)){
                return true;
            }
        }
        return false;
    }
}
