package com.example.portalmanagementapp.SubActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.R;

import java.util.List;
import java.util.stream.Collectors;

public class DataAdapter4 extends RecyclerView.Adapter<DataAdapter4.ViewHolder> {

    private Context context;
    private List<StudentInformation> originalList;
    private List<StudentInformation> filteredList;

    public DataAdapter4(Context context, List<StudentInformation> list) {
        this.context = context;
        this.originalList = list;
        this.filteredList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.data, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentInformation update = filteredList.get(position);
        holder.Name.setText(update.getFullname());
        holder.StudentNumber.setText(update.getStudentNumber());
        holder.Age.setText(update.getAge());

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, StudentNumber, Age, Gender;
        CardView Data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.StudentName);
            StudentNumber = itemView.findViewById(R.id.StudentNumber);
            Age = itemView.findViewById(R.id.StudentAge);
            Gender = itemView.findViewById(R.id.StudentGender);
            Data = itemView.findViewById(R.id.data);

            Data.setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.getMenuInflater().inflate(R.menu.guidance, popup.getMenu());
                popup.setOnMenuItemClickListener(menuItem -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && filteredList != null && position < filteredList.size()) {
                        String studentID = filteredList.get(position).getUserID();
                        String studentNumber = filteredList.get(position).getStudentNumber();
                        switch (menuItem.getItemId()) {
                            case R.id.menu_view:
                                // Handle view option
                                Intent intent = new Intent(context, GuidanceRecord2.class);
                                intent.putExtra("IDNumber", studentID);
                                intent.putExtra("StudentNumber", studentNumber);
                                context.startActivity(intent);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            filteredList = originalList.stream()
                    .filter(item -> item.getFullname().toLowerCase().contains(query.toLowerCase())
                            || item.getStudentNumber().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }
}
