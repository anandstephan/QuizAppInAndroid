package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quizapp.helperClasses.AddCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        String id = getIntent().getStringExtra("id");
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference("questions");
        EditText question,option1,option2,option3,option4,rightanswer,marks;
        Button btnsumbit ;
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        rightanswer = findViewById(R.id.rightanswer);
        marks = findViewById(R.id.marks);
        btnsumbit =  findViewById(R.id.btnsubmit);
        btnsumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.quizapp.helperClasses.AddQuestion addQuestion = new com.example.quizapp.helperClasses.AddQuestion(id,question.getText().toString(),option1.getText().toString(),option2.getText().toString(),option3.getText().toString(),option4.getText().toString(),rightanswer.getText().toString(),marks.getText().toString());
                String key = mDatabase.push().getKey();
                mDatabase.child(key).setValue(addQuestion);
                Toast.makeText(AddQuestion.this, "Question Added Successfully", Toast.LENGTH_SHORT).show();
                question.setText("");
                option1.setText("");
                option3.setText("");
                option2.setText("");
                option4.setText("");
                rightanswer.setText("");

            }
        });
//        mDatabase.orderByChild("id").equalTo(id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    AddCategory addCategory = dataSnapshot.getValue(com.example.quizapp.helperClasses.AddCategory.class);
//                    int k=0;
//                    for(int i=1;i<=Integer.parseInt(addCategory.getTotalquestion());i++){
//                        MakeLayout(i);
//                        if(i==Integer.parseInt(addCategory.getTotalquestion())){
//                            k=1;
//                        }
//                        if(k==1){
//                        MakeSubmitButton();
//                            k=0;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
    void MakeLayout( int i) {
        LinearLayout ll = (LinearLayout)findViewById(R.id.layout);
        EditText editText = new EditText(getApplicationContext());
        editText.setHint("Enter Your Question "+i);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(editText);
        EditText editText1 = new EditText(getApplicationContext());
        editText1.setHint("Enter Your Option 1");
        editText1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(editText1);
        EditText editText2 = new EditText(getApplicationContext());
        editText2.setHint("Enter Your Option 2");
        editText2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(editText2);
        EditText editText3 = new EditText(getApplicationContext());
        editText3.setHint("Enter Your Option 3");
        editText3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(editText3);
        EditText editText4 = new EditText(getApplicationContext());
        editText4.setHint("Enter Your Option 4");
        editText4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(editText4);
        EditText editText5 = new EditText(getApplicationContext());
        editText5.setHint("Enter the Right Answer");
        editText5.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(editText5);

        View viewDivider = new View(getApplicationContext());
        viewDivider.setLayoutParams(new LinearLayout.LayoutParams(25, 45));
        viewDivider.setBackgroundColor(Color.RED);
        ll.addView(viewDivider);
    }

    void MakeSubmitButton(){
        LinearLayout ll = (LinearLayout)findViewById(R.id.layout);
        Button btn = new Button(getApplicationContext());
        btn.setText("Submit");
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = findViewById(R.id.layout);
                Toast.makeText(AddQuestion.this, ""+linearLayout.getChildCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}