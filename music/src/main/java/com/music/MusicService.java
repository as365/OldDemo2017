package com.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
    public MediaPlayer mediaPlayer;
    public boolean tag = false;
    public static final String MUSIC_URL ="http://liangchaojie.oss-cn-hangzhou.aliyuncs.com/chengdu.mp3" ;
    public MusicService() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(MUSIC_URL);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  通过 Binder 来保持 Activity 和 Service 的通信
    public MyBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public void playOrPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
