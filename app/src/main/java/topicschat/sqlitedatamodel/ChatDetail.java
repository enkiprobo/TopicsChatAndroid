package topicschat.sqlitedatamodel;

import android.content.Context;

import com.orm.SugarRecord;

import java.util.List;

import topicschat.networkutil.NetworkUtilTC;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class ChatDetail extends SugarRecord {

    private int idChat;
    private String message;
    private int idTopic;
    private boolean pinned;
    private String dateCreated;

    private String userUsername;
    private String userUserImage;
    private String userIdUser;
    private String userBirthDate;

    public ChatDetail(int idChat, String message, int idTopic, boolean pinned, String dateCreated, String userUsername, String userUserImage, String userIdUser, String userBirthDate) {
        this.idChat = idChat;
        this.message = message;
        this.idTopic = idTopic;
        this.pinned = pinned;
        this.dateCreated = dateCreated;
        this.userUsername = userUsername;
        this.userUserImage = userUserImage;
        this.userIdUser = userIdUser;
        this.userBirthDate = userBirthDate;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserUserImage() {
        return userUserImage;
    }

    public void setUserUserImage(String userUserImage) {
        this.userUserImage = userUserImage;
    }

    public String getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(String userIdUser) {
        this.userIdUser = userIdUser;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public ChatDetail() {

    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ChatDetail(int idChat, String message, int idTopic, boolean pinned, String dateCreated) {

        this.idChat = idChat;
        this.message = message;
        this.idTopic = idTopic;
        this.pinned = pinned;
        this.dateCreated = dateCreated;
    }

    public static List<ChatDetail> getGroupsChat(Context context, int idGroup, String groupName){
        List<GroupsTopic> groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", idGroup+"");
        String whereClause = "id_topic = -1";
        if (groupsTopicList.size() > 0) {
            whereClause = "";
            for (int i = 0; i < groupsTopicList.size(); i++) {
                whereClause += (i == 0) ? "id_topic = " + groupsTopicList.get(i).getIdTopic() : " OR id_topic = " + groupsTopicList.get(i).getIdTopic();
            }
        } else{
            NetworkUtilTC networkUtilTC = new NetworkUtilTC();
            networkUtilTC.getGroupTopic(context, idGroup);
        }

        return ChatDetail.find(ChatDetail.class, whereClause, null);
    }

    public static List<ChatDetail> getGroupsChatFromTopic(Context context, int idTopic){
        List<GroupsTopic> topic = GroupsTopic.find(GroupsTopic.class, "id_topic = ?", idTopic+"");
        if (topic.get(0).getTopicName().equals("All")){
            List<GroupsTopic> groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", topic.get(0).getIdGroup()+"");
            String whereClause = "id_topic = -1";
            if (groupsTopicList.size() > 0) {
                whereClause = "";
                for (int i = 0; i < groupsTopicList.size(); i++) {
                    whereClause += (i == 0) ? "id_topic = " + groupsTopicList.get(i).getIdTopic() : " OR id_topic = " + groupsTopicList.get(i).getIdTopic();
                }
            }
            return ChatDetail.find(ChatDetail.class, whereClause, null);
        }

        return ChatDetail.find(ChatDetail.class, "id_topic = ?", idTopic+"");
    }
}
