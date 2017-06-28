package com.music;

import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/6/28.
 */

public class MediaOnePlayer extends MediaPlayer {
    private static class SingletonHolder {
        private static final MediaOnePlayer INSTANCE = new MediaOnePlayer();
    }
    private MediaOnePlayer (){}
    public static  MediaOnePlayer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initPlayer(String url){
        try {
            this.reset();
            this.setDataSource(url);
            this.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置ImageView暂停和播放的图片
     * @param view
     * @param pause
     * @param playing
     */
    public void playOrPause(ImageView view,int pause,int playing){
        if (this.isPlaying()) {
            this.pause();
            view.setImageResource(playing);
        } else {
            this.start();
            view.setImageResource(pause);
        }
    }

    /**
     * 设置Button暂停和播放图片
     * @param view
     */
    public void playOrPause(Button view){
        if (this.isPlaying()) {
            this.pause();
            view.setText("播放");
        } else {
            this.start();
            view.setText("暂停");
        }
    }

}
