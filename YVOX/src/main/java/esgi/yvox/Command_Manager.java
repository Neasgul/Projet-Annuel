package esgi.yvox;

/**
 * Created by Benoit on 01/07/2016.
 */

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

/**
 * Indexing :
 *      - 0 = stop
 *      - 1 = execute
 *      - 2 = create
 *      - 3 = delete
 */
public class Command_Manager {

    HashMap<Integer, ArrayList<String>> keyword_map = new HashMap<>();


    public Command_Manager(HashMap map) {
        this.keyword_map = map;
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
        String filename = command.getCommandWordList().get(command.getKeyWordPosition()+1);
        File file = new File(filename);
        switch (command.getType()){
            case 1:
                try {
                    Desktop.getDesktop().open(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write("nouveau Fichier");
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
            case 3:
                file.delete();
                break;
            default:
        }

        return true;
    }

    public boolean sendCommand(Command command) {
        HttpURLConnection uuid_connection = null;
        try{
            // Request send
            URL url = new URL(Config.getServer_Address()+"command");
            uuid_connection = (HttpURLConnection) url.openConnection();
            uuid_connection.setRequestMethod("POST");

            // Parameters to send
            String url_params = new String();
            StringBuilder message = new StringBuilder();
            for(String s: command.getCommandWordList()){
                message.append(s+" ");
            }
            System.out.println(User_UUID.getUuid());
            switch (command.getType()){
                case 1:
                    url_params = "uuid="+User_UUID.getUuid()+"&level=Execute&message="+message;
                    break;
                case 2:
                    url_params = "uuid="+User_UUID.getUuid()+"&level=Create&message="+message;
                    break;
                case 3:
                    url_params = "uuid="+User_UUID.getUuid()+"&level=Delete&message="+message;
                    break;
                default:
            }
            uuid_connection.setDoInput(true);
            uuid_connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(uuid_connection.getOutputStream());
            dos.writeBytes(url_params);
            dos.flush();
            dos.close();

            System.out.println("Request send");

            // Respond
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(uuid_connection.getInputStream()));
            String str_res = "";
            StringBuffer res = new StringBuffer();
            while ((str_res = buffRead.readLine()) != null){
                res.append(str_res);
            }
            buffRead.close();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
