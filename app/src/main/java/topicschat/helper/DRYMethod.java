package topicschat.helper;

import android.widget.Button;

import com.example.enkiprobo.topicschat.R;

/**
 * Created by enkiprobo on 11/16/2017.
 */

public class DRYMethod {

    public static void buttonToRiple(Button bt){
        bt.setEnabled(true);
        bt.setBackgroundResource(R.drawable.riple_button_default);
    }
    public static void buttonClicked(Button bt){
        bt.setEnabled(false);
        bt.setBackgroundResource(R.drawable.riple_button_clicked);
    }
}
