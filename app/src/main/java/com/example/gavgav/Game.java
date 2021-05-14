package com.example.gavgav;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback{
    private Thread mainThread;   //многопоточность
    private SurfaceHolder holder;
    private static volatile boolean running = true;
    private Sprite petDog;      //сам объект собаки класса Sprite
    private int viewWidth, viewHeight;      //длина и ширина канваса
    private int wDog, hDog;
    private final int timerInterval = 3;      //задержка между кадрами
    public Bitmap button_back_png, button_back_click_png, button_settings_png, button_settings_click_png;
    public Bitmap fullBackground;
    float clickX, clickY;
    public Game(Context context) {
        super(context);
        mainThread = new Thread(this);
        holder = this.getHolder();
        holder.addCallback(this);
        //делаю фон
        fullBackground = BitmapFactory.decodeResource(getResources(), R.drawable.full_background);
        int widthBG = 1447;
        int heightBG = 2039;
        int coef_bg = viewWidth / widthBG;
        int fullBackground_w = coef_bg * widthBG;
        int fullBackground_h = coef_bg * heightBG;
        fullBackground = Bitmap.createScaledBitmap(fullBackground, 1000, 1500, true);
        //делаю собаку
        Bitmap dogSprite = BitmapFactory.decodeResource(getResources(), R.drawable.dog_sprite);
        wDog = dogSprite.getWidth()/5;
        hDog = dogSprite.getHeight()/5;
        dogSprite = Bitmap.createScaledBitmap(dogSprite, wDog, hDog, true);
        Rect firstFrame = new Rect(0, 0, wDog/4, hDog/4);
        petDog = new Sprite(100,700, 50, 10, firstFrame, dogSprite); //создаём собаку
        petDog.setFrameTime(100);
        /*//делаю кнопки меню и настроек
        button_back_png = BitmapFactory.decodeResource(getResources(), R.drawable.button_back);
        int button_back_w = button_back_png.getWidth()/17;
        int button_back_h = button_back_png.getHeight()/17;
        button_back_png = Bitmap.createScaledBitmap(button_back_png, button_back_w, button_back_h, true);
        button_back_click_png = BitmapFactory.decodeResource(getResources(), R.drawable.button_back_click); //изменяю размеры второй раз у второй картинки
        int button_back_click_w = button_back_click_png.getWidth()/17;
        int button_back_click_h = button_back_click_png.getHeight()/17;
        button_back_click_png = Bitmap.createScaledBitmap(button_back_click_png, button_back_click_w, button_back_click_h, true);
        //вторая кнопка
        button_settings_png = BitmapFactory.decodeResource(getResources(), R.drawable.button_settings); //делаю кнопку и изменяю размеры
        int button_settings_w = button_settings_png.getWidth()/17;
        int button_settings_h = button_settings_png.getHeight()/17;
        button_settings_png = Bitmap.createScaledBitmap(button_settings_png, button_settings_w, button_settings_h, true);
        button_settings_click_png = BitmapFactory.decodeResource(getResources(), R.drawable.button_settings_click); //изменяю размеры второй раз у второй картинки
        int button_settings_click_w = button_settings_click_png.getWidth()/17;
        int button_settings_click_h = button_settings_click_png.getHeight()/17;
        button_settings_click_png = Bitmap.createScaledBitmap(button_settings_click_png, button_settings_click_w, button_settings_click_h, true);*/
        //показ кадров
        for (int i = 1; i <= 4; i++) {
            petDog.addFrame(new Rect((i-1)*wDog/4, 0, i*wDog/4, hDog/4));
        }
        Timer t = new Timer();
        t.start();
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }
    protected void drawing(Canvas canvas) {
        canvas.drawARGB(250, 200, 200, 120);
        Paint paint = new Paint();
        canvas.drawBitmap(fullBackground, 0, 0, paint);
        petDog.draw(canvas);

        paint.setAntiAlias(true);
        paint.setTextSize(55.0f);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(10, 600, 50, paint);
       /* if (isClickBack(clickX, clickY)){
            canvas.drawText("клик назад", 100, 100, paint);
        }
        canvas.drawBitmap(button_back_png, button_back_png.getWidth()/4, button_back_png.getHeight()/4, paint);
        canvas.drawBitmap(button_settings_png, viewWidth - button_settings_png.getWidth() - button_settings_png.getWidth()/4, button_settings_png.getHeight()/4, paint);*/
    }

    protected void update () {
        petDog.update(timerInterval); //update в Sprite
        //изменяет х и у
        if (petDog.getX() <= 0) {
            petDog.setX(1);
            petDog.setVx(-petDog.getVx());
        }
        if (petDog.getY() <= viewHeight / 2) {
            petDog.setY(viewHeight / 2 + 1);
            petDog.setVy(-petDog.getVy());
        }
        if (petDog.getX() >= viewWidth - wDog) {
            petDog.setX(viewWidth-1 - wDog);
            petDog.setVx(-petDog.getVx());
        }
        if (petDog.getY() >= viewHeight - hDog) {
            petDog.setY(viewHeight - 1 - hDog);
            petDog.setVy(-petDog.getVy());
        }
//        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        return true;
    }
    /* public boolean isClickBack(float clickX, float clickY){
         if ((clickX < button_back_png.getWidth()/4 + button_back_png.getWidth()) && (clickX > button_back_png.getWidth()/4) && (clickY
                 < button_back_png.getHeight()/4 + button_back_png.getHeight()) && (clickY > button_back_png.getHeight()/4)){
             return true;
         }
         else { return false;}
     }*/
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mainThread.start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }
    @Override
    public void run() {
        while (running){
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                viewWidth = canvas.getWidth();
                viewHeight = canvas.getHeight();

                drawing(canvas);
                update();
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            update ();
        }
        @Override
        public void onFinish() {
        }
    }

}







