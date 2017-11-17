package topicschat.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.enkiprobo.topicschat.GroupChatActivity;
import com.example.enkiprobo.topicschat.R;

import java.util.List;

import topicschat.helper.TPConstant;
import topicschat.networkutil.NetworkUtilTC;
import topicschat.sqlitedatamodel.GroupsTopic;
import topicschat.sqlitedatamodel.Mute;

/**
 * Created by enkiprobo on 11/17/2017.
 */

public class GroupTopicAdapter extends RecyclerView.Adapter<GroupTopicAdapter.GroupTopicViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<GroupsTopic> groupsTopicList;

    public GroupTopicAdapter(Context context, List<GroupsTopic> groupsTopicList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.groupsTopicList = groupsTopicList;
    }

    public void updateTopicList(List<GroupsTopic> groupsTopicList){
        this.groupsTopicList = groupsTopicList;
        notifyDataSetChanged();
    }
    @Override
    public GroupTopicAdapter.GroupTopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.itemlayout_grouptopic, parent, false);

        return new GroupTopicViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(GroupTopicAdapter.GroupTopicViewHolder holder, int position) {
        GroupsTopic topic = groupsTopicList.get(position);

        holder.mtvTopicName.setText(topic.getTopicName());
    }

    @Override
    public int getItemCount() {
        return groupsTopicList.size();
    }

    class GroupTopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private GroupTopicAdapter adapter;
        private TextView mtvTopicName;
        private LinearLayout mlyGroupTopicList;

        public GroupTopicViewHolder(View itemView, GroupTopicAdapter adapter) {
            super(itemView);
            mtvTopicName = (TextView) itemView.findViewById(R.id.tv_topicsName);
            mlyGroupTopicList = (LinearLayout) itemView.findViewById(R.id.ly_topic);
            this.adapter = adapter;

            mlyGroupTopicList.setOnClickListener(this);
            mlyGroupTopicList.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();

            GroupsTopic topic = groupsTopicList.get(position);

            ((GroupChatActivity)context).setChatTopic(topic);
            ((GroupChatActivity)context).updateTitle();
        }

        @Override
        public boolean onLongClick(View view) {
            Log.d("PENCETYANGLAMA", "lama amat mencetnya");
            int position = getLayoutPosition();
            final GroupsTopic topic = groupsTopicList.get(position);

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            Log.d("PENCETYANGLAMA", topic.getIdTopic()+"");
            List<Mute> muteList = Mute.find(Mute.class, "id_topic = ?", topic.getIdTopic()+"");
            if (muteList.size()<1){

                alertDialog.setTitle("Mute Topic Dialog");
                alertDialog.setMessage("Are you sure you want to mute topic '"+topic.getTopicName()+"'?");

                alertDialog.setButton("Mute", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("PENCETYANGLAMA", "yang ke mute");

                        SharedPreferences sp = context.getSharedPreferences(TPConstant.PREFERENCED_FILE_NAME, context.MODE_PRIVATE);
                        String username = sp.getString(TPConstant.PREFERENCED_USERNAME, "");

                        new NetworkUtilTC().muteChat(context, username, topic.getIdTopic());
                    }
                });
            } else{
                alertDialog.setTitle("Unmute Topic Dialog");
                alertDialog.setMessage("Are you sure you want to unmute topic '"+topic.getTopicName()+"'?");

                alertDialog.setButton("Unmute", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("PENCETYANGLAMA", "yang tidak ke mute");

                        SharedPreferences sp = context.getSharedPreferences(TPConstant.PREFERENCED_FILE_NAME, context.MODE_PRIVATE);
                        String username = sp.getString(TPConstant.PREFERENCED_USERNAME, "");

                        new NetworkUtilTC().unmuteChat(context, username, topic.getIdTopic());
                    }
                });
            }

            alertDialog.show();


            return true;
        }
    }
}
