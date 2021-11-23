package com.example.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    //get data variables
    EditText rollNo, email, password, pass_verify;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_third);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //hooks for getting data
        rollNo = findViewById(R.id.editTextNumber);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword2);
        pass_verify = findViewById(R.id.editTextTextPassword3);
    }

    public void signUp(View view) {

        if (!validateEmail() | !validatePassword() | !validateRoll()) {
            return;
        }

        Intent intent = new Intent(SignUp.this, Home.class);
        Map<String, Object> user = new HashMap<>();
        user.put("Email", email.getText().toString().trim());
        user.put("RollNo", rollNo.getText().toString().trim());

        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnSuccessListener((AuthResult authResult) -> db.collection("Users").document(Objects.requireNonNull(authResult.getUser()).getUid()).set(user).addOnSuccessListener((Void _void) -> startActivity(intent)));


    }

    private boolean validateRoll() {
        String roll = Objects.requireNonNull(rollNo).getText().toString().trim();
        if (roll.isEmpty()) {
            rollNo.setError("Field cannot be empty");
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String val = Objects.requireNonNull(email).getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {

        String val = Objects.requireNonNull(password).getText().toString().trim();
        if (!val.equals(Objects.requireNonNull(pass_verify).getText().toString().trim())) {
            pass_verify.setError("Password Doesn't Match");
            return false;
        }
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";


        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!\nNo space allowed");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
