package bi.udev.guraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class FenetrePrincipale extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView label_current_user, label_current_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenetre_principale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_principale, new ToutProduit())
                .commit();
    }

    public void ajouterProduit(View view){
        Intent intent = new Intent(FenetrePrincipale.this, AjouterProduits.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_principale, new ToutProduit())
                    .commit();
        } else if (id == R.id.nav_profil) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_principale, new Profil())
                    .commit();
        } else if (id == R.id.nav_produits) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_principale, new MesProduit())
                    .commit();
        } else if (id == R.id.nav_message) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_principale, new Messagerie())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
