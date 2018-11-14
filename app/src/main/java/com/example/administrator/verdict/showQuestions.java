package com.example.administrator.verdict;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class showQuestions extends AppCompatActivity {

    ListView showData;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    int i=1;
    String userid,ques,s;
    FirebaseAuth firebaseAuth;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);
        showData=findViewById(R.id.list);

        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        showData.setAdapter(arrayAdapter);
        showData.setClickable(true);

        showData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, final int i, long l) {

                    ques= (String) adapterView.getItemAtPosition(i);
                    check(ques);
                    if(flag) {
                        Intent intent = new Intent(showQuestions.this, VoteScreen.class);
                        intent.putExtra("question", ques);
                        startActivity(intent);
                    }
                   // else
                     //   Toast.makeText(showQuestions.this, "Already Voted", Toast.LENGTH_SHORT).show();
            }
        });



        DatabaseReference putAllQuestiond=FirebaseDatabase.getInstance().getReference("All questions");
        putAllQuestiond.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    HashMap s1= (HashMap) child.getValue();
                    assert s1 != null;
                    String status = (String) s1.get("visible");
                    if(status.equals("Yes")){
                        s = (String) s1.get("Question");
                        arrayList.add(String.valueOf(s));
                   }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,homeScreen.class);
        startActivity(intent);
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
