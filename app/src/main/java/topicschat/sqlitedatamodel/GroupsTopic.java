package topicschat.sqlitedatamodel;

import com.orm.SugarRecord;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class GroupsTopic extends SugarRecord {
    private int idTopic;
    private String topicName;
    private UsersGroup group;
    private int countNotRead;

    public GroupsTopic(){

    }

    public GroupsTopic(int idTopic, String topicName, UsersGroup group, int countNotRead) {
        this.idTopic = idTopic;
        this.topicName = topicName;
        this.group = group;
        this.countNotRead = countNotRead;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public UsersGroup getGroup() {
        return group;
    }

    public void setGroup(UsersGroup group) {
        this.group = group;
    }

    public int getCountNotRead() {
        return countNotRead;
    }

    public void setCountNotRead(int countNotRead) {
        this.countNotRead = countNotRead;
    }
}
