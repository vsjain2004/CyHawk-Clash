package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.text.DecimalFormat;

/**
 * the right joy stick
 */
public class RightStick {
    private double rightBaseX;
    private double baseY;
    //current touch location X and Y

    private double currentX;
    private double currentY;
    private double intensity;

    private float baseRadius;
    private float hatRadius;
    private Paint grey;
    private Paint black;
    private Paint orange;
    private boolean test=true;
    Weapon stickWeapon;

    /**
     * constructor for the right shooting joystick
     * @param canvas screen to be drawn to
     * @param w weapon to shoot
     */
    public RightStick(Canvas canvas,Weapon w) {
        if (canvas != null) {
            hatRadius = (float) Math.max(Math.min(canvas.getWidth(), canvas.getHeight()) / 4.0, 150);
        }
        stickWeapon=w;
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
     * Draws joystick to canvas
     * @param canvas area to be drawn on
     */
    public void draw(Canvas canvas) {
        //variable implementation
        baseY = canvas.getHeight() - canvas.getHeight() / 8.0;
        rightBaseX = canvas.getWidth() - canvas.getWidth()/8.0;
        if(rightBaseX!=0&& test) {
            currentX = rightBaseX;
            currentY = baseY;
            test=false;
        }


        displacement = (float) Math.sqrt((Math.pow(currentX - rightBaseX, 2)) + Math.pow(currentY - baseY, 2));

        //intensity gets the ratio between the current stick displacement and the max radius.
        //AKA, if the stick is moved as far as possible, this should be 1,
        //and if the stick is resting in its default position, this should be 0.

        canvas.drawCircle((float) rightBaseX, (float) baseY, (float) 80, grey);
        if(displacement<baseRadius)  {
            canvas.drawCircle((float)currentX, (float) currentY, (float) 50, orange);
            intensity=displacement/baseRadius;
        }
        else {
            intensity=1;
            float ratio = baseRadius / displacement;
            float constraintX = (float) rightBaseX + (float) (currentX - rightBaseX) * ratio;
            float constraintY = (float) baseY + (float) (currentY - baseY) * ratio;
            black.setTextSize(50);
//            canvas.drawText("constraintX = " +(constraintX), 50, 100, black);
//            canvas.drawText("constraintY = "+(constraintY), 50, 150, black);
//            canvas.drawText("ratio= "+(ratio), 50, 200, black);

            canvas.drawCircle(constraintX, constraintY, (float) 50, orange);
        }

//        canvas.drawText("intensity= "+((float) intensity), 50, 250, black);
//        canvas.drawText("baseY= "+((float) baseY), 50, 300, black);
//        canvas.drawText("currentX= "+((float) currentX), 50, 350, black);
//        canvas.drawText("currentY= "+((float) currentY), 50, 400, black);
//        canvas.drawText("baseRadius= "+(baseRadius), 50, 450, black);
//        canvas.drawText("displacement= "+(displacement), 50, 500, black);
//        canvas.drawText("radians= "+(angle), 50, 550, black);

    }
    double angle;

    int shootUponEquivToFireRate = 0;
    /**
     * updates joystick angle based on radians
     */
    public void update(Canvas canvas,PlayerInGame player) {
        DecimalFormat format = new DecimalFormat("0.00");
        //DEGREES
//        angle = Math.atan((currentY - baseY) / (currentX - leftBaseX))*180/Math.PI;
        //RADIANS
        angle = Math.atan((currentY - baseY) / (currentX - rightBaseX));
        //CORRECTION FOR ANGLE

        //top right quadrant
        if(currentX>rightBaseX&&currentY<baseY){
            angle*=-1;
        }
        //top left quadrant
        else if(currentX<rightBaseX&&currentY<baseY){
            angle = Math.PI-angle;
        }
        //bottom left quadrant
        else if(currentX<rightBaseX&&currentY>baseY){
            angle-=Math.PI;
            angle*=-1;
        }
        //bottom right quadrant
        else if(currentX>rightBaseX&&currentY>baseY){
            angle = 2*Math.PI -angle;
        }
        angle = Double.parseDouble(format.format(angle));
        //increment the fire
        shootUponEquivToFireRate++;
        Paint p = new Paint();
        p.setARGB(255,255,255,255);
        canvas.drawText(Integer.toString(shootUponEquivToFireRate),(float)canvas.getWidth()/2,(float)canvas.getHeight()/2,p);

        canvas.drawText(Double.toString(intensity),(float)canvas.getWidth()/2,(float)canvas.getHeight()/2+50,p);
        if(shootUponEquivToFireRate>=stickWeapon.fireRate && intensity>0.01){
            //shoot the weapon
            stickWeapon.shoot(canvas,this,player);
            //reset shoot count
            shootUponEquivToFireRate=0;
        }
    }



    /**
     * sets player position to x and y
     * @param x width value of touch
     * @param y height value of touch
     */
    public void setPosition(double x, double y) {
        currentX = x;
        currentY = y;
    }

    /**
     *
     * @return weapon in use
     */
    public Weapon getStickWeapon(){
        return stickWeapon;
    }

    /**
     *
     * @return direction of joystick
     */
    public double getFiringDirection(){
        return angle;
    }
    /**
     *
     * @return current X location of touch
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
    public double getRightBaseX(){
        return rightBaseX;
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