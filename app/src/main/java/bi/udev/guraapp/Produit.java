package bi.udev.guraapp;

/**
 * Created by KonstrIctor on 03/09/2019.
 */

public class Produit {
    String titre, prix, photo, slug, vendeur, detail;

    public Produit(String titre, String prix, String photo, String slug, String vendeur, String detail) {
        this.titre = titre;
        this.prix = prix;
        this.photo = photo;
        this.slug = slug;
        this.vendeur = vendeur;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "titre='" + titre + '\'' +
                ", prix='" + prix + '\'' +
                ", photo='" + photo + '\'' +
                ", slug='" + slug + '\'' +
                ", vendeur='" + vendeur + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
