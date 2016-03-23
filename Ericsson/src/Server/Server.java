package Server;


import java.util.ArrayList;
import java.util.List;

import Topic.MySubscriber;

/**
 * Created by Harold_LIU on 3/20/16.
 */
public class Server {

    static private List<User> users = new ArrayList<User>();
    static private int validLoginTime = 0;
    static private int invalidLoginTime = 0;
    static private Server server;
    
    private void userInit()
    {
        users.add(new User("liu","123",false,0,0,0));
        users.add(new User("zhao","123",false,0,0,0));
        users.add(new User("qian","123",false,0,0,0));
        users.add(new User("sun","123",false,0,0,0));
        users.add(new User("li", "123", false, 0, 0, 0));
    }

    private  Server ()
    {
        userInit();
    }

    private boolean checkTime(User user)
    {
        long current = System.currentTimeMillis(); // 当前时间

        if (user.loginRequsetTime ==0)
        {

            user.loginDate = current;
            user.loginRequsetTime ++;
            return  true;
        }
        else if (current-user.loginDate < 1000)
        {

            if(user.loginRequsetTime<5)
            {
                System.out.println(user.loginRequsetTime);
                user.loginRequsetTime++;
                return  true;
            }
            else
                return false;
        }
        else
        {
            user.loginRequsetTime = 0;
            return  true;
        }

    }

    public User findUser(String userName)
    {
        for (int i = 0; i<users.size();i++) {
            if (userName.equals(users.get(i).UserName)) {
                return users.get(i);
            }
        }
        return null;
    }

    public static Server sharedServer()
    {
    	if (server == null)
    	{
    		server = new Server();
    	}
    	return server;
    }
    
    public int login (String userName, String password)
    {
    	System.out.println("after"+userName);
        User theUser = findUser(userName);
        if(!theUser.equals(null))
        {
            if (checkTime(theUser))
            {
                if (password.equals(theUser.Password)) {
                    validLoginTime++;
                    theUser.isLogin = true;
                    try {
						//theUser.setId(MySubscriber.getConsumerCount()+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    return 200;
                }
                else
                {
                    invalidLoginTime++;
                    return 201;
                }
            }
            else
            {
                invalidLoginTime++;
                return 203;
            }
        }
        else
        {
            invalidLoginTime ++;
            return 202;
        }
    }

    public boolean checkConnection(String userName)
    {
        User theUser = findUser(userName);

        if(!theUser.equals(null))
        {
           if (theUser.isLogin)
               return  true;
           else
               return false;
        }
        else
            return false;

    }

    public boolean sendMessages(String senderName ) {
        User sender = findUser(senderName);
        if (!sender.equals(null) && sender.isLogin) {
            sender.sendMessagesNum++;
            if (sender.sendMessagesNum < 2) {
                  return true;
                }else return false;
            } else {
                sender.isLogin = false;
                sender.sendMessagesNum = 0;
                
                // send to client log out
                return false;
            }
        }

	
    
// Login test passed

}
