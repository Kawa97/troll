package com.mindgames.kawa_industries.troll;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by luca.cavallaro on 04/10/2018.
 */

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;
    private boolean playing=false;

    public boolean isPlaying() {
        return playing;
    }

    public void play(){
        playing=true;
        frameIndex=0;
        lastFrame=System.currentTimeMillis();
    }

    public void stop(){
        playing=false;
    }

    private float frameTime;

    private long lastFrame;
    public Animation(Bitmap[] frames,float animationTime){
        this.frames=frames;
        frameIndex=0;
        this.frameTime=animationTime/frames.length;
        lastFrame=System.currentTimeMillis();
    }

    public void draw(Canvas canvas,Rect destination){
        if(!playing)return;

        scaleRect(destination);
        
        canvas.drawBitmap(frames[frameIndex],null,destination,new Paint());
    }

    private void scaleRect(Rect rect) {
        float whRatio=(float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rect.width()>rect.height()){
            rect.left= rect.right-(int)(rect.height()*whRatio);
        }else{
            rect.top= rect.bottom-(int)(rect.width()*(1/whRatio));
        }
    }

    public void update(){
        if(!playing)return;
        if(System.currentTimeMillis()-lastFrame> frameTime*1000){
            frameIndex++;
            frameIndex=frameIndex>=frames.length? 0 : frameIndex;
            lastFrame=System.currentTimeMillis();
        }
    }
}
