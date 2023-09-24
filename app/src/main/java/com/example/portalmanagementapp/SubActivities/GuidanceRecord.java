package com.example.portalmanagementapp.SubActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalmanagementapp.AccountInformation;
import com.example.portalmanagementapp.MainActivity;
import com.example.portalmanagementapp.NotificationActivity;
import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.SignInActivity;
import com.example.portalmanagementapp.SignUpActivity;
import com.example.portalmanagementapp.UserhelperClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GuidanceRecord extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    private DatabaseReference Admin,StudentNumber, usersNotif;
    private FirebaseUser user;
    private String userID;

    String User_Name, Student_Name, Student_Email, Student_LRN;
    private String Time, currentDateTime;

    private TextView Name, Email, Role;
    private ImageView Profile;
    private Button AddRecord, Quarter01, Quarter02, Quarter03, Quarter04;

    RecyclerView recyclerView;
    GuidanceDataAdapter data;
    ArrayList<GuidanceInformation> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_record);

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

        Name=findViewById(R.id.Name);
        Email=findViewById(R.id.UserEmail);
        Role=findViewById(R.id.UserStatus);
        Profile = (ImageView) findViewById(R.id.Prof);
        AddRecord = findViewById(R.id.addrecord);

        Quarter01 = findViewById(R.id.first);
        Quarter02 = findViewById(R.id.second);
        Quarter03 = findViewById(R.id.third);
        Quarter04 = findViewById(R.id.fourth);

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



        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("UserProfile/" + Student_LRN + ".png");


        try{
            File file = File.createTempFile("temp", ".png");
            storageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Profile.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to retrieve.", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
        Intent intent = getIntent();
        String Student_Number = intent.getStringExtra("StudentNumber");
        String ID_Number = intent.getStringExtra("IDNumber");
        Toast.makeText(this, "" + ID_Number, Toast.LENGTH_SHORT).show();
        StudentNumber.child(ID_Number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if(userProfile != null){
                    String a;
                    Student_Name = userProfile.Name;
                    Student_Email = userProfile.StudentEmail;
                    a = userProfile.UserID;

                    Name.setText(Student_Name);
                    Email.setText(Student_Email);
                    Role.setText("Student Account");
                    //Toast.makeText(getApplicationContext(), "LRN : " + a, Toast.LENGTH_SHORT).show();


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

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(ID_Number).child("Quarter01");

                list = new ArrayList<>();
                data = new GuidanceDataAdapter(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);

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


                AddRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), AddRecord.class);
                        intent.putExtra("IDNumber",  ID_Number);
                        intent.putExtra("Quarter",  quart01);
                        startActivity(intent);
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

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(ID_Number).child("Quarter02");

                list = new ArrayList<>();
                data = new GuidanceDataAdapter(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);

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

                AddRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), AddRecord.class);
                        intent.putExtra("IDNumber", ID_Number);
                        intent.putExtra("Quarter",  quart02);
                        startActivity(intent);
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

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(ID_Number).child("Quarter03");

                list = new ArrayList<>();
                data = new GuidanceDataAdapter(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);

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

                AddRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), AddRecord.class);
                        intent.putExtra("IDNumber",  ID_Number);
                        intent.putExtra("Quarter",  quart03);
                        startActivity(intent);
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

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("GuidanceRecord").child(ID_Number).child("Quarter04");

                list = new ArrayList<>();
                data = new GuidanceDataAdapter(getApplicationContext(), list);
                recyclerView = findViewById(R.id.updatelist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(data);

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

                AddRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), AddRecord.class);
                        intent.putExtra("IDNumber",  ID_Number);
                        intent.putExtra("Quarter",  quart04);
                        startActivity(intent);
                    }
                });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout Confirmation")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
        return false;
    }

}