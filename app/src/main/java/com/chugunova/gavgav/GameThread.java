package com.chugunova.gavgav;

import android.graphics.Canvas;

public class GameThread  extends Thread {
    private Game game;
    private volatile boolean running = true;
    public GameThread (Game g){
        game = g;
    }

    @Override
    public void run() {



        while (running){
            Canvas canvas = game.getHolder().lockCanvas();

            if (canvas != null) {

                game.setViewWidth(canvas.getWidth());
                game.setViewHeight(canvas.getHeight());

                game.drawing(canvas);
                game.update();
                game.getHolder().unlockCanvasAndPost(canvas);


            }
        }
        super.run();
    }

    public void requestStop(){
        running = false;
    }


}
