package topicschat.sqlitedatamodel;

import com.orm.SugarRecord;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class Mute extends SugarRecord {
    private int idTopic;

    public Mute(){

    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public Mute(int idTopic) {

        this.idTopic = idTopic;
    }
}
