package com.example.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.fragment_second);
            Button logout = findViewById(R.id.logout);
            ImageButton profile = findViewById(R.id.profileButton);
            ImageButton markSheet = findViewById(R.id.markSheetButton);
            ImageButton timeTable = findViewById(R.id.timeTableButton);
            ImageButton attendance = findViewById(R.id.attendanceButton);
            TextView profileText, markSheetText, timeTableText, attendanceText;
            profileText = findViewById(R.id.profile);
            markSheetText = findViewById(R.id.markSheet);
            timeTableText = findViewById(R.id.timetable);
            attendanceText = findViewById(R.id.attendance);

            logout.setOnClickListener(v -> logOut());
            profile.setOnClickListener(v -> profile());
            markSheet.setOnClickListener(v -> markSheet());
            timeTable.setOnClickListener(v -> timeTable());
            attendance.setOnClickListener(v -> subjectSelector());
            profileText.setOnClickListener(v -> profile());
            markSheetText.setOnClickListener(v -> markSheet());
            timeTableText.setOnClickListener(v -> timeTable());
            attendanceText.setOnClickListener(v -> subjectSelector());
        } else {
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void logOut() {
        mAuth.signOut();
        Intent intent = new Intent(Home.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void profile() {
        Intent intent = new Intent(Home.this, Profile.class);
        startActivity(intent);
    }

    public void markSheet() {
        Intent intent = new Intent(Home.this, MarkSheet.class);
        startActivity(intent);
    }

    public void timeTable() {
        Intent intent = new Intent(Home.this, TimeTable.class);
        startActivity(intent);
    }

    public void subjectSelector() {
        Intent intent = new Intent(Home.this, SubjectSelector.class);
        startActivity(intent);
    }

}