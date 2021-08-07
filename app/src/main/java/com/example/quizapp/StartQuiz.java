package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quizapp.helperClasses.AddCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StartQuiz extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("categories");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        ProgressBar pbshow = findViewById(R.id.pbshow);
        // Read from the database

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    AddCategory addCategory = dataSnapshot1.getValue(AddCategory.class);
                    Log.d("steve",addCategory.getCategoryname());
                    makeButton(addCategory.getCategoryname(),addCategory.getId(),addCategory.getTotalquestion());
                }
                pbshow.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    void makeButton(String btname,String id,String totalquestion){
        LinearLayout ll = findViewById(R.id.layout);
        Button btn = new Button(this);
        btn.setText(""+btname);
        btn.setBackgroundResource(R.color.orange);
        btn.setTextColor(Color.parseColor("#FFFFFF"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,40,0,0);
        btn.setLayoutParams(params);
        ll.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent(StartQuiz.this,AddQuestion.class);
//                in.putExtra("id",id);
//                in.putExtra("totalquestion",totalquestion);
//                startActivity(in);
                HashMap<String,Object> result = new HashMap<>();
                result.put("quizstatus",true);
                myRef.child(id).updateChildren(result);
                Toast.makeText(StartQuiz.this, "Quiz Started from the user Side", Toast.LENGTH_SHORT).show();
            }
        });
    }
}