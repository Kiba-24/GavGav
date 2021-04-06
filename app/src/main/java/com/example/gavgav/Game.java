package com.example.gavgav;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback{
    private Thread mainThread;   //многопоточность
    private SurfaceHolder holder;
    private static volatile boolean running = true;
    private Sprite petDog;      //сам объект собаки класса Sprite

    private int viewWidth;         //длина и ширина канваса
    private int viewHeight;

    private final int timerInterval = 3;      //задержка между кадрами

    public Game(Context context) {
        super(context);
        mainThread = new Thread(this);
        holder = this.getHolder();
        holder.addCallback(this);
        Bitmap dogSprite = BitmapFactory.decodeResource(getResources(), R.drawable.dog_sprite);
        int w = dogSprite.getWidth()/5;
        int h = dogSprite.getHeight()/5;
        dogSprite = Bitmap.createScaledBitmap(dogSprite, w, h, true);
        Rect firstFrame = new Rect(0, 0, w/4, h/4);
        petDog = new Sprite(20, 10, 50, 10, firstFrame, dogSprite); //создаём собаку
        petDog.setFrameTime(100);

        for (int i = 1; i <= 4; i++) {                                                     //по идее, это должно переключать кадры
            petDog.addFrame(new Rect((i-1)*w/4, 0, i*w/4, h/4));     //но как-то я сомневаюсь
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
        petDog.draw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(55.0f);
        paint.setColor(Color.WHITE);
        canvas.drawText("тест пёсов" + petDog.getCurrentFrame(), 100, 100, paint);
    }

    protected void update () {
        petDog.update(timerInterval); //update в Sprite
        //изменяет х и у
        if (petDog.getX() <= 0) {
            petDog.setX(0);
            petDog.setVx(-petDog.getVx());
        }
        if (petDog.getY() <= 0) {
            petDog.setY(0);
            petDog.setVy(-petDog.getVy());
        }
        if (petDog.getX() >= viewWidth) {
            petDog.setX(viewWidth-1);
            petDog.setVx(-petDog.getVx());
        }
        if (petDog.getY() >= viewHeight) {
            petDog.setY(viewHeight - 1);
            petDog.setVy(-petDog.getVy());
        }



//        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //тут тыкать

        return true;
    }

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