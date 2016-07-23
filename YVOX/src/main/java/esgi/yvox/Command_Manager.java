package esgi.yvox;

/**
 * Created by Benoit on 01/07/2016.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Indexing :
 *      - 0 = stop
 *      - 1 = execute
 *      - 2 = create
 *      - 3 = delete
 */
public class Command_Manager {

    HashMap<Integer, ArrayList<String>> keyword_map = new HashMap<>();


    public Command_Manager() {
        ArrayList<String> words = new ArrayList<>();
        words.add("exit");
        words.add("stop");
        words.add("cancel");
        keyword_map.put(0, words);

        words = new ArrayList<>();
        words.add("execute");
        words.add("open");
        words.add("start");
        keyword_map.put(1, words);

        words = new ArrayList<>();
        words.add("create");
        words.add("make");
        //words.add("cancel");
        keyword_map.put(2, words);

        words = new ArrayList<>();
        words.add("delete");
        words.add("remove");
        words.add("suppress");
        keyword_map.put(3, words);

    }



    public boolean isCommandStop(String result){
        for (String command : keyword_map.get(0)){
            if(result.contains(command)){
                return true;
            }
        }
        return false;
    }

    public Command determindeCommand(String command){
        command = command.toLowerCase();
        List<String> command_word = new ArrayList<String>(Arrays.asList(command.split(" ")));
        int pos = 0;
        // pour tous les mots
        for (String word: command_word) {
            // on parcours les diférents type de commande
            for (Map.Entry<Integer, ArrayList<String>> entry : keyword_map.entrySet())
            {
                // et tous les mots clé de cette commande
                for (String keyword:entry.getValue()){
                    // si le mot correspond à un mot clé
                    if(word.equals(keyword)){
                        return new Command(entry.getKey(),pos,command_word);
                    }
                }
            }
            pos++;
        }
        return null;
    }

    public boolean executeCommand(Command command){
        switch (command.getType()){
            case 1:
                break;
            case 2:
                try {
                    String filename = command.getCommandWordList().get(command.getKeyWordPosition()+1)+".txt";
                    File newfile = new File(filename);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(newfile));
                    writer.write("nouveau Fichier");

                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
            case 3:
                break;
            default:
        }
        return true;
    }
}
