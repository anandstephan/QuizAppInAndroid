package com.example.quizapp.helperClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedData {
    public static  SharedPreferences pref;
    public static  void savedSharedData(Context mctx,String id){
        pref = mctx.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userid",""+id);
        Log.d("newthing",""+id);
        editor.commit();
    }

    public  static String getShareData(Context mctx){
        pref = mctx.getSharedPreferences("MyPref", MODE_PRIVATE);
        String userid = pref.getString("userid",null);
        if(userid!=null){
            return userid;
        }else{
            return "false";
        }
    }

    public static void removedData(Context context){
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        Log.d("timepass","");
    }
}
