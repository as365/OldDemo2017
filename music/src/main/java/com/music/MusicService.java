package com.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

public class MusicService extends Service {
    public MediaOnePlayer mediaPlayer=MediaOnePlayer.getInstance();
    public boolean tag = false;
    public MusicService() {
    }

    //  通过 Binder 来保持 Activity 和 Service 的通信
    public MyBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        MusicService getService(String url) {
            mediaPlayer.initPlayer(url);
            return MusicService.this;
        }
    }

    public void playOrPause(Button b) {
        mediaPlayer.playOrPause(b);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }
}
