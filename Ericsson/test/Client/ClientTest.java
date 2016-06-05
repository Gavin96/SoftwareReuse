package Client;

import com.TopicLuo.MySubscriber;
import junit.framework.TestCase;

/**
 * Created by Harold_LIU on 3/25/16.
 */
public class ClientTest extends TestCase {

    private Client client;

    public void setUp() throws Exception {
        super.setUp();
        long ClientCount= MySubscriber.getConsumerCount();
        client = new Client();
        Client.Listen mainListen=client.new Listen("Ericsson",false);
        mainListen.start();
        client.name=String.valueOf(ClientCount);
        Client.Listen loginListen = client.new Listen(client.name,true);
        //Listen loginListen = client.new Listen("sb",true);

        loginListen.start();
    }

    public void testLogin() throws Exception {
        client.Login("liu","123");
        client.Login("dfdsaf","123");
        client.Login("liu","dafdsf");
        client.Login("liu","");
        client.Login("","dasfdsf");
        client.Login("","");

        for (int i =0; i<6; i++)
        {
            client.Login("liu","fafdafaf");
        }
    }

    public void testSendMsg() throws Exception {
        client.sendMsg("dsfadsa","username",true); //
        client.sendMsg("","username",true);
        client.sendMsg("dadfafasdf","",true);
        client.sendMsg("dafas","username",false);
        client.sendMsg("","",true);
        client.sendMsg("","",false);
    }
}