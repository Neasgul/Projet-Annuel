package esgi.yvox;

import java.util.List;

/**
 * Created by Benoit on 06/07/2016.
 */
public class Command {
    private int type;
    private int key_word_position;

    private List<String> command_word_list;

    public Command(int type, int key_word_position, List<String> command_word_list) {
        this.type = type;
        this.key_word_position = key_word_position;
        this.command_word_list = command_word_list;
    }

    public int getType() {
        return type;
    }

    public int getKeyWordPosition() {
        return key_word_position;
    }

    public List<String> getCommandWordList() {
        return command_word_list;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", key_word_position=" + key_word_position +
                ", command_word_list=" + command_word_list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command command = (Command) o;

        if (type != command.type) return false;
        if (key_word_position != command.key_word_position) return false;
        return command_word_list.equals(command.command_word_list);

    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + key_word_position;
        return result;
    }
}
