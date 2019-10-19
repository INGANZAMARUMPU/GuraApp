package bi.udev.guraapp;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by KonstrIctor on 04/09/2019.
 */

class ElementMessagerie extends RecyclerView.Adapter<ElementMessagerie.ViewHolder>{

    Context context;
    ArrayList<Produit> produits;
    String tel;

    public ElementMessagerie(Context context, ArrayList<Produit> produits, String tel) {
        this.context = context;
        this.produits = produits;
        this.tel = tel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_messagerie, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String url_image = Host.URL+produits.get(position).photo;
        Log.i("==========", url_image);
        Glide.with(context).load(url_image).into(holder.img_card_produit);
        holder.label_mess_detail_prod.setText(produits.get(position).detail);
        holder.label_mess_prod_titre.setText(produits.get(position).titre);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListeContacts.class);
                intent.putExtra("tel_active", tel);
                intent.putExtra("slug", produits.get(position).slug);
                intent.putExtra("tel_owner", produits.get(position).vendeur);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_card_produit;
        TextView label_mess_detail_prod, label_mess_prod_titre;
        public View view;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.view = itemView;
            img_card_produit = (ImageView) itemView.findViewById(R.id.img_produit_messagerie);
            label_mess_detail_prod = (TextView) itemView.findViewById(R.id.label_mess_detail_prod);
            label_mess_prod_titre = (TextView) itemView.findViewById(R.id.label_mess_prod_titre);
        }
    }
}
