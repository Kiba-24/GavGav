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

import java.io.IOException;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
   // private Thread mainThread;   //многопоточность
    private SurfaceHolder holder;
    private static volatile boolean running = true;
    private Sound sound;
    private Sprite petDog;      //сам объект собаки класса Sprite
    private int viewWidth, viewHeight;      //длина и ширина канваса
    private int wDog, hDog,soundBark;
    private float vxDog, vyDog;
    private final int timerInterval = 3;      //задержка между кадрами
    public Bitmap fullBackground, bowlEat, homeSleep, ballHappy, bushNeed,
            param, paramBG, lilParam, warSleep, warNeed, warHappy, warEat;
    float clickX, clickY;
    private boolean isDogLeft, isGoToFinger, isFirstTime;
    public int whatObject, coins, newCoins, paramR, paramBGR; //1 - будка, 2 - куст, 3 - мяч, 4 - миска
    Intent intent;
    private float arcSleep, arcEat, arcNeed, arcHappy;
    private boolean isFirstEat, isFirstSleep, isFirstNeed, isFirstHappy;
    private float speedArcParam = (float) 0.005;
    private boolean isFirstGav;




    //int soundBark;
    float leftVolume, rightVolume;

    final private int V_DOG = 50;
    public Game(Context context, float leftVolume, float rightVolume) {
        super(context);
        this.leftVolume = leftVolume;
        this.rightVolume = rightVolume;

       // mainThread = new Thread(this);
        holder = this.getHolder();
        holder.addCallback(this);


        sound = new Sound(4, AudioManager.STREAM_MUSIC, 100);
        soundBark = sound.load(context, R.raw.bark, 1);

        isDogLeft = false;
        //делаю фон

        fullBackground = BitmapFactory.decodeResource(getResources(), R.drawable.full_background);
        //делаю собаку
        Bitmap dogSprite = BitmapFactory.decodeResource(getResources(), R.drawable.dog_sprite);
        wDog = dogSprite.getWidth()/9*2;
        hDog = dogSprite.getHeight()/9*2;
        dogSprite = Bitmap.createScaledBitmap(dogSprite, wDog, hDog, true);
        Rect firstFrame = new Rect(0, 0, wDog/8, hDog);
        petDog = new Sprite(600,800, V_DOG, V_DOG , firstFrame, dogSprite); //создаём собаку влево
        petDog.setFrameTime(100);
        //миска
        bowlEat = BitmapFactory.decodeResource(getResources(), R.drawable.bowl_eat);
        bowlEat = Bitmap.createScaledBitmap(bowlEat,bowlEat.getWidth()/6,
                bowlEat.getHeight()/6, true);
        //будка
        homeSleep = BitmapFactory.decodeResource(getResources(), R.drawable.home_sleep);
        homeSleep = Bitmap.createScaledBitmap(homeSleep,homeSleep.getWidth()/3,
                homeSleep.getHeight()/3, true);
        //мяч
        ballHappy = BitmapFactory.decodeResource(getResources(), R.drawable.ball_happy);
        ballHappy = Bitmap.createScaledBitmap(ballHappy,ballHappy.getWidth()/3,
                ballHappy.getHeight()/3, true);
        //куст
        bushNeed = BitmapFactory.decodeResource(getResources(), R.drawable.bush_need);
        bushNeed = Bitmap.createScaledBitmap(bushNeed,bushNeed.getWidth()/3*2,
                bushNeed.getHeight()/3*2, true);
        //параметры
        param = BitmapFactory.decodeResource(getResources(), R.drawable.param);
        paramBG = BitmapFactory.decodeResource(getResources(), R.drawable.param_bg);
        lilParam = BitmapFactory.decodeResource(getResources(), R.drawable.param_bg);
        warSleep = BitmapFactory.decodeResource(getResources(), R.drawable.war_sleep);
        warEat= BitmapFactory.decodeResource(getResources(), R.drawable.war_eat);
        warNeed = BitmapFactory.decodeResource(getResources(), R.drawable.war_need);
        warHappy = BitmapFactory.decodeResource(getResources(), R.drawable.war_happy);
        //показ кадров
        petDog.removeFrame(0);
        arcSleep = 0;
        arcEat = 0;
        arcNeed = 0;
        arcHappy = 0;
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
        Paint paintArc = new Paint();
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
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paintArc.setColor(Color.WHITE);
        viewHeight = canvas.getHeight();
        viewWidth = canvas.getWidth();
        canvas.drawText("Монетки: " + String.valueOf(coins), 50, 100, paint);

        int space = viewWidth/13;
        paramR = space * 2;
        paramBGR = paramR + paramR/2;
        param = Bitmap.createScaledBitmap(param, paramR, paramR, true);
        warSleep = Bitmap.createScaledBitmap(warSleep, paramR, paramR, true);
        warNeed = Bitmap.createScaledBitmap(warNeed, paramR, paramR, true);
        warHappy = Bitmap.createScaledBitmap(warHappy, paramR, paramR, true);
        warEat = Bitmap.createScaledBitmap(warEat, paramR, paramR, true);
        paramBG = Bitmap.createScaledBitmap(paramBG, paramR + paramR/3,
                paramR + paramR/3, true);


        canvas.drawBitmap(paramBG, space-paramR/6, viewHeight-space-paramR-paramR/6,  paint);
        canvas.drawBitmap(paramBG, space*2 + paramR -paramR/6,
                viewHeight-space-paramR-paramR/6,  paint);
        canvas.drawBitmap(paramBG, space*3 + 2* paramR -paramR/6,
                viewHeight-space-paramR-paramR/6,  paint);
        canvas.drawBitmap(paramBG, space*4 + 3* paramR -paramR/6,
                viewHeight-space-paramR-paramR/6,  paint);

        if (arcSleep >=360){
            canvas.drawBitmap(param, space, viewHeight-space-paramR,  paint);
            canvas.drawText("сон", space*16/10, viewHeight-space/4,  paint);
            canvas.drawArc(space, viewHeight-space-paramR, space*3, viewHeight-space-paramR+param.getHeight(),
                    0, arcSleep, true, paintArc);
            canvas.drawBitmap(warSleep, space, viewHeight-space-paramR,  paint);
        }
        else{
            canvas.drawBitmap(param, space, viewHeight-space-paramR,  paint);
            canvas.drawText("сон", space*16/10, viewHeight-space/4,  paint);
            canvas.drawArc(space, viewHeight-space-paramR, space*3, viewHeight-space-paramR+param.getHeight(),
                    0, arcSleep, true, paintArc);

        }

        if(arcEat>=360){
            canvas.drawBitmap(param, space*2 + paramR, viewHeight-space-paramR,  paint);
            canvas.drawText("еда", paramR+space*26/10, viewHeight-space/4,  paint);
            canvas.drawArc(space*2 + paramR, viewHeight-space-paramR,  paramR +space*4,
                    viewHeight-space-paramR+param.getHeight(),0, arcEat, true, paintArc);
            canvas.drawBitmap(warEat, space*2 + paramR, viewHeight-space-paramR,  paint);
        }
        else{
            canvas.drawBitmap(param, space*2 + paramR, viewHeight-space-paramR,  paint);
            canvas.drawText("еда", paramR+space*26/10, viewHeight-space/4,  paint);
            canvas.drawArc(space*2 + paramR, viewHeight-space-paramR,  paramR +space*4,
                    viewHeight-space-paramR+param.getHeight(),0, arcEat, true, paintArc);
        }

        if(arcNeed >= 360){
            canvas.drawBitmap(param, space*3 + paramR *2, viewHeight-space-paramR,  paint);
            canvas.drawText("нужда", paramR*2+space*36/12, viewHeight-space/4,  paint);
            canvas.drawArc(space*3 + paramR*2, viewHeight-space-paramR,  paramR +space*7,
                    viewHeight-space-paramR+param.getHeight(),0, arcNeed, true, paintArc);
            canvas.drawBitmap(warNeed, space*3 + paramR *2, viewHeight-space-paramR,  paint);
        }
        else{
            canvas.drawBitmap(param, space*3 + paramR *2, viewHeight-space-paramR,  paint);
            canvas.drawText("нужда", paramR*2+space*36/12, viewHeight-space/4,  paint);
            canvas.drawArc(space*3 + paramR*2, viewHeight-space-paramR,  paramR +space*7,
                    viewHeight-space-paramR+param.getHeight(),0, arcNeed, true, paintArc);
        }
        if (arcHappy>=360){
            canvas.drawBitmap(param, space*4 + paramR * 3, viewHeight-space-paramR,  paint);
            canvas.drawText("счастье", paramR*3+space*46/11, viewHeight-space/4,  paint);
            canvas.drawArc(space*4 + paramR*3, viewHeight-space-paramR,  paramR +space*10,
                    viewHeight-space-paramR+param.getHeight(),0, arcHappy, true, paintArc);
            canvas.drawBitmap(warHappy, space*4 + paramR * 3, viewHeight-space-paramR,  paint);
        }
        else{
            canvas.drawBitmap(param, space*4 + paramR * 3, viewHeight-space-paramR,  paint);
            canvas.drawText("счастье", paramR*3+space*46/11, viewHeight-space/4,  paint);
            canvas.drawArc(space*4 + paramR*3, viewHeight-space-paramR,  paramR +space*10,
                    viewHeight-space-paramR+param.getHeight(),0, arcHappy, true, paintArc);

        }
    }

    protected void update () {
        petDog.update(timerInterval, isDogLeft); //update в Sprite
        if (newCoins>0) {
            coins = coins + newCoins;
            newCoins = 0;
        }

        //изменяет х и у
        if (petDog.getX() <= 0 ) {
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
            if (!isFirstGav){
                sound.play(soundBark, leftVolume, rightVolume, 1, 0, 1f);
                isFirstGav = true;
            }

            return true;
        }else {
            isFirstGav = false;
            return false;
        }
    }


    public boolean isClickHomeSleep (float clickX, float clickY){
        if (clickX> 20 && clickX < 20 + homeSleep.getWidth()
                && clickY> viewHeight / 5 && clickY < (viewHeight / 5) + homeSleep.getHeight()){
            whatObject = 1;
            goToProblem();
            return true;
        }
        return false;
    }

    public boolean isClickBushNeed (float clickX, float clickY){
        if (clickX > viewWidth/7 * 5 &&  clickX < viewWidth/7 * 5 + bushNeed.getWidth()
                && clickY> viewHeight / 5 && clickY < (viewHeight / 5) + bushNeed.getHeight()){
            whatObject = 2;
            goToProblem();
            return true;
        }
        return false;
    }
    public boolean isClickBallHappy (float clickX, float clickY){
        if (clickX > viewWidth/3 * 2 &&  clickX < viewWidth/2 * 3 + ballHappy.getWidth()
                && clickY> viewHeight / 3 * 2 && clickY < (viewHeight / 3 * 2) + ballHappy.getHeight()){
            whatObject = 3;
            goToProblem();
            return true;
        }
        return false;
    }
    public boolean isClickBowlEat(float clickX, float clickY){
        if (clickX > viewWidth/4 &&  clickX < viewWidth/4 + bowlEat.getWidth()
                && clickY> viewHeight /2 && clickY < (viewHeight /2) + bowlEat.getHeight()){
            whatObject = 4;
            goToProblem();

            return true;
        }
        return false;
    }
    public void goToProblem(){
        Context context = getContext();
        intent = new Intent(context, MathProblemActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }
    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }
    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }
    public void setNewCoins(int c) {
        newCoins = c;
    }
    public int getWhatObject(){
        return whatObject;
    }
    public void setArcSleep(int arcSleep) { //1
        this.arcSleep = arcSleep;
    }
    public void setArcNeed(int arcNeed) { //2
        this.arcNeed = arcNeed;
    }
    public void setArcHappy(int arcHappy) { //3
        this.arcHappy = arcHappy;
    }
    public void setArcEat(int arcEat) { //4
        this.arcEat = arcEat;
    }


    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            update ();
            if (arcEat <=360) {
                arcEat = (float) (arcEat + speedArcParam);
                isFirstEat = false;
            } else{
                if(!isFirstEat) {
                    Toast.makeText(getContext(), "пора есть!", Toast.LENGTH_SHORT).show();
                    coins = coins - 15;
                    isFirstEat = true;
                }
            }

            if (arcSleep <=360) {
                arcSleep = (float) (arcSleep + speedArcParam);
                isFirstSleep = false;
            } else{
                if (!isFirstSleep) {
                    Toast.makeText(getContext(), "пора спать!", Toast.LENGTH_SHORT).show();
                    coins = coins - 15;
                    isFirstSleep = true;
                }
            }

            if (arcNeed <=360) {
                arcNeed = (float) (arcNeed + speedArcParam);
                isFirstNeed = false;
            } else{
                if(!isFirstNeed) {
                    Toast.makeText(getContext(), "пора в туалет!", Toast.LENGTH_SHORT).show();
                    coins = coins - 15;
                    isFirstNeed = true;
                }
            }
            if (arcHappy <=360) {
                isFirstHappy = false;
                arcHappy = (float) (arcHappy + speedArcParam);
            } else{
                if(!isFirstHappy) {
                    Toast.makeText(getContext(), "пора играть!", Toast.LENGTH_SHORT).show();
                    coins = coins - 15;
                    isFirstHappy = true;
                }
            }

        }
        @Override
        public void onFinish() {
        }
    }

}
