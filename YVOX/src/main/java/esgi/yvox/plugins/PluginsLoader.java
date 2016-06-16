package esgi.yvox.plugins;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;

/**
 * Created by ostro on 13/06/2016.
 */
public class PluginsLoader {

    private String[] files;

    private ArrayList classLanguagePlugins;

    public PluginsLoader(){
        classLanguagePlugins = new ArrayList();
    }

    public PluginsLoader(String[] files) {
        this();
        this.files = files;
    }

    public LanguagePlugins[] loadAllLanguagePlugins() throws Exception {

        initPlugin();

        LanguagePlugins[] langPlugins = new LanguagePlugins[classLanguagePlugins.size()];

        for (int i = 0;i < langPlugins.length;i++){
            langPlugins[i] = (LanguagePlugins) ((Class) classLanguagePlugins.get(i)).newInstance();
        }

        return langPlugins;
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
        Class clazz = null;

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

                    clazz = Class.forName(string, true, loader);
                    for (int j = 0; j < clazz.getInterfaces().length;j++){
                        if (clazz.getInterfaces()[i].getName().toString().equals("plugins.LanguagePlugins")){
                            classLanguagePlugins.add(clazz);
                        }
                    }
                }
            }
        }
    }
}
