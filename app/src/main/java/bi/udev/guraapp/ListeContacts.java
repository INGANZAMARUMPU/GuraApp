package bi.udev.guraapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListeContacts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Contact> contacts;
    private AdaptateurContact adaptateurContact;
    int page;
    private String tel_vendeur, tel_active, slug;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_contacts);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerContacts);
        contacts = new ArrayList<Contact>();

        page = 1;
        tel_active = getIntent().getExtras().getString("tel_active");
        slug = getIntent().getExtras().getString("slug");
        tel_vendeur = getIntent().getExtras().getString("tel_owner");

        sharedPreferences = getSharedPreferences("messagerie", Context.MODE_PRIVATE);
        SharedPreferences.Editor session = sharedPreferences.edit();
        session.putString("slug", slug);
        session.commit();

        chargerContacts(page, tel_active, tel_vendeur, slug);

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adaptateurContact = new AdaptateurContact(this, contacts, tel_active);

        recyclerView.setAdapter(adaptateurContact);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                page++;
                chargerContacts(page, tel_active, tel_vendeur, slug);
            }
        });

    }

    private void chargerContacts(int page, final String tel_active, final String tel_vendeur, String slug) {
        OkHttpClient client = new OkHttpClient();
        String url;

        if (tel_active.equals(tel_vendeur)){
            HttpUrl.Builder urlBuilder = HttpUrl
                    .parse("http://"+ Host.URL+"/usersbyproduct/"+slug+"/"+page)
                    .newBuilder();
            url = urlBuilder.build().toString();
        }else {
            HttpUrl.Builder urlBuilder = HttpUrl
                    .parse("http://"+ Host.URL+"/userbyproduct/"+tel_active+"/"+slug)
                    .newBuilder();
            url = urlBuilder.build().toString();
        }

        Log.i("============", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                final String mMessage = e.getMessage().toString();
                ListeContacts.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListeContacts.this, mMessage, Toast.LENGTH_SHORT).show();
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
                        Contact contact = new Contact(
                                jsonObject.getString("username"),
                                jsonObject.getString("tel"),
                                jsonObject.getString("message"),
                                jsonObject.getString("lu"),
                                jsonObject.getString("avatar")

                        );
                        contacts.add(contact);
                        ListeContacts.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tel_active.equals(tel_vendeur)){
                                    adaptateurContact.notifyDataSetChanged();
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    final String message = e.getMessage();
                    ListeContacts.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ListeContacts.this, message, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
