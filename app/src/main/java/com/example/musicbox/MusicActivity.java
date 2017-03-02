package com.example.musicbox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Services.MusicService;
import com.example.Utils.Mp3Info;

import java.util.ArrayList;

public class MusicActivity extends Activity implements  View.OnClickListener{
    Intent intent;
    public static int position;
    public static ArrayList<Mp3Info> mp3Infos;
    TextView title, author;
    ImageButton play, stop;
   ActivityReceiver activityReceiver;
    public static final String CTL_ACTION = "org.crazyit.action.CTL_ACTION";
    public static final String UPDATE_ACTION = "org.crazyit.action.UPDATE_ACTION";
    int status=0x11;
    String[] titleStrs;
    String[] authorStrs;
    String[] titles=new String[]{"心愿","约定","美丽新世界"};
    String[] authors = new String[]{"未知艺术家", "周蕙", "伍佰"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Intent intent1=getIntent();
        position = intent1.getIntExtra("position", -1);
        mp3Infos = intent1.getParcelableArrayListExtra("list");
        Toast.makeText(MusicActivity.this, position + "", Toast.LENGTH_LONG).show();
        titleStrs = new String[mp3Infos.size()];
        authorStrs = new String[mp3Infos.size()];
         inflateTitleStrs();
       inflateAuthorStrs();
        play = (ImageButton) findViewById(R.id.play);
        stop = (ImageButton) findViewById(R.id.stop);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        activityReceiver=new ActivityReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        registerReceiver(activityReceiver, filter);
        Intent intent = new Intent(MusicActivity.this, MusicService.class);
        startService(intent);
    }
    private  void inflateTitleStrs() {
        for (int i = 0; i < mp3Infos.size(); i++) {
            titleStrs[i]=mp3Infos.get(i).getTitle();
        }
    }

    private  void inflateAuthorStrs() {
        for (int i = 0; i < mp3Infos.size(); i++) {
            authorStrs[i]=mp3Infos.get(i).getArtist();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent("org.crazyit.action.CTL_ACTION");
        switch (v.getId()) {
            case R.id.play:
                intent.putExtra("control", 1);
                break;
            case R.id.stop:
                intent.putExtra("control", 2);
                break;
        }
        sendBroadcast(intent);
    }


    public class ActivityReceiver extends BroadcastReceiver {
            @Override
        public void onReceive(Context context, Intent intent) {
            int update = intent.getIntExtra("update", -1);
            int current = intent.getIntExtra("current", -1);
            if (current>=0) {
                title.setText(titles[current]);
                author.setText(authors[current]);

            }
            switch (update) {
                case 0x11:
                    play.setImageResource(R.drawable.play);
                    status=0x11;
                    break;
                case 0x12:
                    play.setImageResource(R.drawable.pause);
                    status=0x12;
                    break;
                case 0x13:
                    play.setImageResource(R.drawable.play);
                    status=0x13;
                    break;
            }
        }
    }
}
