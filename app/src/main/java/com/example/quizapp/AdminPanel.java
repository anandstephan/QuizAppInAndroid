package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPanel extends AppCompatActivity {

    Button addcategory,addquestion,showcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        addcategory = findViewById(R.id.addcatgory);
        addquestion=findViewById(R.id.addquestion);
        showcategory=findViewById(R.id.showcategory);
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),AddCategory.class);
                startActivity(in);
            }
        });
    }
}