package com.example.gaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class startGameContinue extends AppCompatActivity {
    private Button mStartButton;
    private Button mPauseButton;
    private Button mResetButton;
    private Chronometer mChronometer;

    private long lastPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_continue);

        mStartButton = (Button) findViewById(R.id.button_startMatch);
        mPauseButton = (Button) findViewById(R.id.button_pauseMatch);
        mResetButton = (Button) findViewById(R.id.button_resetMatch);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);



        mStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(lastPause != 0) {
                    mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                }
                    else{ mChronometer.setBase(SystemClock.elapsedRealtime());
                }
                    mChronometer.start();
                    mStartButton.setEnabled(false);
                    mStartButton.setEnabled(true);
            }

        });

        mPauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                lastPause = SystemClock.elapsedRealtime();
                mChronometer.stop();
                mPauseButton.setEnabled(false);
                mStartButton.setEnabled(true);

            }

        });
        mResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mChronometer.stop();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
                mStartButton.setEnabled(true);
                mPauseButton.setEnabled(false);

            }
        });

    }

}
