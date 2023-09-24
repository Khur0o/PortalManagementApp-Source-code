package com.example.portalmanagementapp.SubActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GuidanceDataAdapter extends RecyclerView.Adapter<GuidanceDataAdapter.ViewHolder> {

    private Context context;
    private List<GuidanceInformation> list;

    public GuidanceDataAdapter(Context context, List<GuidanceInformation> list) {
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

            Data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.notifdelete, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int position = getAdapterPosition();
                            guidance = list.get(position);
                            String guidanceKey = guidance.getOffense();
                            String LRN = guidance.getLRN();

                            switch (menuItem.getItemId()) {
                                case R.id.menu_delete:
                                    // Handle delete option

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("DELETING DATA");
                                    builder.setMessage("Do you want to delete this record?");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(LRN).child(guidanceKey);
                                            databaseReference.removeValue();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", null);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                    break;

                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });
        }

    }

}


