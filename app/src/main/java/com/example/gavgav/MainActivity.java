package com.example.gavgav;

import android.graphics.Canvas;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

public class GameThread  extends Thread {
    private Game game;
    private static volatile boolean running = true;
    public GameThread (Game g){
       game = g;



    }

    @Override
    public void run() {



        while (running){
            Canvas canvas = game.gameCanvas();

            Log.d("aaaa", "run "+canvas+" " + game.getHolder());
            if (canvas != null) {
                Log.d("aaaa", "run");
                game.setViewWidth(canvas.getWidth());
                game.setViewHeight(canvas.getHeight());

                game.drawing(canvas);
                game.update();
                game.getHolder().unlockCanvasAndPost(canvas);


            }
        }
        super.run();
    }


}
