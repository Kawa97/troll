package com.mindgames.kawa_industries.troll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by luca.cavallaro on 04/10/2018.
 */

public class GamePlayScene implements Scene {

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer=false;

    private boolean gameOver=false;
    private long gameOverTime;
    private Rect gameOverRect= new Rect();

    private OrientationData orientationData;
    private long frameTime;

    public GamePlayScene(){
        player=new RectPlayer(new Rect(0,0,Constants.SCREEN_WIDTH/10,Constants.SCREEN_WIDTH/10), Color.rgb(255,0,0));
        playerPoint= new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager= new ObstacleManager(Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGHT/3,Constants.SCREEN_WIDTH/12,Color.BLACK);

        orientationData= new OrientationData();
        orientationData.register();
        frameTime=System.currentTimeMillis();
    }

    public void reset(){
        playerPoint= new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager= new ObstacleManager(Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGHT/3,Constants.SCREEN_WIDTH/12,Color.BLACK);
        movingPlayer = false;
        gameOver=false;
        orientationData.newGame();
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE=0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY())){
                    movingPlayer=true;
                }
                if(gameOver && System.currentTimeMillis() -gameOverTime >= 1000){
                    reset();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer=false;
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);

        obstacleManager.draw(canvas);

        if(gameOver){
            Paint paint=new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            drawCenteredText(canvas, paint,"Game Over");
        }
    }

    private void drawCenteredText(Canvas canvas,Paint paint,String text){
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(gameOverRect);
        int cHeight= gameOverRect.height();
        int cWidth= gameOverRect.width();
        paint.getTextBounds(text,0,text.length(),gameOverRect);
        float x= cWidth /2f - gameOverRect.width() /2f- gameOverRect.left;
        float y= cHeight /2f - gameOverRect.height() /2f- gameOverRect.bottom;
        canvas.drawText(text,x,y,paint);

    }

    @Override
    public void update() {
        if(!gameOver) {
            if(frameTime<Constants.INIT_TIME) {
                frameTime = Constants.INIT_TIME;
            }
            int elapsedTime=(int)(System.currentTimeMillis()-frameTime);
            frameTime=System.currentTimeMillis();
            if(orientationData.getOrientation()!=null && orientationData.getStartOrientation()!=null) {
                float pitch=orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll=orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xSpeed=2*roll*Constants.SCREEN_WIDTH/1000f;
                float ySpeed=pitch*Constants.SCREEN_HEIGHT/1000f;

                    playerPoint.x += Math.abs(xSpeed*elapsedTime)>5?xSpeed*elapsedTime:0;
                    playerPoint.y -= Math.abs(ySpeed*elapsedTime)>5?ySpeed*elapsedTime:0;
            }
            if(playerPoint.x<0)playerPoint.x=0;
            else if(playerPoint.x>Constants.SCREEN_WIDTH)playerPoint.x=Constants.SCREEN_WIDTH;
            if(playerPoint.y<0)playerPoint.y=0;
            else if(playerPoint.y>Constants.SCREEN_HEIGHT)playerPoint.y=Constants.SCREEN_HEIGHT;

            player.update(playerPoint);
            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){
                gameOver=true;
                gameOverTime=System.currentTimeMillis();
            }
        }
    }
}
