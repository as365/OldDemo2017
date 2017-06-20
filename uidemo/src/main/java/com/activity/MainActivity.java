package com.activity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tDiv = (ProgressToggleButton) findViewById(R.id.user_div);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.chengdu);
        mMediaPlayer.start();
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
                musicAsyTask = (MusicAsyTask) new MusicAsyTask().execute();
                Log.d("LOG", "finish");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (musicAsyTask != null) {
                    musicAsyTask.cancel(true);
                    musicAsyTask = null;
                    Log.d("LOG", "starttoch");
                }
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

    private MusicAsyTask musicAsyTask;

    public class MusicAsyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            publishProgress(getPercent(mMediaPlayer.getCurrentPosition(),
                    mMediaPlayer.getDuration()));
            try {
                Thread.sleep(1000);
                this.doInBackground();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            mSeekBar.setProgress(progress);
            super.onProgressUpdate(values);
        }

    }

    /**
     * 计算百分比
     *
     * @param progress
     *            当前进度
     * @param total
     *            总进度
     * @return
     */
    public int getPercent(int progress, int total) {
        double baiy = progress * 1.0;
        double baiz = total * 1.0;
        double fen = baiy / baiz;
        return (int) (fen * 100);
    }
}
