package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizapp.helperClasses.SharedData;

public class AdminPanel extends AppCompatActivity {

    Button addcategory,startquiz,showcategory,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        addcategory = findViewById(R.id.addcatgory);
        showcategory=findViewById(R.id.showcategory);
        logout = findViewById(R.id.logout);
        startquiz = findViewById(R.id.startquiz);

        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),AddCategory.class);
                startActivity(in);
            }
        });
        showcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),ShowCategory.class);
                startActivity(in);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData.removedData(getApplicationContext());
                Intent in = new Intent(AdminPanel.this, MainActivity.class);
                startActivity(in);
            }
        });
        startquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),StartQuiz.class);
                startActivity(in);
            }
        });
    }
    @Override
    public void onBackPressed() {
            Toast.makeText(this, "Please use Logout Button", Toast.LENGTH_SHORT).show();

    }
}