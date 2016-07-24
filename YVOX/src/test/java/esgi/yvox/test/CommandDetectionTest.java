package esgi.yvox.test;

import esgi.yvox.Command;
import esgi.yvox.Command_Manager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Benoit on 06/07/2016.
 */
public class CommandDetectionTest {


    Command_Manager command_manager;

    @Before
    public void SetUp(){

        HashMap<Integer, ArrayList<String>> keyword_map = new HashMap<>();
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

        command_manager = new Command_Manager(keyword_map);
    }
    @Test
    public void testCreationCommand(){
        String result = "Create blabla or not may be";
        Command command = command_manager.determindeCommand(result);
        System.out.println("Creation Command :\n"+command);
        assertNotNull(command);
        //command_manager.executeCommand()
    }
    @Test
    public void testExecuteCommand(){
        String result = "blabla or execute remove not may be";
        Command command = command_manager.determindeCommand(result);
        System.out.println("Execute Command :\n"+command);
        assertNotNull(command);
        //command_manager.executeCommand()
    }
    @Test
    public void testDeleteCommand(){
        String result = "blabla delete or not may be";
        Command command = command_manager.determindeCommand(result);
        System.out.println("Delete Command :\n"+command);
        assertNotNull(command);
        //command_manager.executeCommand()
    }
    @Test
    public void testNotCommand(){
        String result = "blabla or not may be";
        Command command = command_manager.determindeCommand(result);
        System.out.println("Not a Command : \n"+command);
        assertNull(command);
        //command_manager.executeCommand()
    }


}
