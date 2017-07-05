package com.activity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.uidemo.R;
import com.views.ProgressToggleButton;

public class MainActivity extends AppCompatActivity {

    private ProgressToggleButton tDiv;

    private MediaPlayer mMediaPlayer;

    private SeekBar mSeekBar;
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
            mSeekBar.setMax(mMediaPlayer.getDuration());
            int progress = getProgress(mMediaPlayer.getCurrentPosition(),mMediaPlayer.getDuration());
            tDiv.setProgress(progress);
            handler.postDelayed(runnable, 200);
        }
    };

    /**
     * 获取进度
     * @param currentPosition
     * @param duration
     * @return
     */
    private int getProgress(int currentPosition, int duration) {
        double progress = ((double) currentPosition / (double) duration);
        int result= (int) (progress*100);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agree_activity);
       // init();
    }

    private void init() {
        tDiv = (ProgressToggleButton) findViewById(R.id.user_div);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.chengdu);
        mMediaPlayer.start();
        handler.post(runnable);
        tDiv.setOnCheckChangesListener(new ProgressToggleButton.onCheckChangesListener() {

            @Override
            public void onchechkchanges(boolean isChecked) {
                Toast.makeText(MainActivity.this, "用户指定样式被点击,状态为" + isChecked,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    Log.d("LOG", progress + "");
                    mMediaPlayer.seekTo((progress * mMediaPlayer.getDuration()) / 100);
                    tDiv.setProgress(progress);
                }

            }
        });
    }

}
