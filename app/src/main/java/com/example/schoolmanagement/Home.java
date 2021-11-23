package com.example.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.fragment_second);
        } else {
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void logOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(Home.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void profile(View view) {
        Intent intent = new Intent(Home.this, Profile.class);
        startActivity(intent);
    }

}