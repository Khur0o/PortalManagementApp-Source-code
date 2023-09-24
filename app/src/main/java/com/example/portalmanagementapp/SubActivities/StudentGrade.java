package com.example.portalmanagementapp.SubActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.UserhelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentGrade extends AppCompatActivity {

    //Side Menu
    private String User_Name;

    //Layout
    private LinearLayout RecitationLayout, ActivityLayout, AssignLayout, QuizLayout, LongQuizLayout, PrelimLayout, PeriodicLayout;
    private Button Recitation, Activity, Assign, Quiz, LongQuiz, Prelim, Periodic, Save, Quarter01, Quarter02, Quarter03, Quarter04;

    //Database path
    private DatabaseReference RecitationGrade, AssignGrade1, AssignGrade2, QuizGrade1, QuizGrade2, LongQuizGrade1, LongQuizGrade2, PrelimGrade1, PrelimGrade2, PeriodicGrade1, PeriodicGrade2;

    //student name and lrn
    private TextView NameStudent, LRNStudent;

    //OverAll score
    private int Recite, TotalAssign, OverallAssign, TotalQuiz, OverallQuiz, TotalLongQuiz, OverallLongQuiz,TotalPrelim, OverallPrelim, TotalPeriodic, OverallPeriodic, getpercentRecite, getpercentAssign, getpercentQuiz, getpercentLongQuiz, getpercentPrelim, getpercentPeriodic;

    //number list on database save
    private int numAssign, numQuiz, numLongQuiz;

    //number list available
    private int a = 0, b = 0, c = 0, d = 0, e = 0;

    //String data from previous activity
    private String studentID, EnglishSub, MathSub, ScienceSub, FilipinoSub, ValuesSub, HistorySub, MAPEHSub;

    //Log History path
    private DatabaseReference LogHistory = FirebaseDatabase.getInstance().getReference("LogHistory");
    //String time and date
    private String Time, currentDateTime;

    //User data
    private FirebaseUser user;
    private String userID;
    private DatabaseReference Admin, studentdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_grade);

        //Layout
        ActivityLayout = findViewById(R.id.activitylayout);
        RecitationLayout = findViewById(R.id.recitelayout);
        AssignLayout = findViewById(R.id.asslayout);
        QuizLayout = findViewById(R.id.quizlayout);
        LongQuizLayout = findViewById(R.id.longquizlayout);
        PrelimLayout = findViewById(R.id.prelimlayout);
        PeriodicLayout = findViewById(R.id.periodiclayout);

        //Add button
        Activity = findViewById(R.id.addactivity);
        Assign = findViewById(R.id.addass);
        Quiz = findViewById(R.id.addquiz);
        LongQuiz = findViewById(R.id.addlongquiz);

        //Save data
        Save = findViewById(R.id.save);

        //Quarter button
        Quarter01 = findViewById(R.id.first);
        Quarter02 = findViewById(R.id.second);
        Quarter03 = findViewById(R.id.third);
        Quarter04 = findViewById(R.id.fourth);

        //Student Data
        NameStudent = findViewById(R.id.name);
        LRNStudent = findViewById(R.id.lrn);

        //Data from previous activity
        Intent intent = getIntent();
        studentID = intent.getStringExtra("studentID");
        EnglishSub = intent.getStringExtra("EnglishSub");

        //Get current time and date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        //Get data on database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess").child("AdministratorList");
        studentdata = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList");

        //Get current user UID
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        GetDataOnAdminandStudent();
        QuarterList();
    }

    //Get data on admin and student
    private void GetDataOnAdminandStudent(){
        Admin.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if(userProfile != null){
                    User_Name = userProfile.Name;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        studentdata.child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if(userProfile != null){

                    String Name, LRN;
                    Name = userProfile.Name;
                    LRN = userProfile.StudentNumber;

                    NameStudent.setText(Name);
                    LRNStudent.setText(LRN);

                    Toast.makeText(StudentGrade.this, "Name : " + Name, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Quarter list button
    private void QuarterList(){

        Quarter01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Quarter02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Quarter03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Quarter04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
