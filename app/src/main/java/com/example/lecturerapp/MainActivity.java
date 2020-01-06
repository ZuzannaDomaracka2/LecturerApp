package com.example.lecturerapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static  final String TAG ="MainActivity";

    EditText mAddText;
    EditText mAddName;
    Button mAddButton;
    TextView mTextView7;
    TextView mAddDate;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;
    DatabaseReference ref;

    DatePickerDialog.OnDateSetListener mDateSetListener;


    Calendar calendar;
    String currentDate;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mTextView7=findViewById(R.id.textView7);
        mAddText=findViewById(R.id.AddText);
        mAddName = findViewById(R.id.AddName);
        mAddButton = findViewById(R.id.AddButton);
        mAddDate = findViewById(R.id.AddDate);

        databaseReference= FirebaseDatabase.getInstance().getReference("global");

        fAuth=FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        mTextView7.setText("Witaj,  " + user.getEmail());
        mAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(MainActivity.this,mDateSetListener,year,month,day);
                dialog.getWindow();
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int mmonth, int mdayOfMonth) {


                String nd="" +mdayOfMonth;
                String nm="" +mmonth;

                if(mdayOfMonth<10)
                {
                    nd="0"+mdayOfMonth;
                }
                if((mmonth+1) <10 )
                {
                    nm="0"+(mmonth+1);
                }
                else {
                    nm="" +(mmonth+1);
                }

                Log.d(TAG,"onDataSet: dd/mm/yyyy : " +nd + "/" + nm+ "/" +year );
                String date = nd + "/" + nm + "/" + year;

                mAddDate.setText(date);
            }
        };


        calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy/MM/dd");
        currentDate = simpleDateFormat.format(calendar.getTime());




    }

    void saveUserInformation() {


        String text = mAddText.getText().toString().trim();
        String name = mAddName.getText().toString().trim();
        String date = mAddDate.getText().toString().trim();




          String id= databaseReference.push().getKey();
          Information information=new Information(text,name,date,currentDate);
          databaseReference.child(id).setValue(information);



    }


    public void send(View view)
    {
        saveUserInformation();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),EndActivity.class));
        finish();

    }



}
