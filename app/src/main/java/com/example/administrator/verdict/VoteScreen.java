package com.example.administrator.verdict;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VoteScreen extends AppCompatActivity {

    TextView txtques;
    String ques, userid, quesvote;
    Button bo1, bo2, bo3, bo4;
    FirebaseAuth firebaseAuth;
    HashMap s1;
    Intent intent;
    int count, o1, o2, o3, o4;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_screen);

        txtques = findViewById(R.id.questiontxt);
        bo1 = findViewById(R.id.bop1);
        bo2 = findViewById(R.id.bop2);
        bo3 = findViewById(R.id.bop3);
        bo4 = findViewById(R.id.bop4);
        ques = getIntent().getExtras().getString("question");
        txtques.setText(ques);

        DatabaseReference showAllQuestion = FirebaseDatabase.getInstance().getReference("All questions");
        showAllQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    s1 = (HashMap) dataSnapshot1.getValue();
                    assert s1 != null;
                    if (s1.get("Question").equals(ques)) {
                        quesvote = dataSnapshot1.getKey();
                        String o1 = s1.get("Option 1").toString();
                        String o2 = s1.get("Option 2").toString();
                        String o3 = s1.get("Option 3").toString();
                        String o4 = s1.get("Option 4").toString();

                        if (!o1.equals("")) {
                            bo1.setVisibility(View.VISIBLE);
                            bo1.setText(o1);
                        }
                        if (!o2.equals("")) {
                            bo2.setVisibility(View.VISIBLE);
                            bo2.setText(o2);
                        }
                        if (!o3.equals("")) {
                            bo3.setVisibility(View.VISIBLE);
                            bo3.setText(o3);
                        }
                        if (!o4.equals("")) {
                            bo4.setVisibility(View.VISIBLE);
                            bo4.setText(o4);
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference setVoteDefaultValue = FirebaseDatabase.getInstance().getReference("Questions").child(userid).child("Votes").child(ques);
        setVoteDefaultValue.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setVoteDefaultValue.setValue(true);
                        votesCount(ques, 1);
                        intent = new Intent(getApplicationContext(), showQuestions.class);
                        startActivity(intent);
                    }
                });
                bo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setVoteDefaultValue.setValue(true);
                        votesCount(ques, 2);
                        intent = new Intent(getApplicationContext(), showQuestions.class);
                        startActivity(intent);
                    }
                });
                bo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setVoteDefaultValue.setValue(true);
                        votesCount(ques, 3);
                        intent = new Intent(getApplicationContext(), showQuestions.class);
                        startActivity(intent);
                    }
                });

                bo4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setVoteDefaultValue.setValue(true);
                        votesCount(ques, 4);
                        intent = new Intent(getApplicationContext(), showQuestions.class);
                        startActivity(intent);
                    }
                });

        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void votesCount(String question, final int i) {
        final DatabaseReference getVoteCount = FirebaseDatabase.getInstance().getReference("All Votes").child(question);
        getVoteCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (i == 1) {
                    String s = (String) dataSnapshot.child("Option 1").getValue();
                    int a= Integer.parseInt(s);
                    a=a+1;
                    String s2= String.valueOf(a);
                    dataSnapshot.child("Option 1").getRef().setValue(s2);

                }
                else if(i==2)
                {
                    String s = (String) dataSnapshot.child("Option 2").getValue();
                    int a= Integer.parseInt(s);
                    a=a+1;
                    String s2= String.valueOf(a);
                    dataSnapshot.child("Option 2").getRef().setValue(s2);
                }
                else if(i==3)
                {
                    String s = (String) dataSnapshot.child("Option 3").getValue();
                    int a= Integer.parseInt(s);
                    a=a+1;
                    String s2= String.valueOf(a);
                    dataSnapshot.child("Option 3").getRef().setValue(s2);

                }
                else
                {
                    String s = (String) dataSnapshot.child("Option 4").getValue();
                    int a= Integer.parseInt(s);
                    a=a+1;
                    String s2= String.valueOf(a);
                    dataSnapshot.child("Option 4").getRef().setValue(s2);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(VoteScreen.this,showQuestions.class);
        startActivity(intent);
    }
}