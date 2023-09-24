package com.example.portalmanagementapp.SubActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.portalmanagementapp.ProfMain;
import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.SignInActivity;
import com.example.portalmanagementapp.UserhelperClass;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class StudentGradeProf extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Side Menu
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;
    private String User_Name;

    //Layout
    private LinearLayout RecitationLayout, ActivityLayout, AssignLayout, QuizLayout, LongQuizLayout, PrelimLayout, PeriodicLayout;
    private Button Activity, Assign, Quiz, LongQuiz, Save, Quarter01, Quarter02, Quarter03, Quarter04;

    //Database path
    private DatabaseReference BehaviorGrade, RecitationGrade, ActivityGrade1, ActivityGrade2, AssignGrade1, AssignGrade2, QuizGrade1, QuizGrade2, LongQuizGrade1, LongQuizGrade2, PrelimGrade1, PrelimGrade2, PeriodicGrade1, PeriodicGrade2, FinalGrade, PercentValues1, PercentValues2, PercentValues3, PercentValues4;

    //student name and lrn
    private TextView NameStudent, LRNStudent;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_grade_prof);

        //Layout
        ActivityLayout = findViewById(R.id.activitylayout);
        RecitationLayout = findViewById(R.id.recitelayout);
        AssignLayout = findViewById(R.id.asslayout);
        QuizLayout = findViewById(R.id.quizlayout);
        LongQuizLayout = findViewById(R.id.longquizlayout);
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
        Subject = intent.getStringExtra("User_Sub");
        Toast.makeText(this, "Click quarter list selection before to input data.", Toast.LENGTH_SHORT).show();

        //Get current time and date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        //Get data on database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess").child("ProfessorList");
        studentdata = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList");

        //Get current user UID
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //Side menu function
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigator_layout);
        toolbar=findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        menu = navigationView.getMenu();
        navigationView.bringToFront();

        Drawable icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_menu_24);
        icon.setTint(Color.WHITE);
        toolbar.setNavigationIcon(icon);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigator_open, R.string.navigator_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.info);

        GetDataOnAdminandStudent();
        QuarterList();
        AddList();
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
                Toast.makeText(getApplicationContext(), "First Quarter", Toast.LENGTH_SHORT).show();

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
                if(Subject.equals("English")){
                    Toast.makeText(getApplicationContext(), "Second Quarter", Toast.LENGTH_SHORT).show();
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
                if(Subject.equals("English")){
                    Toast.makeText(getApplicationContext(), "Third Quarter", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Fourth Quarter", Toast.LENGTH_SHORT).show();
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

        if(ActivityGrade1 != null || AssignGrade1 != null || QuizGrade1 != null || LongQuizGrade1 != null){
            ActivityGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<String> numberList = new ArrayList();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String number = childSnapshot.getKey();
                        numberList.add(number);
                        TotalActivity = numberList.size();
                        AddList();
                        a += a + TotalActivity;
                    }
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
                        TotalAssign = numberList.size();
                        AddList();
                        b += b + TotalAssign;
                    }
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
                        TotalQuiz = numberList.size();
                        AddList();
                        c += c + TotalQuiz;
                    }
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
                        TotalLongQuiz = numberList.size();
                        AddList();
                        d += d + TotalLongQuiz;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else{
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }

        //Create layout with data
        try{
            ActivityGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    ActivityLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ActivityName = snapshot.getKey();
                        ActivityValues1 = snapshot.getValue();
                        getGradeActivity();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            ActivityGrade2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    ActivityLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ActivityValues2 = snapshot.getValue();
                        getGradeActivity();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            AssignGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    AssignLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AssignName = snapshot.getKey();
                        AssignValues1 = snapshot.getValue();
                        getGradeAssign();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            AssignGrade2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    AssignLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AssignValues2 = snapshot.getValue();
                        getGradeAssign();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            QuizGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    QuizLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        QuizName = snapshot.getKey();
                        QuizValues1 = snapshot.getValue();
                        getGradeQuiz();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            QuizGrade2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    QuizLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        QuizValues2 = snapshot.getValue();
                        getGradeQuiz();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            LongQuizGrade1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    LongQuizLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        LongQuizName = snapshot.getKey();
                        LongQuizValues1 = snapshot.getValue();
                        getGradeLongQuiz();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            LongQuizGrade2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear the existing views
                    LongQuizLayout.removeAllViews();
                    //Iterate through the data snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        LongQuizValues2 = snapshot.getValue();
                        getGradeLongQuiz();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            BehaviorGrade.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BehaviorGrades.setText("");
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
                    ReciteGrades.setText("");
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
                    PrelimGrades1.setText("");
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
                    PrelimGrades2.setText("");
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
                    PeriodicGrades1.setText("");
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
                    PeriodicGrades2.setText("");
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
                    Percent1.setText("");
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
                    Percent2.setText("");
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
                    Percent3.setText("");
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
        SaveData();
    }

    private void getGradeActivity(){
        if (ActivityValues1 != null && ActivityValues2 != null) {
            if (ActivityValues1 instanceof String && ActivityValues2 instanceof String ) {
                String scoreString1 = (String) ActivityValues1;
                String scoreString2 = (String) ActivityValues2;
                int score1 = Integer.parseInt(scoreString1);
                int score2 = Integer.parseInt(scoreString2);
                createActivityLayout(ActivityName, score1, score2);
            } else if (ActivityValues1 instanceof Long && ActivityValues2 instanceof Long) {
                Long scoreLong1 = (Long) ActivityValues1;
                Long scoreLong2 = (Long) ActivityValues2;
                int score1 = scoreLong1.intValue();
                int score2 = scoreLong2.intValue();
                createActivityLayout(ActivityName, score1, score2);
            }
        }
    }
    private void getGradeAssign(){
        if (AssignValues1 != null && AssignValues2 != null) {
            if (AssignValues1 instanceof String && AssignValues2 instanceof String ) {
                String scoreString1 = (String) AssignValues1;
                String scoreString2 = (String) AssignValues2;
                int score1 = Integer.parseInt(scoreString1);
                int score2 = Integer.parseInt(scoreString2);
                createAssignLayout(AssignName, score1, score2);
            } else if (AssignValues1 instanceof Long && AssignValues2 instanceof Long) {
                Long scoreLong1 = (Long) AssignValues1;
                Long scoreLong2 = (Long) AssignValues2;
                int score1 = scoreLong1.intValue();
                int score2 = scoreLong2.intValue();
                createAssignLayout(AssignName, score1, score2);
            }
        }
    }
    private void getGradeQuiz(){
        if (QuizValues1 != null && QuizValues2 != null) {
            if (QuizValues1 instanceof String && QuizValues2 instanceof String ) {
                String scoreString1 = (String) QuizValues1;
                String scoreString2 = (String) QuizValues2;
                int score1 = Integer.parseInt(scoreString1);
                int score2 = Integer.parseInt(scoreString2);
                createQuizLayout(QuizName, score1, score2);
            } else if (QuizValues1 instanceof Long && QuizValues2 instanceof Long) {
                Long scoreLong1 = (Long) QuizValues1;
                Long scoreLong2 = (Long) QuizValues2;
                int score1 = scoreLong1.intValue();
                int score2 = scoreLong2.intValue();
                createQuizLayout(QuizName, score1, score2);
            }
        }
    }
    private void getGradeLongQuiz(){
        if (LongQuizValues1 != null && LongQuizValues2 != null) {
            if (LongQuizValues1 instanceof String && LongQuizValues2 instanceof String ) {
                String scoreString1 = (String) LongQuizValues1;
                String scoreString2 = (String) LongQuizValues2;
                int score1 = Integer.parseInt(scoreString1);
                int score2 = Integer.parseInt(scoreString2);
                createLongQuizLayout(LongQuizName, score1, score2);
            } else if (LongQuizValues1 instanceof Long && LongQuizValues2 instanceof Long) {
                Long scoreLong1 = (Long) LongQuizValues1;
                Long scoreLong2 = (Long) LongQuizValues2;
                int score1 = scoreLong1.intValue();
                int score2 = scoreLong2.intValue();
                createLongQuizLayout(LongQuizName, score1, score2);
            }
        }
    }

    private void createActivityLayout(String tname, int score1, int score2) {

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

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
        editText1.setText(String.valueOf(score1));
        editText1.setPadding(40,40,40,40);
        int id1 = View.generateViewId();
        editTextActivityList.add(editText1);
        editText1.setId(id1);
        Act1idsMap.put(tname, id1);

        EditText editText2 = new EditText(getApplicationContext());
        editText2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setPadding(40,40,40,40);
        int id2 = View.generateViewId();
        editTextActivityList.add(editText2);
        editText2.setId(id2);
        Act2idsMap.put(tname, id2);

        ActivityLayout.addView(textView);
        ActivityLayout.addView(linearLayout);

        linearLayout.addView(editText1);
        linearLayout.addView(editText2);
    }
    private void createAssignLayout(String tname, int score1, int score2) {

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

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
        editText1.setText(String.valueOf(score1));
        editText1.setPadding(40,40,40,40);
        int id1 = View.generateViewId();
        editTextAssignList.add(editText1);
        editText1.setId(id1);
        Ass1idsMap.put(tname, id1);

        EditText editText2 = new EditText(getApplicationContext());
        editText2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setPadding(40,40,40,40);
        int id2 = View.generateViewId();
        editTextAssignList.add(editText2);
        editText2.setId(id2);
        Ass2idsMap.put(tname, id2);

        AssignLayout.addView(textView);
        AssignLayout.addView(linearLayout);

        linearLayout.addView(editText1);
        linearLayout.addView(editText2);
    }
    private void createQuizLayout(String tname, int score1, int score2) {

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

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
        editText1.setText(String.valueOf(score1));
        editText1.setPadding(40,40,40,40);
        int id1 = View.generateViewId();
        editTextQuizList.add(editText1);
        editText1.setId(id1);
        Quiz1idsMap.put(tname, id1);

        EditText editText2 = new EditText(getApplicationContext());
        editText2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setPadding(40,40,40,40);
        int id2 = View.generateViewId();
        editTextQuizList.add(editText2);
        editText2.setId(id2);
        Quiz2idsMap.put(tname, id2);

        QuizLayout.addView(textView);
        QuizLayout.addView(linearLayout);

        linearLayout.addView(editText1);
        linearLayout.addView(editText2);
    }
    private void createLongQuizLayout(String tname, int score1, int score2) {

        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 10, 0, 0);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(tname);

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
        editText1.setText(String.valueOf(score1));
        editText1.setPadding(40,40,40,40);
        int id1 = View.generateViewId();
        editTextLongQuizList.add(editText1);
        editText1.setId(id1);
        LongQuiz1idsMap.put(tname, id1);

        EditText editText2 = new EditText(getApplicationContext());
        editText2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editText2.setBackgroundResource(R.drawable.text_border);
        editText2.setText(String.valueOf(score2));
        editText2.setPadding(40,40,40,40);
        int id2 = View.generateViewId();
        editTextLongQuizList.add(editText2);
        editText2.setId(id2);
        LongQuiz2idsMap.put(tname, id2);

        LongQuizLayout.addView(textView);
        LongQuizLayout.addView(linearLayout);

        linearLayout.addView(editText1);
        linearLayout.addView(editText2);
    }

    private void AddList(){

        Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a++;
                //Create TextView
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("Activity "  + a);

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
                editText1.setHint("Enter Score");
                editText1.setPadding(40,40,40,40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextActivityList.add(editText1);
                editText1.setId(id1);
                String actname1 = "Activity "  + a;
                Act1idsMap.put(actname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("OverAll total");
                editText2.setPadding(40,40,40,40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextActivityList.add(editText2);
                editText2.setId(id2);
                String actname2 = "Activity "  + a;
                Act2idsMap.put(actname2, id2);


                ActivityLayout.addView(textView);
                ActivityLayout.addView(linearLayout);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);
            }
        });

        Assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b++;
                //Create TextView
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("Assignment "  + b);

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
                editText1.setHint("Enter Score");
                editText1.setPadding(40,40,40,40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextAssignList.add(editText1);
                editText1.setId(id1);
                String assname1 = "Assignment "  + b;
                Ass1idsMap.put(assname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("OverAll total");
                editText2.setPadding(40,40,40,40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextAssignList.add(editText2);
                editText2.setId(id2);
                String assname2 = "Assignment "  + b;
                Ass2idsMap.put(assname2, id2);


                AssignLayout.addView(textView);
                AssignLayout.addView(linearLayout);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);
            }
        });
        Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c++;
                //Create TextView
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("Quiz "  + c);

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
                editText1.setHint("Enter Score");
                editText1.setPadding(40,40,40,40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextQuizList.add(editText1);
                editText1.setId(id1);
                String quizname1 = "Quiz "  + c;
                Quiz1idsMap.put(quizname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("OverAll total");
                editText2.setPadding(40,40,40,40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextQuizList.add(editText2);
                editText2.setId(id2);
                String quizname2 = "Quiz "  + c;
                Quiz2idsMap.put(quizname2, id2);


                QuizLayout.addView(textView);
                QuizLayout.addView(linearLayout);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);
            }
        });
        LongQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d++;
                //Create TextView
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textViewParams.setMargins(0, 10, 0, 0);
                textView.setLayoutParams(textViewParams);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("LongQuiz "  + d);

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
                editText1.setHint("Enter Score");
                editText1.setPadding(40,40,40,40);
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id1 = View.generateViewId();
                editTextLongQuizList.add(editText1);
                editText1.setId(id1);
                String longquizname1 = "LongQuiz "  + d;
                LongQuiz1idsMap.put(longquizname1, id1);

                EditText editText2 = new EditText(getApplicationContext());
                editText2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText2.setBackgroundResource(R.drawable.text_border);
                editText2.setHint("OverAll total");
                editText2.setPadding(40,40,40,40);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                int id2 = View.generateViewId();
                editTextLongQuizList.add(editText2);
                editText2.setId(id2);
                String longquizname2 = "LongQuiz "  + d;
                LongQuiz2idsMap.put(longquizname2, id2);


                LongQuizLayout.addView(textView);
                LongQuizLayout.addView(linearLayout);

                linearLayout.addView(editText1);
                linearLayout.addView(editText2);
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
                    int percent1 = Integer.parseInt(_Percent1);
                    String _Percent2 = Percent2.getText().toString().trim();
                    int percent2 = Integer.parseInt(_Percent2);
                    String _Percent3 = Percent3.getText().toString().trim();
                    int percent3 = Integer.parseInt(_Percent3);
                    String _Percent4 = Percent4.getText().toString().trim();
                    int percent4 = Integer.parseInt(_Percent4);

                    int finalpercent = percent1 + percent2 + percent3 + percent4;
                    int totalpercent = 100;

                    if (finalpercent == totalpercent) {

                        //Store Percentage
                        PercentValues1.child("PercentValues1").setValue(_Percent1);
                        PercentValues2.child("PercentValues2").setValue(_Percent2);
                        PercentValues3.child("PercentValues3").setValue(_Percent3);
                        PercentValues4.child("PercentValues4").setValue(_Percent4);

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
                        FinalGrade.child("FinalGrade").setValue(last);
                        Toast.makeText(getApplicationContext(), "Save Successfully \nGrade : " + last, Toast.LENGTH_SHORT).show();




                    } else {
                        Toast.makeText(getApplicationContext(), "Double check your percentage", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:

                startActivity(new Intent(this, ProfMain.class));
                break;

            case R.id.info:

                startActivity(new Intent(this, StudentData2.class));
                break;
            case R.id.comp:

                startActivity(new Intent(this, GuidanceCouncil2.class));
                break;

            case R.id.logout:
                logout(this);
                break;
        }
        return false;
    }

    private void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                String Subject = "Sign Out";
                String Compose = User_Name;

                NotifSave save = new NotifSave(Subject, Compose, Time);
                LogHistory.child(Time).setValue(save);

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}