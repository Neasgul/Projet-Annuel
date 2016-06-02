package esgi.yvox.annotation;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.UUID;

/**
 * Created by ostro on 02/06/2016.
 */
public class UUID_Processor {
    public UUID_Processor(Class clazz) {
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
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
