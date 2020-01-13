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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mRegisterButton;
    TextView mLoginText;
    FirebaseAuth fAuth;
    EditText mFullName;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mEmail = findViewById(R.id.Email);
        mPassword=findViewById(R.id.Password);
        mRegisterButton= findViewById(R.id.RegisterButton);
        mLoginText=findViewById(R.id.LoginText);
        fAuth=FirebaseAuth.getInstance();








        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), AddInformationActivity.class));
            finish();
        }


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = mEmail.getText().toString().trim();
                String password =mPassword.getText().toString().trim();



                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Wymagany e-mail.");
                    return;

                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Wymagane hasło.");
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, " Nowe konto zostało utworzone ",Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, " Error ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });

        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
