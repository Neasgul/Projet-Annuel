package esgi.yvox;

/**
 * Created by Benoit on 05/04/2016.
 */
public class Plugin {
    private String directory;
    private String name;
    private String description;
    private String author;
    private String version;

    public Plugin() {
    }

    public Plugin(String directory, String name, String description, String author, String version) {
        this.directory = directory;
        this.name = name;
        this.description = description;
        this.author = author;
        this.version = version;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
