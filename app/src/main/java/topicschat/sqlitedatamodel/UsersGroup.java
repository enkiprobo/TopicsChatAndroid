package topicschat.sqlitedatamodel;

import com.orm.SugarRecord;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class UsersGroup extends SugarRecord {
    private int idGroup;
    private int idGM;
    private String groupName;
    private String groupPhoto;
    private String recentChat;
    private String recenChatDate;
    private int countNotReadAll;

    public UsersGroup(int idGroup, int idGM, String groupName, String groupPhoto, String recentChat, String recenChatDate, int countNotReadAll) {
        this.idGroup = idGroup;
        this.idGM = idGM;
        this.groupName = groupName;
        this.groupPhoto = groupPhoto;
        this.recentChat = recentChat;
        this.recenChatDate = recenChatDate;
        this.countNotReadAll = countNotReadAll;
    }

    public UsersGroup(){
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdGM() {
        return idGM;
    }

    public void setIdGM(int idGM) {
        this.idGM = idGM;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public String getRecentChat() {
        return recentChat;
    }

    public void setRecentChat(String recentChat) {
        this.recentChat = recentChat;
    }

    public String getRecenChatDate() {
        return recenChatDate;
    }

    public void setRecenChatDate(String recenChatDate) {
        this.recenChatDate = recenChatDate;
    }

    public int getCountNotReadAll() {
        return countNotReadAll;
    }

    public void setCountNotReadAll(int countNotReadAll) {
        this.countNotReadAll = countNotReadAll;
    }
}
