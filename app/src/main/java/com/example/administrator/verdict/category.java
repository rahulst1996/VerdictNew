package com.example.administrator.verdict;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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

public class category extends AppCompatActivity {

String categoryItem;

ArrayList questionsList;
ArrayAdapter adapter;
ListView categoryList;
int i=1;
String userid,ques,s;
FirebaseAuth firebaseAuth;
boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,questionsList);

        categoryList.setAdapter(adapter);
        categoryList.setClickable(true);

        DatabaseReference showCategory= FirebaseDatabase.getInstance().getReference("All questions");
        showCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    HashMap category = (HashMap) child.getValue();
                    assert category != null;
                    categoryItem = (String) category.get("Categories");
                    if(categoryItem.equals("CategoryString"))
                            {
                                questionsList.add(category.get("Question"));
                            }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                ques= (String) adapterView.getItemAtPosition(i);
                check(ques);
                if(flag) {
                    Intent intent = new Intent(category.this, VoteScreen.class);
                    intent.putExtra("question", ques);
                    startActivity(intent);
                }
                // else
                //   Toast.makeText(showQuestions.this, "Already Voted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void check(String string) {

        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference checkVote = FirebaseDatabase.getInstance().getReference("Questions").child(userid).child("Votes").child(string);
        checkVote.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null)
                    flag=true;
                else {
                    flag = false;
                    //Toast.makeText(showQuestions.this, "Already voted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
