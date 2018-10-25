package com.example.administrator.verdict;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class addQuestion extends AppCompatActivity {

    EditText ques,opt1,opt2,opt3,opt4;
    Button save,brefresh;
    FirebaseAuth mauth;
    String q,o1,o2,o3,o4,userid;
    long i,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        ques=findViewById(R.id.question);
        opt1=findViewById(R.id.option1);
        opt2=findViewById(R.id.option2);
        opt3=findViewById(R.id.option3);
        opt4=findViewById(R.id.option4);
        save=findViewById(R.id.qsave);




        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countUserQuestions();
                countAllQuestions();
                if(i!=0 && number!=0){
                    createQuestion();

                }
               /* else {
                   // countAllQuestions();
                   // countUserQuestions();
                }*/
            }
        });
    }

    public void createQuestion() {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions").child(userid).child("User's Question " + i);
                q = ques.getText().toString();
                o1 = opt1.getText().toString();
                o2 = opt2.getText().toString();
                o3 = opt3.getText().toString();
                o4 = opt4.getText().toString();

                Map options = new HashMap();
                options.put("Question", q);
                options.put("Option 1", o1);
                options.put("Option 2", o2);
                options.put("Option 3", o3);
                options.put("Option 4", o4);
                databaseReference.setValue(options);
                DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("All Votes").child(q);

                databaseReference3.child("Option 1").setValue("0");
                databaseReference3.child("Option 2").setValue("0");
                databaseReference3.child("Option 3").setValue("0");
                databaseReference3.child("Option 4").setValue("0");


                final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("All questions").child("Question " + number);
                databaseReference2.setValue(options);

                ques.setText("");
                opt1.setText("");
                opt2.setText("");
                opt3.setText("");
                opt4.setText("");

            }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,homeScreen.class);
        startActivity(intent);
    }

    public void countUserQuestions(){
        DatabaseReference databaseReference0=FirebaseDatabase.getInstance().getReference().child("Questions").child(userid);
        databaseReference0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i=dataSnapshot.getChildrenCount()+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void countAllQuestions(){
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1=database.getReference("All questions");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    number = dataSnapshot.getChildrenCount()+1;
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
