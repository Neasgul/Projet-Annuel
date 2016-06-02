package esgi.yvox;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**
 * Created by Benoit on 05/04/2016.
 */
public class PluginManager {
    private static PluginManager mInstance;
    private List<Plugin> mPluginList = new ArrayList<>();
    private String fileName ="plugins.json";

    public static PluginManager getInstance() {
        if (mInstance == null) {
            mInstance =  new PluginManager();
        }
        return mInstance;
    }

    private PluginManager() {
        /*JSONObject jsonPlugin = getPluginJSON();
        JSONArray jsonList = (JSONArray) jsonPlugin.get("list");
        for(int i =0;i<jsonList.size();i++){
            JSONObject plugin = (JSONObject) jsonList.get(i);
            Plugin newPlugin = new Plugin(
                    plugin.get("directory").toString(),
                    plugin.get("name").toString(),
                    plugin.get("description").toString(),
                    plugin.get("author").toString(),
                    plugin.get("version").toString()
                    );
            mPluginList.add(newPlugin);
        }*/
    }
    private  JSONObject getPluginJSON() {
        ClassLoader classLoader = getClass().getClassLoader();
        JSONObject jsonObject = null;
        JSONParser jsonParser = new JSONParser();
        try {
            File file = new File(classLoader.getResource(fileName).getFile());
            FileReader fileReader = new FileReader(file);
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            System.out.println(jsonObject.toJSONString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        finally {
            return jsonObject;
        }

    }
}
