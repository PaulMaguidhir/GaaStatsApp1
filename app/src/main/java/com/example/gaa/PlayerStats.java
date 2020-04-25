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

public class PlayerStats extends AppCompatActivity {
    Button btnReturnHome;
    TextView txtName, txtPosition, txtAge, txtStatus, txtMatches, txtWide, txtFoul, txtYellowCard,
            txtRedCard, txtFreeWon, txtMark, txtGoal, txtPoint;
    private DatabaseReference mDatabase;
    private DatabaseReference mPlayerRef;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    ArrayList<String> arrayList;
    ArrayList<Player> playerList;
    ArrayAdapter arrayAdapter;
    private List<Match> matchList = null;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Match");
        mPlayerRef = FirebaseDatabase.getInstance().getReference().child("Player");
        matchList = new ArrayList<>();

        txtName = findViewById(R.id.txtName);
        txtPosition = findViewById(R.id.txtPosition);
        txtAge = findViewById(R.id.txtAge);
        txtStatus = findViewById(R.id.txtStatus);
        txtMatches = findViewById(R.id.txtMatches);
        txtWide = findViewById(R.id.txtWides);
        txtFoul = findViewById(R.id.txtFouls);
        txtYellowCard = findViewById(R.id.txtYellowCards);
        txtRedCard = findViewById(R.id.txtRedCards);
        txtFreeWon = findViewById(R.id.txtFreeWon);
        txtMark = findViewById(R.id.txtMark);
        txtGoal = findViewById(R.id.txtGoal);
        txtPoint = findViewById(R.id.txtPoint);
        btnReturnHome = findViewById(R.id.btnReturnHomePage4);
        spinner = findViewById(R.id.spinnerPlayers);

        arrayList = new ArrayList<>();
        playerList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout_stats, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayerStats.this, Homepage.class));
            }
        });

        mPlayerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player p = dataSnapshot.getValue(Player.class);
                // If the list does not contain the player, add him.
                if (firebaseUser.getUid().matches(p.getUserID())) {
                    playerList.add(p);
                    if (p.isOnTeam()) {
                        arrayList.add("(T) " + p.getName() + " (" + p.getPosition() + ")");
                    } else {
                        arrayList.add("(S) " + p.getName() + " (" + p.getPosition() + ")");
                    }
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Player p = playerList.get(position);
                int matchesPlayed = 0, widesCount = 0, foulCount = 0, yc = 0, rc = 0,
                        freeWonCount = 0, markCount = 0, goalCount = 0, pointCount = 0;
                if (matchList.size() != 0) {
                    txtName.setText("Name: " + p.getName());
                    txtAge.setText("Age: " + p.getAge());
                    txtPosition.setText("Position: " + p.getPosition());
                    if (p.isOnTeam()) {
                        txtStatus.setText("Status: On Team");
                    } else {
                        txtStatus.setText("Status: Off Team");
                    }
                    //Find No. of Matches
                    Loop:
                    for (int i = 0; i < matchList.size(); i++) {
                        Match m = matchList.get(i);
                        String[] matches = m.getPlayers().split("\n");
                        for (String x : matches) {
                            if (x.matches(p.getPlayerID())) {
                                matchesPlayed++;
                            }
                        }
                        String[] wides = m.getWidePlayers().split("\n");
                        for (String x : wides) {
                            if (x.matches(p.getPlayerID())) {
                                widesCount++;
                            }
                        }
                        String[] fouls = m.getFoulPlayers().split("\n");
                        for (String x : fouls) {
                            if (x.matches(p.getPlayerID())) {
                                foulCount++;
                            }
                        }
                        String[] yellowCards = m.getYcPlayers().split("\n");
                        for (String x : yellowCards) {
                            if (x.matches(p.getPlayerID())) {
                                yc++;
                            }
                        }
                        String[] redCards = m.getRcPlayers().split("\n");
                        for (String x : redCards) {
                            if (x.matches(p.getPlayerID())) {
                                rc++;
                            }
                        }
                        String[] freeWons = m.getFreeWonPlayers().split("\n");
                        for (String x : freeWons) {
                            if (x.matches(p.getPlayerID())) {
                                freeWonCount++;
                            }
                        }
                        String[] marks = m.getMarkPlayers().split("\n");
                        for (String x : marks) {
                            if (x.matches(p.getPlayerID())) {
                                markCount++;
                            }
                        }
                        String[] goals = m.getGoalPlayers().split("\n");
                        for (String x : goals) {
                            if (x.matches(p.getPlayerID())) {
                                goalCount++;
                            }
                        }
                        String[] points = m.getPointPlayers().split("\n");
                        for (String x : points) {
                            if (x.matches(p.getPlayerID())) {
                                pointCount++;
                            }
                        }
                    }
                    txtMatches.setText("Matches: " + matchesPlayed);
                    txtWide.setText("Wides: " + widesCount);
                    txtFoul.setText("Fouls: " + foulCount);
                    txtYellowCard.setText("Yellow Cards: " + yc);
                    txtRedCard.setText("Red Cards: " + rc);
                    txtFreeWon.setText("Free Won: " + freeWonCount);
                    txtMark.setText("Marks: " + markCount);
                    txtGoal.setText("Goals: " + goalCount);
                    txtPoint.setText("Points: " + pointCount);
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


