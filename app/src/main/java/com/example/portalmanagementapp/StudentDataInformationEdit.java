package com.example.portalmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.SubActivities.NotifSave;
import com.example.portalmanagementapp.SubActivities.UserhelperClass2;
import com.example.portalmanagementapp.SubActivities.UserhelperClass3;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentDataInformationEdit extends AppCompatActivity {

    private EditText FullName, StudentNumber, StudentAge, FullAddress, StudentContact, EmergencyName, EmergencyContact, BirthPlace, Birthday;
    private String _FullName, _StudentNumber, _StudentAge, _FullAddress, _StudentContact, _EmergencyName, _EmergencyContact, _BirthPlace, _Birthday, _Gender;
    private CheckBox Male, Female, Check;
    private Button StudentProfile, Register;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private boolean connected;
    private Boolean _Male, _Female, _Check;
    private String Time, currentDateTime;
    private TextView SignIn;
    private FirebaseUser user;
    private String userID;

    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("AdminAccess").child("StudentList");
    DatabaseReference usersNotif = FirebaseDatabase.getInstance().getReference("LogHistory");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_data_information_edit);

        FullName = findViewById(R.id.name);

        StudentNumber = findViewById(R.id.studentnum);
        StudentAge = findViewById(R.id.studentage);

        FullAddress = findViewById(R.id.address);

        StudentContact = findViewById(R.id.phone);
        EmergencyName = findViewById(R.id.emername);
        EmergencyContact = findViewById(R.id.emerphone);

        BirthPlace = findViewById(R.id.birthplace);
        Birthday = findViewById(R.id.birth);

        Male = findViewById(R.id.male);
        Female = findViewById(R.id.female);
        Check = findViewById(R.id.check);
        _Male = Male.isChecked();
        _Female = Female.isChecked();
        _Check = Check.isChecked();

        StudentProfile = findViewById(R.id.img);
        Register = findViewById(R.id.done);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Male.setChecked(false);
            }
        });

        Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Female.setChecked(false);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration();
            }
        });

        StudentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        usersRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass2 userProfile = snapshot.getValue(UserhelperClass2.class);
                if(userProfile != null){
                    _FullName = userProfile.Name;
                    _StudentNumber = userProfile.StudentNumber;
                    _StudentAge = userProfile.Age;
                    _Gender = userProfile.Gender;
                    _FullAddress = userProfile.Address;
                    _StudentContact = userProfile.StudentContact;
                    _EmergencyName = userProfile.EmergencyName;
                    _EmergencyContact = userProfile.EmergencyContact;
                    _Birthday = userProfile.Birthday;
                    _BirthPlace = userProfile.BirthPlace;

                    FullName.setText(_FullName);
                    StudentNumber.setText(_StudentNumber);
                    StudentAge.setText(_StudentAge);
                    FullAddress.setText(_FullAddress);
                    StudentContact.setText(_StudentContact);
                    EmergencyName.setText(_EmergencyName);
                    EmergencyContact.setText(_EmergencyContact);
                    Birthday.setText(_Birthday);
                    BirthPlace.setText(_BirthPlace);

                    if (_Gender.equals("Male")){
                        Male.setChecked(true);
                    }
                    else if (_Gender.equals("Female")){
                        Female.setChecked(true);
                    }
                    else {
                        Toast.makeText(StudentDataInformationEdit.this, "No gender available", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Check.setChecked(true);
        }

    }

    private void uploadImage() {
        String _StudentNumber = StudentNumber.getText().toString().trim();
        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("UserProfile/" + _StudentNumber + ".png");

            UploadTask uploadTask = storageRef.putFile(imageUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // save the image URL to the database
                            String imageUrl = uri.toString();
                            usersRef.child(userID).setValue(imageUrl);
                        }
                    });
                }
            });
        }
    }


    public void Registration(){
        if (_FullName.isEmpty()){
            FullName.setError("Enter firstname!");
            FullName.requestFocus();
            return;
        }

        if (_StudentNumber.isEmpty()){
            StudentNumber.setError("Enter Student Number!");
            StudentNumber.requestFocus();
            return;
        }
        if (_StudentAge.isEmpty()){
            StudentAge.setError("Enter Student Age!");
            StudentAge.requestFocus();
            return;
        }
        if (_FullAddress.isEmpty()){
            FullAddress.setError("Enter House Lot!");
            FullAddress.requestFocus();
            return;
        }
        if (_StudentContact.isEmpty()){
            StudentContact.setError("Enter Student Contact!");
            StudentContact.requestFocus();
            return;
        }

        if (_EmergencyName.isEmpty()){
            EmergencyName.setError("Enter Emergency Name!");
            EmergencyName.requestFocus();
            return;
        }
        if (_EmergencyContact.isEmpty()){
            EmergencyContact.setError("Enter Emergency Contact!");
            EmergencyContact.requestFocus();
            return;
        }
        if (_Birthday.isEmpty()){
            Birthday.setError("Enter Month!");
            Birthday.requestFocus();
            return;
        }
        if (_BirthPlace.isEmpty()){
            BirthPlace.setError("Enter Day!");
            BirthPlace.requestFocus();
            return;
        }

        if(!_Check){
            Check.setError("");
            Check.requestFocus();
        }

        uploadImage();
        UserhelperClass3 user = new UserhelperClass3(_FullName, _StudentNumber, _StudentAge, _Gender, _FullAddress, _StudentContact, _EmergencyName, _EmergencyContact, _Birthday, _BirthPlace);
        usersRef.child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (connected){
                    if(task.isSuccessful()){

                        usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                        String Subject = "Edit Information";
                        String Compose = _FullName + " : Edit Information.";

                        NotifSave save = new NotifSave(Subject, Compose, Time);
                        usersNotif.child(Time).setValue(save);

                        Toast.makeText(getApplicationContext(), "Edit Complete", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), StudentDataInformation.class));

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Registered Unsuccessfully!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }

    });
    }

}