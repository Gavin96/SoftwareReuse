package Server;


/**
 * Created by Harold_LIU on 3/20/16.
 */
public class User {


    private   String UserName;
    private   String Password;
    private   boolean isLogin;
    private   int loginRequsetTime;
    private   long loginDate;
    private   int sendMessagesNum;
    private int groupId;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public int getLoginRequsetTime() {
        return loginRequsetTime;
    }

    public void setLoginRequsetTime(int loginRequsetTime) {
        this.loginRequsetTime = loginRequsetTime;
    }

    public long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }

    public int getSendMessagesNum() {
        return sendMessagesNum;
    }

    public void setSendMessagesNum(int sendMessagesNum) {
        this.sendMessagesNum = sendMessagesNum;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public User(String _username, String _password, boolean _isLogin, int _loginRequestTime, long _loginDate, int _sendMessagesNum,int _groupId)
    {
        UserName = _username;
        Password = _password;
        isLogin = _isLogin;
        loginRequsetTime = _loginRequestTime;
        loginDate = _loginDate;
        sendMessagesNum = _sendMessagesNum;
        groupId = _groupId;
    }


}
