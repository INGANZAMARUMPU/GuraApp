package bi.udev.guraapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by KonstrIctor on 12/09/2019.
 */

class AdaptateurMessage extends RecyclerView.Adapter<AdaptateurMessage.ViewHolder> {

    Context context;
    ArrayList<Message> messages;
    SharedPreferences sharedPreferences;

    public AdaptateurMessage(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
        sharedPreferences = context.getSharedPreferences("user_login", Context.MODE_PRIVATE);
    }

    @Override
    public AdaptateurMessage.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_chat, parent, false);
        return new AdaptateurMessage.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.lbl_chat_message.setText(messages.get(position).message);
        holder.lbl_chat_usrname.setText(messages.get(position).username);

        if (messages.get(position).tel.equals(sharedPreferences.getString("tel", ""))){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                holder.lbl_chat_usrname.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                holder.cardLayout1.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
                holder.cardLayout2.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
            }
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lbl_chat_message, lbl_chat_usrname;
        LinearLayout cardLayout1, cardLayout2;
        public View view;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.view = itemView;
            lbl_chat_message = (TextView) itemView.findViewById(R.id.lbl_chat_message);
            lbl_chat_usrname = (TextView) itemView.findViewById(R.id.lbl_chat_usrname);
            cardLayout1 = (LinearLayout) itemView.findViewById(R.id.cardLayout1);
            cardLayout2 = (LinearLayout) itemView.findViewById(R.id.cardLayout2);
        }
    }
}