package com.example.lecturerapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddInformationActivity extends AppCompatActivity {
    private static  final String TAG ="AddInformationActivity";

    EditText mAddText;
    Button mAddButton;
    TextView mAddDate;
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;


    DatePickerDialog.OnDateSetListener mDateSetListener;
    Calendar calendar;
    String currentDate;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAddText=findViewById(R.id.AddText);
        mAddButton = findViewById(R.id.AddButton);
        mAddDate = findViewById(R.id.AddDate);
        databaseReference= FirebaseDatabase.getInstance().getReference("global");
        fAuth=FirebaseAuth.getInstance();






        mAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(AddInformationActivity.this,mDateSetListener,year,month,day);
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
    public void send(View view)
    {
        String body = mAddText.getText().toString().trim();
        String date = mAddDate.getText().toString().trim();
        String email=fAuth.getCurrentUser().getEmail();
        saveUserInformation(body,email,date );
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();

    }

    void saveUserInformation(String informationBody,String lecturerEmail,String informationDate) {

        String id= databaseReference.push().getKey();
        String fullName=NameHelper.getFullNamefromEmail(lecturerEmail);
          InformationToAdd information=new InformationToAdd(informationBody,fullName,informationDate);
          databaseReference.child(id).setValue(information);

    }







}
