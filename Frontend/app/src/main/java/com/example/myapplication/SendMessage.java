package com.example.myapplication;
import android.graphics.Canvas;
import android.util.*;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.SignUp.theusername;
import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;

/**
 * this class send creates the websocket connection
 */
public class SendMessage extends AppCompatActivity implements WebSocketListener {


    String baseUrl = "ws://coms-309-041.class.las.iastate.edu:8080/chat/";

    /**
     * constructor for chat sender with websockets
     * @param username String with player username
     * @param gameID String with game ID
     */
    public SendMessage(String username, String gameID){

        baseUrl = baseUrl+gameID+"/"+username;
            WebSocketManager.getInstance().connectWebSocket(baseUrl);

        WebSocketManager.getInstance().setWebSocketListener(SendMessage.this);

    }

    /**
     * sends a message through the websocket
     * @param s : string to be sent
     */
    public void sendMessage(String s){
        messages.add(theusername+": "+s);
        if(messages.size()==7){
            messages.remove(0);
        }
        try {
            // send message
            WebSocketManager.getInstance().sendMessage(theusername+": "+s);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage());
        }
    }

    Canvas c = new Canvas();
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    ArrayList<String> messages = new ArrayList<>();
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {

                messages.add(message);

            if(messages.size()==7){
                messages.remove(0);
            }
        });

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
//        String closedBy = remote ? "server" : "local";
//        runOnUiThread(() -> {
//            String s = msgTv.getText().toString();
//            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
//        });
        WebSocketManager.getInstance().disconnectWebSocket();

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
    public void setCanvas(Canvas canvas){
        c=canvas;
    }
    public ArrayList<String> returnMessage(){
        return messages;
    }
}

