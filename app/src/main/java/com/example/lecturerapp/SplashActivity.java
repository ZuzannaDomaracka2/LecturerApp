package com.example.lecturerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fAuth= FirebaseAuth.getInstance();
        final FirebaseUser user =fAuth.getCurrentUser();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(user==null)
                {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }

        }
        }, 100);

    }
}
