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

public class Profile extends AppCompatActivity {

    private static final String TAG = "Profile";
    TextView rollNo, email;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.fragment_fourth);
            rollNo = findViewById(R.id.rollnoSVV);
            email = findViewById(R.id.college_email);
            final DocumentReference docRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getUid()));
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
                    rollNo.setText((String) userData.get("RollNo"));
                    email.setText((String) userData.get("Email"));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            });
        } else {
            Intent intent = new Intent(Profile.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}
