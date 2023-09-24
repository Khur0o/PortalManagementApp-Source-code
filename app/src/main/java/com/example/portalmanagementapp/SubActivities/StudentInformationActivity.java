package com.example.portalmanagementapp.SubActivities;

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
import java.util.Calendar;

public class StudentInformationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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


    private TextView ProfileName, ProfileEmail, StudentName, StudentNumber, StudentAge, StudentGender, StudentAddress, StudentPhone, StudentEmail, EmerName, EmerPhone, Birthday, BirthPlace;
    private ImageView Profile;
    private DatabaseReference reference;
    private Button Edit;

    String _Fullname, _StudentNumber, _Age, _Gender, _Address, _StudentContact, _StudentEmail, _EmergencyName, _EmergencyContact, _Birthday, _BirthPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Admin = FirebaseDatabase.getInstance().getReference("AdminAccess/AdministratorList");

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference("LogHistory");

        ProfileName = findViewById(R.id.studname);
        ProfileEmail = findViewById(R.id.studEmail);
        StudentName = findViewById(R.id.StudentName);
        StudentNumber = findViewById(R.id.StudentNumber);
        StudentAge = findViewById(R.id.StudentAge);
        StudentGender = findViewById(R.id.StudentGender);
        StudentAddress = findViewById(R.id.StudentAddress);
        StudentPhone = findViewById(R.id.StudentPhone);
        StudentEmail = findViewById(R.id.StudentEmail);
        EmerName = findViewById(R.id.emername);
        EmerPhone = findViewById(R.id.emerphone);
        Birthday = findViewById(R.id.StudentBirthday);
        BirthPlace = findViewById(R.id.StudentBirthplace);

        Profile = (ImageView) findViewById(R.id.img);

        Intent intent = getIntent();
        String str = intent.getStringExtra("Number");
        String studentNumber = intent.getStringExtra("StudentNumber");
        Toast.makeText(this, "" + str , Toast.LENGTH_SHORT).show();

        reference = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList").child(str);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if(userProfile != null){
                    _Fullname = userProfile.Name;
                    _StudentNumber = userProfile.StudentNumber;
                    _Age = userProfile.Age;
                    _Gender = userProfile.Gender;
                    _Address = userProfile.Address;
                    _StudentContact = userProfile.StudentContact;
                    _StudentEmail = userProfile.StudentEmail;
                    _EmergencyName = userProfile.EmergencyName;
                    _EmergencyContact = userProfile.EmergencyContact;
                    _BirthPlace = userProfile.BirthPlace;
                    _Birthday = userProfile.Birthday;

                    ProfileName.setText(_Fullname);
                    ProfileEmail.setText(_StudentEmail);
                    StudentName.setText(_Fullname);
                    StudentNumber.setText(_StudentNumber);
                    StudentAge.setText(_Age);
                    StudentGender.setText(_Gender);
                    StudentAddress.setText(_Address);
                    StudentPhone.setText(_StudentContact);
                    StudentEmail.setText(_StudentEmail);
                    EmerName.setText(_EmergencyName);
                    EmerPhone.setText(_EmergencyContact);
                    BirthPlace.setText(_BirthPlace);
                    Birthday.setText(_Birthday);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        StorageReference storageRef = storage.getReference("UserProfile/" + studentNumber + ".png");


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