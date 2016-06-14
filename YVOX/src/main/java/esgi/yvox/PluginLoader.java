package esgi.yvox;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;

/**
 * Created by ostro on 13/06/2016.
 */
public class PluginLoader {

    private String[]files;

    private ArrayList classPlugins;

    public void loadAllPlugins() throws Exception {

        initPlugin();

        //TODO

    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    private void initPlugin() throws Exception {
        if (files == null){
            throw new Exception("File not defined");
        }

        Path[] f = new Path[files.length];
        URLClassLoader loader;
        String string = "";
        Enumeration enumeration;
        Class tmpClass = null;

        for (int i = 0; i < f.length;i++){
            f[i] = Paths.get(files[i]);
            if (f[i] == null){
                break;
            }

            loader = new URLClassLoader(new URL[]{f[i].toUri().toURL()});
            JarFile jarFile = new JarFile(String.valueOf(f[i].toAbsolutePath()));
            enumeration = jarFile.entries(); // Get content of jar

            while (enumeration.hasMoreElements()){
                string = enumeration.nextElement().toString();

                if (string.substring(string.length()-6).compareTo(".class") == 0){
                    string = string.substring(string.length()-6);
                    string = string.replaceAll("/",".");

                    tmpClass = Class.forName(string, true, loader);
                    for (int j = 0; j < tmpClass.getInterfaces().length;j++){
                        if (tmpClass.getInterfaces()[i].getName().toString().equals("Plugin")){
                            classPlugins.add(tmpClass);
                        }
                    }
                }
            }
        }
    }
}
