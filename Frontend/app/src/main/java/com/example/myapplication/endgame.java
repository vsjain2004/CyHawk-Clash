package com.example.myapplication;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class endgame extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashendgame);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        TextView theWin = findViewById(R.id.win);
        Button nextS = findViewById(R.id.buttonN);
        nextS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameEnd = new Intent(endgame.this, home.class);
                gameEnd.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gameEnd);
            }
        });


    }

}