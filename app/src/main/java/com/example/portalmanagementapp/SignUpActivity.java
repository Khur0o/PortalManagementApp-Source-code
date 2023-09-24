package com.example.portalmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.SubActivities.NotifSave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView Img;
    private Button Register;
    private EditText Name, Email, Age, Phone, Password, ConPassword;
    private ProgressBar progressBar;
    private ScrollView Scroll;
    private boolean connected;
    private CheckBox Prof, Admin;
    private Boolean _Prof, _Admin;

    private FirebaseAuth mAuth;

    int SELECT_PICTURE = 200;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersNotif = database.getReference("LogHistory");
    private String Time, currentDateTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Prof = findViewById(R.id.prof);
        Admin = findViewById(R.id.admin);
        mAuth = FirebaseAuth.getInstance();
        Scroll = (ScrollView) findViewById(R.id.scroll);
        Img = (ImageView) findViewById(R.id.img);
        Register = (Button) findViewById(R.id.btn);
        Name = (EditText) findViewById(R.id.name);
        Email = (EditText) findViewById(R.id.email);
        Age = (EditText) findViewById(R.id.age);
        Phone = (EditText) findViewById(R.id.phone);
        Password = (EditText) findViewById(R.id.pass);
        ConPassword = (EditText) findViewById(R.id.ssap);
        progressBar = (ProgressBar) findViewById(R.id.prgressBar);
        Register.setOnClickListener(this);
        Img.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        Prof.setChecked(false);
        Prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Admin.setChecked(false);
                _Prof = true;
                _Admin = false;
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Prof.setChecked(false);
                _Prof = false;
                _Admin = true;
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.loginn:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case  R.id.img:
                imageChooser();
                break;
            case  R.id.btn:
                registerUser();
                break;
        }
    }


    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    Img.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void registerUser() {


        String _Name = Name.getText().toString().trim();
        String _Email = Email.getText().toString().trim();
        String _Age = Age.getText().toString().trim();
        String _Phone = Phone.getText().toString().trim();
        String _Password = Password.getText().toString().trim();
        String _ConPass = ConPassword.getText().toString().trim();

        if (_Name.isEmpty()){
            Name.setError("Enter your Name!");
            Name.requestFocus();
            return;
        }
        if (_Email.isEmpty()){
            Email.setError("Enter your Email!");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_Email).matches()){
            Email.setError("Please provide valid Email!");
            Email.requestFocus();
            return;
        }
        if (_Password.isEmpty()){
            Password.setError("Enter your Password!");
            Password.requestFocus();
            return;
        }
        if (_Password.length() < 8 ){
            Password.setError("Should be 8 characters!");
            Password.requestFocus();
            return;
        }
        if (_ConPass.isEmpty()){
            ConPassword.setError("Enter your Confirm Password!");
            ConPassword.requestFocus();
            return;
        }

        if (!_ConPass.equals(_Password)){
            ConPassword.setError("Not Match in your Password!");
            ConPassword.requestFocus();
            return;
        }

        String _Status = null;
        String _StatusList = null;


        if (_Prof){
            _Status = "Professor Account";
            _StatusList = "ProfessorList";
        }
        if (_Admin){
            _Status  = "Administrator Account";
            _StatusList  = "AdministratorList";
        }

        String final_Status = _Status;
        String final_StatusList = _StatusList;


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(_Email,_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String _UserID = task.getResult().getUser().getUid();
                if (task.isSuccessful()){
                    ImgSave();
                    UserhelperClass user = new UserhelperClass(_Email, _Name, _Age,_Phone, final_Status, _Password, _UserID);
                    FirebaseDatabase.getInstance().getReference("AdminAccess/" + final_StatusList).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (connected){
                                if(task.isSuccessful()){

                                    Name.setText("");
                                    Email.setText("");
                                    Age.setText("");
                                    Phone.setText("");
                                    Password.setText("");
                                    ConPassword.setText("");
                                    Img.setImageResource(R.drawable._19055030);

                                    String Subject = "NEW ACCOUNT";
                                    String Compose = _Name + " : New " + final_Status + ".";

                                    NotifSave save = new NotifSave(Subject, Compose, Time);
                                    usersNotif.child(Time).setValue(save);

                                    Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Registered Unsuccessfully!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Email.setError("This Email Already Exist.");
                    Email.requestFocus();
                    progressBar.setVisibility(View.GONE);
                }
            }

        });

    }
    private void ImgSave() {
        Img.setDrawingCacheEnabled(true);
        Img.buildDrawingCache();
        Bitmap bitmap = Img.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        StorageReference imageRef = storageRef.child("UserProfile/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".png");

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads
            }
        });
    }
}