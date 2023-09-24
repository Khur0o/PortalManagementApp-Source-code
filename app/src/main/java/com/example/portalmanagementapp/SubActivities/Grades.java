package com.example.portalmanagementapp.SubActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.portalmanagementapp.MainActivity;
import com.example.portalmanagementapp.NotificationActivity;
import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.SignInActivity;
import com.example.portalmanagementapp.SignUpActivity;
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
import java.util.Calendar;

public class Grades extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    String User_Name;

    private DatabaseReference ref;
    private FirebaseUser user;
    private String userID, studentID;
    private DatabaseReference Admin, Subjectref;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersNotif = database.getReference("LogHistory");
    private String Time, currentDateTime;

    FrameLayout Subject;
    ImageView SubjectImage;
    TextView Sub, name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        Subject = findViewById(R.id.sub);
        Sub = findViewById(R.id.Subject);
        SubjectImage = findViewById(R.id.subimg);
        name = findViewById(R.id.studname);
        email = findViewById(R.id.studEmail);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess").child("ProfessorList");
        Subjectref = FirebaseDatabase.getInstance().getReference("ProfessorSub").child(userID).child("subject");

        ref = FirebaseDatabase.getInstance().getReference("LogHistory");

        Intent intent = getIntent();
        studentID = intent.getStringExtra("studentID");

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
        navigationView.setCheckedItem(R.id.notif);

        FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList").child(studentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if(userProfile != null){
                    String a, Student_Name, Student_Email;
                    Student_Name = userProfile.Name;
                    Student_Email = userProfile.StudentEmail;
                    a = userProfile.UserID;

                    name.setText(Student_Name);
                    email.setText(Student_Email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            Subjectref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userSubject = snapshot.getValue(String.class);
                    if (userSubject != null) {
                        String User_Sub = userSubject;
                        Sub.setText(User_Sub);

                        try{
                            if (User_Sub.equals("English")) {
                                SubjectImage.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.white_board));
                            }
                            else if (User_Sub.equals("Math")){
                                SubjectImage.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.math));
                            }
                            else if (User_Sub.equals("Science")){
                                SubjectImage.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.science_and_tech));
                            }
                            else if (User_Sub.equals("Filipino")){
                                SubjectImage.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.writing));
                            }
                            else if (User_Sub.equals("Values")){
                                SubjectImage.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.badge));
                            }
                            else if (User_Sub.equals("History")){
                                SubjectImage.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.history_book));
                            }
                            else if (User_Sub.equals("MAPEH")){
                                SubjectImage.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.djpksa));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_SHORT).show();
                        }

                        Subject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), StudentGradeActivity.class);
                                intent.putExtra("Subject", User_Sub);
                                intent.putExtra("studentID", studentID);
                                startActivity(intent);
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error gracefully
                    Toast.makeText(Grades.this, "Failed to retrieve subject: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

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


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:

                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.notif:

                startActivity(new Intent(this, NotificationActivity.class));
                break;

            case R.id.comp:

                startActivity(new Intent(this, GuidanceCouncilActivity.class));
                break;

            case R.id.regis:

                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.info:

                startActivity(new Intent(this, StudentData.class));
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
                usersNotif.child(Time).setValue(save);

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