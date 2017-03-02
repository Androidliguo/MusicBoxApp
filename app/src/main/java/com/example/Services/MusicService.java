package com.example.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.Utils.Mp3Info;
import com.example.musicbox.MusicActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 李果 on 2016/5/5.
 */
public class MusicService extends Service {
    ArrayList<Mp3Info> list=new ArrayList<>();
    MyReceiver serviceReceiver;
    AssetManager am;
    String[]  paths;
    String[] musics = new String[]{"wish.mp3", "promise.mp3", "beautiful.mp3"};
    MediaPlayer mPlayer;
    int status=0x11;
    int current=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        status=0x11;
        am=getAssets();
       current=MusicActivity.position;
        Log.d("--------current------", current+"");
       list=MusicActivity.mp3Infos;
       paths = new String[list.size()];
//        inflatePaths();
        serviceReceiver=new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicActivity.CTL_ACTION);
        registerReceiver(serviceReceiver, filter);
        mPlayer=new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;
                if (current >=3) {
                    current=0;
                }
                Intent sendIntent = new Intent(MusicActivity.UPDATE_ACTION);
                sendIntent.putExtra("current", current);
                sendBroadcast(sendIntent);
                prepareAndPlay2(musics[current]);

            }
        });
    }

    private void inflatePaths() {
        for (int i = 0; i < list.size(); i++) {
            paths[i]=list.get(i).getUrl();
        }
    }

    public class MyReceiver extends BroadcastReceiver {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                int control = intent.getIntExtra("control", -1);
                                switch (control) {
                                    case 1:
                                        if (status == 0x11) {
                                            prepareAndPlay2(musics[current]);
                        status=0x12;
                    }
                    else if (status == 0x12) {
                        mPlayer.pause();
                        status=0x13;
                    }
                    else if (status == 0x13) {
                        mPlayer.start();
                        status=0x12;
                    }
                    break;
                case 2:
                    if (status == 0x12 || status == 0x13) {
                        mPlayer.stop();
                        status=0x11;
                    }
                    break;
               /* case 3:
                    if (status == 0x11) {
                        above();
                        prepareAndPlay(paths[current]);
                        status=0x12;
                    }
                    else if (status == 0x12) {
                        mPlayer.pause();
                        above();
                        prepareAndPlay(paths[current]);
                        status=0x12;
                    }
                    else if (status == 0x13) {
                        above();
                        prepareAndPlay(paths[current]);
                        status=0x12;
                    }*/

            }
            Intent sendIntent = new Intent(MusicActivity.UPDATE_ACTION);
            sendIntent.putExtra("update", status);
            sendIntent.putExtra("current", current);
            sendBroadcast(sendIntent);
        }
    }


   /* private void above() {
        if (current < list.size() - 1) {
            current++;
        } else {
            current = 0;
        }
    }*/

    private void prepareAndPlay(String path) {
        try {
            mPlayer.reset();
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void prepareAndPlay2(String music) {
        try {
            AssetFileDescriptor afd = am.openFd(music);
            mPlayer.reset();
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}