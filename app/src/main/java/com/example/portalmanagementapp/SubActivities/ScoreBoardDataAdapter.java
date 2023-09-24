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

public class ScoreBoardDataAdapter extends RecyclerView.Adapter<ScoreBoardDataAdapter.ViewHolder> {

    private Context context;
    private List<ScoreBoardInformation> list;

    public ScoreBoardDataAdapter(Context context, List<ScoreBoardInformation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.scoreboard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScoreBoardInformation update = list.get(position);
        holder.Qualitative.setText(update.getQualitative());
        holder.Name.setText(update.getName());
        holder.Grade.setText(update.getGrade());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Grade, Qualitative;
        CardView Data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Qualitative = itemView.findViewById(R.id.Qualitative);
            Name = itemView.findViewById(R.id.Name);
            Grade = itemView.findViewById(R.id.Grade);
            Data = itemView.findViewById(R.id.scoreboard);

        }

    }

}


