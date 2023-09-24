package com.example.portalmanagementapp.SubActivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.R;

import java.util.List;

public class GuidanceDataAdapter2 extends RecyclerView.Adapter<GuidanceDataAdapter2.ViewHolder> {

    private Context context;
    private List<GuidanceInformation> list;

    public GuidanceDataAdapter2(Context context, List<GuidanceInformation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.guidancerecord, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GuidanceInformation update = list.get(position);
        holder.Offense.setText(update.getOffense());
        holder.Case.setText(update.getCase());
        holder.Date.setText(update.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Offense, Case, Date;
        CardView Data;
        GuidanceInformation guidance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Offense = itemView.findViewById(R.id.Offense);
            Case = itemView.findViewById(R.id.Case);
            Date = itemView.findViewById(R.id.Date);
            Data = itemView.findViewById(R.id.guidancerecord);
        }

    }

}


