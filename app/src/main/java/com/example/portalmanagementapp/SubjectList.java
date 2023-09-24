package com.example.portalmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.SubActivities.StudentGradeActivity;

public class SubjectList extends AppCompatActivity {

    FrameLayout English, Math, Science, Filipino, Values, History, MAPEH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        English = findViewById(R.id.english);
        Math = findViewById(R.id.math);
        Science = findViewById(R.id.science);
        Filipino = findViewById(R.id.filipino);
        Values = findViewById(R.id.values);
        History = findViewById(R.id.history);
        MAPEH = findViewById(R.id.mapeh);

        Intent intent = getIntent();
        String studentID = intent.getStringExtra("studentID");
        String EnglishSub = "English";
        String MathSub = "Math";
        String ScienceSub = "Science";
        String FilipinoSub = "Filipino";
        String ValuesSub = "Values";
        String HistorySub = "History";
        String MAPEHSub = "MAPEH";

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //StudentGradeActivity.FirstQuarter = true;
                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("Subject", EnglishSub);
                startActivity(intent);
            }
        });
        Math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //StudentGradeActivity.FirstQuarter = true;
                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("Subject", MathSub);
                startActivity(intent);
            }
        });
        Science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //StudentGradeActivity.FirstQuarter = true;
                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("Subject", ScienceSub);
                startActivity(intent);
            }
        });
        Filipino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //StudentGradeActivity.FirstQuarter = true;
                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("Subject", FilipinoSub);
                startActivity(intent);
            }
        });
        Values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //StudentGradeActivity.FirstQuarter = true;
                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("Subject", ValuesSub);
                startActivity(intent);
            }
        });
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //StudentGradeActivity.FirstQuarter = true;
                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("Subject", HistorySub);
                startActivity(intent);
            }
        });
        MAPEH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //StudentGradeActivity.FirstQuarter = true;
                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("Subject", MAPEHSub);
                startActivity(intent);
            }
        });
    }
}