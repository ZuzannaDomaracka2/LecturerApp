package com.example.lecturerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView textView;
    TextView textViewAll;
    RecyclerView mRecyclerView;
    ArrayList<Information> list;
    Adapter adapter;
    DatabaseReference ref;
    DatabaseReference mdatabaseref;
    FirebaseAuth fAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.add_item)
        {
            startActivity(new Intent(getApplicationContext(), AddInformationActivity.class));

            return true;
        }
        if(id==R.id.logout_item)
        {
            fAuth.signOut();

            startActivity(new Intent(getApplicationContext(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fAuth =FirebaseAuth.getInstance();



        getSupportActionBar().setTitle("Witaj," +NameHelper.getFullNamefromEmail(fAuth.getCurrentUser().getEmail()));


        mRecyclerView = findViewById(R.id.myRecyclerview);
        textView = findViewById(R.id.textListNull);
        textViewAll=findViewById(R.id.textListAll);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Information>();

        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("global");
        ref = FirebaseDatabase.getInstance().getReference();

        adapter = new Adapter(HomeActivity.this, list, new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Information item) {

                Query query = ref.child("global").orderByKey().startAt(item.id).endAt(item.id);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                            dataSnapshot1.getRef().removeValue();
                            list.remove(item);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(HomeActivity.this, "Usunięto ",Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


        });
        mRecyclerView.setAdapter(adapter);


        mdatabaseref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list.clear();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String text = (String) dataSnapshot1.child("body").getValue();
                    String name = (String) dataSnapshot1.child("lecturerName").getValue();
                    String date = (String) dataSnapshot1.child("date").getValue();
                    String key = dataSnapshot1.getKey();
                    Information information = new Information(key, text, name, date);
                    textView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if(information.getLecturerName().equals(NameHelper.getFullNamefromEmail(fAuth.getCurrentUser().getEmail()))) {

                        list.add(0, information);
                    }


                }

                adapter.notifyDataSetChanged();


                if (list.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    textViewAll.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }

}
