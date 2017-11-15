package topicschat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enkiprobo.topicschat.GroupChatActivity;
import com.example.enkiprobo.topicschat.R;

import java.util.List;

import topicschat.sqlitedatamodel.UsersGroup;

/**
 * Created by enkiprobo on 11/15/2017.
 */

public class UserGroupAdapter extends RecyclerView.Adapter<UserGroupAdapter.UserGroupViewHolder> {

    public static final String EXTRA_IDGROUP = "com.example.enkiprobo.topicschat.idgroup";
    public static final String EXTRA_IDGM = "com.example.enkiprobo.topicschat.idgm";
    public static final String EXTRA_GROUPNAME = "com.example.enkiprobo.topicschat.groupname";
    public static final String EXTRA_GROUPPHOTO = "com.example.enkiprobo.topicschat.groupphoto";

    private LayoutInflater layoutInflater;
    private Context context;

    List<UsersGroup> usersGroupList;

    public UserGroupAdapter(Context context, List<UsersGroup> usersGroupList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.usersGroupList = usersGroupList;
    }

    public void setUsersGroupList(List<UsersGroup> usersGroupList){
        this.usersGroupList = usersGroupList;
    }

    @Override
    public UserGroupAdapter.UserGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.itemlayout_usersgroup, parent, false);

        return new UserGroupViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(UserGroupAdapter.UserGroupViewHolder holder, int position) {
        UsersGroup group = usersGroupList.get(position);

        String notReadCount = group.getCountNotReadAll()>0 ? group.getCountNotReadAll()+"" : "";

        holder.mtvNotReadCount.setText(notReadCount);
        holder.mtvGroupName.setText(group.getGroupName());
        holder.mtvPreviewMessage.setText(group.getRecentChat());
        holder.mtvLastTime.setText(group.getRecenChatDate());


    }

    @Override
    public int getItemCount() {
        return (int) UsersGroup.count(UsersGroup.class);
    }

    class UserGroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mtvGroupName;
        TextView mtvPreviewMessage;
        TextView mtvLastTime;
        TextView mtvNotReadCount;

        final UserGroupAdapter adapter;

        public UserGroupViewHolder(View itemView, UserGroupAdapter adapter) {
            super(itemView);

            mtvGroupName =(TextView) itemView.findViewById(R.id.tv_groupName);
            mtvPreviewMessage = (TextView) itemView.findViewById(R.id.tv_previewMessage);
            mtvLastTime =(TextView) itemView.findViewById(R.id.tv_lastTime);
            mtvNotReadCount = (TextView) itemView.findViewById(R.id.tv_notReadCount);

            this.adapter = adapter;

            mtvGroupName.setOnClickListener(this);
            mtvPreviewMessage.setOnClickListener(this);
            mtvLastTime.setOnClickListener(this);
            mtvNotReadCount.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();

            UsersGroup group = usersGroupList.get(position);

            Intent inten = new Intent(context, GroupChatActivity.class);
            inten.putExtra(EXTRA_IDGROUP, group.getIdGroup());
            inten.putExtra(EXTRA_IDGM, group.getIdGM());
            inten.putExtra(EXTRA_GROUPNAME, group.getGroupName());
            inten.putExtra(EXTRA_GROUPPHOTO, group.getGroupPhoto());

            context.startActivity(inten);
        }
    }
}
