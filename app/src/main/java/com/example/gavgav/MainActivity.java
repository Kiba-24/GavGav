package com.example.gavgav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //пока простосоздаю канвас,ч тобы проверить собаку
        super.onCreate(savedInstanceState);              //к слову, собака не проверилась
        //setContentView(R.layout.activity_main);
        setContentView(new Game(this));

    }
}