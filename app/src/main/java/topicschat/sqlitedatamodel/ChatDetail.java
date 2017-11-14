package topicschat.sqlitedatamodel;

import com.orm.SugarRecord;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class ChatDetail extends SugarRecord {

    private int idChat;
    private String message;
    private GroupsTopic topic;
    private boolean pinned;
    private String dateCreated;

    public ChatDetail() {

    }

    public ChatDetail(int idChat, String message, GroupsTopic topic, boolean pinned, String dateCreated) {
        this.idChat = idChat;
        this.message = message;
        this.topic = topic;
        this.pinned = pinned;
        this.dateCreated = dateCreated;
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

    public GroupsTopic getTopic() {
        return topic;
    }

    public void setTopic(GroupsTopic topic) {
        this.topic = topic;
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
}
