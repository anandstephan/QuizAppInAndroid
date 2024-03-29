package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.helperClasses.AddUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText fname,lname,pno,clgname,password;
    Button signup,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        pno = findViewById(R.id.pno);
        password = findViewById(R.id.pass);
        clgname = findViewById(R.id.clg);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                String MobilePattern = "[0-9]{10}";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (fname.getText().toString().isEmpty()) {
                    fname.setError("First Name Can't be blank");
                } else if (lname.getText().toString().isEmpty()) {
                    lname.setError("Last Name Can't be blank");
                } else if (pno.getText().toString().isEmpty()) {
                    pno.setError("Phone Number Can't be blank");
                } else if (!pno.getText().toString().matches(MobilePattern)) {
                    pno.setError("Please insert a valid Mobile Number");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Password Can't be blank");
                } else if (clgname.getText().toString().isEmpty()) {
                    clgname.setError("College Name Can't be blank");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users");
                    String key = myRef.push().getKey();
                    AddUser addUser = new AddUser(key, fname.getText().toString(), lname.getText().toString(), pno.getText().toString(), clgname.getText().toString(), pno.getText().toString() + password.getText().toString(), password.getText().toString());
                    myRef.child(key).setValue(addUser);
                    fname.setText("");
                    lname.setText("");
                    pno.setText("");
                    clgname.setText("");
                    password.setText("");
                    Toast.makeText(MainActivity.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, Login.class);
                startActivity(in);
            }
        });
    }
}