package com.example.portalmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalmanagementapp.SubActivities.NotifSave;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class blank extends AppCompatActivity {

    private DatabaseReference Profreference, Studreference, Adminreference;
    private FirebaseUser user;
    private String userID;
    String ProfStatus, StudStatus, AdminStatus, NameStudent, NameProf, NameAdmin;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersNotif = database.getReference("LogHistory");
    private String Time, currentDateTime;

    private CountDownTimer countDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        Profreference = FirebaseDatabase.getInstance().getReference("AdminAccess/ProfessorList");
        Studreference = FirebaseDatabase.getInstance().getReference("AdminAccess/StudentList");
        Adminreference = FirebaseDatabase.getInstance().getReference("AdminAccess/AdministratorList");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentDateTime = sdf.format(calendar.getTime());
        Time = currentDateTime;

        countDown = new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "No account found.", Toast.LENGTH_SHORT).show();
            }
        }.start();

        Profreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if(userProfile != null){
                    ProfStatus = userProfile.Status;
                    NameProf = userProfile.Name;
                    checkUserType();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Studreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if(userProfile != null){
                    StudStatus = userProfile.Status;
                    NameStudent = userProfile.Name;
                    checkUserType();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Adminreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserhelperClass userProfile = snapshot.getValue(UserhelperClass.class);
                if(userProfile != null){
                    AdminStatus = userProfile.Status;
                    NameAdmin = userProfile.Name;
                    checkUserType();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkUserType() {
        if (ProfStatus != null && ProfStatus.equals("Professor Account")){
            //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ProfMain.class));
            String Subject = "Sign In";
            String Compose = NameProf;

            NotifSave save = new NotifSave(Subject, Compose, Time);
            usersNotif.child(Time).setValue(save);
            countDown.cancel();
            finish();
        } if (StudStatus != null && StudStatus.equals("Student Account")){
            //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), StudentMain.class));
            String Subject = "Sign In";
            String Compose = NameStudent;

            NotifSave save = new NotifSave(Subject, Compose, Time);
            usersNotif.child(Time).setValue(save);
            countDown.cancel();
            finish();
        } if (AdminStatus != null && AdminStatus.equals("Administrator Account")){
           // Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            String Subject = "Sign In";
            String Compose = NameAdmin;

            NotifSave save = new NotifSave(Subject, Compose, Time);
            usersNotif.child(Time).setValue(save);
            countDown.cancel();
            finish();
        }
    }
}
