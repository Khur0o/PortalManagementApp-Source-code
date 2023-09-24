package com.example.portalmanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.portalmanagementapp.SubActivities.AdminData;
import com.example.portalmanagementapp.SubActivities.GuidanceCouncilActivity;
import com.example.portalmanagementapp.SubActivities.NotifSave;
import com.example.portalmanagementapp.SubActivities.ProfData;
import com.example.portalmanagementapp.SubActivities.StudentData;
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

public class AccountInformation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    String User_Name;

    private DatabaseReference ref;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference Admin;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersNotif = database.getReference("LogHistory");
    private String Time, currentDateTime;

    private FrameLayout AdminAcc, ProfessorAcc, StudentAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        AdminAcc = (FrameLayout) findViewById(R.id.AdminAccount);
        ProfessorAcc = (FrameLayout) findViewById(R.id.ProfessorAccount);
        StudentAcc = (FrameLayout) findViewById(R.id.StudentAccount);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess/AdministratorList");

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
        setListener();
    }

    private void setListener() {

        AdminAcc.setOnClickListener(view -> guidanceCouncil());
        ProfessorAcc.setOnClickListener(view -> datainfo());
        StudentAcc.setOnClickListener(view -> enrollmentRegister());



    }
    protected void guidanceCouncil() {
        startActivity(new Intent(getApplicationContext(), AdminData.class));
        overridePendingTransition(0,0);
    }
    protected void datainfo() {
        startActivity(new Intent(getApplicationContext(), ProfData.class));
        overridePendingTransition(0,0);
    }

    protected void enrollmentRegister() {
        startActivity(new Intent(getApplicationContext(), StudentData.class));
        overridePendingTransition(0,0);
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