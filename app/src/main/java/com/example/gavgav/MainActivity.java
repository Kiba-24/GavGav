package com.example.gavgav;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        //setContentView(new Game(this));
        //Log.d(LOG_TAG, "Main ");



    }
    //ничего не работает :)
}