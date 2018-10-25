package com.example.administrator.verdict;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homeScreen extends AppCompatActivity {

    TextView tname;
    FirebaseAuth mauth;
    String name;
    Toolbar t;
    Button postButton,voteButton,userCreatedQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        t=findViewById(R.id.tool);
        postButton=findViewById(R.id.postbtn);
        voteButton=findViewById(R.id.votebtn);
        userCreatedQuestions=findViewById(R.id.myQuesbtn);
        setSupportActionBar(t);

        mauth=FirebaseAuth.getInstance();
        String userid=mauth.getCurrentUser().getUid();
        DatabaseReference current_userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        current_userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.child("Name").getValue().toString();
                t.setTitle("Welcome "+ name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(homeScreen.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(homeScreen.this,addQuestion.class);
                startActivity(intent);
            }
        });

        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(homeScreen.this,showQuestions.class);
                startActivity(intent);
            }
        });

        userCreatedQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(homeScreen.this,UserQuestions.class);
                startActivity(intent);
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i= item.getItemId();
        switch (i){
            case R.id.itm1:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(homeScreen.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.itm2:
               /// Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                break;

            case R.id.itm3:
                break;

        }
        return true;


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
