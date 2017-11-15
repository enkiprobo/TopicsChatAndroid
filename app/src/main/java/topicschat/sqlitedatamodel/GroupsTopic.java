package topicschat.sqlitedatamodel;

import com.orm.SugarRecord;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class GroupsTopic extends SugarRecord {
    private int idTopic;
    private String topicName;
    private int idGroup;
    private int countNotRead;

    public GroupsTopic(){

    }

    public GroupsTopic(int idTopic, String topicName, int idGroup) {
        this.idTopic = idTopic;
        this.topicName = topicName;
        this.idGroup = idGroup;
        this.countNotRead = 0;
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

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getCountNotRead() {
        return countNotRead;
    }

    public void setCountNotRead(int countNotRead) {
        this.countNotRead = countNotRead;
    }
}
