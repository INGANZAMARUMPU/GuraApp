package bi.udev.guraapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsProduits extends AppCompatActivity {

    TextView lbl_details_titre, lbl_details_prix, lbl_details_details;
    String titre, prix, photo, slug, vendeur, detail;
    ImageView image_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_produits);

        titre = getIntent().getStringExtra("titre");
        prix = getIntent().getStringExtra("prix");
        photo = getIntent().getStringExtra("photo");
        slug = getIntent().getStringExtra("slug");
        vendeur = getIntent().getStringExtra("vendeur");
        detail = getIntent().getStringExtra("detail");

        lbl_details_details = (TextView) findViewById(R.id.lbl_details_details);
        lbl_details_prix = (TextView) findViewById(R.id.lbl_details_prix);
        lbl_details_titre = (TextView) findViewById(R.id.lbl_details_titre);

        Log.i("=======", Host.URL+photo);
//        Glide.with(image_details.getContext()).load(Host.URL+photo).into(image_details);
        lbl_details_details.setText(detail);
        lbl_details_titre.setText(titre);
        lbl_details_prix.setText(prix);


    }
}
