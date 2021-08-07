package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizapp.R;
import com.example.quizapp.helperClasses.FinalResult;
import com.example.quizapp.helperClasses.SharedData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Result extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvattempt = view.findViewById(R.id.attempt);
        TextView tvwrongattempt = view.findViewById(R.id.wrongattempt);
        TextView tvnotattempt = view.findViewById(R.id.notattempt);
        TextView tvscore = view.findViewById(R.id.score);
        String id = SharedData.getShareData(getContext());
        ProgressBar pbresult = view.findViewById(R.id.pbresult);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("highscore").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getActivity(), "-->"+snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                Log.d("somenew",id+"--"+snapshot.getChildrenCount());
                if (snapshot.getChildrenCount() == 0) {
                    pbresult.setVisibility(View.INVISIBLE);

                } else {
//                tvresult.setText("0");
//                    Toast.makeText(getActivity(), "kkk"+snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                    tvattempt.setText(""+snapshot.getValue(FinalResult.class).getRight());
                    tvwrongattempt.setText(""+snapshot.getValue(FinalResult.class).getWrong());
                    tvnotattempt.setText(""+snapshot.getValue(FinalResult.class).getNotattempt());
                    tvscore.setText(""+snapshot.getValue(FinalResult.class).getScore());
//                    Toast.makeText(getActivity(), "---"+snapshot.getValue(FinalResult.class).getRight()+"--"+snapshot.getValue(FinalResult.class).getWrong()+"--"+snapshot.getValue(FinalResult.class).getNotattempt(), Toast.LENGTH_SHORT).show();

                    pbresult.setVisibility(View.INVISIBLE);
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getActivity().setTitle("Result");
    }
}
