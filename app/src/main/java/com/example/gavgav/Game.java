package com.example.gavgav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private Thread mainThread;   //многопоточность
    private SurfaceHolder holder;
    private static volatile boolean running = true;
    private Sound sound;
    private Sprite petDog;      //сам объект собаки класса Sprite
    private int viewWidth, viewHeight;      //длина и ширина канваса
    private int wDog, hDog;
    private float vxDog, vyDog;
    private final int timerInterval = 3;      //задержка между кадрами
    public Bitmap fullBackground, bowlEat, homeSleep, ballHappy, bushNeed, param, paramBG, lilParam;
    float clickX, clickY;
    private boolean isDogLeft, isGoToFinger, isFirstTime;
    private int whatObject, coins, newCoins, paramR, paramBGR; //1 - будка, 2 - куст, 3 - мяч, 4 - миска
    Intent intent;

    //int soundBark;
    float leftVolume, rightVolume;

    final private int V_DOG = 50;
    public Game(Context context, float leftVolume, float rightVolume) {
        super(context);
        this.leftVolume = leftVolume;
        this.rightVolume = rightVolume;

        mainThread = new Thread(this);
        holder = this.getHolder();
        holder.addCallback(this);

        intent = new Intent(context, MathProblemActivity.class);

        sound = new Sound(4, AudioManager.STREAM_MUSIC, 100);
        int soundBark = sound.load(context, R.raw.bark, 1);

        isDogLeft = false;
        //делаю фон

        fullBackground = BitmapFactory.decodeResource(getResources(), R.drawable.full_background);
        //делаю собаку
        Bitmap dogSprite = BitmapFactory.decodeResource(getResources(), R.drawable.dog_sprite);
        wDog = dogSprite.getWidth()/3;
        hDog = dogSprite.getHeight()/3;
        dogSprite = Bitmap.createScaledBitmap(dogSprite, wDog, hDog, true);
        Rect firstFrame = new Rect(0, 0, wDog/8, hDog);
        petDog = new Sprite(100,700, V_DOG, V_DOG , firstFrame, dogSprite); //создаём собаку влево
        petDog.setFrameTime(100);
        //миска
        bowlEat = BitmapFactory.decodeResource(getResources(), R.drawable.bowl_eat);
        bowlEat = Bitmap.createScaledBitmap(bowlEat,bowlEat.getWidth()/4,
                bowlEat.getHeight()/4, true);
        //будка
        homeSleep = BitmapFactory.decodeResource(getResources(), R.drawable.home_sleep);
        homeSleep = Bitmap.createScaledBitmap(homeSleep,homeSleep.getWidth()/2,
                homeSleep.getHeight()/2, true);
        //мяч
        ballHappy = BitmapFactory.decodeResource(getResources(), R.drawable.ball_happy);
        ballHappy = Bitmap.createScaledBitmap(ballHappy,ballHappy.getWidth()/2,
                ballHappy.getHeight()/2, true);
        //куст
        bushNeed = BitmapFactory.decodeResource(getResources(), R.drawable.bush_need);
        //параметры
        param = BitmapFactory.decodeResource(getResources(), R.drawable.param);
        paramBG = BitmapFactory.decodeResource(getResources(), R.drawable.param_bg);
        lilParam = BitmapFactory.decodeResource(getResources(), R.drawable.param_bg);
        //показ кадров
        petDog.removeFrame(0);
        for (int i = 1; i <= 8; i++) {
            petDog.addFrame(new Rect((i - 1) * (wDog/8), 0, i * (wDog/8), hDog ));

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
        fullBackground = Bitmap.createScaledBitmap(fullBackground, canvas.getWidth(),
                canvas.getHeight(), true);
        canvas.drawBitmap(fullBackground, 0, 0, paint);
        canvas.drawBitmap(bowlEat,canvas.getWidth() / 4, canvas.getHeight()/2, paint);
        canvas.drawBitmap(homeSleep, 20, canvas.getHeight() / 5, paint);
        canvas.drawBitmap(ballHappy, canvas.getWidth() / 3 * 2, canvas.getHeight() / 3 * 2, paint);
        canvas.drawBitmap(bushNeed, canvas.getWidth()/7 * 5, canvas.getHeight()/5, paint);

        petDog.draw(canvas);
        paint.setAntiAlias(true);
        paint.setTextSize(55.0f);
        paint.setColor(Color.WHITE);
        viewHeight = canvas.getHeight();
        viewWidth = canvas.getWidth();
        canvas.drawText(String.valueOf(coins), 50, 50, paint);

        int space = viewWidth/17;
        paramR = space * 3;
        paramBGR = paramR + paramR/3;
        param = Bitmap.createScaledBitmap(param, paramR, paramR, true);
        paramBG = Bitmap.createScaledBitmap(paramBG, paramR + paramR/3,
                paramR + paramR/3, true);
        lilParam = Bitmap.createScaledBitmap(paramBG, param.getWidth()/3,
                param.getHeight()/3, true);

        canvas.drawBitmap(paramBG, space-paramR/6, viewHeight-space-paramR-paramR/6,  paint);
        canvas.drawBitmap(paramBG, space*2 + paramR -paramR/6,
                viewHeight-space-paramR-paramR/6,  paint);
        canvas.drawBitmap(paramBG, space*3 + 2* paramR -paramR/6,
                viewHeight-space-paramR-paramR/6,  paint);
        canvas.drawBitmap(paramBG, space*4 + 3* paramR -paramR/6,
                viewHeight-space-paramR-paramR/6,  paint);


        canvas.drawBitmap(param, space, viewHeight-space-paramR,  paint);
        canvas.drawBitmap(param, space*2 + paramR, viewHeight-space-paramR,  paint);
        canvas.drawBitmap(param, space*3 + paramR *2, viewHeight-space-paramR,  paint);
        canvas.drawBitmap(param, space*4 + paramR * 3, viewHeight-space-paramR,  paint);

        //canvas.drawCircle(2*space+space/2, viewHeight-space*2-space/2, space/2, paint);
        canvas.drawBitmap(lilParam, space*2,viewHeight-space*3,  paint);
        canvas.drawA


    }

    protected void update () {
        petDog.update(timerInterval, isDogLeft); //update в Sprite
        //sound.play(soundBark, 100, 100, 1, 0, 1f);
        newCoins = intent.getIntExtra("newCoins", 0);
        coins = coins + newCoins;

        //изменяет х и у
        if (petDog.getX() <= 0 && petDog.getX() >=-100) {
            isDogLeft = !isDogLeft;
            petDog.setX(1);
            petDog.setVx(-petDog.getVx());
        }
        if (petDog.getY() < viewHeight/3) {
            petDog.setY(viewHeight/3);
            petDog.setVy(-petDog.getVy());
        }
        if (petDog.getX() >= viewWidth - (wDog/8)) {
            isDogLeft = !isDogLeft;
            petDog.setX(viewWidth-1 - (wDog/8));
            petDog.setVx(-petDog.getVx());
        }
        if (petDog.getY() >= viewHeight-(hDog) - paramBGR) {
            petDog.setY(viewHeight-(hDog)-1-paramBGR);
            petDog.setVy(-petDog.getVy());
        }

        if (isStopGoToFinger(clickX, clickY)){ //тут собака специально не доходит до конца,
            petDog.setVx(0);                   //потому что палец толстый
            petDog.setVy(0);
        }
//        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isGoToFinger = true;
                if (isGoToFinger) {
                    goToFinger(clickX, clickY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                clickX = event.getX();
                clickY = event.getY();
                if (isGoToFinger) {
                    goToFinger(clickX, clickY);
                }
                break;
            case MotionEvent.ACTION_UP:
                isGoToFinger = false;
                petDog.setVy(vyDog);
                petDog.setVx(vxDog);
                isClickHomeSleep(clickX, clickY);
                isClickBushNeed(clickX, clickY);
                isClickBallHappy(clickX, clickY);
                isClickBowlEat(clickX, clickY);
                break;
        }
        return true;



    }
    public void goToFinger (float clickX, float clickY){
        Log.d("coins", ""+coins);
        float xDog = (float) petDog.getX() + (wDog/16);
        float yDog = (float) petDog.getY() + (hDog/10);
        float a =  clickX-xDog;
        float b = clickY-yDog;
        float c = (float) Math.sqrt(a * a + b * b);

        if (xDog > clickX && !isDogLeft){
            isDogLeft =  true;
        }
        if (xDog < clickX && isDogLeft){
            isDogLeft =  false;
        }
        vxDog = a / c * V_DOG;
        vyDog = b / c * V_DOG;
        petDog.setVx(vxDog);
        petDog.setVy(vyDog);

    }
    public boolean isStopGoToFinger(float clickX, float clickY){
        if (!isGoToFinger){
            return false;
        }
        Rect dog = petDog.getBoundingBoxRect();
        int x = (int) clickX;
        int y = (int) clickY;
        if (dog.contains(x, y))
        {

            return true;
        }else {return false;}
    }


    public boolean isClickHomeSleep (float clickX, float clickY){
        if (clickX> 20 && clickX < 20 + homeSleep.getWidth()
                && clickY> viewHeight / 5 && clickY < (viewHeight / 5) + homeSleep.getHeight()){
            whatObject = 1;
            goToProblem(whatObject);
            return true;
        }
        return false;
    }

    public boolean isClickBushNeed (float clickX, float clickY){
        if (clickX > viewWidth/7 * 5 &&  clickX < viewWidth/7 * 5 + bushNeed.getWidth()
                && clickY> viewHeight / 5 && clickY < (viewHeight / 5) + bushNeed.getHeight()){
            whatObject = 2;
            goToProblem(whatObject);
            return true;
        }
        return false;
    }
    public boolean isClickBallHappy (float clickX, float clickY){
        if (clickX > viewWidth/3 * 2 &&  clickX < viewWidth/2 * 3 + ballHappy.getWidth()
                && clickY> viewHeight / 3 * 2 && clickY < (viewHeight / 3 * 2) + ballHappy.getHeight()){
            whatObject = 3;
            goToProblem(whatObject);
            return true;
        }
        return false;
    }
    public boolean isClickBowlEat(float clickX, float clickY){
        if (clickX > viewWidth/4 &&  clickX < viewWidth/4 + bowlEat.getWidth()
                && clickY> viewHeight /2 && clickY < (viewHeight /2) + bowlEat.getHeight()){
            whatObject = 4;
            goToProblem(whatObject);

            return true;
        }
        return false;
    }
    public void goToProblem(int whatObject){
        Context context = getContext();
        context.startActivity(intent);
        isFirstTime = true;
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