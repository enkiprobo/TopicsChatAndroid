package topicschat.sqlitedatamodel;

import com.orm.SugarRecord;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class Mute extends SugarRecord {
    private GroupsTopic topic;

    public Mute(){

    }

    public Mute(GroupsTopic topic) {
        this.topic = topic;
    }

    public GroupsTopic getTopic() {
        return topic;
    }

    public void setTopic(GroupsTopic topic) {
        this.topic = topic;
    }
}
