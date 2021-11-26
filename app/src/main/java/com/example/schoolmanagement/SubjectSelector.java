package com.example.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SubjectSelector extends AppCompatActivity {
    private static final String TAG = "SubjectSelector";
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.fragment_eight);
            final Button maths = findViewById(R.id.MathsButton);
            final Button computerGraphics = findViewById(R.id.ComputerGraphicsButton);
            final Button dataStructures = findViewById(R.id.DataStructureButton);
            maths.setOnClickListener(v -> toAttendance("Maths"));
            computerGraphics.setOnClickListener(v -> toAttendance("ComputerGraphics"));
            dataStructures.setOnClickListener(v -> toAttendance("DataStructures"));
        } else {
            Intent intent = new Intent(SubjectSelector.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void toAttendance(String subject) {
        Intent intent = new Intent(SubjectSelector.this, Attendance.class);
        intent.putExtra("Subject", subject);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
