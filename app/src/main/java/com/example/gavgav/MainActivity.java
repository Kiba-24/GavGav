package com.example.gavgav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
<<<<<<< Updated upstream
import android.util.Log;
=======
import android.util.DisplayMetrics;
import android.view.Display;
>>>>>>> Stashed changes
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        setContentView( R.layout.activity_main);
        //setContentView(new Game(this));
        //Log.d(LOG_TAG, "Main ");
=======
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        screenW = metricsB.widthPixels;
        screenH = metricsB.heightPixels;
        //setContentView( R.layout.activity_main);
        setContentView(new Game(this, leftVolume, rightVolume));

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        leftVolume = curVolume / maxVolume;
        rightVolume = curVolume / maxVolume;

>>>>>>> Stashed changes



    }
    //ничего не работает :)
}