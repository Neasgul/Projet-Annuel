package esgi.yvox.annotation;

import esgi.yvox.Config;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.UUID;

/**
 * Created by ostro on 02/06/2016.
 */
public class UUID_Processor {
    public UUID_Processor(Class clazz) {
        if(!clazz.isAnnotationPresent(esgi.yvox.annotation.UUID.class)){
            String cur_dir = System.getProperty("user.dir");
            Path path = Paths.get(cur_dir);
            try{
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
                Iterator<Path> iterator = directoryStream.iterator();
                int count = 0;
                while (iterator.hasNext()){
                    Path path1 = iterator.next();
                    if(path1.endsWith("token.txt")){
                        count++;
                        break;
                    }
                }
                directoryStream.close();
                if (count == 0) {
                    UUID uuid = UUID.randomUUID();
                    FileWriter fw = new FileWriter("token.txt");
                    fw.write(uuid.toString());
                    fw.close();
                    sendNewUser(uuid.toString());
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void sendNewUser(String user_id){
        HttpURLConnection uuid_connection = null;
        try{
            // Request send
            URL uuid_url = new URL(Config.getServer_Address() + "user/add");
            uuid_connection = (HttpURLConnection) uuid_url.openConnection();
            uuid_connection.setRequestMethod("POST");

            // Parameters to send
            String url_params = "uuid=" + user_id + "&name=" + System.getProperty("user.name");

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
            System.out.println("Respond " + res);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
