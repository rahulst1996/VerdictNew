package com.example.administrator.verdict;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignIn extends AppCompatActivity {

    Button bad;
    EditText epass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        epass = findViewById(R.id.adminPass);
        bad = findViewById(R.id.loginAdmin);
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pass = epass.getText().toString();
                DatabaseReference admin = FirebaseDatabase.getInstance().getReference("admin");
                admin.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().equals(pass)){
                            Toast.makeText(AdminSignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),adminConsole.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(AdminSignIn.this, "You dont have permission to access admin console", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }


}
