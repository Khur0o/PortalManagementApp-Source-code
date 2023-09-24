package com.example.portalmanagementapp;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.example.portalmanagementapp.SubActivities.GuidanceRecord3;
import com.example.portalmanagementapp.SubActivities.NotifSave;
import com.example.portalmanagementapp.SubActivities.SubjectListProf;
import com.example.portalmanagementapp.SubActivities.UserhelperClass2;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    private DatabaseReference Admin, reference;
    private FirebaseUser user;
    private String userID;
    String User_Name, User_Email, User_Role;
    private ImageView Profile;
    private TextView Name, Email, Student, Role;

    private FrameLayout test01, test02, test03;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersNotif = database.getReference("LogHistory");
    private String Time, currentDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        test01 = (FrameLayout) findViewById(R.id.btnRelativeGuidance);
        test02 = (FrameLayout) findViewById(R.id.data);
        test03 = (FrameLayout) findViewById(R.id.student);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentLists");
        Toast.makeText(this, "" + userID, Toast.LENGTH_SHORT).show();


        Name = (TextView) findViewById(R.id.Name);
        Email = (TextView) findViewById(R.id.UserEmail);
        //Student = (TextView) findViewById(R.id.numstudent);
        Role = (TextView) findViewById(R.id.UserStatus);
        Profile = (ImageView) findViewById(R.id.Prof);

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
        navigationView.setCheckedItem(R.id.home);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("UserProfile/" + userID + ".png");


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

        Glide.with(getApplicationContext()).load(storageRef).into(Profile);
        reference = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList").child(userID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if(userProfile != null){

                    User_Name = userProfile.Name;
                    User_Email = userProfile.StudentEmail;
                    User_Role = userProfile.Status;

                    Name.setText(User_Name);
                    Email.setText(User_Email);
                    Role.setText(User_Role);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                //TokenID tokenid = new TokenID(token);
                reference.child("Token").setValue(token);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        setListener();
    }



    private void setListener() {

        test01.setOnClickListener(view -> guidanceCouncil());
        test02.setOnClickListener(view -> datainfo());
        test03.setOnClickListener(view -> Grades());

    }
    protected void guidanceCouncil() {
        startActivity(new Intent(getApplicationContext(), GuidanceRecord3.class));
        overridePendingTransition(0,0);
    }

    protected void datainfo() {
        startActivity(new Intent(getApplicationContext(), StudentDataInformation.class));
        overridePendingTransition(0,0);
    }

    protected void Grades() {
        startActivity(new Intent(getApplicationContext(), SubjectListProf.class));
        overridePendingTransition(0,0);
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