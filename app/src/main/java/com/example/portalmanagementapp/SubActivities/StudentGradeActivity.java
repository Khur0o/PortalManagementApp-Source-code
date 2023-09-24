package com.example.portalmanagementapp.SubActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.SubjectList;
import com.example.portalmanagementapp.UserhelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentGradeActivity extends AppCompatActivity {

    //Layout
    private LinearLayout RecitationLayout, ActivityLayouts, AssignLayouts, QuizLayouts, LongQuizLayouts, PrelimLayout, PeriodicLayout;
    private Button Activity, Assign, Quiz, LongQuiz, Save, Quarter01, Quarter02, Quarter03, Quarter04;

    //Database path
    private DatabaseReference BehaviorGrade, RecitationGrade, ActivityGrade1, ActivityGrade2, AssignGrade1, AssignGrade2, QuizGrade1, QuizGrade2, LongQuizGrade1, LongQuizGrade2, PrelimGrade1, PrelimGrade2, PeriodicGrade1, PeriodicGrade2, FinalGrade, PercentValues1, PercentValues2, PercentValues3, PercentValues4, Final;
    private DatabaseReference Grades;

    //student name and lrn
    private TextView NameStudent, LRNStudent;
    private String User_Name;

    //Get data in Edittext
    private EditText ReciteGrades, BehaviorGrades, PrelimGrades1, PrelimGrades2, PeriodicGrades1, PeriodicGrades2, Percent1, Percent2, Percent3, Percent4;

    //OverAll score
    private int Recite, TotalActivity, OverallActivity, TotalAssign, OverallAssign, TotalQuiz, OverallQuiz, TotalLongQuiz, OverallLongQuiz,TotalPrelim, OverallPrelim, TotalPeriodic, OverallPeriodic, getpercentRecite, getpercentAssign, getpercentQuiz, getpercentLongQuiz, getpercentPrelim, getpercentPeriodic;

    //number list on database save
    private int numAct1, numAct2, numAssign1, numAssign2, numQuiz1, numQuiz2, numLongQuiz1, numLongQuiz2;

    //number list available
    private int a = 0, b = 0, c = 0, d = 0, e = 0;

    //String data from previous activity
    private String studentID, Subject;

    //Log History path
    private DatabaseReference LogHistory = FirebaseDatabase.getInstance().getReference("LogHistory");
    //String time and date
    private String Time, currentDateTime;

    //User data
    private FirebaseUser user;
    private String userID;
    private DatabaseReference Admin, studentdata;

    //Get Data and Set Data
    private String ActivityName, AssignName, QuizName, LongQuizName;
    private Object BehaviorValues, ReciteValues, ActivityValues1, ActivityValues2, AssignValues1, AssignValues2, QuizValues1, QuizValues2, LongQuizValues1, LongQuizValues2, PrelimValues1, PrelimValues2, PeriodicValues1, PeriodicValues2, Percentage1, Percentage2, Percentage3, Percentage4;

    private List<EditText> editTextActivityList = new ArrayList<>();
    private List<EditText> editTextAssignList = new ArrayList<>();
    private List<EditText> editTextQuizList = new ArrayList<>();
    private List<EditText> editTextLongQuizList = new ArrayList<>();
    private HashMap<String, Integer> Act1idsMap = new HashMap<>();
    private HashMap<String, Integer> Act2idsMap = new HashMap<>();
    private HashMap<String, Integer> Ass1idsMap = new HashMap<>();
    private HashMap<String, Integer> Ass2idsMap = new HashMap<>();
    private HashMap<String, Integer> Quiz1idsMap = new HashMap<>();
    private HashMap<String, Integer> Quiz2idsMap = new HashMap<>();
    private HashMap<String, Integer> LongQuiz1idsMap = new HashMap<>();
    private HashMap<String, Integer> LongQuiz2idsMap = new HashMap<>();

    private String Name;
    private String formattedNumber;
    private String ProfStatus, NameProf, AdminStatus, NameAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_grade);

        //Layout
        ActivityLayouts = findViewById(R.id.activitylayout);
        RecitationLayout = findViewById(R.id.recitelayout);
        AssignLayouts = findViewById(R.id.asslayout);
        QuizLayouts = findViewById(R.id.quizlayout);
        LongQuizLayouts = findViewById(R.id.longquizlayout);
        PrelimLayout = findViewById(R.id.prelimlayout);
        PeriodicLayout = findViewById(R.id.periodiclayout);

        //EditText
        ReciteGrades = findViewById(R.id.recite);
        BehaviorGrades = findViewById(R.id.behavior);
        PrelimGrades1 = findViewById(R.id.prelim1);
        PrelimGrades2 = findViewById(R.id.prelim2);
        PeriodicGrades1 = findViewById(R.id.periodic1);
        PeriodicGrades2 = findViewById(R.id.periodic2);

        Percent1 = findViewById(R.id.percent1);
        Percent2 = findViewById(R.id.percent2);
        Percent3 = findViewById(R.id.percent3);
        Percent4 = findViewById(R.id.percent4);

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
        Subject = intent.getStringExtra("Subject");

        //Toast.makeText(this, "Click quarter list selection before to input data.", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + studentID + "\n" + Subject, Toast.LENGTH_SHORT).show();
        //Get current time and date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        //Get data on database and current user UID
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess").child("AdministratorList");
        studentdata = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList");

        // Disable button
        Activity.setEnabled(false);
        Assign.setEnabled(false);
        Quiz.setEnabled(false);
        LongQuiz.setEnabled(false);
        Save.setEnabled(false);

        GetDataOnAdminandStudent();
        QuarterList();
        AddList();
        SaveData();
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

                    String LRN;
                    Name = userProfile.Name;
                    LRN = userProfile.StudentNumber;

                    NameStudent.setText(Name);
                    LRNStudent.setText(LRN);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Quarter list button
    private void QuarterList(){

        PercentValues1 = FirebaseDatabase.getInstance().getReference("Percentage").child(userID).child("PercentValues1");
        PercentValues2 = FirebaseDatabase.getInstance().getReference("Percentage").child(userID).child("PercentValues2");
        PercentValues3 = FirebaseDatabase.getInstance().getReference("Percentage").child(userID).child("PercentValues3");
        PercentValues4 = FirebaseDatabase.getInstance().getReference("Percentage").child(userID).child("PercentValues4");

        Quarter01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentGradeActivity.this, "First Quarter", Toast.LENGTH_SHORT).show();
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));

                Activity.setEnabled(true);
                Assign.setEnabled(true);
                Quiz.setEnabled(true);
                LongQuiz.setEnabled(true);
                Save.setEnabled(true);

                if(Subject.equals("English")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FirstQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("English").child("FirstQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("FirstQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Math")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FirstQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Math").child("FirstQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("FirstQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Science")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FirstQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Science").child("FirstQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("FirstQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Filipino")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FirstQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Filipino").child("FirstQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("FirstQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Values")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FirstQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Values").child("FirstQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("FirstQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("History")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FirstQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("History").child("FirstQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("FirstQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("MAPEH")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FirstQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("MAPEH").child("FirstQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("FirstQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
            }
        });
        Quarter02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentGradeActivity.this, "Second Quarter", Toast.LENGTH_SHORT).show();
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));

                Activity.setEnabled(true);
                Assign.setEnabled(true);
                Quiz.setEnabled(true);
                LongQuiz.setEnabled(true);
                Save.setEnabled(true);

                if(Subject.equals("English")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("SecondQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("English").child("SecondQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("SecondQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Math")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("SecondQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Math").child("SecondQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("SecondQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Science")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("SecondQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Science").child("SecondQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("SecondQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Filipino")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("SecondQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Filipino").child("SecondQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("SecondQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Values")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("SecondQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Values").child("SecondQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("SecondQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("History")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("SecondQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("History").child("SecondQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("SecondQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("MAPEH")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("SecondQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("MAPEH").child("SecondQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("SecondQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
            }
        });
        Quarter03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentGradeActivity.this, "Third Quarter", Toast.LENGTH_SHORT).show();
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));

                Activity.setEnabled(true);
                Assign.setEnabled(true);
                Quiz.setEnabled(true);
                LongQuiz.setEnabled(true);
                Save.setEnabled(true);

                if(Subject.equals("English")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("ThirdQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("English").child("ThirdQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("ThirdQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Math")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("ThirdQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Math").child("ThirdQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("ThirdQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Science")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("ThirdQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Science").child("ThirdQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("ThirdQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Filipino")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("ThirdQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Filipino").child("ThirdQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("ThirdQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Values")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("ThirdQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Values").child("ThirdQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("ThirdQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("History")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("ThirdQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("History").child("ThirdQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("ThirdQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("MAPEH")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("ThirdQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("MAPEH").child("ThirdQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("ThirdQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
            }
        });
        Quarter04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentGradeActivity.this, "Fourth Quarter", Toast.LENGTH_SHORT).show();
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));

                Activity.setEnabled(true);
                Assign.setEnabled(true);
                Quiz.setEnabled(true);
                LongQuiz.setEnabled(true);
                Save.setEnabled(true);

                if(Subject.equals("English")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("English").child(studentID).child("FourthQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("English").child("FourthQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("FourthQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Math")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Math").child(studentID).child("FourthQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Math").child("FourthQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("FourthQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Science")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Science").child(studentID).child("FourthQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Science").child("FourthQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("FourthQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Filipino")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("QuizOverAll");
                    LongQuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Filipino").child(studentID).child("FourthQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Filipino").child("FourthQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("FourthQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("Values")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("Values").child(studentID).child("FourthQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("Values").child("FourthQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("FourthQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("History")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("History").child(studentID).child("FourthQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("History").child("FourthQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("FourthQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
                if(Subject.equals("MAPEH")){
                    RecitationGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("Recitation");
                    ActivityGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("ActivityScore");
                    ActivityGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("ActivityOverAll");
                    AssignGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("AssignmentScore");
                    AssignGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("AssignmentOverAll");
                    QuizGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("QuizScore");
                    QuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("QuizOverAll");
                    LongQuizGrade1= FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("LongQuizScore");
                    LongQuizGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("LongQuizOverAll");
                    PrelimGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("PrelimScore");
                    PrelimGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("PrelimOverAll");
                    PeriodicGrade1 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("PeriodicScore");
                    PeriodicGrade2 = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("PeriodicOverAll");
                    BehaviorGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("Behavior");
                    FinalGrade = FirebaseDatabase.getInstance().getReference("Subject").child("MAPEH").child(studentID).child("FourthQuarter").child("FinalGrade");
                    Final = FirebaseDatabase.getInstance().getReference("ScoreBoard").child("MAPEH").child("FourthQuarter").child(studentID);

                    Grades = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("FourthQuarter");
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    CheckPath();
                }
            }
        });
    }

    private void CheckPath(){
        //Create layout with data
        try{
            //Clear the existing views
            ActivityLayouts.removeAllViews();
            AssignLayouts.removeAllViews();
            QuizLayouts.removeAllViews();
            LongQuizLayouts.removeAllViews();
            BehaviorGrades.setText("");
            ReciteGrades.setText("");
            PrelimGrades1.setText("");
            PrelimGrades2.setText("");
            PeriodicGrades1.setText("");
            PeriodicGrades2.setText("");
            Percent1.setText("");
            Percent2.setText("");
            Percent3.setText("");
            Percent4.setText("");

            ActivityGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Object> activityValues1 = new ArrayList<>();
                    List<String> activityNames = new ArrayList<>();

                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                        String activityName = datasnapshot.getKey();
                        Object activityValue1 = datasnapshot.getValue();

                        activityNames.add(activityName);
                        activityValues1.add(activityValue1);
                    }

                    ActivityGrade2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Object> activityValues2 = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Object activityValue2 = snapshot.getValue();
                                activityValues2.add(activityValue2);
                            }

                            getGradeActivity(activityNames, activityValues1, activityValues2);

                            ActivityGrade2.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle onCancelled if needed
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled if needed
                }
            });
            AssignGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Object> AssignValues1 = new ArrayList<>();
                    List<String> AssignNames = new ArrayList<>();

                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                        String AssignName = datasnapshot.getKey();
                        Object AssignValue1 = datasnapshot.getValue();

                        AssignNames.add(AssignName);
                        AssignValues1.add(AssignValue1);
                    }

                    AssignGrade2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Object> AssignValues2 = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Object AssignValue2 = snapshot.getValue();
                                AssignValues2.add(AssignValue2);
                            }

                            // Pass an empty list instead of null for activityValues1
                            getGradeAssign(AssignNames, AssignValues1, AssignValues2);

                            // Remove the ValueEventListener after gathering the data
                            AssignGrade2.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle onCancelled if needed
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled if needed
                }
            });
            QuizGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Object> QuizValues1 = new ArrayList<>();
                    List<String> QuizNames = new ArrayList<>();

                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                        String QuizName = datasnapshot.getKey();
                        Object QuizValue1 = datasnapshot.getValue();

                        QuizNames.add(QuizName);
                        QuizValues1.add(QuizValue1);
                    }

                    QuizGrade2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Object> QuizValues2 = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Object QuizValue2 = snapshot.getValue();
                                QuizValues2.add(QuizValue2);
                            }

                            // Pass an empty list instead of null for activityValues1
                            getGradeQuiz(QuizNames, QuizValues1, QuizValues2);

                            // Remove the ValueEventListener after gathering the data
                            QuizGrade2.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle onCancelled if needed
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled if needed
                }
            });
            LongQuizGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Object> LongQuizValues1 = new ArrayList<>();
                    List<String> LongQuizNames = new ArrayList<>();

                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                        String LongQuizName = datasnapshot.getKey();
                        Object LongQuizValue1 = datasnapshot.getValue();

                        LongQuizNames.add(LongQuizName);
                        LongQuizValues1.add(LongQuizValue1);
                    }

                    LongQuizGrade2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Object> LongQuizValues2 = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Object LongQuizValue2 = snapshot.getValue();
                                LongQuizValues2.add(LongQuizValue2);
                            }

                            // Pass an empty list instead of null for activityValues1
                            getGradeLongQuiz(LongQuizNames, LongQuizValues1, LongQuizValues2);

                            // Remove the ValueEventListener after gathering the data
                            LongQuizGrade2.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle onCancelled if needed
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled if needed
                }
            });
            BehaviorGrade.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BehaviorValues = snapshot.getValue();
                        BehaviorGrades.setText(String.valueOf(BehaviorValues));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            RecitationGrade.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ReciteValues = snapshot.getValue();
                        ReciteGrades.setText(String.valueOf(ReciteValues));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PrelimGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PrelimValues1 = snapshot.getValue();
                        PrelimGrades1.setText(String.valueOf(PrelimValues1));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PrelimGrade2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PrelimValues2 = snapshot.getValue();
                        PrelimGrades2.setText(String.valueOf(PrelimValues2));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PeriodicGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PeriodicValues1 = snapshot.getValue();
                        PeriodicGrades1.setText(String.valueOf(PeriodicValues1));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PeriodicGrade2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PeriodicValues2 = snapshot.getValue();
                        PeriodicGrades2.setText(String.valueOf(PeriodicValues2));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PercentValues1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Percentage1 = snapshot.getValue();
                        Percent1.setText(String.valueOf(Percentage1));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PercentValues2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Percentage2 = snapshot.getValue();
                        Percent2.setText(String.valueOf(Percentage2));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PercentValues3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Percentage3 = snapshot.getValue();
                        Percent3.setText(String.valueOf(Percentage3));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            PercentValues4.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Percentage4 = snapshot.getValue();
                        Percent4.setText(String.valueOf(Percentage4));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(this, "ERROR Checkpath: " + e, Toast.LENGTH_SHORT).show();
        }

        if(ActivityGrade1 != null || AssignGrade1 != null || QuizGrade1 != null || LongQuizGrade1 != null){
            ActivityGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<String> numberList = new ArrayList();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String number = childSnapshot.getKey();
                        numberList.add(number);
                    }
                    TotalActivity = numberList.size();
                    AddList();
                    a += TotalActivity;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            AssignGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<String> numberList = new ArrayList();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String number = childSnapshot.getKey();
                        numberList.add(number);
                    }
                    TotalAssign = numberList.size();
                    AddList();
                    b += TotalAssign;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            QuizGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<String> numberList = new ArrayList();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String number = childSnapshot.getKey();
                        numberList.add(number);

                    }
                    TotalQuiz = numberList.size();
                    AddList();
                    c += TotalQuiz;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            LongQuizGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<String> numberList = new ArrayList();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String number = childSnapshot.getKey();
                        numberList.add(number);

                    }
                    TotalLongQuiz = numberList.size();
                    AddList();
                    d += TotalLongQuiz;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else{
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }


    }
    private void getGradeActivity(List<String> activityNames, List<Object> activityValues1, List<Object> activityValues2) {
        if (activityValues1 != null && activityValues2 != null) {
            int size = Math.max(activityValues1.size(), activityValues2.size());
            for (int i = 0; i < size; i++) {
                Object value1 = i < activityValues1.size() ? activityValues1.get(i) : null;
                Object value2 = i < activityValues2.size() ? activityValues2.get(i) : null;

                if (value1 instanceof String && value2 instanceof String) {
                    String scoreString1 = (String) value1;
                    String scoreString2 = (String) value2;
                    int score1 = Integer.parseInt(scoreString1);
                    int score2 = Integer.parseInt(scoreString2);

                    String activityName = "";
                    if (activityNames != null && i < activityNames.size()) {
                        activityName = activityNames.get(i);
                    }

                    createActivityLayout(activityName, score1, score2);
                } else if (value1 instanceof Long && value2 instanceof Long) {
                    Long scoreLong1 = (Long) value1;
                    Long scoreLong2 = (Long) value2;
                    int score1 = scoreLong1 != null ? scoreLong1.intValue() : 0;
                    int score2 = scoreLong2 != null ? scoreLong2.intValue() : 0;

                    String activityName = "";
                    if (activityNames != null && i < activityNames.size()) {
                        activityName = activityNames.get(i);
                    }

                    createActivityLayout(activityName, score1, score2);
                }
            }
        } else {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getGradeAssign(List<String> AssignNames, List<Object> AssignValues1, List<Object> AssignValues2) {
        if (AssignValues1 != null && AssignValues2 != null) {
            int size = Math.max(AssignValues1.size(), AssignValues2.size());
            for (int i = 0; i < size; i++) {
                Object value1 = i < AssignValues1.size() ? AssignValues1.get(i) : null;
                Object value2 = i < AssignValues2.size() ? AssignValues2.get(i) : null;

                if (value1 instanceof String && value2 instanceof String) {
                    String scoreString1 = (String) value1;
                    String scoreString2 = (String) value2;
                    int score1 = Integer.parseInt(scoreString1);
                    int score2 = Integer.parseInt(scoreString2);

                    String AssignName = "";
                    if (AssignNames != null && i < AssignNames.size()) {
                        AssignName = AssignNames.get(i);
                    }

                    createAssignLayout(AssignName, score1, score2);
                } else if (value1 instanceof Long && value2 instanceof Long) {
                    Long scoreLong1 = (Long) value1;
                    Long scoreLong2 = (Long) value2;
                    int score1 = scoreLong1 != null ? scoreLong1.intValue() : 0;
                    int score2 = scoreLong2 != null ? scoreLong2.intValue() : 0;

                    String AssignName = "";
                    if (AssignNames != null && i < AssignNames.size()) {
                        AssignName = AssignNames.get(i);
                    }

                    createAssignLayout(AssignName, score1, score2);
                }
            }
        } else {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }
    private void getGradeQuiz(List<String> QuizNames, List<Object> QuizValues1, List<Object> QuizValues2) {
        if (QuizValues1 != null && QuizValues2 != null) {
            int size = Math.max(QuizValues1.size(), QuizValues2.size());
            for (int i = 0; i < size; i++) {
                Object value1 = i < QuizValues1.size() ? QuizValues1.get(i) : null;
                Object value2 = i < QuizValues2.size() ? QuizValues2.get(i) : null;

                if (value1 instanceof String && value2 instanceof String) {
                    String scoreString1 = (String) value1;
                    String scoreString2 = (String) value2;
                    int score1 = Integer.parseInt(scoreString1);
                    int score2 = Integer.parseInt(scoreString2);

                    String QuizName = "";
                    if (QuizNames != null && i < QuizNames.size()) {
                        QuizName = QuizNames.get(i);
                    }

                    createQuizLayout(QuizName, score1, score2);
                } else if (value1 instanceof Long && value2 instanceof Long) {
                    Long scoreLong1 = (Long) value1;
                    Long scoreLong2 = (Long) value2;
                    int score1 = scoreLong1 != null ? scoreLong1.intValue() : 0;
                    int score2 = scoreLong2 != null ? scoreLong2.intValue() : 0;

                    String QuizName = "";
                    if (QuizNames != null && i < QuizNames.size()) {
                        QuizName = QuizNames.get(i);
                    }

                    createQuizLayout(QuizName, score1, score2);
                }
            }
        } else {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }
    private void getGradeLongQuiz(List<String> LongQuizNames, List<Object> LongQuizValues1, List<Object> LongQuizValues2) {
        if (LongQuizValues1 != null && LongQuizValues2 != null) {
            int size = Math.max(LongQuizValues1.size(), LongQuizValues2.size());
            for (int i = 0; i < size; i++) {
                Object value1 = i < LongQuizValues1.size() ? LongQuizValues1.get(i) : null;
                Object value2 = i < LongQuizValues2.size() ? LongQuizValues2.get(i) : null;

                if (value1 instanceof String && value2 instanceof String) {
                    String scoreString1 = (String) value1;
                    String scoreString2 = (String) value2;
                    int score1 = Integer.parseInt(scoreString1);
                    int score2 = Integer.parseInt(scoreString2);

                    String LongQuizName = "";
                    if (LongQuizNames != null && i < LongQuizNames.size()) {
                        LongQuizName = LongQuizNames.get(i);
                    }

                    createLongQuizLayout(LongQuizName, score1, score2);
                } else if (value1 instanceof Long && value2 instanceof Long) {
                    Long scoreLong1 = (Long) value1;
                    Long scoreLong2 = (Long) value2;
                    int score1 = scoreLong1 != null ? scoreLong1.intValue() : 0;
                    int score2 = scoreLong2 != null ? scoreLong2.intValue() : 0;

                    String LongQuizName = "";
                    if (LongQuizNames != null && i < LongQuizNames.size()) {
                        LongQuizName = LongQuizNames.get(i);
                    }

                    createLongQuizLayout(LongQuizName, score1, score2);
                }
            }
        } else {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void createActivityLayout(String tname, int score1, int score2) {
        LinearLayout activityLayout = new LinearLayout(getApplicationContext());
        activityLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        activityLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout activityLayout2 = new LinearLayout(getApplicationContext());
        activityLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        activityLayout2.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

        EditText editText1 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams1.setMargins(0, 0, 20, 0);
        editText1.setLayoutParams(editTextParams1);
        editText1.setBackgroundResource(R.drawable.text_border);
        editText1.setText(String.valueOf(score1));
        editText1.setTextColor(Color.BLACK);
        editText1.setSingleLine(true);
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText1.setPadding(40, 40, 40, 40);
        int id1 = View.generateViewId();
        editTextActivityList.add(editText1);
        editText1.setId(id1);
        Act1idsMap.put(tname, id1);
        //editText1.setEnabled(false);

        EditText editText2 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams2 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams2.setMargins(0, 0, 20, 0);
        editText2.setLayoutParams(editTextParams2);
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setTextColor(Color.BLACK);
        editText2.setSingleLine(true);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText2.setPadding(40, 40, 40, 40);
        int id2 = View.generateViewId();
        editTextActivityList.add(editText2);
        editText2.setId(id2);
        Act2idsMap.put(tname, id2);
        //editText2.setEnabled(false);

        activityLayout2.addView(textView);
        activityLayout.addView(editText1);
        activityLayout.addView(editText2);
        activityLayout2.addView(activityLayout);

        ActivityLayouts.addView(activityLayout2);
    }
    private void createAssignLayout(String tname, int score1, int score2) {
        LinearLayout AssignLayout = new LinearLayout(getApplicationContext());
        AssignLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        AssignLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout AssignLayout2 = new LinearLayout(getApplicationContext());
        AssignLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        AssignLayout2.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

        EditText editText1 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams1.setMargins(0, 0, 20, 0);
        editText1.setLayoutParams(editTextParams1);
        editText1.setBackgroundResource(R.drawable.text_border);
        editText1.setText(String.valueOf(score1));
        editText1.setTextColor(Color.BLACK);
        editText1.setSingleLine(true);
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText1.setPadding(40, 40, 40, 40);
        int id1 = View.generateViewId();
        editTextAssignList.add(editText1);
        editText1.setId(id1);
        Ass1idsMap.put(tname, id1);
        //editText1.setEnabled(false);

        EditText editText2 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams2 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams2.setMargins(0, 0, 20, 0);
        editText2.setLayoutParams(editTextParams2);
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setTextColor(Color.BLACK);
        editText2.setSingleLine(true);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText2.setPadding(40, 40, 40, 40);
        int id2 = View.generateViewId();
        editTextAssignList.add(editText1);
        editText1.setId(id2);
        Ass2idsMap.put(tname, id2);
        //editText2.setEnabled(false);

        AssignLayout2.addView(textView);
        AssignLayout.addView(editText1);
        AssignLayout.addView(editText2);
        AssignLayout2.addView(AssignLayout);

        AssignLayouts.addView(AssignLayout2);

    }
    private void createQuizLayout(String tname, int score1, int score2) {
        LinearLayout QuizLayout = new LinearLayout(getApplicationContext());
        QuizLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        QuizLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout QuizLayout2 = new LinearLayout(getApplicationContext());
        QuizLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        QuizLayout2.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

        EditText editText1 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams1.setMargins(0, 0, 20, 0);
        editText1.setLayoutParams(editTextParams1);
        editText1.setBackgroundResource(R.drawable.text_border);
        editText1.setText(String.valueOf(score1));
        editText1.setTextColor(Color.BLACK);
        editText1.setSingleLine(true);
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText1.setPadding(40, 40, 40, 40);
        int id1 = View.generateViewId();
        editTextQuizList.add(editText1);
        editText1.setId(id1);
        Quiz1idsMap.put(tname, id1);
        //editText1.setEnabled(false);

        EditText editText2 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams2 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams2.setMargins(0, 0, 20, 0);
        editText2.setLayoutParams(editTextParams2);
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setTextColor(Color.BLACK);
        editText2.setSingleLine(true);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText2.setPadding(40, 40, 40, 40);
        int id2 = View.generateViewId();
        editTextQuizList.add(editText2);
        editText2.setId(id2);
        Quiz2idsMap.put(tname, id2);
        //editText2.setEnabled(false);

        QuizLayout2.addView(textView);
        QuizLayout.addView(editText1);
        QuizLayout.addView(editText2);
        QuizLayout2.addView(QuizLayout);

        QuizLayouts.addView(QuizLayout2);

    }
    private void createLongQuizLayout(String tname, int score1, int score2) {
        LinearLayout LongQuizLayout = new LinearLayout(getApplicationContext());
        LongQuizLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LongQuizLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout LongQuizLayout2 = new LinearLayout(getApplicationContext());
        LongQuizLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LongQuizLayout2.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

        EditText editText1 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams1.setMargins(0, 0, 20, 0);
        editText1.setLayoutParams(editTextParams1);
        editText1.setBackgroundResource(R.drawable.text_border);
        editText1.setText(String.valueOf(score1));
        editText1.setTextColor(Color.BLACK);
        editText1.setSingleLine(true);
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText1.setPadding(40, 40, 40, 40);
        int id1 = View.generateViewId();
        editTextLongQuizList.add(editText1);
        editText1.setId(id1);
        LongQuiz1idsMap.put(tname, id1);
        //editText1.setEnabled(false);

        EditText editText2 = new EditText(getApplicationContext());
        LinearLayout.LayoutParams editTextParams2 = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextParams2.setMargins(0, 0, 20, 0);
        editText2.setLayoutParams(editTextParams2);
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setTextColor(Color.BLACK);
        editText2.setSingleLine(true);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText2.setPadding(40, 40, 40, 40);
        int id2 = View.generateViewId();
        editTextLongQuizList.add(editText2);
        editText2.setId(id2);
        LongQuiz1idsMap.put(tname, id2);
        //editText2.setEnabled(false);

        LongQuizLayout2.addView(textView);
        LongQuizLayout.addView(editText1);
        LongQuizLayout.addView(editText2);
        LongQuizLayout2.addView(LongQuizLayout);

        LongQuizLayouts.addView(LongQuizLayout2);

    }

    private void AddList(){

        Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a++;
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("Activity " + a);

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                EditText editText1 = new EditText(getApplicationContext());
                LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                editTextParams1.setMargins(0, 0, 20, 0);
                editText1.setLayoutParams(editTextParams1);
                editText1.setBackgroundResource(R.drawable.text_border);
                editText1.setHint("Score");
                editText1.setPadding(40, 40, 40, 40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextActivityList.add(editText1);
                editText1.setId(id1);
                String actname1 = "Activity " + a;
                Act1idsMap.put(actname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("Over All");
                editText2.setPadding(40, 40, 40, 40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextActivityList.add(editText2);
                editText2.setId(id2);
                String actname2 = "Activity " + a;
                Act2idsMap.put(actname2, id2);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);

                ActivityLayouts.addView(textView);
                ActivityLayouts.addView(linearLayout);
            }
        });

        Assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b++;
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("Assignment " + b);

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                EditText editText1 = new EditText(getApplicationContext());
                LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                editTextParams1.setMargins(0, 0, 20, 0);
                editText1.setLayoutParams(editTextParams1);
                editText1.setBackgroundResource(R.drawable.text_border);
                editText1.setHint("Score");
                editText1.setPadding(40, 40, 40, 40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextAssignList.add(editText1);
                editText1.setId(id1);
                String actname1 = "Assignment " + b;
                Ass1idsMap.put(actname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("Over All");
                editText2.setPadding(40, 40, 40, 40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextAssignList.add(editText2);
                editText2.setId(id2);
                String actname2 = "Assignment " + b;
                Ass2idsMap.put(actname2, id2);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);

                AssignLayouts.addView(textView);
                AssignLayouts.addView(linearLayout);
            }
        });
        Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("Quiz " + c);

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                EditText editText1 = new EditText(getApplicationContext());
                LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                editTextParams1.setMargins(0, 0, 20, 0);
                editText1.setLayoutParams(editTextParams1);
                editText1.setBackgroundResource(R.drawable.text_border);
                editText1.setHint("Score");
                editText1.setPadding(40, 40, 40, 40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextQuizList.add(editText1);
                editText1.setId(id1);
                String actname1 = "Quiz " + c;
                Quiz1idsMap.put(actname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("Over All");
                editText2.setPadding(40, 40, 40, 40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextQuizList.add(editText2);
                editText2.setId(id2);
                String actname2 = "Quiz " + c;
                Quiz2idsMap.put(actname2, id2);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);

                QuizLayouts.addView(textView);
                QuizLayouts.addView(linearLayout);
            }
        });
        LongQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d++;
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("Long Quiz " + d);

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                EditText editText1 = new EditText(getApplicationContext());
                LinearLayout.LayoutParams editTextParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                editTextParams1.setMargins(0, 0, 20, 0);
                editText1.setLayoutParams(editTextParams1);
                editText1.setBackgroundResource(R.drawable.text_border);
                editText1.setHint("Score");
                editText1.setPadding(40, 40, 40, 40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextLongQuizList.add(editText1);
                editText1.setId(id1);
                String actname1 = "LongQuiz " + d;
                LongQuiz1idsMap.put(actname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("Over All");
                editText2.setPadding(40, 40, 40, 40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextLongQuizList.add(editText2);
                editText2.setId(id2);
                String actname2 = "LongQuiz " + d;
                LongQuiz2idsMap.put(actname2, id2);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);

                LongQuizLayouts.addView(textView);
                LongQuizLayouts.addView(linearLayout);
            }
        });

    }

    private void SaveData(){
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    //Get Percentage
                    String _Percent1 = Percent1.getText().toString().trim();
                    int percent1 = (_Percent1.isEmpty()) ? 0 : Integer.parseInt(_Percent1);
                    String _Percent2 = Percent2.getText().toString().trim();
                    int percent2 = (_Percent2.isEmpty()) ? 0 : Integer.parseInt(_Percent2);
                    String _Percent3 = Percent3.getText().toString().trim();
                    int percent3 = (_Percent3.isEmpty()) ? 0 : Integer.parseInt(_Percent3);
                    String _Percent4 = Percent4.getText().toString().trim();
                    int percent4 = (_Percent4.isEmpty()) ? 0 : Integer.parseInt(_Percent4);

                    int finalpercent = percent1 + percent2 + percent3 + percent4;
                    int totalpercent = 100;
                    Toast.makeText(StudentGradeActivity.this, "" + finalpercent, Toast.LENGTH_SHORT).show();
                    if (finalpercent == totalpercent) {

                        //Store Percentage
                        PercentValues1.child("Values1").setValue(_Percent1);
                        PercentValues2.child("Values2").setValue(_Percent2);
                        PercentValues3.child("Values3").setValue(_Percent3);
                        PercentValues4.child("Values4").setValue(_Percent4);

                        //Behavior
                        String Behavior = BehaviorGrades.getText().toString().trim();
                        int scoreBehavior = Integer.parseInt(Behavior);
                        BehaviorGrade.child("Behavior").setValue(scoreBehavior);

                        //Recitation
                        String Recite = ReciteGrades.getText().toString().trim();
                        int scoreRecite = Integer.parseInt(Recite);
                        RecitationGrade.child("Recitation").setValue(scoreRecite);

                        //Prelim
                        String Prelim1 = PrelimGrades1.getText().toString().trim();
                        int scorePrelim1 = Integer.parseInt(Prelim1);
                        PrelimGrade1.child("PrelimScore").setValue(scorePrelim1);

                        String Prelim2 = PrelimGrades2.getText().toString().trim();
                        int scorePrelim2 = Integer.parseInt(Prelim2);
                        PrelimGrade2.child("PrelimOverAll").setValue(scorePrelim2);

                        //Periodic
                        String Periodic1 = PeriodicGrades1.getText().toString().trim();
                        int scorePeriodic1 = Integer.parseInt(Periodic1);
                        PeriodicGrade1.child("PeriodicOverAll").setValue(scorePeriodic1);

                        String Periodic2 = PeriodicGrades2.getText().toString().trim();
                        int scorePeriodic2 = Integer.parseInt(Periodic2);
                        PeriodicGrade2.child("PeriodicOverAll").setValue(scorePeriodic2);

                        //Activity
                        for (String currenttname : Act1idsMap.keySet()) {
                            EditText currentEditText = findViewById(Act1idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numAct1 += score;
                                ActivityGrade1.child(currenttname).setValue(score);
                            }
                        }
                        for (String currenttname : Act2idsMap.keySet()) {
                            EditText currentEditText = findViewById(Act2idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numAct2 += score;
                                ActivityGrade2.child(currenttname).setValue(score);
                            }
                        }

                        //Assignment
                        for (String currenttname : Ass1idsMap.keySet()) {
                            EditText currentEditText = findViewById(Ass1idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numAssign1 += score;
                                AssignGrade1.child(currenttname).setValue(score);
                            }
                        }
                        for (String currenttname : Ass2idsMap.keySet()) {
                            EditText currentEditText = findViewById(Ass2idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numAssign2 += score;
                                AssignGrade2.child(currenttname).setValue(score);
                            }
                        }
                        //Quiz
                        for (String currenttname : Quiz1idsMap.keySet()) {
                            EditText currentEditText = findViewById(Quiz1idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numQuiz1 += score;
                                QuizGrade1.child(currenttname).setValue(score);
                            }
                        }
                        for (String currenttname : Quiz2idsMap.keySet()) {
                            EditText currentEditText = findViewById(Quiz2idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numQuiz2 += score;
                                QuizGrade2.child(currenttname).setValue(score);
                            }
                        }
                        //LongQuiz
                        for (String currenttname : LongQuiz1idsMap.keySet()) {
                            EditText currentEditText = findViewById(LongQuiz1idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numLongQuiz1 += score;
                                LongQuizGrade1.child(currenttname).setValue(score);
                            }
                        }
                        for (String currenttname : LongQuiz2idsMap.keySet()) {
                            EditText currentEditText = findViewById(LongQuiz2idsMap.get(currenttname));
                            String scoreText = currentEditText.getText().toString();

                            if (!scoreText.isEmpty()) {
                                int score = Integer.parseInt(scoreText);
                                numLongQuiz2 += score;
                                LongQuizGrade2.child(currenttname).setValue(score);
                            }
                        }

                        float _percent1 = (float) percent1 / 100;
                        float _percent2 = (float) percent2 / 100;
                        float _percent3 = (float) percent3 / 100;
                        float _percent4 = (float) percent4 / 100;

                        //percent 1 (Recite)
                        float _finalsubscore1 = scoreRecite * _percent1;

                        //percent 2 (Behavior)
                        float _finalsubscore2 = scoreBehavior * _percent2;

                        //percent 3 (Act/Ass/Quiz/LongQuiz)
                        float totalActAssQuizLongQuiz = numAct1 + numAssign1 + numQuiz1 + numLongQuiz1;
                        float overallActAssQuizLongQuiz = numAct2 + numAssign2 + numQuiz2 + numLongQuiz2;
                        float finalsubscore1 = totalActAssQuizLongQuiz / overallActAssQuizLongQuiz;
                        float _finalsubscore3 = finalsubscore1 * 100;
                        _finalsubscore3 *= _percent3;

                        //percent 4 (Prelim/Periodic)
                        float totalPrelimPeriodic = scorePrelim1 + scorePeriodic1;
                        float overallPrelimPeriodic = scorePrelim2 + scorePeriodic2;
                        float finalsubscore2 = totalPrelimPeriodic / overallPrelimPeriodic;
                        float _finalsubscore4 = finalsubscore2 * 100;
                        _finalsubscore4 *= _percent4;

                        //final grade
                        float last = _finalsubscore1 + _finalsubscore2 + _finalsubscore3 + _finalsubscore4;
                        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                        formattedNumber = decimalFormat.format(last);
                        FinalGrade.child("FinalGrade").setValue(formattedNumber);
                        Toast.makeText(StudentGradeActivity.this, "Save Successfully \nGrade : " + last, Toast.LENGTH_SHORT).show();
                        XwX();
                    } else {
                        Toast.makeText(StudentGradeActivity.this, "Double check your percentage", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    //Toast.makeText(StudentGradeActivity.this, "Error : " + e, Toast.LENGTH_SHORT).show();
                    System.out.println(e);

                    //Get Percentage
                    String _Percent1 = Percent1.getText().toString().trim();
                    int percent1 = (_Percent1.isEmpty()) ? 0 : Integer.parseInt(_Percent1);
                    String _Percent2 = Percent2.getText().toString().trim();
                    int percent2 = (_Percent2.isEmpty()) ? 0 : Integer.parseInt(_Percent2);
                    String _Percent3 = Percent3.getText().toString().trim();
                    int percent3 = (_Percent3.isEmpty()) ? 0 : Integer.parseInt(_Percent3);
                    String _Percent4 = Percent4.getText().toString().trim();
                    int percent4 = (_Percent4.isEmpty()) ? 0 : Integer.parseInt(_Percent4);

                    int finalpercent = percent1 + percent2 + percent3 + percent4;
                    int totalpercent = 100;

                    Toast.makeText(StudentGradeActivity.this, "" + finalpercent, Toast.LENGTH_SHORT).show();
                    if (finalpercent == totalpercent) {

                        //Store Percentage
                        PercentValues1.child("Values1").setValue(_Percent1);
                        PercentValues2.child("Values2").setValue(_Percent2);
                        PercentValues3.child("Values3").setValue(_Percent3);
                        PercentValues4.child("Values4").setValue(_Percent4);

                        //Behavior
                        String Behavior = BehaviorGrades.getText().toString().trim();
                        int scoreBehavior = Integer.parseInt(Behavior);
                        BehaviorGrade.child("Behavior").setValue(scoreBehavior);

                        //Recitation
                        String Recite = ReciteGrades.getText().toString().trim();
                        int scoreRecite = Integer.parseInt(Recite);
                        RecitationGrade.child("Recitation").setValue(scoreRecite);

                        //Prelim
                        String Prelim1 = PrelimGrades1.getText().toString().trim();
                        int scorePrelim1 = Integer.parseInt(Prelim1);
                        PrelimGrade1.child("PrelimScore").setValue(scorePrelim1);

                        String Prelim2 = PrelimGrades2.getText().toString().trim();
                        int scorePrelim2 = Integer.parseInt(Prelim2);
                        PrelimGrade2.child("PrelimOverAll").setValue(scorePrelim2);

                        //Periodic
                        String Periodic1 = PeriodicGrades1.getText().toString().trim();
                        int scorePeriodic1 = Integer.parseInt(Periodic1);
                        PeriodicGrade1.child("PeriodicOverAll").setValue(scorePeriodic1);

                        String Periodic2 = PeriodicGrades2.getText().toString().trim();
                        int scorePeriodic2 = Integer.parseInt(Periodic2);
                        PeriodicGrade2.child("PeriodicOverAll").setValue(scorePeriodic2);

                        // Activity
                        for (EditText editText : editTextActivityList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : Act1idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                ActivityGrade1.child(activityName).setValue(text);
                            }
                        }

                        for (EditText editText : editTextActivityList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : Act2idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                ActivityGrade2.child(activityName).setValue(text);
                            }
                        }

                        //Assignment
                        for (EditText editText : editTextAssignList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : Ass1idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                AssignGrade1.child(activityName).setValue(text);
                            }
                        }

                        for (EditText editText : editTextAssignList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : Ass2idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                AssignGrade2.child(activityName).setValue(text);
                            }
                        }

                        //Quiz
                        for (EditText editText : editTextQuizList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : Quiz1idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                QuizGrade1.child(activityName).setValue(text);
                            }
                        }

                        for (EditText editText : editTextQuizList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : Quiz2idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                QuizGrade2.child(activityName).setValue(text);
                            }
                        }

                        //Long Quiz
                        for (EditText editText : editTextLongQuizList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : LongQuiz1idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                LongQuizGrade1.child(activityName).setValue(text);
                            }
                        }

                        for (EditText editText : editTextLongQuizList) {
                            String text = editText.getText().toString().trim();
                            int id = editText.getId();
                            String activityName = "";

                            for (Map.Entry<String, Integer> entry : LongQuiz2idsMap.entrySet()) {
                                if (entry.getValue() == id) {
                                    activityName = entry.getKey();
                                    break;
                                }
                            }

                            if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(activityName)) {
                                LongQuizGrade2.child(activityName).setValue(text);
                            }
                        }
                        float _percent1 = (float) percent1 / 100;
                        float _percent2 = (float) percent2 / 100;
                        float _percent3 = (float) percent3 / 100;
                        float _percent4 = (float) percent4 / 100;

                        //percent 1 (Recite)
                        float _finalsubscore1 = scoreRecite * _percent1;

                        //percent 2 (Behavior)
                        float _finalsubscore2 = scoreBehavior * _percent2;

                        //percent 3 (Act/Ass/Quiz/LongQuiz)
                        float totalActAssQuizLongQuiz = numAct1 + numAssign1 + numQuiz1 + numLongQuiz1;
                        float overallActAssQuizLongQuiz = numAct2 + numAssign2 + numQuiz2 + numLongQuiz2;
                        float finalsubscore1 = totalActAssQuizLongQuiz / overallActAssQuizLongQuiz;
                        float _finalsubscore3 = finalsubscore1 * 100;
                        _finalsubscore3 *= _percent3;
                        //Toast.makeText(StudentGradeActivity.this, "Activity : " + numAct1 + "\nAssignment : " + numAssign1 + "\nQuiz: " + numQuiz1 + "\nLong Quiz : " + numLongQuiz1, Toast.LENGTH_SHORT).show();

                        //percent 4 (Prelim/Periodic)
                        float totalPrelimPeriodic = scorePrelim1 + scorePeriodic1;
                        float overallPrelimPeriodic = scorePrelim2 + scorePeriodic2;
                        float finalsubscore2 = totalPrelimPeriodic / overallPrelimPeriodic;
                        float _finalsubscore4 = finalsubscore2 * 100;
                        _finalsubscore4 *= _percent4;

                        //final grade
                        float last = _finalsubscore1 + _finalsubscore2 + _finalsubscore3 + _finalsubscore4;
                        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                        formattedNumber = decimalFormat.format(last);
                        FinalGrade.child("FinalGrade").setValue(formattedNumber);
                        Toast.makeText(StudentGradeActivity.this, "Save Successfully \nGrade : " + last, Toast.LENGTH_SHORT).show();
                        XwX();
                    }
                }
            }
        });
    }

    private void XwX(){

        Toast.makeText(this, "" + Name, Toast.LENGTH_SHORT).show();
        float e = Float.parseFloat(formattedNumber);

        if (e >= 95.00 && e <= 100){
            String description = "Excellent";
            Scoreboard user = new Scoreboard(Name, formattedNumber, description, studentID);
            Final.setValue(user);
            Grades.child(description).child(studentID).setValue(formattedNumber);
        }
        else if (e >= 90.00 && e <= 94.99){
            String description = "VeryGood";
            Scoreboard user = new Scoreboard(Name, formattedNumber, description, studentID);
            Final.setValue(user);
            Grades.child(description).child(studentID).setValue(formattedNumber);
        }
        else if (e >= 85.00 && e <= 89.99){
            String description = "HighlySatisfactory";
            Scoreboard user = new Scoreboard(Name, formattedNumber, description, studentID);
            Final.setValue(user);
            Grades.child(description).child(studentID).setValue(formattedNumber);
        }
        else if (e >= 80.00 && e <= 85.99){
            String description = "Good";
            Scoreboard user = new Scoreboard(Name, formattedNumber, description, studentID);
            Final.setValue(user);
            Grades.child(description).child(formattedNumber).setValue(formattedNumber);
        }
        else if (e >= 76.00 && e <= 79.99){
            String description = "Satisfactory";
            Scoreboard user = new Scoreboard(Name, formattedNumber, description, studentID);
            Final.setValue(user);
            Grades.child(description).child(studentID).setValue(formattedNumber);
        }
        else if (e >= 75.00 && e <= 75.99){
            String description = "Passing";
            Scoreboard user = new Scoreboard(Name, formattedNumber, description, studentID);
            Final.setValue(user);
            Grades.child(description).child(studentID).setValue(formattedNumber);
        }
        else {
            String description = "Retake";
            Scoreboard user = new Scoreboard(Name, formattedNumber, description, studentID);
            Final.setValue(user);
            Grades.child(description).child(studentID).setValue(formattedNumber);
        }

        GradesHightoLow user = new GradesHightoLow(Subject, Name, formattedNumber,studentID);
        Final.child("Final").setValue(user);

        DatabaseReference Profreference = FirebaseDatabase.getInstance().getReference("AdminAccess").child("ProfessorList");
        DatabaseReference Adminreference = FirebaseDatabase.getInstance().getReference("AdminAccess").child("AdministratorList");

        Profreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if(userProfile != null){
                    ProfStatus = userProfile.Status;
                    NameProf = userProfile.Name;
                    checkUserType();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Adminreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if(userProfile != null){
                    AdminStatus = userProfile.Status;
                    NameAdmin = userProfile.Name;
                    checkUserType();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkUserType() {
        if (ProfStatus != null && ProfStatus.equals("Professor Account")){
            startActivity(new Intent(this, Grades.class));
        } else if (AdminStatus != null && AdminStatus.equals("Administrator Account")){
            startActivity(new Intent(this, SubjectList .class));
        }
    }

}