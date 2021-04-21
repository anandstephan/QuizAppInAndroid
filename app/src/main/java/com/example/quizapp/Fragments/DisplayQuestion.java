package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizapp.R;
import com.example.quizapp.helperClasses.AddCategory;
import com.example.quizapp.helperClasses.AddQuestion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayQuestion extends Fragment {
    ArrayList al;

    DisplayQuestion(ArrayList al) {
        this.al = al;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<al.size();i++) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("questions");
            mDatabase.orderByChild("id").equalTo(""+al.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                        AddQuestion addQuestion = dataSnapshot1.getValue(AddQuestion.class);
                        showQuestion(addQuestion,view);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }
    void showQuestion(AddQuestion addQuestion,View view){
        LinearLayout ll = view.findViewById(R.id.layout1);
        TextView tv = new TextView(getActivity());
        RadioGroup radioGroup = new RadioGroup(getActivity());
        RadioButton option1 = new RadioButton(getActivity());
        option1.setText("" + addQuestion.getOption1());
        RadioButton option2 = new RadioButton(getActivity());
        option2.setText("" + addQuestion.getOption2());
        RadioButton option3 = new RadioButton(getActivity());
        option3.setText("" + addQuestion.getOption3());
        RadioButton option4 = new RadioButton(getActivity());
        option4.setText("" + addQuestion.getOption4());
        tv.setText("" + addQuestion.getQuestion());
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        option1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        radioGroup.addView(option1);
        radioGroup.addView(option2);
        radioGroup.addView(option3);
        radioGroup.addView(option4);

        ll.addView(tv);
        ll.addView(radioGroup);
    }
}