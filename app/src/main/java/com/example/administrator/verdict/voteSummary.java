package com.example.administrator.verdict;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class voteSummary extends AppCompatActivity {

    TextView q,v1,v2,v3,v4;
    String question,userid,o1,o2,o3,o4,vt1,vt2,vt3,vt4;
    FirebaseAuth firebaseAuth;
    HashMap hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_summary);
        q=findViewById(R.id.questiontxt);
        v1=findViewById(R.id.vote1txt);
        v2=findViewById(R.id.vote2txt);
        v3=findViewById(R.id.vote3txt);
        v4=findViewById(R.id.vote4txt);



        question= getIntent().getExtras().getString("Question");
        q.setText(question);

        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();

        final DatabaseReference quesAndOptions= FirebaseDatabase.getInstance().getReference("Questions").child(userid);
        final DatabaseReference votesCount=FirebaseDatabase.getInstance().getReference("All Votes").child(question);

        quesAndOptions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        hashMap= (HashMap) dataSnapshot1.getValue();
                        assert hashMap != null;
                        String a= (String) hashMap.get("Question");
                        if(question.equals(a)){
                            o1=hashMap.get("Option 1").toString();
                            o2=hashMap.get("Option 2").toString();
                            o3=hashMap.get("Option 3").toString();
                            o4=hashMap.get("Option 4").toString();
                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        votesCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { {
                        vt1=  dataSnapshot.child("Option 1").getValue().toString();
                        vt2=  dataSnapshot.child("Option 2").getValue().toString();
                        vt3=  dataSnapshot.child("Option 3").getValue().toString();
                        vt4=  dataSnapshot.child("Option 4").getValue().toString();

                        v1.setText(o1+ "   Votes   "+vt1);
                        v2.setText(o2+ "   Votes   "+vt2);
                        v3.setText(o3+ "   Votes   "+vt3);
                        v4.setText(o4+ "   Votes   "+vt4);

            }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
