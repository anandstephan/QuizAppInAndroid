package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizapp.helperClasses.AddUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText fname,lname,pno,clgname;
    Button signup,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        pno = findViewById(R.id.pno);
        clgname = findViewById(R.id.clg);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                String key = myRef.push().getKey();
                AddUser addUser = new AddUser(key,fname.getText().toString(),lname.getText().toString(),pno.getText().toString(),clgname.getText().toString());
                myRef.child(key).setValue(addUser);
            }
        });
    }
}