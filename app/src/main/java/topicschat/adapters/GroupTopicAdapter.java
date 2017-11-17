package topicschat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.enkiprobo.topicschat.GroupChatActivity;
import com.example.enkiprobo.topicschat.R;

import java.util.List;

import topicschat.sqlitedatamodel.GroupsTopic;

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

    class GroupTopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private GroupTopicAdapter adapter;
        private TextView mtvTopicName;
        private LinearLayout mlyGroupTopicList;

        public GroupTopicViewHolder(View itemView, GroupTopicAdapter adapter) {
            super(itemView);
            mtvTopicName = (TextView) itemView.findViewById(R.id.tv_topicsName);
            mlyGroupTopicList = (LinearLayout) itemView.findViewById(R.id.ly_topic);
            this.adapter = adapter;

            mlyGroupTopicList.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();

            GroupsTopic topic = groupsTopicList.get(position);

            ((GroupChatActivity)context).setChatTopic(topic);
            ((GroupChatActivity)context).updateTitle();
        }
    }
}
