package com.mindgames.kawa_industries.troll;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by luca.cavallaro on 04/10/2018.
 */

public class AnimationManager {
    private Animation[] animations;
    private int animationIndex=0;

    public AnimationManager(Animation[] animations){
        this.animations=animations;
    }

    public void playAnimation(int index){
        for(int i=0; i< animations.length;i++){
            if (i==index){
                if(!animations[animationIndex].isPlaying()) {
                    animations[i].play();
                }
            }else{
//                animations[i].stop();
            }
        }
        animationIndex=index;
    }

    public void draw(Canvas canvas, Rect rect){
        if(animations[animationIndex].isPlaying()){
            animations[animationIndex].draw(canvas,rect);
        }
    }

    public void update(){
        if(animations[animationIndex].isPlaying()){
            animations[animationIndex].update();
        }
    }
}
