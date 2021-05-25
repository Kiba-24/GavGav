package com.example.gavgav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    //ImageButton button_back, button_settings;
    Game game;
    public float leftVolume,rightVolume;
    public int screenW, screenH;
    GameThread gameThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView( R.layout.activity_main);
        //setContentView(new Game(this));
        //Log.d(LOG_TAG, "Main ");

        //setContentView( R.layout.activity_main);


        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        leftVolume = curVolume / maxVolume;
        rightVolume = curVolume / maxVolume;
        game = new Game(this, leftVolume, rightVolume);
        setContentView(game);


    }

    @Override
    protected void onResume() {
        super.onResume();
        int nc = getIntent().getIntExtra("newCoins", 0);
        game.newCoins = nc;
        Log.d("mon", ""+nc);
        gameThread = new GameThread(game);
        gameThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameThread.requestStop();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //ничего не работает :)
}
