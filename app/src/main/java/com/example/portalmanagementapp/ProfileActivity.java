package com.example.portalmanagementapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;
    String User_Name, User_Email, User_Phone, User_Age;

    private ImageView Profile;
    private TextView Name, Email, Phone, Age;
    private Button SignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("AdminAccess");
        userID = user.getUid();

        Name = (TextView) findViewById(R.id.textfullName);
        Email = (TextView) findViewById(R.id.textEmail);
        Age = (TextView) findViewById(R.id.textAge);
        Phone = (TextView) findViewById(R.id.textPhone);
        Profile = (ImageView) findViewById(R.id.img);

        SignOut = (Button) findViewById(R.id.logout);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("UserProfile/" + userID + ".png");

        try {
            File localfile = File.createTempFile("tempfile", ".png");
            storageRef.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    Profile.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, "Failed to retrieve.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Glide.with(getApplicationContext()).load(storageRef).into(Profile);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if (userProfile != null) {
                    User_Name = userProfile.Name;
                    User_Email = userProfile.Email;
                    User_Age = userProfile.Age;
                    User_Phone = userProfile.Phone;

                    Name.setText(User_Name);
                    Email.setText(User_Email);
                    Age.setText("Age : " + User_Age);
                    Phone.setText("Phone : " + User_Phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //BOTTOM NAVIGATION VIEW
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        onBackPressed();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        onBackPressed();
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        onBackPressed();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


    }
}