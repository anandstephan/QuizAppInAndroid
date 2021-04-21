package com.example.quizapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp.R;
import com.example.quizapp.helperClasses.AddCategory;
import com.example.quizapp.helperClasses.AddQuestion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowCategory extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("categories");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AddCategory addCategory = dataSnapshot1.getValue(AddCategory.class);
                    Log.d("steve", addCategory.getCategoryname());
                    makeButton(addCategory.getCategoryname(), addCategory.getId(),view);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }


    void makeButton(String btname, String id,View view) {
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.layout1);
        ArrayList arrayList = new ArrayList();
        Button btn = new Button(getActivity());
        btn.setText("" + btname);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference("categories");
                mDatabase.orderByChild("categoryname").equalTo(btname).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            AddCategory addCategory = dataSnapshot1.getValue(AddCategory.class);
                            Log.d("steveid",addCategory.getId());
                            arrayList.add(addCategory.getId());
                        }
//                        showAllQuestion(addCategory.getId(),view);
                        MakeLayout(arrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    void MakeLayout(ArrayList al){
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DisplayQuestion displayQuestion = new DisplayQuestion(al);
        ft.replace(R.id.content_frame, displayQuestion);
        ft.commit();

    }
}

