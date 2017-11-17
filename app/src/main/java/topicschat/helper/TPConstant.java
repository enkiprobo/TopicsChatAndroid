package topicschat.helper;

/**
 * Created by enkiprobo on 11/16/2017.
 */

public class TPConstant {

    // for network purpose
    public static final String URL_BASE = "https://topicschatapi.herokuapp.com/";

    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_FULLNAME = "fullname";
    public static final String PARAM_PROFILEIMAGE = "profileimage";
    public static final String PARAM_BIRTHDATE = "birthdate";
    public static final String PARAM_GROUPNAME = "groupname";
    public static final String PARAM_GROUPIMAGE = "groupimage";
    public static final String PARAM_IDTOPIC = "idtopic";
    public static final String PARAM_IDGM = "idgm";
    public static final String PARAM_MESSAGE = "message";
    public static final String PARAM_IDGCD = "idgcd";
    public static final String PARAM_PIN = "pin";
    public static final String PARAM_IDGROUP = "idgroup";
    public static final String PARAM_TOPICNAME = "topicname";

    // for shared preferenced
    public static final String PREFERENCED_FILE_NAME = "com.example.enkiprobo.topicschat";

    public static final String PREFERENCED_USERNAME = "username";
    public static final String PREFERENCED_FULLNAME = "fullname";
    public static final String PREFERENCED_USERIMAGE = "profileimage";
    public static final String PREFERENCED_USERBIRTH = "birthdate";

    // for intent extra
    public static final String EXTRA_IDGROUP = "com.example.enkiprobo.topicschat.idgroup";
    public static final String EXTRA_IDGM = "com.example.enkiprobo.topicschat.idgm";
    public static final String EXTRA_GROUPNAME = "com.example.enkiprobo.topicschat.groupname";
    public static final String EXTRA_GROUPPHOTO = "com.example.enkiprobo.topicschat.groupphoto";

    // web socket
    public static final String WEBSOCKET_URL = "wss://topicschatapi.herokuapp.com/topicslivechat";

}
