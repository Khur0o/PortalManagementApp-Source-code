package com.example.portalmanagementapp.SubActivities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.R;
import com.example.portalmanagementapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EnrollmentRegistrationActivity extends AppCompatActivity {

    private EditText FirstName, MiddleName, LastName, StudentNumber, StudentAge, Unit, Barangay, City, Province, StudentContact, StudentEmail, EmergencyName, EmergencyContact, BirthPlace, DateMM, DateDD, DateYYYY, Password, ConPassword;
    private CheckBox Male, Female, Check;
    private Button StudentProfile, Register;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private boolean connected;
    private Boolean _Male, _Female, _Check;
    private String Time, currentDateTime;
    private FirebaseAuth mAuth;
    private TextView SignIn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("AdminAccess/StudentList");
    DatabaseReference usersNotif = database.getReference("LogHistory");

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("AdminAccess/StudentList");

    boolean male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_registration);

        mAuth = FirebaseAuth.getInstance();

        FirstName = findViewById(R.id.firstname);
        MiddleName = findViewById(R.id.middlename);
        LastName = findViewById(R.id.lastname);

        StudentNumber = findViewById(R.id.studentnum);
        StudentAge = findViewById(R.id.studentage);

        Password = findViewById(R.id.pass);
        ConPassword = findViewById(R.id.ssap);

        Unit = findViewById(R.id.unit);
        Barangay = findViewById(R.id.barangay);
        City = findViewById(R.id.city);
        Province = findViewById(R.id.province);

        StudentContact = findViewById(R.id.phone);
        StudentEmail = findViewById(R.id.email);
        EmergencyName = findViewById(R.id.emername);
        EmergencyContact = findViewById(R.id.emerphone);

        BirthPlace = findViewById(R.id.birthplace);
        DateMM = findViewById(R.id.birthmm);
        DateDD = findViewById(R.id.birthdd);
        DateYYYY = findViewById(R.id.birthyyyy);

        Male = findViewById(R.id.male);
        Female = findViewById(R.id.female);
        Check = findViewById(R.id.check);
        _Male = Male.isChecked();
        _Female = Female.isChecked();
        _Check = Check.isChecked();

        StudentProfile = findViewById(R.id.img);
        Register = findViewById(R.id.done);

        SignIn = findViewById(R.id.loginn);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        String Acc = StudentEmail.getText().toString().trim();

        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Male.setChecked(false);
                male = false;
                female = true;
            }
        });

        Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Female.setChecked(false);
                male = true;
                female = false;
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

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

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
                            usersRef.child(mAuth.getCurrentUser().getUid()).child("UserProfile").setValue(imageUrl);
                        }
                    });
                }
            });
        }
    }


    public void Registration(){
        String _FirstName = FirstName.getText().toString().trim();
        String _MiddleName = MiddleName.getText().toString().trim();
        String _LastName = LastName.getText().toString().trim();

        String Fullname = _LastName + ", " + _FirstName + " " + _MiddleName + ".";

        String _StudentNumber = StudentNumber.getText().toString().trim();
        String _StudentAge = StudentAge.getText().toString().trim();

        String _Password = Password.getText().toString().trim();
        String _ConPass = ConPassword.getText().toString().trim();

        String _Unit = Unit.getText().toString().trim();
        String _Barangay = Barangay.getText().toString().trim();
        String _City = City.getText().toString().trim();
        String _Province = Province.getText().toString().trim();

        String Address = _Unit + ", " + _Barangay + ", " + _City + ", " + _Province + ".";

        String _StudentContact = StudentContact.getText().toString().trim();
        String _StudentEmail = StudentEmail.getText().toString().trim();
        String _EmergencyName = EmergencyName.getText().toString().trim();
        String _EmergencyContact = EmergencyContact.getText().toString().trim();

        String _DateMM = DateMM.getText().toString().trim();
        String _DateDD = DateDD.getText().toString().trim();
        String _DateYYYY = DateYYYY.getText().toString().trim();

        String _BirthPlace = BirthPlace.getText().toString().trim();

        String Date = _DateMM + "/ " + _DateDD + "/ " + _DateYYYY + "." ;

        String Status = "Student Account";

        if (_FirstName.isEmpty()){
            FirstName.setError("Enter firstname!");
            FirstName.requestFocus();
            return;
        }

        if (_LastName.isEmpty()){
            LastName.setError("Enter lastname!");
            LastName.requestFocus();
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
        if (_Unit.isEmpty()){
            Unit.setError("Enter House Lot!");
            Unit.requestFocus();
            return;
        }
        if (_Barangay.isEmpty()){
            Barangay.setError("Enter Barangay!");
            Barangay.requestFocus();
            return;
        }
        if (_City.isEmpty()){
            City.setError("Enter City!");
            City.requestFocus();
            return;
        }
        if (_Province.isEmpty()){
            Province.setError("Enter Province!");
            Province.requestFocus();
            return;
        }
        if (_StudentContact.isEmpty()){
            StudentContact.setError("Enter Student Contact!");
            StudentContact.requestFocus();
            return;
        }
        if (_StudentEmail.isEmpty()){
            StudentEmail.setError("Enter Student Email!");
            StudentEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_StudentEmail).matches()){
            StudentEmail.setError("Please provide valid Email!");
            StudentEmail.requestFocus();
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
        if (_DateMM.isEmpty()){
            DateMM.setError("Enter Month!");
            DateMM.requestFocus();
            return;
        }
        if (_DateDD.isEmpty()){
            DateDD.setError("Enter Day!");
            DateDD.requestFocus();
            return;
        }
        if (_DateYYYY.isEmpty()){
            DateYYYY.setError("Enter Year!");
            DateYYYY.requestFocus();
            return;
        }

        String _Gender = null;
        if (male){
            _Gender = "Male";
        }
        if (female){
            _Gender  = "Female";
        }
        if(!_Check){
            Check.setError("");
            Check.requestFocus();
        }

        String final_Gender = _Gender;
        mAuth.createUserWithEmailAndPassword(_StudentEmail,_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    uploadImage();
                    String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    UserhelperClass2 user = new UserhelperClass2(Fullname, _StudentNumber, _StudentAge, final_Gender, Address, _StudentContact, _StudentEmail, _EmergencyName, _EmergencyContact, Date, _BirthPlace, _Password, Status, UserID, Time);
                    FirebaseDatabase.getInstance().getReference("AdminAccess/StudentList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (connected){
                                if(task.isSuccessful()){


                                    usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                                    String Subject = "NEW ENROLLED";
                                    String Compose = Fullname + " : New Student Enrolled.";

                                    NotifSave save = new NotifSave(Subject, Compose, Time);
                                    usersNotif.child(Time).setValue(save);

                                    Toast.makeText(EnrollmentRegistrationActivity.this, "Registered Complete", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Registered Unsuccessfully!", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    StudentEmail.setError("This Email Already Exist.");
                    StudentEmail.requestFocus();
                }
            }

        });

    }

}