package topicschat.networkutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.enkiprobo.topicschat.R;
import com.example.enkiprobo.topicschat.RegistrationSuccessActivity;
import com.example.enkiprobo.topicschat.UserMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class NetworkUtilTC {

    private final String URL_BASE = "https://topicschatapi.herokuapp.com/";
    public static final String PREFERENCED_NAME = "com.example.enkiprobo.topicschat";

    private final String PARAM_USERNAME = "username";
    private final String PARAM_PASSWORD = "password";
    private final String PARAM_FULLNAME = "fullname";
    private final String PARAM_PROFILEIMAGE = "profileimage";
    private final String PARAM_BIRTHDATE = "birthdate";
    private final String PARAM_GROUPNAME = "groupname";
    private final String PARAM_GROUPIMAGE = "groupimage";
    private final String PARAM_IDTOPIC = "idtopic";
    private final String PARAM_IDGM = "idgm";
    private final String PARAM_MESSAGE = "message";
    private final String PARAM_IDGCD = "idgcd";
    private final String PARAM_PIN = "pin";
    private final String PARAM_IDGROUP = "idgroup";
    private final String PARAM_TOPICNAME = "topicname";

    private OkHttpClient client = new OkHttpClient();

    public void createUser(final Context context, String username, String password, String fullname, String profileimage, String birthdate) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_USERNAME, username)
                .addFormDataPart(PARAM_PASSWORD, password)
                .addFormDataPart(PARAM_FULLNAME, fullname)
                .addFormDataPart(PARAM_PROFILEIMAGE, profileimage)
                .addFormDataPart(PARAM_BIRTHDATE, birthdate)
                .build();

        Request request = new Request.Builder()
                .url(URL_BASE + "createuser")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessage);
                        tv.setVisibility(View.VISIBLE);
                        tv.setText("please try again later");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                String lompatan = "";
                try {
                    JSONObject hasil = new JSONObject(responseData);
                    lompatan = hasil.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String status = lompatan;

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessage);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_register);
                        if (!status.equals("OK")) {
                            tv.setText(status);
                            tv.setVisibility(View.VISIBLE);

                            bt.setBackgroundColor(context.getResources().getColor(R.color.mainPurple));
                            bt.setEnabled(true);
                        } else {
                            Intent inten = new Intent(context, RegistrationSuccessActivity.class);

                            context.startActivity(inten);
                            ((Activity) context).finish();
                        }
                    }
                });
            }
        });
    }

    public void Login(final Context context, String username, String password){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_USERNAME, username)
                .addFormDataPart(PARAM_PASSWORD, password)
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "login")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorLogin);
                        tv.setVisibility(View.VISIBLE);
                        tv.setText("please try again later");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString = response.body().string();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorLogin);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_login);
                        try {
                            JSONObject responseJson = new JSONObject(responseString);
                            String status = responseJson.getString("status");
                            if (!status.equals("OK")){
                                tv.setVisibility(View.VISIBLE);
                                tv.setText(status);
                                bt.setEnabled(true);
                                bt.setBackgroundColor(context.getResources().getColor(R.color.mainPurple));
                                Log.d("MENCOBALOGIN", "gak ok");
                            }else{
                                Log.d("MENCOBALOGIN", "berhasil mendapatkan status ok");
                                JSONObject userJson = responseJson.getJSONObject("user");
                                String username = userJson.getString("username");
                                String fullname = userJson.getString("full_name");
                                String profileImage = userJson.getString("profile_image");
                                String birthDate = userJson.getString("birth_date");

                                SharedPreferences mPreference = context.getSharedPreferences(PREFERENCED_NAME, context.MODE_PRIVATE);
                                SharedPreferences.Editor pEditor = mPreference.edit();
                                pEditor.putString("username", username);
                                pEditor.putString("fullname", fullname);
                                pEditor.putString("profileimage", profileImage);
                                pEditor.putString("birthdate", birthDate);
                                pEditor.apply();

                                Intent inten = new Intent(context, UserMainActivity.class);
                                context.startActivity(inten);
                                ((Activity) context).finish();
                            }
                        } catch (JSONException e) {
                            tv.setVisibility(View.VISIBLE);
                            tv.setText("username or password not exist");
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

}
