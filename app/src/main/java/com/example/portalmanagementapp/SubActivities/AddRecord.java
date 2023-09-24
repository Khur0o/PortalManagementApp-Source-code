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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.portalmanagementapp.AccountInformation;
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

public class AddRecord extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    private CheckBox Case01, Case02, Case03, Case04, Case05, Case06, Case07, Case08, Case09, Case10, Case11, Case12, Case13, Case14, Case15, Case16, Case17, Case18, Case19, Case20, Case21, Case22, Case23, Case24, Case25, Case26, Case27, Case28, Case29, Case30, Case31, Minor1stOffense, Minor2ndOffense, Minor3rdOffense, LessGrave1stOffense, LessGrave2ndOffense, LessGrave3rdOffense, Grave1stOffense, Grave2ndOffense, Grave3rdOffense, Grave4thOffense;
    private TextView Minor1st, Minor2nd, Minor3rd, Less1st, Less2nd, Less3rd, Grave1st, Grave2nd, Grave3rd, Grave4th;
    private String strCase01, strCase02, strCase03, strCase04, strCase05, strCase06, strCase07, strCase08, strCase09, strCase10, strCase11, strCase12, strCase13, strCase14, strCase15, strCase16, strCase17, strCase18, strCase19, strCase20, strCase21, strCase22, strCase23, strCase24, strCase25, strCase26, strCase27, strCase28, strCase29, strCase30, strCase31, strMinor1stOffense, strMinor2ndOffense, strMinor3rdOffense, strLessGrave1stOffense, strLessGrave2ndOffense, strLessGrave3rdOffense, strGrave1stOffense, strGrave2ndOffense, strGrave3rdOffense, strGrave4thOffense;
    private Button AddRecord;

    private DatabaseReference Admin,StudentNumber, usersNotif;
    private FirebaseUser user;
    private String userID, Case;

    String User_Name, Student_Name, Student_Email, Student_LRN, Student_ID;
    private String Time, currentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess/AdministratorList");
        StudentNumber = FirebaseDatabase.getInstance().getReference("AdminAccess/StudentList");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersNotif = database.getReference("LogHistory");

        AddRecord = findViewById(R.id.next);

        Case01 = findViewById(R.id.Case01);
        Case02 = findViewById(R.id.Case02);
        Case03 = findViewById(R.id.Case03);
        Case04 = findViewById(R.id.Case04);
        Case05 = findViewById(R.id.Case05);
        Case06 = findViewById(R.id.Case06);
        Case07 = findViewById(R.id.Case07);
        Case08 = findViewById(R.id.Case08);
        Case09 = findViewById(R.id.Case09);
        Case10 = findViewById(R.id.Case10);
        Case11 = findViewById(R.id.Case11);
        Case12 = findViewById(R.id.Case12);
        Case13 = findViewById(R.id.Case13);
        Case14 = findViewById(R.id.Case14);
        Case15 = findViewById(R.id.Case15);
        Case16 = findViewById(R.id.Case16);
        Case17 = findViewById(R.id.Case17);
        Case18 = findViewById(R.id.Case18);
        Case19 = findViewById(R.id.Case19);
        Case20 = findViewById(R.id.Case20);
        Case21 = findViewById(R.id.Case21);
        Case22 = findViewById(R.id.Case22);
        Case23 = findViewById(R.id.Case23);
        Case24 = findViewById(R.id.Case24);
        Case25 = findViewById(R.id.Case25);
        Case26 = findViewById(R.id.Case26);
        Case27 = findViewById(R.id.Case27);
        Case28 = findViewById(R.id.Case28);
        Case29 = findViewById(R.id.Case29);
        Case30 = findViewById(R.id.Case30);
        Case31 = findViewById(R.id.Case31);
        /**

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

         **/
        Case01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case01.getText().toString().trim();
                //Case01.setChecked(true);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case02.getText().toString().trim();
                Case01.setChecked(false);
                //Case02.setChecked(true);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case03.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                //Case03.setChecked(true);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case04.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                //Case04.setChecked(true);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case05.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case05.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                //Case05.setChecked(true);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case06.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case06.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                //Case06.setChecked(true);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case07.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case07.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                //Case07.setChecked(true);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case08.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case08.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                //Case08.setChecked(true);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case09.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case09.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                //Case09.setChecked(true);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case10.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                //Case10.setChecked(true);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case11.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                //Case11.setChecked(true);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case12.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                //Case12.setChecked(true);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case13.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                //Case13.setChecked(true);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case14.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                //Case14.setChecked(true);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case15.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                //Case15.setChecked(true);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case16.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                //Case16.setChecked(true);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case17.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                //Case17.setChecked(true);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case18.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                //Case18.setChecked(true);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case19.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                //Case19.setChecked(true);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case20.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                //Case20.setChecked(true);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case21.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                //Case21.setChecked(true);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case22.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                //Case22.setChecked(true);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case23.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                //Case23.setChecked(true);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case24.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                //Case24.setChecked(true);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case25.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                //Case25.setChecked(true);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case26.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case26.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                //Case26.setChecked(true);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case27.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case27.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                //Case27.setChecked(true);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case28.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case28.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                //Case28.setChecked(true);
                Case29.setChecked(false);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case29.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case29.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                //Case29.setChecked(true);
                Case30.setChecked(false);
                Case31.setChecked(false);
                Save();
            }
        });
        Case30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case30.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                //Case30.setChecked(true);
                Case31.setChecked(false);
                Save();
            }
        });
        Case31.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Case = Case31.getText().toString().trim();
                Case01.setChecked(false);
                Case02.setChecked(false);
                Case03.setChecked(false);
                Case04.setChecked(false);
                Case05.setChecked(false);
                Case06.setChecked(false);
                Case07.setChecked(false);
                Case08.setChecked(false);
                Case09.setChecked(false);
                Case10.setChecked(false);
                Case11.setChecked(false);
                Case12.setChecked(false);
                Case13.setChecked(false);
                Case14.setChecked(false);
                Case15.setChecked(false);
                Case16.setChecked(false);
                Case17.setChecked(false);
                Case18.setChecked(false);
                Case19.setChecked(false);
                Case20.setChecked(false);
                Case21.setChecked(false);
                Case22.setChecked(false);
                Case23.setChecked(false);
                Case24.setChecked(false);
                Case25.setChecked(false);
                Case26.setChecked(false);
                Case27.setChecked(false);
                Case28.setChecked(false);
                Case29.setChecked(false);
                Case30.setChecked(false);
                //Case31.setChecked(true);
                Save();
            }
        });

        /**
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

         **/
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


        Admin.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if (userProfile != null) {
                    User_Name = userProfile.Name;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = getIntent();
        String ID_Number = intent.getStringExtra("IDNumber");

        Toast.makeText(this, "" + ID_Number, Toast.LENGTH_SHORT).show();
        StudentNumber.child(ID_Number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if (userProfile != null) {
                    Student_Name = userProfile.Name;
                    Student_Email = userProfile.StudentEmail;
                    Student_LRN = userProfile.StudentNumber;
                    Student_ID = userProfile.UserID;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Save(){
        Intent intent = getIntent();
        String ID_Number = intent.getStringExtra("IDNumber");
        String Quarter = intent.getStringExtra("Quarter");

        AddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddRecordOffense.class);
                intent.putExtra("Case",  Case);
                intent.putExtra("IDNumber",  ID_Number);
                intent.putExtra("Quarter",  Quarter);
                startActivity(intent);

/**


 List<String> strings2List = new ArrayList<>();
 for (String str : new String[]{strMinor1stOffense, strMinor2ndOffense, strMinor3rdOffense, strLessGrave1stOffense, strLessGrave2ndOffense, strLessGrave3rdOffense, strGrave1stOffense, strGrave2ndOffense, strGrave3rdOffense, strGrave4thOffense}) {
 if (str != null) {
 strings2List.add(str);
 }
 }
 String[] Offense = strings2List.toArray(new String[0]);


 DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GuidanceRecord");

 for (String strOffense : Offense) {


 for (String strCase : Case) {
 Toast.makeText(AddRecord.this, "" + strCase, Toast.LENGTH_SHORT).show();

 UserhelperClassAddRecord user = new UserhelperClassAddRecord(Student_Name, ID_Number, Student_Email, strCase, strOffense, Time);
 databaseReference.child(ID_Number).child(strOffense).setValue(user);

 }
 }


 startActivity(new Intent(getApplicationContext(), GuidanceRecord.class));
 **/
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
                case R.id.regis:

                    startActivity(new Intent(this, SignUpActivity.class));
                    break;
                case R.id.info:

                    startActivity(new Intent(this, AccountInformation.class));
                    break;
                case R.id.comp:

                    startActivity(new Intent(this, GuidanceCouncilActivity.class));
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