package com.example.schoolmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class TimeTable extends AppCompatActivity {

    private static final String TAG = "TimeTable";
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (mAuth.getCurrentUser() != null) {
            setContentView(R.layout.fragment_fifth);
            StorageReference timeTableRef = storageRef.child("TimeTable.jpg");
            ImageView timeTable = findViewById(R.id.timetableimage);
            Glide.with(this /* context */)
                    .load(timeTableRef).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(timeTable);
        } else {
            Intent intent = new Intent(TimeTable.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
