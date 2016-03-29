package Server;


/**
 * Created by Harold_LIU on 3/20/16.
 */
public class User {


    public  String UserName;
    public  String Password;
    public  boolean isLogin;
    public  int loginRequsetTime;
    public  long loginDate;
    public  int sendMessagesNum;

    public User(String _username, String _password, boolean _isLogin, int _loginRequestTime, long _loginDate, int _sendMessagesNum)
    {

        UserName = _username;
        Password = _password;
        isLogin = _isLogin;
        loginRequsetTime = _loginRequestTime;
        loginDate = _loginDate;
        sendMessagesNum = _sendMessagesNum;
    }



}
