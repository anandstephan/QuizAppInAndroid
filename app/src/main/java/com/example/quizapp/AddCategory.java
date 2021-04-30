package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class
AddCategory extends AppCompatActivity {
    EditText categoryname,totalmarks,totalquestion;
    Button btn_add_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        categoryname = findViewById(R.id.categoryname);
        totalmarks = findViewById(R.id.totalmarks);
        totalquestion = findViewById(R.id.totalquestion);
        btn_add_category = findViewById(R.id.btnaddcategory);
        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("categories");
                String key = myRef.push().getKey();
                com.example.quizapp.helperClasses.AddCategory addCategory = new com.example.quizapp.helperClasses.AddCategory(key,categoryname.getText().toString(),totalmarks.getText().toString(),totalquestion.getText().toString());
                myRef.child(key).setValue(addCategory);
                Toast.makeText(AddCategory.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                categoryname.setText("");
                totalmarks.setText("");
                totalquestion.setText("");
            }

        });
    }
}