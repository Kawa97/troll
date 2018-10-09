package com.mindgames.kawa_industries.troll;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by luca.cavallaro on 02/10/2018.
 */

public class Obstacle implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;
    private int color;


    public Obstacle(int rectHeight, int color,int startX,int startY,int playerGap){
        this.color=color;
        rectangle= new Rect(0,startY,startX,startY+rectHeight);
        rectangle2= new Rect(startX+playerGap,startY,Constants.SCREEN_WIDTH,startY+rectHeight);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void incrementY(float y){
        rectangle.top+=y;
        rectangle.bottom+=y;
        rectangle2.top+=y;
        rectangle2.bottom+=y;
    }

    public boolean playerCollide(RectPlayer player){
        boolean collide=(Rect.intersects(rectangle, player.getRectangle())||Rect.intersects(rectangle2, player.getRectangle()));
        if(player.getRectangle().left>Constants.SCREEN_WIDTH
                ||player.getRectangle().right<0
                ||player.getRectangle().top>Constants.SCREEN_HEIGHT
                || player.getRectangle().top<0){
            collide=true;
        }
        return collide;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);
    }

}
