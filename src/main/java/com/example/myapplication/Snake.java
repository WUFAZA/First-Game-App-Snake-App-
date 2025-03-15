package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<Segment> segments;
    private int screenWidth, screenHeight;
    private int direction = Direction.RIGHT;
    private static final int UNIT_SIZE = 50;  // size of each snake segment

    public Snake(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        segments = new ArrayList<>();
        segments.add(new Segment(screenWidth / 2, screenHeight / 2));  // add initial head segment
    }

    public void move() {
        // Move snake segments
        for (int i = segments.size() - 1; i > 0; i--) {
            segments.get(i).x = segments.get(i - 1).x;
            segments.get(i).y = segments.get(i - 1).y;
        }
        Segment head = segments.get(0);
        switch (direction) {
            case Direction.UP:
                head.y -= UNIT_SIZE;
                break;
            case Direction.DOWN:
                head.y += UNIT_SIZE;
                break;
            case Direction.LEFT:
                head.x -= UNIT_SIZE;
                break;
            case Direction.RIGHT:
                head.x += UNIT_SIZE;
                break;
        }
    }

    public void grow() {
        // Grow snake by adding a new segment at the tail's position
        Segment tail = segments.get(segments.size() - 1);
        segments.add(new Segment(tail.x, tail.y));
    }

    public boolean checkCollision(Food food) {
        // Check collision with food considering the new food size
        Segment head = segments.get(0);
        return head.x < food.getX() + food.getSize() &&
                head.x + UNIT_SIZE > food.getX() &&
                head.y < food.getY() + food.getSize() &&
                head.y + UNIT_SIZE > food.getY();
    }


    public boolean checkSelfCollision() {
        // Check self-collision
        Segment head = segments.get(0);
        for (int i = 1; i < segments.size(); i++) {
            if (head.x == segments.get(i).x && head.y == segments.get(i).y) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWallCollision(int screenWidth, int screenHeight) {
        // Check wall collision
        Segment head = segments.get(0);
        return head.x < 0 || head.x >= screenWidth || head.y < 0 || head.y >= screenHeight;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.GREEN);
        for (Segment segment : segments) {
            canvas.drawRect(segment.x, segment.y, segment.x + UNIT_SIZE, segment.y + UNIT_SIZE, paint);
        }
    }

    // Method to get the direction of the snake
    public int getDirection() {
        return direction;
    }

    // Method to set the direction of the snake
    public void setDirection(int direction) {
        this.direction = direction;
    }

    // Method to get the head segment of the snake
    public Segment getHead() {
        return segments.get(0);
    }

    public class Segment {
        int x, y;
        Segment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Direction {
        static final int UP = 0;
        static final int DOWN = 1;
        static final int LEFT = 2;
        static final int RIGHT = 3;
    }
}
