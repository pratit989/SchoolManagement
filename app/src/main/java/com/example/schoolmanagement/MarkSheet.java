package com.example.schoolmanagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MarkSheet extends AppCompatActivity {

    private static final String TAG = "Attendance";
    TextView dataStructures, computerGraphics, maths;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.fragment_seven);
            dataStructures = findViewById(R.id.datastructuremarks);
            computerGraphics = findViewById(R.id.cgmarks);
            maths = findViewById(R.id.mathsmarks);
            Map<String, TextView> subjectMarks = new HashMap<>();
            subjectMarks.put("DataStructures", dataStructures);
            subjectMarks.put("ComputerGraphics", computerGraphics);
            subjectMarks.put("Maths", maths);

            subjectMarks.forEach((subject, marks) -> {
                final DocumentReference docRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser().getUid())).collection("Marks").document(subject);
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
                        marks.setText((String) userData.get("Percentage"));
                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                });
            });
        } else {
            Intent intent = new Intent(MarkSheet.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
