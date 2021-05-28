package com.example.gavgav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    public static Game game;
    private SharedPreferences prefs;
    public float leftVolume,rightVolume;
    public static final String APP_PREFERENCES = "data";
    GameThread gameThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        leftVolume = curVolume / maxVolume;
        rightVolume = curVolume / maxVolume;
        game = new Game(this, leftVolume, rightVolume);
        if(prefs.contains("coins")) {
            game.coins = prefs.getInt("coins", 0);
            game.setArcEat(prefs.getFloat("arcEat",0 ));
            game.setArcHappy(prefs.getFloat("arcHappy",0 ));
            game.setArcNeed(prefs.getFloat("arcNeed",0 ));
            game.setArcSleep(prefs.getFloat("arcSleep",0 ));
            game.isStart = prefs.getBoolean("isStart", true);
        }
        setContentView(game);


    }

    @Override
    protected void onResume() {
        super.onResume();
        gameThread = new GameThread(game);
        gameThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs.edit().putInt("coins", game.coins).apply();
        prefs.edit().putFloat("arcEat", game.arcEat).apply();
        prefs.edit().putFloat("arcHappy", game.arcHappy).apply();
        prefs.edit().putFloat("arcNeed", game.arcNeed).apply();
        prefs.edit().putFloat("arcSleep", game.arcSleep).apply();
        prefs.edit().putBoolean("isStart", game.isStart).apply();
        gameThread.requestStop();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //ничего не работает :)
}
