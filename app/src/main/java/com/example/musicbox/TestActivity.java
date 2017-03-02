package com.example.musicbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Utils.Mp3Info;

import java.util.ArrayList;

public class TestActivity extends Activity {
    public static int position;
    public static ArrayList<Mp3Info> mp3Infos;
    Button bn;
    String[] titleStrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        bn = (Button) findViewById(R.id.bn);
        Intent intent1 = getIntent();
        position = intent1.getIntExtra("position", -1);
        mp3Infos = intent1.getParcelableArrayListExtra("list");
        titleStrs = new String[mp3Infos.size()];
        inflateTitleStrs();
        Toast.makeText(TestActivity.this, String.valueOf(mp3Infos.size()), Toast.LENGTH_LONG).show();
        Toast.makeText(TestActivity.this, mp3Infos.get(1).getArtist(), Toast.LENGTH_LONG).show();
//        Toast.makeText(TestActivity.this,String.valueOf(titleStrs.length), Toast.LENGTH_LONG).show();
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, Test2Activity.class);
                startActivity(intent);

            }
        });
    }


       public  void inflateTitleStrs() {
        for (int i = 0; i < mp3Infos.size(); i++) {
            titleStrs[i]=mp3Infos.get(i).getTitle();
        }
    }


}
