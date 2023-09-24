package com.example.portalmanagementapp.SubActivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OverAllhandled extends AppCompatActivity {

    private WebView chartWebView;
    private String a = "Excellent" , b = "Very Good", c = "Highly Satisfactory", d = "Good", e = "Satisfactory", f = "Passing", g = "Retake";
    private  Float overallstudent, studentcount, overallstudentEnglish, overallstudentMath, overallstudentScience, overallstudentFilipino, overallstudentValues, overallstudentHistory,overallstudentMAPEH;
    private  int studentcountEnglishExcellent = 0, studentcountMathExcellent = 0, studentcountScienceExcellent = 0, studentcountFilipinoExcellent = 0, studentcountValuesExcellent = 0, studentcountHistoryExcellent = 0, studentcountMAPEHExcellent = 0;
    private  int studentcountEnglishVeryGood = 0, studentcountMathVeryGood = 0, studentcountScienceVeryGood = 0, studentcountFilipinoVeryGood = 0, studentcountValuesVeryGood = 0, studentcountHistoryVeryGood = 0, studentcountMAPEHVeryGood = 0;
    private  int studentcountEnglishHighlySatisfactory = 0, studentcountMathHighlySatisfactory = 0, studentcountScienceHighlySatisfactory = 0, studentcountFilipinoHighlySatisfactory = 0, studentcountValuesHighlySatisfactory = 0, studentcountHistoryHighlySatisfactory = 0, studentcountMAPEHHighlySatisfactory = 0;
    private  int studentcountEnglishGood = 0, studentcountMathGood = 0, studentcountScienceGood = 0, studentcountFilipinoGood = 0, studentcountValuesGood = 0, studentcountHistoryGood = 0, studentcountMAPEHGood = 0;
    private  int studentcountEnglishSatisfactory = 0, studentcountMathSatisfactory = 0, studentcountScienceSatisfactory = 0, studentcountFilipinoSatisfactory = 0, studentcountValuesSatisfactory = 0, studentcountHistorySatisfactory = 0, studentcountMAPEHSatisfactory = 0;
    private  int studentcountEnglishPassing = 0, studentcountMathPassing = 0, studentcountSciencePassing = 0, studentcountFilipinoPassing = 0, studentcountValuesPassing = 0, studentcountHistoryPassing = 0, studentcountMAPEHPassing = 0;
    private  int studentcountEnglishRetake = 0, studentcountMathRetake = 0, studentcountScienceRetake = 0, studentcountFilipinoRetake = 0, studentcountValuesRetake = 0, studentcountHistoryRetake = 0, studentcountMAPEHRetake = 0;

    private  float EnglishExcellent = 0, MathExcellent = 0, ScienceExcellent = 0, FilipinoExcellent = 0, ValuesExcellent = 0, HistoryExcellent = 0, MAPEHExcellent = 0;
    private  float EnglishVeryGood = 0, MathVeryGood = 0, ScienceVeryGood = 0, FilipinoVeryGood = 0, ValuesVeryGood = 0, HistoryVeryGood = 0, MAPEHVeryGood = 0;
    private  float EnglishHighlySatisfactory = 0, MathHighlySatisfactory = 0, ScienceHighlySatisfactory = 0, FilipinoHighlySatisfactory = 0, ValuesHighlySatisfactory = 0, HistoryHighlySatisfactory = 0, MAPEHHighlySatisfactory = 0;
    private  float EnglishGood = 0, MathGood = 0, ScienceGood = 0, FilipinoGood = 0, ValuesGood = 0, HistoryGood = 0, MAPEHGood = 0;
    private  float EnglishSatisfactory = 0, MathSatisfactory = 0, ScienceSatisfactory = 0, FilipinoSatisfactory = 0, ValuesSatisfactory = 0, HistorySatisfactory = 0, MAPEHSatisfactory = 0;
    private  float EnglishPassing = 0, MathPassing = 0, SciencePassing = 0, FilipinoPassing = 0, ValuesPassing = 0, HistoryPassing = 0, MAPEHPassing = 0;
    private  float EnglishRetake = 0, MathRetake = 0, ScienceRetake = 0, FilipinoRetake = 0, ValuesRetake = 0, HistoryRetake = 0, MAPEHRetake = 0;

    private Button Quarter01, Quarter02, Quarter03, Quarter04;
    private DatabaseReference English, Math, Science, Filipino, Values, History, MAPEH, ref;

    private Spinner spinner;
    private String selectedItem, Subject;

    private TextView Excellent, VeryGood, HighlySatisfactory, Good, Satisfactory, Passing, Retake;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_allhandled);

        spinner = findViewById(R.id.my_spinner);

        Quarter01 = findViewById(R.id.first);
        Quarter02 = findViewById(R.id.second);
        Quarter03 = findViewById(R.id.third);
        Quarter04 = findViewById(R.id.fourth);

        Excellent = findViewById(R.id.ExcellentID);
        VeryGood = findViewById(R.id.VeryGoodID);
        HighlySatisfactory = findViewById(R.id.HighlySatisfactoryID);
        Good = findViewById(R.id.GoodID);
        Satisfactory = findViewById(R.id.SatisfactoryID);
        Passing = findViewById(R.id.PassingID);
        Retake = findViewById(R.id.RetakeID);

        chartWebView = findViewById(R.id.chartWebView);
        WebSettings webSettings = chartWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        chartWebView.setWebViewClient(new WebViewClient());

        String[] subjectArray = getResources().getStringArray(R.array.subject);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sub, subjectArray);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                Subject = selectedItem;

                Toast.makeText(OverAllhandled.this, "" + Subject, Toast.LENGTH_SHORT).show();
                CheckPath();
                updateChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    private void CheckPath() {
        Quarter01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));

                English = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("FirstQuarter");
                Math = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("FirstQuarter");
                Science = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("FirstQuarter");
                Filipino = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("FirstQuarter");
                Values = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("FirstQuarter");
                History = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("FirstQuarter");
                MAPEH = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("FirstQuarter");

                if (Subject.equals("English")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("English").child("FirstQuarter");
                    English();
                    list();
                }
                else if (Subject.equals("Math")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Math").child("FirstQuarter");
                    Math();
                    list();
                }
                else if (Subject.equals("Science")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Science").child("FirstQuarter");
                    Science();
                    list();
                }
                else if (Subject.equals("Filipino")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Filipino").child("FirstQuarter");
                    Filipino();
                    list();
                }
                else if (Subject.equals("Values")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Values").child("FirstQuarter");
                    Values();
                    list();
                }
                else if (Subject.equals("History")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("History").child("FirstQuarter");
                    History();
                    list();
                }
                else if (Subject.equals("MAPEH")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("MAPEH").child("FirstQuarter");
                    MAPEH();
                    list();
                }
            }
        });
        Quarter02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));


                English = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("SecondQuarter");
                Math = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("SecondQuarter");
                Science = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("SecondQuarter");
                Filipino = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("SecondQuarter");
                Values = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("SecondQuarter");
                History = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("SecondQuarter");
                MAPEH = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("SecondQuarter");

                if (Subject.equals("English")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("English").child("SecondQuarter");
                    English();
                    list();
                }
                else if (Subject.equals("Math")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Math").child("SecondQuarter");
                    Math();
                    list();
                }
                else if (Subject.equals("Science")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Science").child("SecondQuarter");
                    Science();
                    list();
                }
                else if (Subject.equals("Filipino")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Filipino").child("SecondQuarter");
                    Filipino();
                    list();
                }
                else if (Subject.equals("Values")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Values").child("SecondQuarter");
                    Values();
                    list();
                }
                else if (Subject.equals("History")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("History").child("SecondQuarter");
                    History();
                    list();
                }
                else if (Subject.equals("MAPEH")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("MAPEH").child("SecondQuarter");
                    MAPEH();
                    list();
                }
            }
        });
        Quarter03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));


                English = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("ThirdQuarter");
                Math = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("ThirdQuarter");
                Science = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("ThirdQuarter");
                Filipino = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("ThirdQuarter");
                Values = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("ThirdQuarter");
                History = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("ThirdQuarter");
                MAPEH = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("ThirdQuarter");

                if (Subject.equals("English")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("English").child("ThirdQuarter");
                    English();
                    list();
                }
                else if (Subject.equals("Math")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Math").child("ThirdQuarter");
                    Math();
                    list();
                }
                else if (Subject.equals("Science")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Science").child("ThirdQuarter");
                    Science();
                    list();
                }
                else if (Subject.equals("Filipino")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Filipino").child("ThirdQuarter");
                    Filipino();
                    list();
                }
                else if (Subject.equals("Values")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Values").child("ThirdQuarter");
                    Values();
                    list();
                }
                else if (Subject.equals("History")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("History").child("ThirdQuarter");
                    History();
                    list();
                }
                else if (Subject.equals("MAPEH")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("MAPEH").child("ThirdQuarter");
                    MAPEH();
                    list();
                }
            }
        });
        Quarter04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));


                English = FirebaseDatabase.getInstance().getReference("HightoLow").child("English").child("FourthQuarter");
                Math = FirebaseDatabase.getInstance().getReference("HightoLow").child("Math").child("FourthQuarter");
                Science = FirebaseDatabase.getInstance().getReference("HightoLow").child("Science").child("FourthQuarter");
                Filipino = FirebaseDatabase.getInstance().getReference("HightoLow").child("Filipino").child("FourthQuarter");
                Values = FirebaseDatabase.getInstance().getReference("HightoLow").child("Values").child("FourthQuarter");
                History = FirebaseDatabase.getInstance().getReference("HightoLow").child("History").child("FourthQuarter");
                MAPEH = FirebaseDatabase.getInstance().getReference("HightoLow").child("MAPEH").child("FourthQuarter");



                if (Subject.equals("English")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("English").child("FourthQuarter");
                    English();
                    list();
                }
                else if (Subject.equals("Math")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Math").child("FourthQuarter");
                    Math();
                    list();
                }
                else if (Subject.equals("Science")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Science").child("FourthQuarter");
                    Science();
                    list();
                }
                else if (Subject.equals("Filipino")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Filipino").child("FourthQuarter");
                    Filipino();
                    list();
                }
                else if (Subject.equals("Values")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("Values").child("FourthQuarter");
                    Values();
                    list();
                }
                else if (Subject.equals("History")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("History").child("FourthQuarter");
                    History();
                    list();
                }
                else if (Subject.equals("MAPEH")){
                    ref = FirebaseDatabase.getInstance().getReference("Scoreboard").child("MAPEH").child("FourthQuarter");
                    MAPEH();
                    list();
                }

            }
        });
    }

    private void English() {

        Excellent.setText("0");
        VeryGood.setText("0");
        HighlySatisfactory.setText("0");
        Good.setText("0");
        Satisfactory.setText("0");
        Passing.setText("0");
        Retake.setText("0");

        English.child("Excellent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountEnglishExcellent = numberList.size();

                if (studentcountEnglishExcellent != 0) {
                    Excellent.setText(String.valueOf(studentcountEnglishExcellent));
                }

                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        English.child("VeryGood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountEnglishVeryGood = numberList.size();
                if (studentcountEnglishVeryGood != 0){
                    VeryGood.setText(String.valueOf(studentcountEnglishVeryGood));
                }

                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        English.child("HighlySatisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountEnglishHighlySatisfactory = numberList.size();
                if (studentcountEnglishHighlySatisfactory != 0){
                    HighlySatisfactory.setText(String.valueOf(studentcountEnglishHighlySatisfactory));
                }

                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        English.child("Good").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountEnglishGood = numberList.size();
                if(studentcountEnglishGood != 0){
                    Good.setText(String.valueOf(studentcountEnglishGood));
                }

                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        English.child("Satisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountEnglishSatisfactory = numberList.size();
                if(studentcountEnglishSatisfactory != 0){
                    Satisfactory.setText(String.valueOf(studentcountEnglishSatisfactory));
                }
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        English.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountEnglishPassing = numberList.size();
                if(studentcountEnglishPassing != 0){
                    Passing.setText(String.valueOf(studentcountEnglishPassing));
                }

                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        English.child("Retake").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountEnglishRetake = numberList.size();
                if(studentcountEnglishRetake != 0){
                    Retake.setText(String.valueOf(studentcountEnglishRetake));
                }

                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
    private void Math(){

        Excellent.setText("0");
        VeryGood.setText("0");
        HighlySatisfactory.setText("0");
        Good.setText("0");
        Satisfactory.setText("0");
        Passing.setText("0");
        Retake.setText("0");

        Math.child("Excellent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMathExcellent = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Math.child("VeryGood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMathVeryGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Math.child("HighlySatisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMathHighlySatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Math.child("Good").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMathGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Math.child("Satisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMathSatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Math.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMathPassing = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Math.child("Retake").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMathRetake = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }
    private void Science(){

        Excellent.setText("0");
        VeryGood.setText("0");
        HighlySatisfactory.setText("0");
        Good.setText("0");
        Satisfactory.setText("0");
        Passing.setText("0");
        Retake.setText("0");

        Science.child("Excellent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountScienceExcellent = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Science.child("VeryGood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountScienceVeryGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Science.child("HighlySatisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountScienceHighlySatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Science.child("Good").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountScienceGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Science.child("Satisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountScienceSatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Science.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountSciencePassing = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Science.child("Retake").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountScienceRetake = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void Filipino(){

        Excellent.setText("0");
        VeryGood.setText("0");
        HighlySatisfactory.setText("0");
        Good.setText("0");
        Satisfactory.setText("0");
        Passing.setText("0");
        Retake.setText("0");

        Filipino.child("Excellent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountFilipinoExcellent = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Filipino.child("VeryGood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountFilipinoVeryGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Filipino.child("HighlySatisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountFilipinoHighlySatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Filipino.child("Good").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountFilipinoGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Filipino.child("Satisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountFilipinoSatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Filipino.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountFilipinoPassing = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Filipino.child("Retake").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountFilipinoRetake = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void Values(){

        Excellent.setText("0");
        VeryGood.setText("0");
        HighlySatisfactory.setText("0");
        Good.setText("0");
        Satisfactory.setText("0");
        Passing.setText("0");
        Retake.setText("0");

        Values.child("Excellent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountValuesExcellent = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Values.child("VeryGood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountValuesVeryGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        Values.child("HighlySatisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountValuesHighlySatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Values.child("Good").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountValuesGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Values.child("Satisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountValuesSatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Values.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountValuesPassing = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Values.child("Retake").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountValuesRetake = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void History(){

        Excellent.setText("0");
        VeryGood.setText("0");
        HighlySatisfactory.setText("0");
        Good.setText("0");
        Satisfactory.setText("0");
        Passing.setText("0");
        Retake.setText("0");

        History.child("Excellent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountHistoryExcellent = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        History.child("VeryGood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountHistoryVeryGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        History.child("HighlySatisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountHistoryHighlySatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        History.child("Good").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountHistoryGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        History.child("Satisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountHistorySatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        History.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountHistoryPassing = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        History.child("Retake").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountHistoryRetake = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }
    private void MAPEH(){

        Excellent.setText("0");
        VeryGood.setText("0");
        HighlySatisfactory.setText("0");
        Good.setText("0");
        Satisfactory.setText("0");
        Passing.setText("0");
        Retake.setText("0");

        MAPEH.child("Excellent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMAPEHExcellent = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        MAPEH.child("VeryGood").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMAPEHVeryGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        MAPEH.child("HighlySatisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMAPEHHighlySatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        MAPEH.child("Good").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMAPEHGood = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        MAPEH.child("Satisfactory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMAPEHSatisfactory = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        MAPEH.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMAPEHPassing = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        MAPEH.child("Passing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> numberList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String number = childSnapshot.getKey();
                    numberList.add(number);
                }
                studentcountMAPEHPassing = numberList.size();
                ComputeStudentCount();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void ComputeStudentCount(){

        overallstudentEnglish = (float) studentcountEnglishExcellent + (float) studentcountEnglishVeryGood + (float) studentcountEnglishHighlySatisfactory + (float) studentcountEnglishGood + (float) studentcountEnglishSatisfactory + (float) studentcountEnglishPassing + (float) studentcountEnglishRetake;
        overallstudentMath = (float) studentcountMathExcellent + (float) studentcountMathVeryGood + (float) studentcountMathHighlySatisfactory + (float) studentcountMathGood + (float) studentcountMathSatisfactory + (float) studentcountMathPassing + (float) studentcountMathRetake;
        overallstudentScience = (float) studentcountScienceExcellent + (float) studentcountScienceVeryGood + (float) studentcountScienceHighlySatisfactory + (float) studentcountScienceGood + (float) studentcountScienceSatisfactory + (float) studentcountSciencePassing + (float) studentcountScienceRetake;
        overallstudentFilipino = (float) studentcountFilipinoExcellent + (float) studentcountFilipinoVeryGood + (float) studentcountFilipinoHighlySatisfactory + (float) studentcountFilipinoGood + (float) studentcountFilipinoSatisfactory + (float) studentcountFilipinoPassing + (float) studentcountFilipinoRetake;
        overallstudentValues = (float) studentcountValuesExcellent + (float) studentcountValuesVeryGood + (float) studentcountValuesHighlySatisfactory + (float) studentcountValuesGood + (float) studentcountValuesSatisfactory + (float) studentcountValuesPassing + (float) studentcountValuesRetake;
        overallstudentHistory = (float) studentcountHistoryExcellent + (float) studentcountHistoryVeryGood + (float) studentcountHistoryHighlySatisfactory + (float) studentcountHistoryGood + (float) studentcountHistorySatisfactory + (float) studentcountHistoryPassing + (float) studentcountHistoryRetake;
        overallstudentMAPEH = (float) studentcountMAPEHExcellent + (float) studentcountMAPEHVeryGood + (float) studentcountMAPEHHighlySatisfactory + (float) studentcountMAPEHGood + (float) studentcountMAPEHSatisfactory + (float) studentcountMAPEHPassing + (float) studentcountMAPEHRetake;

        //English
        EnglishExcellent = (studentcountEnglishExcellent / overallstudentEnglish) * 100;
        EnglishVeryGood = (studentcountEnglishVeryGood / overallstudentEnglish) * 100;
        EnglishHighlySatisfactory = (studentcountEnglishHighlySatisfactory / overallstudentEnglish) * 100;
        EnglishGood = (studentcountEnglishGood / overallstudentEnglish) * 100;
        EnglishSatisfactory = (studentcountEnglishSatisfactory / overallstudentEnglish) * 100;
        EnglishPassing = (studentcountEnglishPassing / overallstudentEnglish) * 100;
        EnglishRetake = (studentcountEnglishRetake / overallstudentEnglish) * 100;

        //Math
        MathExcellent = (studentcountMathExcellent / overallstudentMath) * 100;
        MathVeryGood = (studentcountMathVeryGood / overallstudentMath) * 100;
        MathHighlySatisfactory = (studentcountMathHighlySatisfactory / overallstudentMath) * 100;
        MathGood = (studentcountMathGood / overallstudentMath) * 100;
        MathSatisfactory = (studentcountMathSatisfactory / overallstudentMath) * 100;
        MathPassing = (studentcountMathPassing / overallstudentMath) * 100;
        MathRetake = (studentcountMathRetake / overallstudentMath) * 100;

        //Science
        ScienceExcellent = (studentcountScienceExcellent / overallstudentScience) * 100;
        ScienceVeryGood = (studentcountScienceVeryGood / overallstudentScience) * 100;
        ScienceHighlySatisfactory = (studentcountScienceHighlySatisfactory / overallstudentScience) * 100;
        ScienceGood = (studentcountScienceGood / overallstudentScience) * 100;
        ScienceSatisfactory = (studentcountScienceSatisfactory / overallstudentScience) * 100;
        SciencePassing = (studentcountSciencePassing / overallstudentScience) * 100;
        ScienceRetake = (studentcountScienceRetake / overallstudentScience) * 100;

        //Filipino
        FilipinoExcellent = (studentcountFilipinoExcellent / overallstudentFilipino) * 100;
        FilipinoVeryGood = (studentcountFilipinoVeryGood / overallstudentFilipino) * 100;
        FilipinoHighlySatisfactory = (studentcountFilipinoHighlySatisfactory / overallstudentFilipino) * 100;
        FilipinoGood = (studentcountFilipinoGood / overallstudentFilipino) * 100;
        FilipinoSatisfactory = (studentcountFilipinoSatisfactory / overallstudentFilipino) * 100;
        FilipinoPassing = (studentcountFilipinoPassing / overallstudentFilipino) * 100;
        FilipinoRetake = (studentcountFilipinoRetake / overallstudentFilipino) * 100;

        //Values
        ValuesExcellent = (studentcountValuesExcellent / overallstudentValues) * 100;
        ValuesVeryGood = (studentcountValuesVeryGood / overallstudentValues) * 100;
        ValuesHighlySatisfactory = (studentcountValuesHighlySatisfactory / overallstudentValues) * 100;
        ValuesGood = (studentcountValuesGood / overallstudentValues) * 100;
        ValuesSatisfactory = (studentcountValuesSatisfactory / overallstudentValues) * 100;
        ValuesPassing = (studentcountValuesPassing / overallstudentValues) * 100;
        ValuesRetake = (studentcountValuesRetake / overallstudentValues) * 100;

        //History
        HistoryExcellent = (studentcountHistoryExcellent / overallstudentHistory) * 100;
        HistoryVeryGood = (studentcountHistoryVeryGood / overallstudentHistory) * 100;
        HistoryHighlySatisfactory = (studentcountHistoryHighlySatisfactory / overallstudentHistory) * 100;
        HistoryGood = (studentcountHistoryGood / overallstudentHistory) * 100;
        HistorySatisfactory = (studentcountHistorySatisfactory / overallstudentHistory) * 100;
        HistoryPassing = (studentcountHistoryPassing / overallstudentHistory) * 100;
        HistoryRetake = (studentcountHistoryRetake / overallstudentHistory) * 100;

        //MAPEH
        MAPEHExcellent = (studentcountMAPEHExcellent / overallstudentMAPEH) * 100;
        MAPEHVeryGood = (studentcountMAPEHVeryGood / overallstudentMAPEH) * 100;
        MAPEHHighlySatisfactory = (studentcountMAPEHHighlySatisfactory / overallstudentMAPEH) * 100;
        MAPEHGood = (studentcountMAPEHGood / overallstudentMAPEH) * 100;
        MAPEHSatisfactory = (studentcountMAPEHSatisfactory / overallstudentMAPEH) * 100;
        MAPEHPassing = (studentcountMAPEHPassing / overallstudentMAPEH) * 100;
        MAPEHRetake = (studentcountMAPEHRetake / overallstudentMAPEH) * 100;

        updateChart();

    }

    private void updateChart() {

        int aa = 15, bb = 10, cc = 13, dd = 7, ee = 11, ff = 14, gg = 30;

        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append("['Category', 'Value'],\n");
        /**
        dataBuilder.append("['").append(a).append("', ").append(EnglishExcellent).append("],\n");
        dataBuilder.append("['").append(b).append("', ").append(EnglishVeryGood).append("],\n");
        dataBuilder.append("['").append(c).append("', ").append(EnglishHighlySatisfactory).append("],\n");
        dataBuilder.append("['").append(d).append("', ").append(EnglishGood).append("],\n");
        dataBuilder.append("['").append(e).append("', ").append(EnglishSatisfactory).append("],\n");
        dataBuilder.append("['").append(f).append("', ").append(EnglishPassing).append("],\n");
        dataBuilder.append("['").append(g).append("', ").append(EnglishRetake).append("],\n");
**/
        if (Subject.equals("English")){
            dataBuilder.append("['Excellent', ").append(EnglishExcellent).append("],\n");
            dataBuilder.append("['Very Good', ").append(EnglishVeryGood).append("],\n");
            dataBuilder.append("['Highly Satisfactory', ").append(EnglishHighlySatisfactory).append("],\n");
            dataBuilder.append("['Good', ").append(EnglishGood).append("],\n");
            dataBuilder.append("['Satisfactory', ").append(EnglishSatisfactory).append("],\n");
            dataBuilder.append("['Passing', ").append(EnglishPassing).append("],\n");
            dataBuilder.append("['Retake', ").append(EnglishRetake).append("],\n");
        }
        else if (Subject.equals("Math")){
            dataBuilder.append("['Excellent', ").append(MathExcellent).append("],\n");
            dataBuilder.append("['Very Good', ").append(MathVeryGood).append("],\n");
            dataBuilder.append("['Highly Satisfactory', ").append(MathHighlySatisfactory).append("],\n");
            dataBuilder.append("['Good', ").append(MathGood).append("],\n");
            dataBuilder.append("['Satisfactory', ").append(MathSatisfactory).append("],\n");
            dataBuilder.append("['Passing', ").append(MathPassing).append("],\n");
            dataBuilder.append("['Retake', ").append(MathRetake).append("],\n");
        }
        else if (Subject.equals("Science")){
            dataBuilder.append("['Excellent', ").append(ScienceExcellent).append("],\n");
            dataBuilder.append("['Very Good', ").append(ScienceVeryGood).append("],\n");
            dataBuilder.append("['Highly Satisfactory', ").append(ScienceHighlySatisfactory).append("],\n");
            dataBuilder.append("['Good', ").append(ScienceGood).append("],\n");
            dataBuilder.append("['Satisfactory', ").append(ScienceSatisfactory).append("],\n");
            dataBuilder.append("['Passing', ").append(SciencePassing).append("],\n");
            dataBuilder.append("['Retake', ").append(ScienceRetake).append("],\n");
        }
        else if (Subject.equals("Filipino")){
            dataBuilder.append("['Excellent', ").append(FilipinoExcellent).append("],\n");
            dataBuilder.append("['Very Good', ").append(FilipinoVeryGood).append("],\n");
            dataBuilder.append("['Highly Satisfactory', ").append(FilipinoHighlySatisfactory).append("],\n");
            dataBuilder.append("['Good', ").append(FilipinoGood).append("],\n");
            dataBuilder.append("['Satisfactory', ").append(FilipinoSatisfactory).append("],\n");
            dataBuilder.append("['Passing', ").append(FilipinoPassing).append("],\n");
            dataBuilder.append("['Retake', ").append(FilipinoRetake).append("],\n");
        }
        else if (Subject.equals("Values")){
            dataBuilder.append("['Excellent', ").append(ValuesExcellent).append("],\n");
            dataBuilder.append("['Very Good', ").append(ValuesVeryGood).append("],\n");
            dataBuilder.append("['Highly Satisfactory', ").append(ValuesHighlySatisfactory).append("],\n");
            dataBuilder.append("['Good', ").append(ValuesGood).append("],\n");
            dataBuilder.append("['Satisfactory', ").append(ValuesSatisfactory).append("],\n");
            dataBuilder.append("['Passing', ").append(ValuesPassing).append("],\n");
            dataBuilder.append("['Retake', ").append(ValuesRetake).append("],\n");
        }
        else if (Subject.equals("History")){
            dataBuilder.append("['Excellent', ").append(HistoryExcellent).append("],\n");
            dataBuilder.append("['Very Good', ").append(HistoryVeryGood).append("],\n");
            dataBuilder.append("['Highly Satisfactory', ").append(HistoryHighlySatisfactory).append("],\n");
            dataBuilder.append("['Good', ").append(HistoryGood).append("],\n");
            dataBuilder.append("['Satisfactory', ").append(HistorySatisfactory).append("],\n");
            dataBuilder.append("['Passing', ").append(HistoryPassing).append("],\n");
            dataBuilder.append("['Retake', ").append(HistoryRetake).append("],\n");
        }
        else if (Subject.equals("MAPEH")){
            dataBuilder.append("['Excellent', ").append(MAPEHExcellent).append("],\n");
            dataBuilder.append("['Very Good', ").append(MAPEHVeryGood).append("],\n");
            dataBuilder.append("['Highly Satisfactory', ").append(MAPEHHighlySatisfactory).append("],\n");
            dataBuilder.append("['Good', ").append(MAPEHGood).append("],\n");
            dataBuilder.append("['Satisfactory', ").append(MAPEHSatisfactory).append("],\n");
            dataBuilder.append("['Passing', ").append(MAPEHPassing).append("],\n");
            dataBuilder.append("['Retake', ").append(MAPEHRetake).append("],\n");
        }
        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        google.charts.load('current', {'packages':['corechart']});\n" +
                "        google.charts.setOnLoadCallback(drawChart);\n" +
                "        function drawChart() {\n" +
                "            var data = google.visualization.arrayToDataTable([\n" +
                dataBuilder.toString() +
                "            ]);\n" +
                "            var options = {\n" +
                "                title: ' " + Subject + " ',\n" +
                "                colors: ['#FFCCCC', '#CCFFCC', '#CCCCFF', '#FFFFCC', '#FFCCFF', '#CCFFFF', '#F2F2F2'],\n" +
                "                pieSliceTextStyle: {color: 'black'},\n" +
                "                legend: 'none'\n" +
                "            };\n" +
                "            var chart = new google.visualization.PieChart(document.getElementById('pieChart'));\n" +
                "            chart.draw(data, options);\n" +
                "        }\n" +
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"pieChart\" style=\"width: 100%; height: 100%;\"></div>\n" +
                "</body>\n" +
                "</html>";

        chartWebView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
    }
    private void Chart() {

        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
    }

    private void list(){

        RecyclerView recyclerView;
        ScoreBoardDataAdapter data;
        ArrayList<ScoreBoardInformation> list;

        recyclerView = findViewById(R.id.list);

        list = new ArrayList<>();
        data = new ScoreBoardDataAdapter(getApplicationContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(data);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ScoreBoardInformation update = dataSnapshot.getValue(ScoreBoardInformation.class);
                    if (update != null) {
                        list.add(update);
                    }
                }
                data.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
