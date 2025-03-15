package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private boolean isGameOver;
    private int screenWidth, screenHeight;
    private Paint paint;
    private Snake snake;
    private Food food;
    private static final int UNIT_SIZE = 50;
    private static final int FRAME_RATE = 10; // Frames per second

    public GameView(Context context) {
        super(context);

        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;

        paint = new Paint();
        snake = new Snake(screenWidth, screenHeight);
        food = new Food(screenWidth, screenHeight);

        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startGame();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                pauseGame();
            }
        });
    }

    @Override
    public void run() {
        while (isPlaying) {
            long startTime = System.currentTimeMillis();
            update();
            draw();
            long duration = System.currentTimeMillis() - startTime;
            long sleepTime = (1000 / FRAME_RATE) - duration;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        if (isGameOver)
            return;

        snake.move();
        if (snake.checkCollision(food)) {
            snake.grow();
            food.spawn();
            Log.d("GameView", "Food eaten, snake grew");
        }

        if (snake.checkSelfCollision() || snake.checkWallCollision(screenWidth, screenHeight)) {
            isPlaying = false;
            isGameOver = true;
            post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Game Over!", Toast.LENGTH_SHORT).show();
                }
            });
            Log.d("GameView", "Game Over");
        }
    }



    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLACK);
            snake.draw(canvas, paint);
            food.draw(canvas, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isGameOver) {
            resetGame();
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float touchX = event.getX();
                float touchY = event.getY();
                Snake.Segment head = snake.getHead();
                if (snake.getDirection() == Snake.Direction.LEFT || snake.getDirection() == Snake.Direction.RIGHT) {
                    if (touchY < head.y) {
                        snake.setDirection(Snake.Direction.UP);
                    } else {
                        snake.setDirection(Snake.Direction.DOWN);
                    }
                } else {
                    if (touchX < head.x) {
                        snake.setDirection(Snake.Direction.LEFT);
                    } else {
                        snake.setDirection(Snake.Direction.RIGHT);
                    }
                }
                break;
        }
        return true;
    }

    private void startGame() {
        isPlaying = true;
        isGameOver = false;
        thread = new Thread(this);
        thread.start();
    }

    private void pauseGame() {
        try {
            isPlaying = false;
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resetGame() {
        pauseGame();
        snake = new Snake(screenWidth, screenHeight);
        food.spawn();
        startGame();
    }
}
