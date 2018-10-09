package com.mindgames.kawa_industries.troll;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by luca.cavallaro on 04/10/2018.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent event);
}
