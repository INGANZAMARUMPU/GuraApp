package bi.udev.guraapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {

    String tel_source, nom_source, nom_dest, tel_dest, slug, message;
    int page=1;
    TextView txt_message_message;
    SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AdaptateurMessage adaptateurMessage;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerMessage);
        messages = new ArrayList<Message>();

        sharedPreferences = getSharedPreferences("user_login", Context.MODE_PRIVATE);
        if(!(sharedPreferences.getBoolean("is_connected", false))){
            Intent intent = new Intent(this, ConnexionActivity.class);
            startActivity(intent);
        }else{
            tel_source = sharedPreferences.getString("tel", "");
            nom_source = sharedPreferences.getString("username", "");
        }
        sharedPreferences = getSharedPreferences("messagerie", Context.MODE_PRIVATE);
        nom_dest = sharedPreferences.getString("nom_dest", "");
        tel_dest = sharedPreferences.getString("tel_dest", "");
        slug = sharedPreferences.getString("slug", "");

        txt_message_message = (TextView) findViewById(R.id.txt_message_message);

        chargerMessages(page, tel_source, tel_dest, slug);

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adaptateurMessage = new AdaptateurMessage(this, messages);

        recyclerView.setAdapter(adaptateurMessage);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                page++;
                chargerMessages(page, tel_source, tel_dest, slug);
            }
        });
    }

    private void chargerMessages(int page, String tel_source, String tel_dest, String slug) {
        OkHttpClient client = new OkHttpClient();
        String url;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://"+ Host.URL+"/chat/"+tel_source+"/"+tel_dest+"/"+slug+"/"+page).newBuilder();
        url = urlBuilder.build().toString();

        Log.i("============", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                final String mMessage = e.getMessage().toString();
                MessageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MessageActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for( int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Message message = new Message(
                                jsonObject.getString("tel"),
                                jsonObject.getString("username"),
                                jsonObject.getString("message")
                        );
                        messages.add(message);
                        MessageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    adaptateurMessage.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (Exception e) {
                    final String message = e.getMessage();
                    MessageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MessageActivity.this, message, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    public void envoyerMessage(View view){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://"+ Host.URL+"/sendmessage").newBuilder();
        urlBuilder.addQueryParameter("source", tel_source);
        urlBuilder.addQueryParameter("destination", tel_dest);
        urlBuilder.addQueryParameter("slug",slug);
        urlBuilder.addQueryParameter("message", txt_message_message.getText().toString());
        String url = urlBuilder.build().toString();
        Log.i("=============", url);
        txt_message_message.setText("");
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                final String mMessage = e.getMessage().toString();
                MessageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MessageActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    MessageActivity.this.finish();
                } catch (Exception e) {
                    final String message = e.getMessage();
                    MessageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        Toast.makeText(MessageActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
