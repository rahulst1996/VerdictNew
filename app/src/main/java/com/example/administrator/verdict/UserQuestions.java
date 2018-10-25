package com.example.administrator.verdict;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserQuestions extends AppCompatActivity {

    ListView userQuesList;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth firebaseAuth;
    String userid,ques,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);

        userQuesList=findViewById(R.id.quesList);

        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        userQuesList.setAdapter(arrayAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference getUserQustions= FirebaseDatabase.getInstance().getReference("Questions").child(userid);
        getUserQustions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(!dataSnapshot1.getKey().equals("Votes")){
                    HashMap hashMap= (HashMap) dataSnapshot1.getValue();
                    ques= (String) hashMap.get("Question");
                    arrayList.add(ques);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userQuesList.setClickable(true);
        userQuesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                title=adapterView.getItemAtPosition(i).toString();
                Intent intent= new Intent(UserQuestions.this,voteSummary.class);
                intent.putExtra("Question",title);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,homeScreen.class);
        startActivity(intent);
    }
}
