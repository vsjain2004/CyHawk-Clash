package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.text.DecimalFormat;

public class LeftStick {
    private double leftBaseX;
    private double baseY;
    //current touch location X and Y

    private double currentX;
    private double currentY;
    private double intensity;
    private int pointerCount;

    private float baseRadius;
    private float hatRadius;
    private Paint grey;
    private Paint black;
    private Paint orange;
    private boolean test=true;

    /**
     * constructor for left stick, sets paint and hat radius
     * @param canvas
     */
    public LeftStick(Canvas canvas) {
        if (canvas != null) {
            hatRadius = (float) Math.max(Math.min(canvas.getWidth(), canvas.getHeight()) / 4.0, 150);
        }

        baseRadius = 50;
        grey = new Paint();
        grey.setARGB(255, 100, 100, 255);
        black = new Paint();
        black.setARGB(255, 255, 255, 255);
        orange = new Paint();
        orange.setARGB(255, 255, 100, 100);

    }
    float displacement;

    /**
     * draws left joystick to canvas based on direction the joystick is pulled.
     * @param canvas phone screen to be drawn to
     */
    public void draw(Canvas canvas) {
        //variable implementation
        baseY = canvas.getHeight() - canvas.getHeight() / 8.0;
        leftBaseX = canvas.getWidth() / 8.0;

        if(leftBaseX!=0&& test) {
            currentX = leftBaseX;
            currentY = baseY;
            test=false;
        }


        displacement = (float) Math.sqrt((Math.pow(currentX - leftBaseX, 2)) + Math.pow(currentY - baseY, 2));

        //intensity gets the ratio between the current stick displacement and the max radius.
        //AKA, if the stick is moved as far as possible, this should be 1,
        //and if the stick is resting in its default position, this should be 0.

        canvas.drawCircle((float) leftBaseX, (float) baseY, (float) 80, grey);
        if(displacement<baseRadius)  {
            canvas.drawCircle((float)currentX, (float) currentY, (float) 50, orange);
            intensity=displacement/baseRadius;
        }
        else {
            intensity=1;
            float ratio = baseRadius / displacement;
            float constraintX = (float) leftBaseX + (float) (currentX - leftBaseX) * ratio;
            float constraintY = (float) baseY + (float) (currentY - baseY) * ratio;
            black.setTextSize(50);
//            canvas.drawText("constraintX = " +(constraintX), 50, 100, black);
//            canvas.drawText("constraintY = "+(constraintY), 50, 150, black);
//            canvas.drawText("ratio= "+(ratio), 50, 200, black);

            canvas.drawCircle(constraintX,  constraintY, (float) 50, orange);
        }
//
//        canvas.drawText("intensity= "+((float) intensity), 50, 250, black);
//        canvas.drawText("baseY= "+((float) baseY), 50, 300, black);
//        canvas.drawText("currentX= "+((float) currentX), 50, 350, black);
//        canvas.drawText("currentY= "+((float) currentY), 50, 400, black);
//        canvas.drawText("baseRadius= "+(baseRadius), 50, 450, black);
//        canvas.drawText("displacement= "+(displacement), 50, 500, black);
//        canvas.drawText("radians= "+(angle), 50, 550, black);
//        canvas.drawText("pointer count="+pointerCount,50,600,black);

    }
    double angle;
    /**
     * updates joystick angle based on radians
     */
    public void update() {
        DecimalFormat format = new DecimalFormat("0.00");
        //DEGREES
//        angle = Math.atan((currentY - baseY) / (currentX - leftBaseX))*180/Math.PI;
        //RADIANS
        angle = Math.atan((currentY - baseY) / (currentX - leftBaseX));
        //CORRECTION FOR ANGLE

        //top right quadrant
        if(currentX>leftBaseX&&currentY<baseY){
            angle*=-1;
        }
        //top left quadrant
        else if(currentX<leftBaseX&&currentY<baseY){
            angle = Math.PI-angle;
        }
        //bottom left quadrant
        else if(currentX<leftBaseX&&currentY>baseY){
            angle-=Math.PI;
            angle*=-1;
        }
        //bottom right quadrant
        else if(currentX>leftBaseX&&currentY>baseY){
            angle = 2*Math.PI -angle;
        }
        angle = Double.parseDouble(format.format(angle));

    }
    double prevX;
    double prevY;
    /**
     * sets player position to x and y
     * @param x width value of touch
     * @param y height value of touch
     */

    public void setPosition(double x, double y) {

        if(x!=0&&y!=0) {
            currentX = x;
            currentY = y;
            prevX=x;
            prevY=y;
        }
        else if(x==0&&y==0){
            currentX=prevX;
            currentY=prevY;
        }


    }

    /**
     * returns angle (drection of movement)
     * @return
     */
    public double getMovementDirection(){
        return angle;
    }

    /**
     * returns current X location of touch
     * @return
     */
    public double getCurrentX(){
        return currentX;
    }

    /**
     *
     * @return current Y location of touch
     */
    public double getCurrentY(){
        return currentY;
    }

    /**
     *
     * @return middle of left joystick on X
     */
    public double getLeftBaseX(){
        return leftBaseX;
    }

    /**
     *
     * @return middle of left joystick on Y
     */
    public double getBaseY(){
        return baseY;
    }

    /**
     *
     * @return intensity of joystick's tilt
     */
    public double getIntensity(){
        return intensity;
    }
}