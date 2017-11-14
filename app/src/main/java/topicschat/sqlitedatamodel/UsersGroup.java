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

    public UsersGroup(){
    }

    public UsersGroup(int idGroup, int idGM, String groupName, String groupPhoto) {
        this.idGroup = idGroup;
        this.idGM = idGM;
        this.groupName = groupName;
        this.groupPhoto = groupPhoto;
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
}
