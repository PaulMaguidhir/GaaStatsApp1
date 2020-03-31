package com.example.gaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class startGameContinue extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Button mStartButton;
    private Button mPauseButton;
    private Button mResetButton;
    private Chronometer mChronometer;
    private long lastPause;
    private DatabaseReference sDatabase;

    //Score Var
    double scoreHomeTeam = 0;
    double scoreAwayTeam = 0;
    int foul = 0;
    int wide = 0;
    int yellowCard = 0;
    int redCard = 0;
    int freeWon = 0;
    int mark = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_continue);
        sDatabase = FirebaseDatabase.getInstance().getReference("Match Details");

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
            case R.id.matchFoul:
                Toast.makeText(this, "Foul Selected", Toast.LENGTH_SHORT);
                return true;
            case R.id.mark:
                Toast.makeText(this, "Mark Selected", Toast.LENGTH_SHORT);
                return true;
            default:
                return false;
        }
    }

    public void homeTeamScoresGoal(MenuItem item) {
        scoreHomeTeam = scoreHomeTeam + 1;
        displayforHomeTeam(scoreHomeTeam);
    }

    public void homeTeamScoresPoint(MenuItem item) {
        scoreHomeTeam = scoreHomeTeam + 0.01;
        displayforHomeTeam(scoreHomeTeam);
    }

    private void displayforHomeTeam(double scoreHomeTeam) {
        TextView scoreView = (TextView) findViewById(R.id.homeTeamScore);
        scoreView.setText("" + scoreHomeTeam);

    }

    //Scoring Away function
    public void awayTeamScoresGoal(MenuItem item) {
        scoreAwayTeam = scoreAwayTeam + 1;
        displayforAwayTeam(scoreAwayTeam);
    }


    public void awayTeamScoresPoint(MenuItem item) {
        scoreAwayTeam = scoreAwayTeam + 0.01;
        displayforAwayTeam(scoreAwayTeam);
    }

    private void displayforAwayTeam(double scoreHomeTeam) {
        TextView scoreView = (TextView) findViewById(R.id.awayTeamScore);
        scoreView.setText("" + scoreAwayTeam);
    }
//Keeps track of match fouls
    public void matchFoul(MenuItem item) {
        foul = foul + 1;
        displayforfoul(foul);
   }
    private void displayforfoul(int foul) {
        TextView scoreView = (TextView) findViewById(R.id.matchFoul);
        scoreView.setText("" + foul);

    }
    //Keeps track of match wides
    public void matchWide(MenuItem item) {
        wide = wide + 1;
        displayforWide(wide);
    }
    private void displayforWide(int wide) {
        TextView scoreView = (TextView) findViewById(R.id.matchWide);
        scoreView.setText("" + wide);

    }
    //Keeps track of match mark
    public void matchMark(MenuItem item) {
        mark = mark + 1;
        displayforMark(mark);
    }
    private void displayforMark(int mark) {
        TextView scoreView = (TextView) findViewById(R.id.matchMark);
        scoreView.setText("" + mark);

    }
    //Keeps track of match free won
    public void matchFreewon(MenuItem item) {
        freeWon = freeWon + 1;
        displayforfreeWon(freeWon);
    }
    private void displayforfreeWon(int freeWon) {
        TextView scoreView = (TextView) findViewById(R.id.matchFreeWon);
        scoreView.setText("" + freeWon);

    }
    //Keeps track of match red card
    public void matchredCard(MenuItem item) {
        redCard = redCard + 1;
        displayforredCard(redCard);
    }
    private void displayforredCard(int redCard) {
        TextView scoreView = (TextView) findViewById(R.id.matchRedCard);
        scoreView.setText("" + redCard);

    }

    //Keeps track of match yellow card
    public void matchyellowCard(MenuItem item) {
        yellowCard = yellowCard + 1;
        displayforyellowCard(yellowCard);
    }
    private void displayforyellowCard(int yellowCard) {
        TextView scoreView = (TextView) findViewById(R.id.matchYellowCard);
        scoreView.setText("" + yellowCard);

    }


    public  void saveMatchDetailsStarGame(View view){
        String scoreHomeTeam =((TextView) findViewById(R.id.homeTeamScore)).getText().toString();
        String scoreAwayTeam =((TextView) findViewById(R.id.awayTeamScore)).getText().toString();
        String foul =((TextView) findViewById(R.id.matchFoul)).getText().toString();
        String wide =((TextView) findViewById(R.id.matchWide)).getText().toString();
        String redCard =((TextView) findViewById(R.id.matchRedCard)).getText().toString();
        String yellowCard =((TextView) findViewById(R.id.matchYellowCard)).getText().toString();
        String freeWon =((TextView) findViewById(R.id.matchFreeWon)).getText().toString();
        String mark =(( TextView) findViewById(R.id.matchMark)).getText().toString();
       

        sDatabase.child("Home Team Score").setValue(scoreHomeTeam);
        sDatabase.child("Away Team Score").setValue(scoreAwayTeam);
        sDatabase.child("Foul").setValue(foul);
        sDatabase.child("Wide").setValue(wide);
        sDatabase.child("Red Card").setValue(redCard);
        sDatabase.child("Yellow card").setValue(yellowCard);
        sDatabase.child("Free Won").setValue(freeWon);
        sDatabase.child("Mark").setValue(mark);
        sDatabase.child("Match Time").setValue(mChronometer);
        startActivity(new Intent(startGameContinue.this, homepage.class));
    }

}



