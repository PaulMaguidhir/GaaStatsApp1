package com.example.gaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class startGameContinue extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private Button mStartButton;
    private Button mPauseButton;
    private Button mResetButton;
    private Chronometer mChronometer;
    private long lastPause;

    //Score Var
    double scoreHomeTeam = 0;
    double scoreAwayTeam = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_continue);

        mStartButton = (Button) findViewById(R.id.button_startMatch);
        mPauseButton = (Button) findViewById(R.id.button_pauseMatch);
        mResetButton = (Button) findViewById(R.id.button_resetMatch);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);


        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lastPause != 0) {
                    mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                } else {
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                }
                mChronometer.start();
                mStartButton.setEnabled(false);
                mStartButton.setEnabled(true);
            }

        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastPause = SystemClock.elapsedRealtime();
                mChronometer.stop();
                mPauseButton.setEnabled(false);
                mStartButton.setEnabled(true);

            }

        });
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChronometer.stop();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
                mStartButton.setEnabled(true);
                mPauseButton.setEnabled(false);
               //Resetting score Vareibles
                scoreHomeTeam = 0;
                scoreAwayTeam = 0;
                displayforHomeTeam(0);
                displayforAwayTeam(0);

            }
        });

    }

    //Pop up function
    public void inputStats(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.inputstats_menu);
        popup.show();

    }


    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Point:
                Toast.makeText(this, "Point Selected", Toast.LENGTH_SHORT);
                return true;
            case R.id.Goal:
                Toast.makeText(this, "Goal Selected", Toast.LENGTH_SHORT);
                return true;
                case R.id.pointConceded:
                Toast.makeText(this, "Point Conceded", Toast.LENGTH_SHORT);
                return true;
            case R.id.goalConceded:
                Toast.makeText(this, "Goal Conceded", Toast.LENGTH_SHORT);
                return true;
            case R.id.yellowCard:
                Toast.makeText(this, "Yellow Card Selected", Toast.LENGTH_SHORT);
                return true;
            case R.id.redCard:
                Toast.makeText(this, "Red card Selected", Toast.LENGTH_SHORT);
                return true;
            case R.id.wide:
                Toast.makeText(this, "Wide Selected", Toast.LENGTH_SHORT);
                return true;
            case R.id.freeWon:
                Toast.makeText(this, "Free won Selected", Toast.LENGTH_SHORT);
                return true;
            case R.id.foul:
                Toast.makeText(this, "Foul Selected", Toast.LENGTH_SHORT);
                return true;
            case R.id.mark:
                Toast.makeText(this, "Mark Selected", Toast.LENGTH_SHORT);
                return true;
                default:
                    return false;
        }
    }

    public void homeTeamScoresGoal (MenuItem item){
        scoreHomeTeam = scoreHomeTeam + 1;
        displayforHomeTeam(scoreHomeTeam);
    }

    public void homeTeamScoresPoint(MenuItem item){
        scoreHomeTeam = scoreHomeTeam + 0.01;
        displayforHomeTeam(scoreHomeTeam);
    }
    private void displayforHomeTeam(double scoreHomeTeam){
        TextView scoreView= (TextView) findViewById(R.id.homeTeamScore);
        scoreView.setText("" + scoreHomeTeam);

    }
    //Scoring Away function
    public void awayTeamScoresGoal(MenuItem item){
        scoreAwayTeam = scoreAwayTeam + 1;
        displayforAwayTeam(scoreAwayTeam);
    }
    public void awayTeamScoresPoint(MenuItem item){
        scoreAwayTeam = scoreAwayTeam + 0.01;
        displayforAwayTeam(scoreAwayTeam);
    }
    private void displayforAwayTeam(double scoreHomeTeam){
        TextView scoreView= (TextView) findViewById(R.id.awayTeamScore);
        scoreView.setText("" + scoreAwayTeam);

    }

}



