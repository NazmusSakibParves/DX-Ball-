package parves.android.DxBall;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author S.M. NAZMUS SAKIB PARVES
 *
 */

public class GameCanvas extends View implements Runnable{

	public static boolean gameOver;
    public static boolean newLife;
    public static int life;

    public  static int canvasHeight,canvasWidth;
    Paint paint;
    float barWidth=300;
    float brickX=0, brickY=80;
    int score=0;
    Ball myBall;
    Bar myBar;
    float left,right,top,bottom;
    float downX,downY,upX,upY;
    boolean leftPos,rightPos,first=true,second=false;
    int min_distance=50;
    int ballSpeed;
    public static int checkWidth=0;
    final MediaPlayer mp;

    ArrayList<Bricks> bricks=new ArrayList<Bricks>();

    public GameCanvas(Context context, MediaPlayer mp) {
        super(context);
        this.mp = mp;
        paint=new Paint();
        //myBall.move();
        myBar=new Bar();
        life=3;
        gameOver=false;
        newLife=true;

    }

    public void stageOne(Canvas canvas)
    {
    	for(int i=0; i<20; i++){
            int brickColor;
            if(brickX>=canvas.getWidth()) {
                brickX = 0;
                brickY += 100;
            }
            if(i%2==0)
                //brickColor = Color.RED;
            	brickColor = Color.MAGENTA;
            else
                brickColor = Color.BLUE;
            bricks.add(new Bricks(brickX+2,brickY,brickX+canvas.getWidth()/5-5,brickY+95,brickColor));
            brickX+=canvas.getWidth()/5;
        }

        myBall=new Ball(canvas.getWidth()/2,canvas.getHeight()/2,Color.RED,20);
        myBall.bounce(canvas);

        left = getWidth()/2-(barWidth/2);
        top = getHeight() - 20;
        right = getWidth()/2+(barWidth/2);
        bottom = getHeight();

        myBar.setBottom(bottom);
        myBar.setLeft(left);
        myBar.setRight(right);
        myBar.setTop(top);
        checkWidth = canvas.getWidth();

        myBall.setDx(4);
        myBall.setDy(4);
        Log.d("", bricks.size() + "");
    }

    public void stageTwo(Canvas canvas)
    {
    	for(int i=0; i<55; i++){
            int brickColor;
            if(brickX>=canvas.getWidth()) {
                brickX = 0;
                brickY += 35;
            }
            if(i%2==0)
                brickColor = Color.BLACK;
            else
                brickColor = Color.GREEN;
            bricks.add(new Bricks(brickX+2,brickY,brickX+canvas.getWidth()/5-5,brickY+30,brickColor));
            brickX+=canvas.getWidth()/5;
        }

        myBall=new Ball(canvas.getWidth()/2,canvas.getHeight()/2,Color.RED,20);
        myBall.bounce(canvas);

        left = getWidth()/2-(barWidth/2);
        top = getHeight() - 20;
        right = getWidth()/2+(barWidth/2);
        bottom = getHeight();

        myBar.setBottom(bottom);
        myBar.setLeft(left);
        myBar.setRight(right);
        myBar.setTop(top);
        checkWidth = canvas.getWidth();

        myBall.setDx(4);
        myBall.setDy(4);
        
        gameOver = true;
    }
    
    public void gameOver(Canvas canvas)
    {
    	paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        paint.setColor(Color.RED);
        paint.setTextSize(50);
        paint.setFakeBoldText(true);
        canvas.drawText("GAME OVER",canvas.getWidth()/2-110,canvas.getHeight()/2,paint);
        canvas.drawText("FINAL SCORE: "+score,canvas.getWidth()/2-150,canvas.getHeight()/2+60,paint);
        gameOver = false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
//Draw canvas
    @Override
    protected void onDraw(Canvas canvas) {

    	//canvas.drawColor(Color.WHITE);
        canvasHeight=canvas.getHeight();
        canvasWidth=canvas.getWidth();
        if(first==true) {
            first=false;
            this.stageOne(canvas);
        }
        
        if(newLife){
        	mp.start();
            newLife = false;
            //new ball
            ballSpeed = 12;
            myBall=new Ball(canvas.getWidth()/2,canvas.getHeight()-50,Color.GREEN,20);
            myBall.setDx(ballSpeed);
            myBall.setDy(-ballSpeed);
        }
        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        //Ball
        canvas.drawCircle(myBall.getX(), myBall.getY(), myBall.getRadius(), myBall.getPaint());
        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        canvas.drawText("Score: "+score,10,30,paint);

        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        canvas.drawText("Life: "+life,canvas.getWidth()-110,40,paint);


        //Bar
        canvas.drawRect(myBar.getLeft(), myBar.getTop(), myBar.getRight(), myBar.getBottom(), myBar.getPaint());

        //bricks
        for(int i=0;i<bricks.size();i++){
       canvas.drawRect(bricks.get(i).getLeft(),bricks.get(i).getTop(),bricks.get(i).getRight(),bricks.get(i).getBottom(),bricks.get(i).getPaint());
        }

        if(gameOver){
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

            paint.setColor(Color.RED);
            paint.setTextSize(50);
            paint.setFakeBoldText(true);
            canvas.drawText("GAME OVER",canvas.getWidth()/2-110,canvas.getHeight()/2,paint);
            canvas.drawText("FINAL SCORE: "+score,canvas.getWidth()/2-150,canvas.getHeight()/2+60,paint);
            gameOver = false;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //((DxballGameActivity)getContext()).finish();
        }

        if(gameOver == false)
        {	
	        this.ballBrickCollision(bricks,myBall,canvas);
	        this.ballBarCollision(myBar,myBall, canvas);
	        myBall.ballBoundaryCheck(canvas);
	
	        myBall.moveBall();
	
	        myBar.moveBar(leftPos,rightPos);
	        this.run();
        }
        else
        	this.gameOver(canvas);
    }
    public void ballBarCollision(Bar myBar,Ball myBall,Canvas canvas){
        if(((myBall.getY()+ myBall.getRadius())>=myBar.getTop())&&((myBall.getY()+myBall.getRadius())<=myBar.getBottom())&& ((myBall.getX())>=myBar.getLeft())&& ((myBall.getX())<=myBar.getRight())) {
            myBall.setDy(-(myBall.getDy()));

        }

    }
    public void ballBrickCollision(ArrayList<Bricks> brick ,Ball myBall,Canvas canvas){
        for(int i=0;i<brick.size();i++) {
            if (((myBall.getY() - myBall.getRadius()) <= brick.get(i).getBottom()) && ((myBall.getY() + myBall.getRadius()) >= brick.get(i).getTop()) && ((myBall.getX()) >= brick.get(i).getLeft()) && ((myBall.getX()) <= brick.get(i).getRight())) {
            	brick.remove(i);
            	mp.start();
            	if(brick.isEmpty())
            	{
            		this.stageTwo(canvas);
            		newLife = true;
            	}
                score+=1;
                myBall.setDy(-(myBall.getDy()));
            }
        }
    }


//Moving Bar
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                downX=event.getX();
                downY=event.getY();
                return true;

            }
            case MotionEvent.ACTION_UP:{
                upX=event.getX();
                upY=event.getY();

                float deltaX=downX-upX;
                float deltaY=downY-upY;

                if(Math.abs(deltaX) > Math.abs(deltaY)){
                    if(Math.abs(deltaX) > min_distance) {
                        if (deltaX < 0) {
                            //left=left+100;
                            //right=right+100;
                            leftPos=true;
                            rightPos=false;
                            myBar.moveBar(leftPos, rightPos);
                            return true;
                        }
                        if (deltaX > 0) {
                            leftPos=false;
                            rightPos=true;
                            myBar.moveBar(leftPos,rightPos);
                            //Right to left
                            return true;
                        }
                    }
                    else{
                        return  false;
                    }
                }
                else{
                    if(Math.abs(deltaY) > min_distance) {
                        if (deltaY < 0) {
                            //top to bottom
                            return true;
                        }
                        if (deltaY > 0) {
                            //bottom to top
                            return true;
                        }
                    }
                    else{
                        return  false;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void run() {

        invalidate();
    }
    public void collision(){

    }


}
