package esgi.yvox.test;

import esgi.yvox.Main;
import esgi.yvox.User_UUID;
import esgi.yvox.annotation.UUID_Processor;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by Benoit on 14/06/2016.
 */
public class UUIDBehaviorTest {

    @Test
    public void testcreationOfUUIDFile(){
        UUID_Processor uuid_processor = new UUID_Processor(Main.class);
        try{
            BufferedReader br = new BufferedReader(new FileReader("token.txt"));
            assertTrue(true);
        } catch (FileNotFoundException e) {
            assertTrue(false);
        }
    }
    @Test
    public void testUUIDRead(){
        User_UUID.readUUID();
        String uuid = User_UUID.getUuid();
        assertNotNull(uuid);
    }

}
