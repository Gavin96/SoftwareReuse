package Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import reuse.cm.ReadJson;
import Server.ServerSave.Listen;

import static org.junit.Assert.*;

/**
 * Created by Harold_LIU on 5/31/16.
 */
public class ServerSaveTest {

    static public String port;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMain() throws Exception {
        port = "tcp://localhost:" + ReadJson.GetConfig("port", "sets.txt");
        ServerSave serverSave=new ServerSave();
        Listen receivedMsg = serverSave.new Listen("ServerMsg");
        receivedMsg.start();
        System.out.println("--------Server Recevie Start------");
    }
}