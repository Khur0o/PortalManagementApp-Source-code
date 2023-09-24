package com.example.portalmanagementapp.SubActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.UserhelperClass;
import com.example.portalmanagementapp.ViewGrades;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubjectListProf extends AppCompatActivity {

    private FrameLayout SubjectEnglish, SubjectMath, SubjectScience, SubjectFilipino, SubjectValues, SubjectHistory, SubjectMAPEH;
    private String studentID;

    private DatabaseReference Studentdata, English1, English2, English3, English4, Math1, Math2, Math3, Math4, Science1, Science2, Science3, Science4, Filipino1, Filipino2, Filipino3, Filipino4, Values1, Values2, Values3, Values4, History1, History2, History3, History4, MAPEH1, MAPEH2, MAPEH3, MAPEH4;
    private Object ValueEnglish1, ValueEnglish2, ValueEnglish3, ValueEnglish4, ValueMath1, ValueMath2, ValueMath3, ValueMath4, ValueScience1, ValueScience2, ValueScience3, ValueScience4, ValueFilipino1, ValueFilipino2, ValueFilipino3, ValueFilipino4, ValueValues1, ValueValues2, ValueValues3, ValueValues4, ValueHistory1, ValueHistory2, ValueHistory3, ValueHistory4, ValueMAPEH1, ValueMAPEH2, ValueMAPEH3, ValueMAPEH4;

    private TextView StudentName, StudentEmail, English, Math, Science, Filipino, Values, History, MAPEH;

    private FirebaseUser studentuser;
    private String userID;

    String Sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list_prof);

        //Student data
        StudentName = findViewById(R.id.studname);
        StudentEmail = findViewById(R.id.studEmail);

        //Textview
        English = findViewById(R.id.englishgrade);
        Math = findViewById(R.id.mathgrade);
        Science = findViewById(R.id.sciencegrade);
        Filipino = findViewById(R.id.filipinograde);
        Values = findViewById(R.id.valuesgrade);
        History = findViewById(R.id.historygrade);
        MAPEH = findViewById(R.id.mapehgrade);

        //Framelayout
        SubjectEnglish = findViewById(R.id.english);
        SubjectMath = findViewById(R.id.math);
        SubjectScience = findViewById(R.id.science);
        SubjectFilipino = findViewById(R.id.filipino);
        SubjectValues = findViewById(R.id.values);
        SubjectHistory = findViewById(R.id.history);
        SubjectMAPEH = findViewById(R.id.mapeh);

        studentuser = FirebaseAuth.getInstance().getCurrentUser();
        userID = studentuser.getUid();

        //Data from previous activity
        Intent intent = getIntent();
        studentID = intent.getStringExtra("studentID");

        CheckUser();
        ClickSubject();
    }

    private void CheckUser() {

        Studentdata = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList");
        if (studentID != null && !studentID.isEmpty()) {
            Studentdata.child(studentID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                    if(userProfile != null){
                        String User_Name, User_Email;
                        User_Name = userProfile.Name;
                        User_Email = userProfile.Email;

                        StudentName.setText(User_Name);
                        StudentEmail.setText(User_Email);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //English
            English1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("FinalGrade");
            English2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("FinalGrade");
            English3 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("FinalGrade");
            English4 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("FinalGrade");

            //Math
            Math1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("FinalGrade");
            Math2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("FinalGrade");
            Math3 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("FinalGrade");
            Math4 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("FinalGrade");

            //Science
            Science1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("FinalGrade");
            Science2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("FinalGrade");
            Science3 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("FinalGrade");
            Science4 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("FinalGrade");

            //Filipino
            Filipino1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("FinalGrade");
            Filipino2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("FinalGrade");
            Filipino3 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("FinalGrade");
            Filipino4 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("FinalGrade");

            //Values
            Values1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("FinalGrade");
            Values2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("FinalGrade");
            Values3 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("FinalGrade");
            Values4 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("FinalGrade");

            //Values
            History1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("FinalGrade");
            History2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("FinalGrade");
            History3 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("FinalGrade");
            History4 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("FinalGrade");

            //MAPEH
            MAPEH1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("FinalGrade");
            MAPEH2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("FinalGrade");
            MAPEH3 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("FinalGrade");
            MAPEH4 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("FinalGrade");

            if (English1 != null || English2 != null || English3 != null || English4 != null){
                //Toast.makeText(this, "Noice 1", Toast.LENGTH_SHORT).show();
                getEnglish();
            }
            if (Math1 != null || Math2 != null || Math3 != null || Math4 != null){
                //Toast.makeText(this, "Noice 1", Toast.LENGTH_SHORT).show();
                getMath();
            }
            if (Science1 != null || Science2 != null || Science3 != null || Science4 != null){
                //Toast.makeText(this, "Noice 1", Toast.LENGTH_SHORT).show();
                getScience();
            }
            if (Filipino1 != null || Filipino2 != null || Filipino3 != null || Filipino4 != null){
                //Toast.makeText(this, "Noice 1", Toast.LENGTH_SHORT).show();
                getFilipino();
            }
            if (Values1 != null || Filipino2 != null || Filipino3 != null || Filipino4 != null){
                //Toast.makeText(this, "Noice 1", Toast.LENGTH_SHORT).show();
                getValues();
            }
            if (History1 != null || History2 != null || History3 != null || History4 != null){
                //Toast.makeText(this, "Noice 1", Toast.LENGTH_SHORT).show();
                getHistory();
            }
            if (MAPEH1 != null || MAPEH2 != null || MAPEH3 != null || MAPEH4 != null){
                //Toast.makeText(this, "Noice 1", Toast.LENGTH_SHORT).show();
                getMAPEH();
            }

        } else {

            Studentdata.child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                    if(userProfile != null){
                        String User_Name, User_Email;
                        User_Name = userProfile.Name;
                        User_Email = userProfile.Email;

                        StudentName.setText(User_Name);
                        StudentEmail.setText(User_Email);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //English
            English1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(userID).child("FirstQuarter").child("FinalGrade");
            English2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(userID).child("SecondQuarter").child("FinalGrade");
            English3 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(userID).child("ThirdQuarter").child("FinalGrade");
            English4 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(userID).child("FourthQuarter").child("FinalGrade");

            //Math
            Math1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(userID).child("FirstQuarter").child("FinalGrade");
            Math2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(userID).child("SecondQuarter").child("FinalGrade");
            Math3 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(userID).child("ThirdQuarter").child("FinalGrade");
            Math4 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(userID).child("FourthQuarter").child("FinalGrade");

            //Science
            Science1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(userID).child("FirstQuarter").child("FinalGrade");
            Science2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(userID).child("SecondQuarter").child("FinalGrade");
            Science3 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(userID).child("ThirdQuarter").child("FinalGrade");
            Science4 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(userID).child("FourthQuarter").child("FinalGrade");

            //Filipino
            Filipino1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(userID).child("FirstQuarter").child("FinalGrade");
            Filipino2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(userID).child("SecondQuarter").child("FinalGrade");
            Filipino3 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(userID).child("ThirdQuarter").child("FinalGrade");
            Filipino4 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(userID).child("FourthQuarter").child("FinalGrade");

            //Values
            Values1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(userID).child("FirstQuarter").child("FinalGrade");
            Values2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(userID).child("SecondQuarter").child("FinalGrade");
            Values3 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(userID).child("ThirdQuarter").child("FinalGrade");
            Values4 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(userID).child("FourthQuarter").child("FinalGrade");

            //History
            History1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(userID).child("FirstQuarter").child("FinalGrade");
            History2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(userID).child("SecondQuarter").child("FinalGrade");
            History3 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(userID).child("ThirdQuarter").child("FinalGrade");
            History4 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(userID).child("FourthQuarter").child("FinalGrade");

            //MAPEH
            MAPEH1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(userID).child("FirstQuarter").child("FinalGrade");
            MAPEH2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(userID).child("SecondQuarter").child("FinalGrade");
            MAPEH3 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(userID).child("ThirdQuarter").child("FinalGrade");
            MAPEH4 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(userID).child("FourthQuarter").child("FinalGrade");

            if (English1 != null || English2 != null || English3 != null || English4 != null){
                //Toast.makeText(this, "English 2", Toast.LENGTH_SHORT).show();
                getEnglish();
            }
            else if (Math1 != null || Math2 != null || Math3 != null || Math4 != null){
                //Toast.makeText(this, "Math 2", Toast.LENGTH_SHORT).show();
                getMath();
            }
            else if (Science1 != null || Science2 != null || Science3 != null || Science4 != null){
                //Toast.makeText(this, "Science 2", Toast.LENGTH_SHORT).show();
                getScience();
            }
            else if (Filipino1 != null || Filipino2 != null || Filipino3 != null || Filipino4 != null){
                //Toast.makeText(this, "Filipino 2", Toast.LENGTH_SHORT).show();
                getFilipino();
            }
            else if (Values1 != null || Values2 != null || Values3 != null || Values4 != null){
                //Toast.makeText(this, "Values 2", Toast.LENGTH_SHORT).show();
                getValues();
            }
            else if (History1 != null || History2 != null || History3 != null || History4 != null){
                //Toast.makeText(this, "History 2", Toast.LENGTH_SHORT).show();
                getHistory();
            }
            else if (MAPEH1 != null || MAPEH2 != null || MAPEH3 != null || MAPEH4 != null){
                //Toast.makeText(this, "MAPEH 2", Toast.LENGTH_SHORT).show();
                getMAPEH();
            }
            else{
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void getEnglish() {
        English1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueEnglish1 = snapshot.getValue();
                    ScoreEnglish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        English2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueEnglish2 = snapshot.getValue();
                    ScoreEnglish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        English3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueEnglish3 = snapshot.getValue();
                    ScoreEnglish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        English4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueEnglish4 = snapshot.getValue();
                    ScoreEnglish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getMath() {
        Math1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMath1 = snapshot.getValue();
                    ScoreMath();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Math2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMath2 = snapshot.getValue();
                    ScoreMath();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Math3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMath3 = snapshot.getValue();
                    ScoreMath();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Math4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMath4 = snapshot.getValue();
                    ScoreMath();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getScience() {
        Science1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueScience1 = snapshot.getValue();
                    ScoreScience();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Science2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueScience2 = snapshot.getValue();
                    ScoreScience();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Science3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueScience3 = snapshot.getValue();
                    ScoreScience();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Science4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueScience4 = snapshot.getValue();
                    ScoreScience();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getFilipino() {
        Filipino1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueFilipino1 = snapshot.getValue();
                    ScoreFilipino();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Filipino2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueFilipino2 = snapshot.getValue();
                    ScoreFilipino();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Filipino3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueFilipino3 = snapshot.getValue();
                    ScoreFilipino();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Filipino4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueFilipino4 = snapshot.getValue();
                    ScoreFilipino();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getValues() {
        Values1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueValues1 = snapshot.getValue();
                    ScoreValues();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Values2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueValues2 = snapshot.getValue();
                    ScoreValues();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Values3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueValues3 = snapshot.getValue();
                    ScoreValues();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Values4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueValues4 = snapshot.getValue();
                    ScoreValues();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getHistory() {
        History1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueHistory1 = snapshot.getValue();
                    ScoreHistory();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        History2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueHistory2 = snapshot.getValue();
                    ScoreHistory();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        History3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueHistory3 = snapshot.getValue();
                    ScoreHistory();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        History4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueHistory4 = snapshot.getValue();
                    ScoreHistory();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getMAPEH() {
        MAPEH1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMAPEH1 = snapshot.getValue();
                    ScoreMAPEH();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        MAPEH2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMAPEH2 = snapshot.getValue();
                    ScoreMAPEH();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        MAPEH3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMAPEH3 = snapshot.getValue();
                    ScoreMAPEH();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        MAPEH4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate through the data snapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ValueMAPEH4 = snapshot.getValue();
                    ScoreMAPEH();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void ScoreEnglish() {
        if (ValueEnglish1 != null) {
            English.setText(String.valueOf(ValueEnglish1));
        }
        if (ValueEnglish1 != null && ValueEnglish2 != null) {
            float gr1 = (float) ValueEnglish1, gr2 = (float) ValueEnglish2, total, finaltotal;
            total = gr1 + gr2;
            finaltotal = total / 2;
            English.setText(String.valueOf(finaltotal));
        }
        if (ValueEnglish1 != null && ValueEnglish2 != null && ValueEnglish3 != null) {
            float gr1 = (float) ValueEnglish1, gr2 = (float) ValueEnglish2, gr3 = (float) ValueEnglish3, total, finaltotal;
            total = gr1 + gr2 + gr3;
            finaltotal = total / 3;
            English.setText(String.valueOf(finaltotal));
        }
        if (ValueEnglish1 != null && ValueEnglish2 != null && ValueEnglish3 != null && ValueEnglish4 != null) {
            float gr1 = (float) ValueEnglish1, gr2 = (float) ValueEnglish2, gr3 = (float) ValueEnglish3, gr4 = (float) ValueEnglish4, total, finaltotal;
            total = gr1 + gr2 + gr3 + gr4;
            finaltotal = total / 4;
            English.setText(String.valueOf(finaltotal));
        }
    }
    private void ScoreMath() {
        if (ValueMath1 != null) {
            Math.setText(String.valueOf(ValueMath1));
        }
        if (ValueMath1 != null && ValueMath2 != null) {
            float gr1 = (float) ValueMath1, gr2 = (float) ValueMath2, total, finaltotal;
            total = gr1 + gr2;
            finaltotal = total / 2;
            Math.setText(String.valueOf(finaltotal));
        }
        if (ValueMath1 != null && ValueMath2 != null && ValueMath3 != null) {
            float gr1 = (float) ValueMath1, gr2 = (float) ValueMath2, gr3 = (float) ValueMath3, total, finaltotal;
            total = gr1 + gr2 + gr3;
            finaltotal = total / 3;
            Math.setText(String.valueOf(finaltotal));
        }
        if (ValueMath1 != null && ValueMath2 != null && ValueMath3 != null && Math4 != null) {
            float gr1 = (float) ValueMath1, gr2 = (float) ValueMath2, gr3 = (float) ValueMath3, gr4 = (float) ValueMath4, total, finaltotal;
            total = gr1 + gr2 + gr3 + gr4;
            finaltotal = total / 4;
            Math.setText(String.valueOf(finaltotal));
        }
    }
    private void ScoreScience() {
        if (ValueScience1 != null) {
            Science.setText(String.valueOf(ValueScience1));
        }
        if (ValueScience1 != null && ValueScience2 != null) {
            float gr1 = (float) ValueScience1, gr2 = (float) ValueScience2, total, finaltotal;
            total = gr1 + gr2;
            finaltotal = total / 2;
            Science.setText(String.valueOf(finaltotal));
        }
        if (ValueScience1 != null && ValueScience2 != null && ValueScience3 != null) {
            float gr1 = (float) ValueScience1, gr2 = (float) ValueScience2, gr3 = (float) ValueScience3, total, finaltotal;
            total = gr1 + gr2 + gr3;
            finaltotal = total / 3;
            Science.setText(String.valueOf(finaltotal));
        }
        if (ValueScience1 != null && ValueScience2 != null && ValueScience3 != null && ValueScience4 != null) {
            float gr1 = (float) ValueScience1, gr2 = (float) ValueScience2, gr3 = (float) ValueScience3, gr4 = (float) ValueScience4, total, finaltotal;
            total = gr1 + gr2 + gr3 + gr4;
            finaltotal = total / 4;
            Science.setText(String.valueOf(finaltotal));
        }
    }
    private void ScoreFilipino() {
        if (ValueFilipino1 != null) {
            Filipino.setText(String.valueOf(ValueFilipino1));
        }
        if (ValueFilipino1 != null && ValueFilipino2 != null) {
            float gr1 = (float) ValueFilipino1, gr2 = (float) ValueFilipino2, total, finaltotal;
            total = gr1 + gr2;
            finaltotal = total / 2;
            Filipino.setText(String.valueOf(finaltotal));
        }
        if (ValueFilipino1 != null && ValueFilipino2 != null && ValueFilipino3 != null) {
            float gr1 = (float) ValueFilipino1, gr2 = (float) ValueFilipino2, gr3 = (float) ValueFilipino3, total, finaltotal;
            total = gr1 + gr2 + gr3;
            finaltotal = total / 3;
            Filipino.setText(String.valueOf(finaltotal));
        }
        if (ValueFilipino1 != null && ValueFilipino2 != null && ValueFilipino3 != null && ValueFilipino4 != null) {
            float gr1 = (float) ValueFilipino1, gr2 = (float) ValueFilipino2, gr3 = (float) ValueFilipino3, gr4 = (float) ValueFilipino4, total, finaltotal;
            total = gr1 + gr2 + gr3 + gr4;
            finaltotal = total / 4;
            Filipino.setText(String.valueOf(finaltotal));
        }
    }
    private void ScoreValues() {
        if (ValueValues1 != null) {
            Values.setText(String.valueOf(ValueValues1));
        }
        if (ValueValues1 != null && ValueValues2 != null) {
            float gr1 = (float) ValueValues1, gr2 = (float) ValueValues2, total, finaltotal;
            total = gr1 + gr2;
            finaltotal = total / 2;
            Values.setText(String.valueOf(finaltotal));
        }
        if (ValueValues1 != null && ValueValues2 != null && ValueValues3 != null) {
            float gr1 = (float) ValueValues1, gr2 = (float) ValueValues2, gr3 = (float) ValueValues3, total, finaltotal;
            total = gr1 + gr2 + gr3;
            finaltotal = total / 3;
            Values.setText(String.valueOf(finaltotal));
        }
        if (ValueValues1 != null && ValueValues2 != null && ValueValues3 != null && ValueValues4 != null) {
            float gr1 = (float) ValueValues1, gr2 = (float) ValueValues2, gr3 = (float) ValueValues3, gr4 = (float) ValueValues4, total, finaltotal;
            total = gr1 + gr2 + gr3 + gr4;
            finaltotal = total / 4;
            Values.setText(String.valueOf(finaltotal));
        }
    }
    private void ScoreHistory() {
        if (ValueHistory1 != null) {
            History.setText(String.valueOf(ValueHistory1));
        }
        if (ValueHistory1 != null && ValueHistory2 != null) {
            float gr1 = (float) ValueHistory1, gr2 = (float) ValueHistory2, total, finaltotal;
            total = gr1 + gr2;
            finaltotal = total / 2;
            History.setText(String.valueOf(finaltotal));
        }
        if (ValueHistory1 != null && ValueHistory2 != null && ValueHistory3 != null) {
            float gr1 = (float) ValueHistory1, gr2 = (float) ValueHistory2, gr3 = (float) ValueHistory3, total, finaltotal;
            total = gr1 + gr2 + gr3;
            finaltotal = total / 3;
            History.setText(String.valueOf(finaltotal));
        }
        if (ValueHistory1 != null && ValueHistory2 != null && ValueHistory3 != null && ValueHistory4 != null) {
            float gr1 = (float) ValueHistory1, gr2 = (float) ValueHistory2, gr3 = (float) ValueHistory3, gr4 = (float) ValueHistory4, total, finaltotal;
            total = gr1 + gr2 + gr3 + gr4;
            finaltotal = total / 4;
            History.setText(String.valueOf(finaltotal));
        }
    }
    private void ScoreMAPEH() {
        if (ValueMAPEH1 != null) {
            MAPEH.setText(String.valueOf(ValueMAPEH1));
        }
        if (ValueMAPEH1 != null && ValueMAPEH2 != null) {
            float gr1 = (float) ValueMAPEH1, gr2 = (float) ValueMAPEH2, total, finaltotal;
            total = gr1 + gr2;
            finaltotal = total / 2;
            MAPEH.setText(String.valueOf(finaltotal));
        }
        if (ValueMAPEH1 != null && ValueMAPEH2 != null && ValueMAPEH3 != null) {
            float gr1 = (float) ValueMAPEH1, gr2 = (float) ValueMAPEH2, gr3 = (float) ValueMAPEH3, total, finaltotal;
            total = gr1 + gr2 + gr3;
            finaltotal = total / 3;
            MAPEH.setText(String.valueOf(finaltotal));
        }
        if (ValueMAPEH1 != null && ValueMAPEH2 != null && ValueMAPEH3 != null && ValueMAPEH4 != null) {
            float gr1 = (float) ValueMAPEH1, gr2 = (float) ValueMAPEH2, gr3 = (float) ValueMAPEH3, gr4 = (float) ValueMAPEH4, total, finaltotal;
            total = gr1 + gr2 + gr3 + gr4;
            finaltotal = total / 4;
            MAPEH.setText(String.valueOf(finaltotal));
        }
    }

    private void ClickSubject(){

        SubjectEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub = "English";
                data();
            }
        });
        SubjectMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub = "Math";
                data();
            }
        });
        SubjectScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub = "Science";
                data();
            }
        });
        SubjectFilipino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub = "Filipino";
                data();
            }
        });
        SubjectValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub = "Values";
                data();
            }
        });
        SubjectHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub = "History";
                data();
            }
        });
        SubjectMAPEH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub = "MAPEH";
                data();
            }
        });
    }

    private void data(){

        if (studentID != null && !studentID.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), ViewGrades.class);
            intent.putExtra("ID", studentID);
            intent.putExtra("Subject", Sub);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), ViewGrades.class);
            intent.putExtra("ID", userID);
            intent.putExtra("Subject", Sub);
            startActivity(intent);
        }
    }
}