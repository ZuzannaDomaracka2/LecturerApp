package com.example.lecturerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEmailL;
    EditText mPasswordL;
    Button mSendButtonL;
    TextView mRegisterTextL;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mEmailL=findViewById(R.id.EmailL);
        mPasswordL=findViewById(R.id.PasswordL);
        mSendButtonL= findViewById(R.id.SendButtonL);
        mRegisterTextL=findViewById(R.id.RegisterTextL);
        fAuth=FirebaseAuth.getInstance();

        mSendButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailL.getText().toString().trim();
                String password =mPasswordL.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    mEmailL.setError("Wymagany e-mail.");
                    return;

                }
                if(TextUtils.isEmpty(password))
                {
                    mPasswordL.setError("Wymagane hasło.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Toast.makeText(Login.this, " Zalogowano poprawnie  ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this, " Login lub hasło jest niepoprawne  " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        mRegisterTextL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}
