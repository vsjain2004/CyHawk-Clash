package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import static com.example.myapplication.SignUp.theusername;
import java.util.ArrayList;


public class InGameChat {
    //    private String BASE_URL = "ws://10.0.2.2:8080/chat/";
    private Paint grey=new Paint();
    private Paint white = new Paint();
    private Paint darkOrange = new Paint();
    private Paint lightOrange = new Paint();
    private Paint lighten = new Paint();

    private Paint darken = new Paint();
    private float buttonLeft;
    private float buttonTop;
    private float buttonBottom;
    private float buttonRight;

    private float keyIncrement;
    private float chatWidth;
    private float chatHeight;
    private float keyLineTop;
    private float keyLineMiddle;



    private float keyLineBottom;

    private float qLeft;
    private float wLeft;
    private float eLeft;
    private float rLeft;
    private float tLeft;
    private float yLeft;
    private float uLeft;
    private float iLeft;
    private float oLeft;
    private float pLeft;
    private float aLeft;
    private float sLeft;
    private float dLeft;
    private float fLeft;
    private float gLeft;
    private float hLeft;
    private float jLeft;
    private float kLeft;
    private float lLeft;
    private float zLeft;
    private float xLeft;
    private float cLeft;
    private float vLeft;
    private float bLeft;
    private float nLeft;
    private float mLeft;
    private float deleteLeft;
    private float sendLeft;


    private ArrayList<String> messages=new ArrayList<>();

    float leftIncrement;
    float keyWidth;
    float buffer;
    boolean toggle=true;
    //isPressed: whether or not chat should show
    private Boolean isPressed=false;
    //message to be sent
    String message = "";
    /**
     * constructor: sets paint colors
     */
    public InGameChat(){
        //sets grey to black with alpha of 200(mostly opaque)
        grey.setTextSize(75);
        grey.setARGB(240,100,100,100);
        white.setARGB(255,0,0,0);
        darkOrange.setARGB(255,242,112,5);
        lightOrange.setARGB(255,245,151,0);
        lightOrange.setTextSize(50);
        lighten.setARGB(50,255,255,255);
        darken.setARGB(50,0,0,0);
    }

    //text that represents the whole chat
    public String text = "";
    /**
     * draws chat toggle button and chat to the screen
     * @param canvas phone screen to be drawn to
     */
    public void draw(Canvas canvas){
        if(canvas!=null) {
            canvas.drawText(text,canvas.getWidth()-canvas.getWidth()/12,canvas.getHeight()/12,darkOrange);
            if(toggle) {
                keyIncrement=(float)(canvas.getWidth()/12.0);
                buttonLeft=keyIncrement/10;
                buttonTop=keyIncrement/10;
                buttonRight=keyIncrement-keyIncrement/10;
                buttonBottom= keyIncrement-keyIncrement/10;
                leftIncrement=keyIncrement+keyIncrement/12;
                chatHeight=canvas.getHeight()-keyIncrement-keyIncrement/12;
                keyWidth=(float)((canvas.getWidth()-2*leftIncrement)*0.09);
                buffer = (float)((canvas.getWidth()-2*leftIncrement)*0.00909091);
                keyLineTop=chatHeight-3*(keyWidth+buffer)+keyWidth;
                keyLineMiddle=chatHeight-2*(keyWidth+buffer)+keyWidth;
                keyLineBottom=chatHeight-buffer;
                //first line of keys
                qLeft=leftIncrement+buffer;
                wLeft=qLeft+keyWidth+buffer;
                eLeft=wLeft+keyWidth+buffer;
                rLeft=eLeft+keyWidth+buffer;
                tLeft=rLeft+keyWidth+buffer;
                yLeft=tLeft+keyWidth+buffer;
                uLeft=yLeft+keyWidth+buffer;
                iLeft=uLeft+keyWidth+buffer;
                oLeft=iLeft+keyWidth+buffer;
                pLeft=oLeft+keyWidth+buffer;
                //second line of keys
                aLeft=leftIncrement+buffer+(float)0.25*keyWidth;
                sLeft=aLeft+keyWidth+buffer;
                dLeft=sLeft+keyWidth+buffer;
                fLeft=dLeft+keyWidth+buffer;
                gLeft=fLeft+keyWidth+buffer;
                hLeft=gLeft+keyWidth+buffer;
                jLeft=hLeft+keyWidth+buffer;
                kLeft=jLeft+keyWidth+buffer;
                lLeft=kLeft+keyWidth+buffer;
                //third line of keys
                zLeft=leftIncrement+buffer+keyWidth*(float)0.9;
                xLeft=zLeft+keyWidth+buffer;
                cLeft=xLeft+keyWidth+buffer;
                vLeft=cLeft+keyWidth+buffer;
                bLeft=vLeft+keyWidth+buffer;
                nLeft=bLeft+keyWidth+buffer;
                mLeft=nLeft+keyWidth+buffer;
                deleteLeft = mLeft+keyWidth+buffer;
                sendLeft=canvas.getWidth()-2*leftIncrement+buffer;
                toggle=false;
            }

            if (!isPressed) {
                canvas.drawRect(buttonLeft, buttonTop, buttonRight, buttonBottom, darkOrange);

            } else {

                //first part of drawing lit button
                canvas.drawRect(buttonLeft, buttonTop, buttonRight, buttonBottom, lightOrange);
                //draw chat box
                canvas.drawRect(keyIncrement, keyIncrement, canvas.getWidth()-keyIncrement,
                        chatHeight+keyIncrement/12, grey);
                //lighten inner chat box
                canvas.drawRect(keyIncrement+keyIncrement/12,keyIncrement+keyIncrement/12,
                        canvas.getWidth()-keyIncrement-keyIncrement/12,
                        chatHeight,lighten);


                //TOP ROW
                canvas.drawRect(qLeft,keyLineTop-keyWidth,qLeft+keyWidth,keyLineTop,grey); //Q
                canvas.drawRect(wLeft,keyLineTop-keyWidth,wLeft+keyWidth,keyLineTop,grey); //W
                canvas.drawRect(eLeft,keyLineTop-keyWidth,eLeft+keyWidth,keyLineTop,grey); //E
                canvas.drawRect(rLeft,keyLineTop-keyWidth,rLeft+keyWidth,keyLineTop,grey); //R
                canvas.drawRect(tLeft,keyLineTop-keyWidth,tLeft+keyWidth,keyLineTop,grey); //T
                canvas.drawRect(yLeft,keyLineTop-keyWidth,yLeft+keyWidth,keyLineTop,grey); //Y
                canvas.drawRect(uLeft,keyLineTop-keyWidth,uLeft+keyWidth,keyLineTop,grey); //U
                canvas.drawRect(iLeft,keyLineTop-keyWidth,iLeft+keyWidth,keyLineTop,grey); //I
                canvas.drawRect(oLeft,keyLineTop-keyWidth,oLeft+keyWidth,keyLineTop,grey); //O
                canvas.drawRect(pLeft,keyLineTop-keyWidth,pLeft+keyWidth,keyLineTop,grey); //P
                //MIDDLE ROW
                canvas.drawRect(aLeft,keyLineMiddle-keyWidth,aLeft+keyWidth,keyLineMiddle,grey); //A
                canvas.drawRect(sLeft,keyLineMiddle-keyWidth,sLeft+keyWidth,keyLineMiddle,grey); //S
                canvas.drawRect(dLeft,keyLineMiddle-keyWidth,dLeft+keyWidth,keyLineMiddle,grey); //D
                canvas.drawRect(fLeft,keyLineMiddle-keyWidth,fLeft+keyWidth,keyLineMiddle,grey); //F
                canvas.drawRect(gLeft,keyLineMiddle-keyWidth,gLeft+keyWidth,keyLineMiddle,grey); //G
                canvas.drawRect(hLeft,keyLineMiddle-keyWidth,hLeft+keyWidth,keyLineMiddle,grey); //H
                canvas.drawRect(jLeft,keyLineMiddle-keyWidth,jLeft+keyWidth,keyLineMiddle,grey); //K
                canvas.drawRect(kLeft,keyLineMiddle-keyWidth,kLeft+keyWidth,keyLineMiddle,grey); //J
                canvas.drawRect(lLeft,keyLineMiddle-keyWidth,lLeft+keyWidth,keyLineMiddle,grey); //L
                //BOTTOM ROW
                canvas.drawRect(zLeft,keyLineBottom-keyWidth,zLeft+keyWidth,keyLineBottom,grey); //Z
                canvas.drawRect(xLeft,keyLineBottom-keyWidth,xLeft+keyWidth,keyLineBottom,grey); //X
                canvas.drawRect(cLeft,keyLineBottom-keyWidth,cLeft+keyWidth,keyLineBottom,grey); //C
                canvas.drawRect(vLeft,keyLineBottom-keyWidth,vLeft+keyWidth,keyLineBottom,grey); //V
                canvas.drawRect(bLeft,keyLineBottom-keyWidth,bLeft+keyWidth,keyLineBottom,grey); //B
                canvas.drawRect(nLeft,keyLineBottom-keyWidth,nLeft+keyWidth,keyLineBottom,grey); //N
                canvas.drawRect(mLeft,keyLineBottom-keyWidth,mLeft+keyWidth,keyLineBottom,grey); //M
                canvas.drawRect(deleteLeft,keyLineBottom-keyWidth,deleteLeft+keyWidth,keyLineBottom,grey);
                canvas.drawRect(sendLeft,keyLineBottom-keyWidth,sendLeft+keyWidth,keyLineBottom,grey);
                //draws textbox
                canvas.drawRect(2*leftIncrement,leftIncrement+leftIncrement/2,
                        canvas.getWidth()-2*leftIncrement,2*leftIncrement,lighten);
                canvas.drawText(message,2*leftIncrement,2*leftIncrement-leftIncrement/20,grey);
            }
            //lighten inner button
            canvas.drawRect(buttonLeft+(float)buttonBottom/10,buttonTop+(float)buttonBottom/10,
                    buttonRight-(float)buttonBottom/10,buttonBottom-(float)buttonBottom/10,lighten);
            //draw chat border
            canvas.drawRect(canvas.getWidth()-(float)canvas.getWidth()/4,0,canvas.getWidth(),MaxHeight+50,darken);
        }
        //drawing messages in chat



        Log.d("hi",messages.toString());
        //decrement through array of messages
        for(int i=messages.size()-1;i>=0;i--){
            canvas.drawText(messages.get(i),canvas.getWidth()-canvas.getWidth()/4+10,curHeight,lightOrange);
            curHeight-=50;
        }
        curHeight=350;

    }
    int curHeight =350;
    int MaxHeight =350;
    /**
     *
     * @return width of key
     */
    public float getKeyIncrement() {
        return keyIncrement;
    }

    /**
     * toggles boolean that affects if chat is shown
     */
    public void toggle(){
        isPressed= !isPressed;
    }

    /**
     *
     * @return if the chat is open or closed(boolean)
     */
    public boolean getIsPressed(){
        return isPressed;
    }

    /**
     *
     * @return left side of button
     */
    public float getButtonLeft() {
        return buttonLeft;
    }
    /**
     *
     * @return top of button
     */
    public float getButtonTop() {
        return buttonTop;
    }
    /**
     *
     * @return bottom side of button
     */
    public float getButtonBottom() {
        return buttonBottom;
    }
    /**
     *
     * @return right side of button
     */
    public float getButtonRight() {
        return buttonRight;
    }

    /**
     *
     * @return array of keys from top left to bottom right(qwertyuiopasdfghjklzxcvbnm)
     */
    public float[] getKeyLefts(){
            float[] keys = {qLeft, wLeft, eLeft, rLeft, tLeft, yLeft, uLeft, iLeft, oLeft, pLeft, aLeft,
                         sLeft, dLeft, fLeft, gLeft, hLeft, jLeft, kLeft, lLeft,zLeft,xLeft,cLeft,vLeft,
                         bLeft,nLeft,mLeft,deleteLeft};
            return keys;
    }

    /**
     * returns value for top of the top row of keys
     * @return
     */
    public float getKeyLineTop() {
        return keyLineTop;
    }

    /**
     * returns value for top of the middle row of keys
     * @return
     */
    public float getKeyLineMiddle() {
        return keyLineMiddle;
    }

    /**
     * returns value for top of the bottom row of keys
     * @return
     */
    public float getKeyLineBottom() {
        return keyLineBottom;
    }

    /**
     *
     * @return message that is currently being written
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message thing that you want to be sent
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessages(ArrayList<String> messages){
        this.messages=messages;
    }
    public ArrayList<String> getMessages(){
        return messages;
    }
    /**
     * buffer between keys
     * @return
     */
    public float getBuffer(){
        return buffer;
    }

    /**
     *
     * @return left side of send bar for drawing
     */
    public float getSendLeft(){
        return sendLeft;
    }

    public void setText(String tx){
        text=tx;
    }
}
