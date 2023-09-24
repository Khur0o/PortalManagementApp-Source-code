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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.SignInActivity;
import com.example.portalmanagementapp.StudentDataInformation;
import com.example.portalmanagementapp.StudentMain;
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

public class GuidanceRecord3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    private DatabaseReference Admin,StudentNumber, usersNotif;
    private FirebaseUser user;
    private String userID, User_Name, User_Number;

    private String Time, currentDateTime;
    private Button AddRecord, Quarter01, Quarter02, Quarter03, Quarter04;

    RecyclerView recyclerView;
    GuidanceDataAdapter2 data;
    ArrayList<GuidanceInformation> list;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_record3);

        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess/StudentList");
        StudentNumber = FirebaseDatabase.getInstance().getReference("AdminAccess/StudentList");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Quarter01 = findViewById(R.id.first);
        Quarter02 = findViewById(R.id.second);
        Quarter03 = findViewById(R.id.third);
        Quarter04 = findViewById(R.id.fourth);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersNotif = database.getReference("LogHistory");

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
        navigationView.setCheckedItem(R.id.comp);

        StudentNumber.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if(userProfile != null){
                    User_Name = userProfile.Name;
                    User_Number = userProfile.StudentNumber;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        quarter();
    }

    private void quarter(){
        Intent intent = getIntent();
        String Student_Number = intent.getStringExtra("StudentNumber");
        String ID_Number = intent.getStringExtra("IDNumber");

        String quart01 = "Quarter01", quart02 = "Quarter02", quart03 = "Quarter03", quart04 = "Quarter04";

        Quarter01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));


                list = new ArrayList<>();
                data = new GuidanceDataAdapter2(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);


                    ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(userID).child(quart01);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                GuidanceInformation  update = dataSnapshot.getValue(GuidanceInformation.class);
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
        });
        Quarter02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));

                list = new ArrayList<>();
                data = new GuidanceDataAdapter2(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);


                    ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(userID).child(quart02);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                GuidanceInformation  update = dataSnapshot.getValue(GuidanceInformation.class);
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
        });
        Quarter03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));

                list = new ArrayList<>();
                data = new GuidanceDataAdapter2(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);


                    ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(userID).child(quart03);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                GuidanceInformation  update = dataSnapshot.getValue(GuidanceInformation.class);
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
        });
        Quarter04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Quarter01.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter02.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter03.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corners));
                Quarter04.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cornersgrn));

                list = new ArrayList<>();
                data = new GuidanceDataAdapter2(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);


                    ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(userID).child(quart04);

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                GuidanceInformation  update = dataSnapshot.getValue(GuidanceInformation.class);
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
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:

                startActivity(new Intent(this, StudentMain.class));
                break;

            case R.id.info:

                startActivity(new Intent(this, StudentDataInformation.class));
                break;
            case R.id.comp:
                startActivity(new Intent(this, GuidanceRecord3.class));
                break;
            case R.id.view:

                startActivity(new Intent(this, SubjectListProf.class));
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