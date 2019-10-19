package bi.udev.guraapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by KonstrIctor on 12/09/2019.
 */

class AdaptateurContact  extends RecyclerView.Adapter<AdaptateurContact.ViewHolder> {

    Context context;
    ArrayList<Contact> contacts;
    String tel;
    SharedPreferences sharedPreferences;

    public AdaptateurContact(Context context, ArrayList<Contact> contacts, String tel) {
        this.context = context;
        this.contacts = contacts;
        this.tel = tel;
    }

    @Override
    public AdaptateurContact.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_contact, parent, false);
        return new AdaptateurContact.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdaptateurContact.ViewHolder holder, final int position) {
        String url_image = Host.URL + contacts.get(position).avatar;
        Log.i("==========", url_image);
        Glide.with(context).load(url_image).into(holder.img_contact_avatar);
        holder.lbl_contact_message.setText(contacts.get(position).message);
        holder.lbl_contact_usrname.setText(contacts.get(position).username);
        if (contacts.get(position).lu.equals("false")){
            holder.lbl_contact_usrname.setTextColor(context.getResources().getColor(R.color.unread));
        }else{
            holder.lbl_contact_usrname.setTextColor(context.getResources().getColor(R.color.read));
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences("messagerie", Context.MODE_PRIVATE);
                SharedPreferences.Editor session = sharedPreferences.edit();
                session.putString("nom_dest", contacts.get(position).username);
                session.putString("tel_dest", contacts.get(position).tel);
                session.commit();

                Intent intent = new Intent(context, MessageActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_contact_avatar;
        TextView lbl_contact_message, lbl_contact_usrname;
        public View view;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.view = itemView;
            img_contact_avatar = (ImageView) itemView.findViewById(R.id.img_contact_avatar);
            lbl_contact_message = (TextView) itemView.findViewById(R.id.lbl_contact_message);
            lbl_contact_usrname = (TextView) itemView.findViewById(R.id.lbl_contact_usrname);
        }
    }
}