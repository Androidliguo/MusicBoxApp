package com.example.musicbox;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.Utils.Mp3Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        list = (ListView) findViewById(R.id.music_list);
        setListAdapter();

    }

    public ArrayList<Mp3Info> getMp3Infos() {
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        ArrayList<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
        while (cursor.moveToNext()) {
            Mp3Info mp3Info = new Mp3Info();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic != 0) {
                mp3Info.setId(id);
                mp3Info.setSize(size);
                mp3Info.setDuration(duration);
                mp3Info.setArtist(artist);
                mp3Info.setTitle(title);
                mp3Info.setUrl(url);
                mp3Infos.add(mp3Info);
            }

        }
        return mp3Infos;
    }

    public void setListAdapter() {
        final ArrayList<Mp3Info> mp3Infos = getMp3Infos();
        List<Map<String, Object>> mp3List = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < mp3Infos.size(); i++) {
            Mp3Info mp3Info = mp3Infos.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("size", mp3Info.getSize());
            long hour = mp3Info.getDuration() / 3600000;
            long minute = (mp3Info.getDuration() - hour * 3600000) / 60000;
            long ms = mp3Info.getDuration() % 60000;
            map.put("duration", hour + ":" + minute + ":" + ms);
            map.put("artist", mp3Info.getArtist());
            map.put("title", mp3Info.getTitle());
            map.put("url", mp3Info.getUrl());
            mp3List.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, mp3List, R.layout.line, new String[]{"title", "artist", "duration"}, new int[]{R.id.title, R.id.artist, R.id.duration});
        list.setAdapter(simpleAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

