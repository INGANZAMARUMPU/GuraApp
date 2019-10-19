package bi.udev.guraapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class Profil extends Fragment {

    private String nom, prenom, email, tel, addresse, ville, avatar, username;
    private EditText champ_nom, champ_prenom, champ_username, champ_email,
            champ_password, champ_tel, champ_addresse, champ_ville;
    private ImageButton img_avatar;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);

        sharedPreferences = getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);

        if(!(sharedPreferences.getBoolean("is_connected", false))){
            Intent intent = new Intent(getActivity(), ConnexionActivity.class);
            startActivity(intent);
        }

        nom = sharedPreferences.getString("nom", "");
        prenom = sharedPreferences.getString("prenom", "");
        username = sharedPreferences.getString("username", "");
        email = sharedPreferences.getString("email", "");
        tel = sharedPreferences.getString("tel", "");
        addresse = sharedPreferences.getString("addresse", "");
        ville = sharedPreferences.getString("ville", "");
        avatar = sharedPreferences.getString("avatar", "");

        img_avatar = (ImageButton) rootView.findViewById(R.id.btn_profil_avatar);

        champ_nom = (EditText) rootView.findViewById(R.id.txt_profil_nom);
        champ_prenom = (EditText) rootView.findViewById(R.id.txt_profil_prenom);
        champ_username = (EditText) rootView.findViewById(R.id.txt_profil_username);
        champ_email = (EditText) rootView.findViewById(R.id.txt_profil_email);
        champ_tel = (EditText) rootView.findViewById(R.id.txt_profil_tel);
        champ_addresse = (EditText) rootView.findViewById(R.id.txt_profil_addr);
        champ_ville = (EditText) rootView.findViewById(R.id.txt_profil_ville);
        champ_password = (EditText) rootView.findViewById(R.id.txt_profil_passwd);

        champ_nom.setText(nom);
        champ_prenom.setText(prenom);
        champ_username.setText(username);
        champ_email.setText(email);
        champ_tel.setText(tel);
        champ_addresse.setText(addresse);
        champ_ville.setText(ville);
        champ_password.setText("mot de passe");

        return rootView;
    }
}
