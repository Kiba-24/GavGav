package com.example.gavgav;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.CountDownTimer;
<<<<<<< Updated upstream
import android.view.Display;
=======
import android.util.Log;
>>>>>>> Stashed changes
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
<<<<<<< Updated upstream
public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback{
=======

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
>>>>>>> Stashed changes
    private Thread mainThread;   //многопоточность
    private SurfaceHolder holder;
    private static volatile boolean running = true;
    private Sound sound;
    private Sprite petDog;      //сам объект собаки класса Sprite
    private int viewWidth, viewHeight;      //длина и ширина канваса
    private int wDog, hDog;
<<<<<<< Updated upstream
    private final int timerInterval = 3;      //задержка между кадрами
    public Bitmap button_back_png, button_back_click_png, button_settings_png, button_settings_click_png;
    public Bitmap fullBackground;
    float clickX, clickY;
    public Game(Context context) {
=======
    private float vxDog, vyDog;
    private final int timerInterval = 3;      //задержка между кадрами
    public Bitmap fullBackground, bowlEat, homeSleep, ballHappy, bushNeed, param, paramBG;
    float clickX, clickY;
    private boolean isDogLeft, isGoToFinger, isFirstTime;
    private int whatObject, coins, newCoins, paramR, paramBGR; //1 - будка, 2 - куст, 3 - мяч, 4 - миска
    Intent intent;

    //int soundBark;
    float leftVolume, rightVolume;

    final private int V_DOG = 50;
    public Game(Context context, float leftVolume, float rightVolume) {
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
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
=======
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
        //показ кадров
        petDog.removeFrame(0);
        for (int i = 1; i <= 8; i++) {
            petDog.addFrame(new Rect((i - 1) * (wDog/8), 0, i * (wDog/8), hDog ));

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
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
=======
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

        canvas.drawCircle(2*space+space/2, viewHeight-space*2-space/2, space/2, paint);



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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    /* public boolean isClickBack(float clickX, float clickY){
         if ((clickX < button_back_png.getWidth()/4 + button_back_png.getWidth()) && (clickX > button_back_png.getWidth()/4) && (clickY
                 < button_back_png.getHeight()/4 + button_back_png.getHeight()) && (clickY > button_back_png.getHeight()/4)){
             return true;
         }
         else { return false;}
     }*/
=======
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

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
}







=======
}
>>>>>>> Stashed changes
