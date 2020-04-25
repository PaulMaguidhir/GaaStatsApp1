package com.example.gaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MatchStats extends AppCompatActivity {
    Button btnReturnHome;
    TextView txtDate, txtVenue, txtOpponent, txtCompetition, txtAwayTeamScore, txtHomeTeamScore,
            txtWide, txtFoul, txtYellowCard, txtRedCard, txtFreeWon, txtMark, txtGoal, txtPoint, txtShare;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    private List<Match> matchList = null;
    Spinner spinner;
    String emailBody = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchstats);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Match");
        matchList = new ArrayList<>();

        txtDate = findViewById(R.id.txtDate);
        txtVenue = findViewById(R.id.txtVenue);
        txtOpponent = findViewById(R.id.txtOpponent);
        txtCompetition = findViewById(R.id.txtCompetition);
        txtAwayTeamScore = findViewById(R.id.txtAwayTeamScore);
        txtHomeTeamScore = findViewById(R.id.txtHomeTeamScore);
        txtWide = findViewById(R.id.txtWideMatch);
        txtFoul = findViewById(R.id.txtFoulMatch);
        txtYellowCard = findViewById(R.id.txtYellowCardsMatch);
        txtRedCard = findViewById(R.id.txtRedCardsMatch);
        txtFreeWon = findViewById(R.id.txtFreeWonMatch);
        txtMark = findViewById(R.id.txtMarkMatch);
        txtGoal = findViewById(R.id.txtGoalMatch);
        txtPoint = findViewById(R.id.txtPointMatch);
        txtShare = findViewById(R.id.txtShareEmail);
        btnReturnHome = findViewById(R.id.btnReturnHomePage5);
        spinner = findViewById(R.id.spinnerMatch);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout_stats, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"pewm97@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Match Stats");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
                startActivity(Intent.createChooser(emailIntent, "Match Stats"));
            }
        });

        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchStats.this, Homepage.class));
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Match m = matchList.get(position);
                if (matchList.size() != 0) {
                    txtDate.setText("Date: " + m.getDate());
                    txtVenue.setText("Venue: " + m.getVenue());
                    txtOpponent.setText("Opponent: " + m.getOpponent());
                    txtCompetition.setText("Competition Type: " + m.getCompetition());
                    txtAwayTeamScore.setText("Away Team Score: " + m.getAwayTeamScore());
                    txtHomeTeamScore.setText("Home Team Score: " + m.getHomeTeamScore());
                    txtWide.setText("Wide: " + m.getWides());
                    txtFoul.setText("Foul: " + m.getFouls());
                    txtYellowCard.setText("Yellow Cards: " + m.getYellowCards());
                    txtRedCard.setText("Red Cards: " + m.getRedCards());
                    txtFreeWon.setText("Free Wons: " + m.getFreeWons());
                    txtMark.setText("Marks: " + m.getMarks());
                    txtGoal.setText("Goals: " + m.getGoals());
                    txtPoint.setText("Points: " + m.getPoints());
                    emailBody = "Date: " + m.getDate() + "\nVenue: " + m.getVenue() + "\nOpponent: "
                            + m.getOpponent() + "\nCompetition Type: " + m.getCompetition()
                            + "\nAway Team Score: " + m.getAwayTeamScore() + "\nHome Team Score: "
                            + m.getHomeTeamScore() + "\nWide: " + m.getWides() + "\nFoul: " + m.getFouls()
                            + "\nYellow Cards: " + m.getYellowCards() + "\nRed Cards: " + m.getRedCards()
                            + "\nFree Wons: " + m.getFreeWons() + "\nMarks: " + m.getMarks()
                            + "\nGoals: " + m.getGoals() + "\nPoints: " + m.getPoints();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Match m = dataSnapshot.getValue(Match.class);
                // If the list does not contain the player, add him.
                if (firebaseUser.getUid().matches(m.getUserID())) {
                    matchList.add(m);
                    arrayList.add("Home vs " + m.getOpponent() + " (" + m.getDate() + ") ");
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}