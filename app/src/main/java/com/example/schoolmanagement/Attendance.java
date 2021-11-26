package com.example.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

public class Attendance extends AppCompatActivity {

    private static final String TAG = "Attendance";
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView theory, practical, overall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.fragment_six);
            theory = findViewById(R.id.theoryattendance);
            practical = findViewById(R.id.practicalattendance);
            overall = findViewById(R.id.overallattendance);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String subject = extras.getString("Subject");
                Log.w(TAG, subject);
                final DocumentReference docRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser().getUid())).collection("Attendance").document(subject);
                docRef.addSnapshotListener((snapshot, e) -> {
                    Map<String, Object> userData;
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: " + snapshot.getData());
                        userData = snapshot.getData();
                        assert userData != null;
                        practical.setText((String) userData.get("Practical"));
                        theory.setText((String) userData.get("Theory"));
                        overall.setText((String) userData.get("Overall"));
                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                });
            }
        } else {
            Intent intent = new Intent(Attendance.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
