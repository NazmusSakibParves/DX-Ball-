package parves.android.DxBall;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * 
 * @author S.M. NAZMUS SAKIB PARVES
 *
 */


public class Bar {
	 float top,bottom,left,right;
	    Canvas canvas = new Canvas();
	    Paint paint;
	    Point point;
	    int x,y;


	    Bar(){
	        left =0;
	        top=0;
	        right=0;
	        bottom=0;
	        paint=new Paint();
	        paint.setColor(Color.BLUE);

	    }

	    public void setBottom(float bottom) {
	        this.bottom = bottom;
	    }

	    public void setLeft(float left) {
	        this.left = left;
	    }

	    public void setRight(float right) {
	        this.right = right;
	    }

	    public void setTop(float top) {
	        this.top = top;
	    }

	    public float getLeft() {
	        return left;
	    }

	    public float getRight() {
	        return right;
	    }

	    public float getBottom() {
	        return bottom;
	    }

	    public Paint getPaint() {
	        return paint;
	    }

	    public float getTop() {
	        return top;
	    }

	    public void moveBar(boolean leftPos,boolean rightPos){
	        if(leftPos==true){
	            if(GameCanvas.checkWidth>=right) {
	                left = left + 8;
	                right = right + 8;
	            }


	        }
	        else if(rightPos==true){
	            if(0<=left) {
	                //if()
	                left = left - 8;
	                right = right - 8;
	            }

	        }

	   }

}
