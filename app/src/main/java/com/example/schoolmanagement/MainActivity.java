package com.example.schoolmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //variables
    ImageView backgroundImage;
    TextView poweredByLine;

    //animations
    Animation sideAnim, bottomAnim;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        int SPLASH_TIMER = 1000;
        new Handler().postDelayed(() -> {


            onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

            if (user != null) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            } else {

                SharedPreferences.Editor  editor = onBoardingScreen.edit();
                editor.putBoolean("firstTime",false);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
            finish();

        }, SPLASH_TIMER);

    }
}