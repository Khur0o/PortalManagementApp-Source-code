package com.example.portalmanagementapp.SubActivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.NotifUpdate;
import com.example.portalmanagementapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecordDataAdapter extends RecyclerView .Adapter<RecordDataAdapter.ViewHolder>{

    Context context;
    ArrayList<NotifUpdate> list;

    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static String userID = user.getUid();

    public RecordDataAdapter(Context context, ArrayList<NotifUpdate> list){
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public RecordDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notif,parent,false);
        return new ViewHolder(v, list);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordDataAdapter.ViewHolder holder, int position) {

        NotifUpdate update = list.get(list.size() - 1 - position);
        holder.Subject.setText(update.getSubject());
        holder.Compose.setText(update.getCompose());
        holder.Time.setText(update.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView Subject, Compose, Time;
        CardView Notif;
        public ViewHolder(@NonNull View itemView, ArrayList<NotifUpdate> list) {
            super(itemView);

            Subject = itemView.findViewById(R.id.Subject);
            Compose = itemView.findViewById(R.id.ComposeLetter);
            Time = itemView.findViewById(R.id.Date);
            Notif = itemView.findViewById(R.id.notif);
            Notif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.notifdelete, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int position = getAdapterPosition();
                            String Time = list.get(position).getTime();


                            switch (menuItem.getItemId()) {
                                case R.id.menu_delete:
                                    // Handle delete option

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LogHistory");
                                    databaseReference.removeValue();


                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                    //return true;
                }
            });

        }
    }
}
