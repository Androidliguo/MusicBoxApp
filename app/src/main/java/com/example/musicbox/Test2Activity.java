package com.example.musicbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.Utils.Mp3Info;

import java.util.ArrayList;

public class Test2Activity extends Activity {
    ArrayList<Mp3Info> list;
    int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        list=TestActivity.mp3Infos;
        current=TestActivity.position;
        Toast.makeText(Test2Activity.this, String.valueOf(current), Toast.LENGTH_LONG).show();
        Toast.makeText(Test2Activity.this, String.valueOf(list.size()), Toast.LENGTH_LONG).show();
    }

}
