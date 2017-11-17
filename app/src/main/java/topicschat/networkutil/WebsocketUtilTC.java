package topicschat.networkutil;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.enkiprobo.topicschat.GroupChatActivity;
import com.example.enkiprobo.topicschat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import topicschat.adapters.GroupChatAdapter;
import topicschat.adapters.GroupTopicAdapter;
import topicschat.helper.TPConstant;
import topicschat.sqlitedatamodel.ChatDetail;
import topicschat.sqlitedatamodel.GroupsTopic;
import topicschat.sqlitedatamodel.Mute;
import topicschat.sqlitedatamodel.UsersGroup;

/**
 * Created by enkiprobo on 11/17/2017.
 */

public class WebsocketUtilTC {
    public static WebSocket wss;
    public static Context contextN;
    public static Context contextMain;
    public static Context contextHolder;
    private static NotificationManager nm;

    public static void initWebsocket(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(TPConstant.WEBSOCKET_URL).build();
        wss = client.newWebSocket(request, new TopicsChatWebsocketListener());
    }

    public static void initWebsocket(Context context){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(TPConstant.WEBSOCKET_URL).build();
        wss = client.newWebSocket(request, new TopicsChatWebsocketListener());

        contextMain = context;
        contextHolder = contextMain;
        nm = (NotificationManager) contextHolder.getSystemService(contextHolder.NOTIFICATION_SERVICE);
    }

    public static void setChatLiveContext(Context context){
        contextN = context;
        contextHolder = contextN;
    }
    public static void setContextToMain(){
        contextHolder = contextMain;
    }

    public static void progressMessage(String text){
        try {
            JSONObject resposneJSON = new JSONObject(text);
            String category = resposneJSON.getString("category");
            if (category.equals("chat")){
                JSONObject chatInfoJson = resposneJSON.getJSONObject(category);

                final int idTopic = chatInfoJson.getInt("id_topic");
                final List<GroupsTopic> topic = GroupsTopic.find(GroupsTopic.class, "id_topic = ?", idTopic+"");
                if (topic.size()>0){
                    final GroupsTopic topic1 = topic.get(0);
                    topic1.setCountNotRead(topic1.getCountNotRead()+1);
                    topic1.save();

                    final int idGroupChatDetail = chatInfoJson.getInt("id_gcd");
                    final String message = chatInfoJson.getString("chat_message");
                    boolean pin = chatInfoJson.getBoolean("pin");
                    String createdTime = chatInfoJson.getString("created_time");

                    JSONObject user = chatInfoJson.getJSONObject("user");
                    String userUsername = user.getString("username");
                    String userUserFullname = user.getString("full_name");
                    String userUserBirth = user.getString("birth_date");
                    String userUserImage = user.getString("profile_image");

//                        ChatDetail chat = new ChatDetail(idGCD,message,idTopic,pin,createdTime);
                    final ChatDetail chatDetail = new ChatDetail(idGroupChatDetail,message,idTopic,pin,createdTime,userUsername,userUserImage,userUserFullname,userUserBirth);

//                    final ChatDetail chatDetail = new ChatDetail(idGroupChatDetail,message,idTopic,pin,createdTime);
                    chatDetail.save();

                    if (contextHolder != null){
                        ((Activity)contextHolder).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recyclerView = (RecyclerView) ((Activity) contextHolder).findViewById(R.id.rv_chatList);
                                RecyclerView recyclerView1 = (RecyclerView) ((Activity) contextHolder).findViewById(R.id.rv_topicList);
                                GroupsTopic gt = null;
                                if (contextHolder == contextN) {
                                    gt = ((GroupChatActivity) contextHolder).getGroupTopic();
                                }
                                GroupsTopic topicss = GroupsTopic.find(GroupsTopic.class, "id_topic = ?", idTopic+"").get(0);
                                if (gt != null){
                                    if (recyclerView != null && gt.getIdTopic() == chatDetail.getIdTopic()){
                                        GroupChatAdapter adapter = (GroupChatAdapter) recyclerView.getAdapter();
                                        List<ChatDetail> chatDetails = ChatDetail.getGroupsChatFromTopic(contextHolder, idTopic);
                                        adapter.setChatDetailList(chatDetails);
                                        adapter.notifyDataSetChanged();

                                        recyclerView.scrollToPosition(chatDetails.size()-1);

                                        GroupTopicAdapter groupTopicAdapter = (GroupTopicAdapter) recyclerView1.getAdapter();
                                        List<GroupsTopic> groupsTopicList = GroupsTopic.getGroupTopic(topic1.getIdGroup());
                                        groupTopicAdapter.updateTopicList(groupsTopicList);

                                    }
                                    else {
                                        List<Mute> mute = Mute.find(Mute.class, "id_topic = ?", idTopic+"");
                                        if(mute.size()<1){
                                            String groupName = UsersGroup.find(UsersGroup.class, "id_group = ?", topic.get(0).getIdGroup()+"").get(0).getGroupName();
                                            showNotification(groupName, topicss.getTopicName(), message);
                                        }
                                    }
                                }
                                 else {
                                    List<Mute> mute = Mute.find(Mute.class, "id_topic = ?", idTopic+"");
                                    if(mute.size()<1){
                                        String groupName = UsersGroup.find(UsersGroup.class, "id_group = ?", topic.get(0).getIdGroup()+"").get(0).getGroupName();
                                        showNotification(groupName, topicss.getTopicName(), message);
                                    }
                                }
                            }
                        });
                    }
                }
            } else if (category.equals("topic")){
                JSONObject topicInfoJson = resposneJSON.getJSONObject(category);

                int idTopic = topicInfoJson.getInt("id_topic");
                final int idGroup = topicInfoJson.getInt("id_group");
                final String topicName = topicInfoJson.getString("topic_name");

                GroupsTopic topic = new GroupsTopic(idTopic,topicName,idGroup);
                topic.save();
                Log.d("WEBSOCKETOKHTTP", "berhasil masuk topic");

                ((Activity)contextHolder).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView rv = (RecyclerView) ((Activity) contextHolder).findViewById(R.id.rv_topicList);
                        if (rv != null ){
                            GroupTopicAdapter adapter = (GroupTopicAdapter) rv.getAdapter();
                            List<GroupsTopic> groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", idGroup+"");
                            adapter.updateTopicList(groupsTopicList);
                            adapter.notifyDataSetChanged();

                        } else {

                            String groupName = UsersGroup.find(UsersGroup.class, "id_group = ?", idGroup+"").get(0).getGroupName();
                            String message = "topic '"+topicName+"' has been created";
                            showNotification(groupName, topicName, message);
                        }
                    }
                });
            } else if (category.equals("group")){

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void showNotification(String title, String topic, String message){
        Notification notif = new NotificationCompat.Builder(contextHolder)
                .setContentTitle(title+" - "+topic)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_topic)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();

        nm.notify(0, notif);
    }

    static class TopicsChatWebsocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            Log.d("WEBSOCKETOKHTTP", "berhasil terkoneksi");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Log.d("WEBSOCKETOKHTTP", "pesan masuk: "+text);

            progressMessage(text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            Log.d("WEBSOCKETOKHTTP", "pesan masuk byte: "+bytes.toString());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            Log.d("WEBSOCKETOKHTTP", "mau ditutup");
            if (reason.equals("timeout")){
                initWebsocket();
            } else{
                wss = null;
            }
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            Log.d("WEBSOCKETOKHTTP", "ketutup");
        }
    }

}
