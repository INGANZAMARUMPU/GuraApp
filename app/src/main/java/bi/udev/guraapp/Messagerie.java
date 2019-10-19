package bi.udev.guraapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Messagerie extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Produit> produits;
    private ElementMessagerie adaptateurMessagerie;
    int page;
    SharedPreferences sharedPreferences;
    private String tel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_messagerie, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerMessagerie);
        produits = new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);

        if(!(sharedPreferences.getBoolean("is_connected", false))){
            Intent intent = new Intent(getActivity(), ConnexionActivity.class);
            startActivity(intent);
        }

        page = 1;
        tel = sharedPreferences.getString("tel", "");
        chargerMessagerie(page, Integer.parseInt(tel));

        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adaptateurMessagerie = new ElementMessagerie(getActivity(), produits, tel);

        recyclerView.setAdapter(adaptateurMessagerie);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                page++;
                chargerMessagerie(page, Integer.parseInt(tel));
            }
        });

        return rootView;
    }

    private void chargerMessagerie(int page, int tel) {
        int no_page = page;
        int  telephone = tel;
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://"+ Host.URL+"/messagerie/"+telephone+"/"+no_page).newBuilder();
        String url = urlBuilder.build().toString();

        Log.i("============", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                final String mMessage = e.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
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
                        Produit produit = new Produit(
                                jsonObject.getString("titre"),
                                jsonObject.getString("prix"),
                                jsonObject.getString("photo"),
                                jsonObject.getString("slug"),
                                jsonObject.getString("vendeur"),
                                jsonObject.getString("details")

                        );
                        Log.i("========", produit.toString());
                        produits.add(produit);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adaptateurMessagerie.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (Exception e) {
                    final String message = e.getMessage();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
