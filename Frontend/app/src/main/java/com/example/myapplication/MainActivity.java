package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.runGame.*;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import java.util.Objects;

/**
 * create the and draws the game
 */
public class MainActivity extends AppCompatActivity {

    /**
     * this is the main constructor for the game, it formats the screen for the game to be drawn
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow().getDecorView().getWindowInsetsController()).hide(
                android.view.WindowInsets.Type.statusBars()
        );
        getWindow().getDecorView().getWindowInsetsController().hide(
                android.view.WindowInsets.Type.statusBars()
                        | android.view.WindowInsets.Type.navigationBars()
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(new Game(this));




    }



}
