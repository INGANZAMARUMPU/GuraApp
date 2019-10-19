package bi.udev.guraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connexion(View view){
        Intent intent = new Intent(MainActivity.this, ConnexionActivity.class);
        startActivity(intent);
    }
    public void inscription(View view){
        Intent intent = new Intent(MainActivity.this, Inscription.class);
        startActivity(intent);
    }
}
