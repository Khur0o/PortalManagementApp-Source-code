package com.example.portalmanagementapp.SubActivities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.R;

import java.util.List;

public class DataAdapter3 extends RecyclerView.Adapter<DataAdapter3.ViewHolder> {

    private List<UserhelperClassAddSection > sectionKeys;

    public DataAdapter3(List<UserhelperClassAddSection> sectionKeys) {
        this.sectionKeys = sectionKeys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String sectionKey = String.valueOf(sectionKeys.get(position));
        holder.Section.setText(sectionKey);
    }

    @Override
    public int getItemCount() {
        return sectionKeys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Section;
        CardView Data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Section = itemView.findViewById(R.id.section);
            Data = itemView.findViewById(R.id.data);
        }
    }
}

