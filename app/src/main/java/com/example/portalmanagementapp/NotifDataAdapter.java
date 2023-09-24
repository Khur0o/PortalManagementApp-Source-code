package com.example.portalmanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifDataAdapter extends RecyclerView .Adapter<NotifDataAdapter.ViewHolder>{

    Context context;
    ArrayList<NotifUpdate> list;


    public NotifDataAdapter(Context context, ArrayList<NotifUpdate> list){
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public NotifDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notif,parent,false);
        return new ViewHolder(v, list);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifDataAdapter.ViewHolder holder, int position) {

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


        }
    }
}
