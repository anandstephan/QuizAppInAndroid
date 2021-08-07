package com.example.quizapp.Fragments;

import android.annotation.SuppressLint;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp.R;
import com.example.quizapp.helperClasses.AddCategory;
import com.example.quizapp.helperClasses.AddQuestion;
import com.example.quizapp.helperClasses.FinalResult;
import com.example.quizapp.helperClasses.SharedData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

public class DisplayQuestion extends Fragment {
    ArrayList al;
    int test = 0;
    double perquestionmark;
    DisplayQuestion(ArrayList al,double perquestionmark) {
        this.al = al;
        this.perquestionmark = perquestionmark;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar pb = view.findViewById(R.id.dispquestion);
        TextView timer = view.findViewById(R.id.timer);
        ArrayList rightanswer = new ArrayList();
        for(int i=0;i<al.size();i++) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("questions");

            int finalI = i;
            mDatabase.orderByChild("id").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    FirebaseDatabase.getInstance().getReference("categories").child(""+al.get(finalI)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for (DataSnapshot dataSnapshot2: snapshot1.getChildren()){
//                                AddCategory addCategory = dataSnapshot2.getValue(AddCategory.class);
                                if(dataSnapshot2.getValue().equals(true)){
                                    test =1;
                                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                        AddQuestion addQuestion = dataSnapshot1.getValue(AddQuestion.class);
                                        showQuestion(addQuestion,view);
                                        rightanswer.add(addQuestion.getRightanswer());
                                    }
                                }
                                else if(dataSnapshot2.getValue().equals(false)){
                                    Toast.makeText(getActivity(), "Quiz is not Start now please contact admin", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        Button btnsubmit = view.findViewById(R.id.submit);
        ArrayList selectedanswer = new ArrayList();


        new CountDownTimer(2400000, 1000) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to edittext
//                Toast.makeText(getContext(), "Ticking!!!", Toast.LENGTH_SHORT).show();
                if (test == 1) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    timer.setText("TIME LEFT : " + String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds));
                }else{
                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                }
            }


            public void onFinish() {
//                Toast.makeText(getContext(), "Finish----", Toast.LENGTH_SHORT).show();

                Submit(view,rightanswer,selectedanswer);
            }

        }.start();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Submit(view,rightanswer,selectedanswer);
//                Toast.makeText(getActivity(), "!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @SuppressLint("ResourceAsColor")
    void showQuestion(AddQuestion addQuestion, View view){
        LinearLayout ll = view.findViewById(R.id.layout1);
        TextView tv = new TextView(getActivity());
        tv.setTextSize(30);
        tv.setTextColor(getResources().getColor(R.color.orange));
        RadioGroup radioGroup = new RadioGroup(getActivity());
        RadioButton option1 = new RadioButton(getActivity());
        option1.setTextColor(getResources().getColor(R.color.orange));
        option1.setText("" + addQuestion.getOption1());
        RadioButton option2 = new RadioButton(getActivity());
        option2.setText("" + addQuestion.getOption2());
        option2.setTextColor(getResources().getColor(R.color.orange));
        RadioButton option3 = new RadioButton(getActivity());
        option3.setText("" + addQuestion.getOption3());
        option3.setTextColor(getResources().getColor(R.color.orange));
        RadioButton option4 = new RadioButton(getActivity());
        option4.setText("" + addQuestion.getOption4());
        option4.setTextColor(getResources().getColor(R.color.orange));
        tv.setText("" + addQuestion.getQuestion());
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        option1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        option1.setTextSize(20);
        option2.setTextSize(20);
        option3.setTextSize(20);
        option4.setTextSize(20);
        radioGroup.addView(option1);
        radioGroup.addView(option2);
        radioGroup.addView(option3);
        radioGroup.addView(option4);
        ll.addView(tv);
        ll.addView(radioGroup);

    }

    public  void Submit(View view,ArrayList rightanswer,ArrayList selectedanswer){
        SharedData sd = new SharedData();
        LinearLayout ll = view.findViewById(R.id.layout1);

        for(int i=0;i<ll.getChildCount();i++){
            View element = ll.getChildAt(i);
            if(element instanceof RadioGroup) {
                int radioButtonID = ((RadioGroup) element).getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) element.findViewById(radioButtonID);
                if(!(radioButton instanceof RadioButton)) {
//                    Toast.makeText(getActivity(), "!!!chaman!!", Toast.LENGTH_SHORT).show();
                    selectedanswer.add("notattempt");
                }else {
                    String selectedtext = (String) radioButton.getText();
                    selectedanswer.add(selectedtext);
                }
            }
        }
        int score=0;
        int notattempt = 0;
        int wrong=0;
        int right=0;
//        Log.d("kyahua",""+rightanswer.size());
        for(int i=0;i<rightanswer.size();i++){
//            Toast.makeText(getContext(), "TEST khatam!!!", Toast.LENGTH_SHORT).show();
           Log.d("steve",rightanswer.get(i)+"--=-"+selectedanswer.get(i));
            if(rightanswer.get(i).equals(selectedanswer.get(i))){
                score= (int) (score+perquestionmark);//update this score on user tree
                right++;
            }else if(selectedanswer.get(i).toString()=="notattempt"){
                notattempt++;
            }else{
                wrong++;
            }
        }
        String id = sd.getShareData(getActivity());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("highscore");
        FinalResult finalResult = new FinalResult(""+score,""+notattempt,""+wrong,""+right);
        mDatabase.child(id).setValue(finalResult);
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Result result = new Result();
            ft.replace(R.id.content_frame, result);
            ft.commit();

    }


}