package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Random;

public class Food {

    private int x, y;
    private int screenWidth, screenHeight;
    private Random random;
    private final static int UNIT_SIZE = 50;
    private final static int FOOD_SIZE = 20;  // new size of the food

    public Food(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        random = new Random();
        spawn();
    }

    public void spawn() {
        x = (random.nextInt(screenWidth / UNIT_SIZE)) * UNIT_SIZE;
        y = (random.nextInt(screenHeight / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawRect(x, y, x + FOOD_SIZE, y + FOOD_SIZE, paint);  // draw the food with the new size
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return FOOD_SIZE;  // return the size of the food
    }
}
