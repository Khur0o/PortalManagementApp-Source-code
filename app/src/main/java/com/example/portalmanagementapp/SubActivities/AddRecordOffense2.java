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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.portalmanagementapp.ProfMain;
import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.SignInActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddRecordOffense2 extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    private CheckBox Minor1stOffense, Minor2ndOffense, Minor3rdOffense, LessGrave1stOffense, LessGrave2ndOffense, LessGrave3rdOffense, Grave1stOffense, Grave2ndOffense, Grave3rdOffense, Grave4thOffense;
    private TextView Minor1st, Minor2nd, Minor3rd, Less1st, Less2nd, Less3rd, Grave1st, Grave2nd, Grave3rd, Grave4th;
    private String strMinor1stOffense, strMinor2ndOffense, strMinor3rdOffense, strLessGrave1stOffense, strLessGrave2ndOffense, strLessGrave3rdOffense, strGrave1stOffense, strGrave2ndOffense, strGrave3rdOffense, strGrave4thOffense;
    private Button AddRecord;

    private DatabaseReference Admin,StudentNumber, usersNotif;
    private FirebaseUser user;
    private String userID;

    String User_Name, Student_Name, Student_Email, Student_LRN;
    private String Time, currentDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record_offense2);

        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess/ProfessorList");
        StudentNumber = FirebaseDatabase.getInstance().getReference("AdminAccess/StudentList");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersNotif = database.getReference("LogHistory");

        AddRecord = findViewById(R.id.addrecord);

        Minor1stOffense = findViewById(R.id.Minor1stOffense);
        Minor2ndOffense = findViewById(R.id.Minor2ndOffense);
        Minor3rdOffense = findViewById(R.id.Minor3rdOffense);
        LessGrave1stOffense = findViewById(R.id.LessGrave1stOffense);
        LessGrave2ndOffense = findViewById(R.id.LessGrave2ndOffense);
        LessGrave3rdOffense = findViewById(R.id.LessGrave3rdOffense);
        Grave1stOffense = findViewById(R.id.Grave1stOffense);
        Grave2ndOffense = findViewById(R.id.Grave2ndOffense);
        Grave3rdOffense = findViewById(R.id.Grave3rdOffense);
        Grave4thOffense = findViewById(R.id.Grave4thOffense);

        Minor1st = findViewById(R.id.txtMinor1stOffense);
        Minor2nd = findViewById(R.id.txtMinor2ndOffense);
        Minor3rd = findViewById(R.id.txtMinor3rdOffense);
        Less1st = findViewById(R.id.txtLessGrave1stOffense);
        Less2nd = findViewById(R.id.txtLessGrave2ndOffense);
        Less3rd = findViewById(R.id.txtLessGrave3rdOffense);
        Grave1st = findViewById(R.id.txtGrave1stOffense);
        Grave1st = findViewById(R.id.txtGrave2ndOffense);
        Grave2nd = findViewById(R.id.txtGrave3rdOffense);
        Grave3rd = findViewById(R.id.txtGrave4thOffense);

        Minor1stOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strMinor1stOffense = "Minor " + Minor1st.getText().toString().trim();
            }
        });

        Minor2ndOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strMinor2ndOffense = "Minor " + Minor2nd.getText().toString().trim();
            }
        });

        Minor3rdOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strMinor3rdOffense = "Minor " + Minor3rd.getText().toString().trim();
            }
        });

        LessGrave1stOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strLessGrave1stOffense = "Less Grave " + Less1st.getText().toString().trim();
            }
        });

        LessGrave2ndOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strLessGrave2ndOffense = "Less Grave " + Less2nd.getText().toString().trim();
            }
        });

        LessGrave3rdOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strLessGrave3rdOffense = "Less Grave " + Less3rd.getText().toString().trim();
            }
        });

        Grave1stOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strGrave1stOffense = "Grave " + Grave1st.getText().toString().trim();
            }
        });

        Grave2ndOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strGrave2ndOffense = "Grave " + Grave2nd.getText().toString().trim();
            }
        });

        Grave3rdOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strGrave3rdOffense = "Grave " + Grave3rd.getText().toString().trim();
            }
        });

        Grave4thOffense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strGrave4thOffense = "Grave " + Grave4th.getText().toString().trim();
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigator_layout);
        toolbar = findViewById(R.id.toolbar_id);
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
        navigationView.setCheckedItem(R.id.comp);


        Intent intent = getIntent();
        String Case = intent.getStringExtra("Case");
        String Student_ID = intent.getStringExtra("Student_ID");


        AddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> strings2List = new ArrayList<>();
                for (String str : new String[]{strMinor1stOffense, strMinor2ndOffense, strMinor3rdOffense, strLessGrave1stOffense, strLessGrave2ndOffense, strLessGrave3rdOffense, strGrave1stOffense, strGrave2ndOffense, strGrave3rdOffense, strGrave4thOffense}) {
                    if (str != null) {
                        strings2List.add(str);
                    }
                }
                String[] Offense = strings2List.toArray(new String[0]);


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GuidanceRecord");

                for (String strOffense : Offense) {
                    UserhelperClassAddRecord user = new UserhelperClassAddRecord(Case, strOffense, Time);
                    databaseReference.child(Student_ID).child(strOffense).setValue(user);
                }

                startActivity(new Intent(getApplicationContext(), GuidanceRecord.class));
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