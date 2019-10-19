package bi.udev.guraapp;

/**
 * Created by KonstrIctor on 03/09/2019.
 */

public class Utilisateur {
    String id, nom, prenom, username, email, tel, addresse, ville, avatar;

    public Utilisateur(String id, String nom, String prenom, String username, String email, String tel, String addresse, String ville, String avatar) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.email = email;
        this.tel = tel;
        this.addresse = addresse;
        this.ville = ville;
        this.avatar = avatar;
    }

}
