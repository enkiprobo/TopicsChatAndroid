package topicschat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enkiprobo.topicschat.R;

import java.util.List;

import topicschat.sqlitedatamodel.ChatDetail;
import topicschat.sqlitedatamodel.GroupsTopic;

/**
 * Created by enkiprobo on 11/15/2017.
 */

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.GroupChatViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ChatDetail> chatDetailList;

    public GroupChatAdapter(Context context, List<ChatDetail> chatDetailList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.chatDetailList = chatDetailList;
    }

    public void setChatDetailList(List<ChatDetail> chatDetailList){
        this.chatDetailList = chatDetailList;
        notifyDataSetChanged();
    }

    public void update(int idGroup){
        List<GroupsTopic> groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", idGroup+"");
        String whereClause = "";
        if (groupsTopicList.size()>0){
            for (int i = 0; i < groupsTopicList.size(); i++) {
                whereClause += (i==0)? "id_topic = "+groupsTopicList.get(i).getIdGroup(): " OR id_topic = "+groupsTopicList.get(i).getIdGroup();
            }
            chatDetailList = ChatDetail.find(ChatDetail.class, whereClause, null);
        }
    }

    @Override
    public GroupChatAdapter.GroupChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.itemlayout_groupchat, parent, false);

        return new GroupChatViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(GroupChatAdapter.GroupChatViewHolder holder, int position) {
        ChatDetail chat = chatDetailList.get(position);

        holder.mtvChatMessage.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatDetailList.size();
    }

    class GroupChatViewHolder extends RecyclerView.ViewHolder{

        TextView mtvChatMessage;
//        CircleImageView mcivUserPhoto;
        GroupChatAdapter adapter;

        public GroupChatViewHolder(View itemView, GroupChatAdapter adapter) {
            super(itemView);
            mtvChatMessage = (TextView) itemView.findViewById(R.id.tv_chatMessageOne);
//            mcivUserPhoto = (CircleImageView) itemView.findViewById(R.id.civ_userImage);

            this.adapter = adapter;
        }
    }
}
