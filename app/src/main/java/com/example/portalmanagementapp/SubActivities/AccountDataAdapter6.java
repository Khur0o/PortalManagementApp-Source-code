package com.example.portalmanagementapp.SubActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
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
import java.util.stream.Collectors;

public class AccountDataAdapter6 extends RecyclerView.Adapter<AccountDataAdapter6.ViewHolder> {

    private Context context;
    private List<AccountDataInformation> originalList;
    private List<AccountDataInformation> filteredList;

    public AccountDataAdapter6(Context context, List<AccountDataInformation> list) {
        this.context = context;
        this.originalList = list;
        this.filteredList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.dataaccount, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountDataInformation update = filteredList.get(position);
        holder.Name.setText(update.getName());
        holder.Age.setText(update.getAge());
        holder.Status.setText(update.getStatus());
        holder.UserID.setText(update.getUserID());

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Status, Age, UserID;
        CardView Data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            Status = itemView.findViewById(R.id.status);
            Age = itemView.findViewById(R.id.Age);
            UserID = itemView.findViewById(R.id.accID);
            Data = itemView.findViewById(R.id.dataaccount);

            Data.setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(menuItem -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && filteredList != null && position < filteredList.size()) {
                        String studentID = filteredList.get(position).getUserID();
                        switch (menuItem.getItemId()) {
                            case R.id.menu_view:
                                // Handle view option
                                if (position != RecyclerView.NO_POSITION && filteredList != null && position < filteredList.size()) {
                                    Intent intent = new Intent(context, StudentInformationActivity.class);
                                    intent.putExtra("Number", studentID);
                                    context.startActivity(intent);
                                }
                                break;
                            case R.id.menu_grades:
                                // Handle grades option
                                if (position != RecyclerView.NO_POSITION && filteredList != null && position < filteredList.size()) {
                                    Intent intent = new Intent(context, Grades.class);
                                    intent.putExtra("studentID", studentID);
                                    context.startActivity(intent);
                                }
                                break;

                            case R.id.menu_grades1:
                                // Handle grades option
                                if (position != RecyclerView.NO_POSITION && filteredList != null && position < filteredList.size()) {
                                    Intent intent = new Intent(context, SubjectListProf.class);
                                    intent.putExtra("studentID", studentID);
                                    context.startActivity(intent);
                                }
                                break;
                            case R.id.menu_delete:

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("DELETING DATA");
                                builder.setMessage("Do you want to delete this data?");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AdminAccess/StudentList/" + studentID);
                                        databaseReference.removeValue();
                                    }
                                });
                                builder.setNegativeButton("Cancel", null);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                                break;
                        }
                    }
                    return false;
                });
                popup.show();
            });
        }
    }

    public void filterList(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredList = originalList;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                filteredList = originalList.stream()
                        .filter(item -> item.getName().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        notifyDataSetChanged();
    }



}
