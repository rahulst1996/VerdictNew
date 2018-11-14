package com.example.administrator.verdict;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class approveQues extends AppCompatActivity {

    TextView t, t1, t2, t3, t4;
    Button b;
    String ques, quesNumber;
    HashMap s1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_ques);

        t = findViewById(R.id.questiontxt);
        t1 = findViewById(R.id.top1);
        t2 = findViewById(R.id.top2);
        t3 = findViewById(R.id.top3);
        t4 = findViewById(R.id.top4);
        b = findViewById(R.id.bapp);
        ques = getIntent().getExtras().getString("question");
        t.setText(ques);

        final DatabaseReference showAllQuestion = FirebaseDatabase.getInstance().getReference("All questions");
        showAllQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    s1 = (HashMap) dataSnapshot1.getValue();
                    assert s1 != null;
                    if (s1.get("Question").equals(ques)) {
                        //Log.d("loda",dataSnapshot1.getKey());
                        quesNumber = dataSnapshot1.getKey();
                        String o1 = s1.get("Option 1").toString();
                        String o2 = s1.get("Option 2").toString();
                        String o3 = s1.get("Option 3").toString();
                        String o4 = s1.get("Option 4").toString();

                        if (!o1.equals("")) {
                            t1.setVisibility(View.VISIBLE);
                            t1.setText(o1);
                        }
                        if (!o2.equals("")) {
                            t2.setVisibility(View.VISIBLE);
                            t2.setText(o2);
                        }
                        if (!o3.equals("")) {
                            t3.setVisibility(View.VISIBLE);
                            t3.setText(o3);
                        }
                        if (!o4.equals("")) {
                            t4.setVisibility(View.VISIBLE);
                            t4.setText(o4);
                        }
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approve();
            }
        });

    }

    public void approve() {
        final DatabaseReference showAllQuestion1 = FirebaseDatabase.getInstance().getReference("All questions").child(quesNumber).child("visible");
        showAllQuestion1.setValue("Yes");
        Intent intent = new Intent(this,adminConsole.class);
        startActivity(intent);
    }
}