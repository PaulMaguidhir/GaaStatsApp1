package com.example.gaa;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartGameContinue extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TextView txtHomeScore, txtAwayScore;
    Button btnStart, btnPause, btnReset, btnCancel, btnSave, player1, player2, player3, player4, player5,
            player6, player7, player8, player9, player10, player11, player12, player13, player14, player15;
    private Chronometer mChronometer;
    private long lastPause;
    private DatabaseReference mDatabase;
    private DatabaseReference mRefMatch;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private int selectedPlayer = -1;
    private int subPoolIndex = 0;
    private Player s = null;
    private int substitutionIndex = -1;
    private Player[] mPlayers;
    private ArrayList<Player> subPlayer;
    private ArrayList<Player> subPool;
    private ArrayList<Player> mDisPlayers;
    String venue = null, opponent = null, competition = null, date = null;
    private Match match = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_continue);
        mDatabase = FirebaseDatabase.getInstance().getReference("Player");
        mRefMatch = FirebaseDatabase.getInstance().getReference("Match");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        match = new Match();
        match.setUserID(firebaseUser.getUid());
        mPlayers = new Player[16];

        for (int i = 0; i < mPlayers.length; i++) {
            mPlayers[i] = null;
        }

        mDisPlayers = new ArrayList<>();
        subPlayer = new ArrayList<>();
        subPool = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            venue = b.getString("venue");
            opponent = b.getString("opponent");
            competition = b.getString("competition");
            date = b.getString("date");
            match.setVenue(venue);
            match.setOpponent(opponent);
            match.setCompetition(competition);
            match.setDate(date);
        }

        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        player5 = findViewById(R.id.player5);
        player6 = findViewById(R.id.player6);
        player7 = findViewById(R.id.player7);
        player8 = findViewById(R.id.player8);
        player9 = findViewById(R.id.player9);
        player10 = findViewById(R.id.player10);
        player11 = findViewById(R.id.player11);
        player12 = findViewById(R.id.player12);
        player13 = findViewById(R.id.player13);
        player14 = findViewById(R.id.player14);
        player15 = findViewById(R.id.player15);
        txtHomeScore = findViewById(R.id.txtHomeScore);
        txtAwayScore = findViewById(R.id.txtAwayScore);

        mChronometer = findViewById(R.id.chronometer);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player p = dataSnapshot.getValue(Player.class);
                // If the list does not contain the player, add him.
                if (firebaseUser.getUid().matches(p.getUserID())) {
                    if (p.getPosition().matches("Goalkeeper")) {
                        if (p.isOnTeam() && mPlayers[1] == null) {
                            mPlayers[1] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Right Corner-Back")) {
                        if (p.isOnTeam() && mPlayers[2] == null) {
                            mPlayers[2] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Full-Back")) {
                        if (p.isOnTeam() && mPlayers[3] == null) {
                            mPlayers[3] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Left Corner-Back")) {
                        if (p.isOnTeam() && mPlayers[4] == null) {
                            mPlayers[4] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Right Half-Back")) {
                        if (p.isOnTeam() && mPlayers[5] == null) {
                            mPlayers[5] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Centre-Back")) {
                        if (p.isOnTeam() && mPlayers[6] == null) {
                            mPlayers[6] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Left Half-Back")) {
                        if (p.isOnTeam() && mPlayers[7] == null) {
                            mPlayers[7] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Right Midfielder")) {
                        if (p.isOnTeam() && mPlayers[8] == null) {
                            mPlayers[8] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Left Midfielder")) {
                        if (p.isOnTeam() && mPlayers[9] == null) {
                            mPlayers[9] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Right Half-Forward")) {
                        if (p.isOnTeam() && mPlayers[10] == null) {
                            mPlayers[10] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Centre Forward")) {
                        if (p.isOnTeam() && mPlayers[11] == null) {
                            mPlayers[11] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Left Half-Forward")) {
                        if (p.isOnTeam() && mPlayers[12] == null) {
                            mPlayers[12] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Right Corner-Forward")) {
                        if (p.isOnTeam() && mPlayers[13] == null) {
                            mPlayers[13] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Full Forward")) {
                        if (p.isOnTeam() && mPlayers[14] == null) {
                            mPlayers[14] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    } else if (p.getPosition().matches("Left Corner-Forward")) {
                        if (p.isOnTeam() && mPlayers[15] == null) {
                            mPlayers[15] = p;
                            match.addPlayer(p.getPlayerID());
                        } else {
                            subPlayer.add(p);
                        }
                    }
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


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPause != 0) {
                    mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                } else {
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                }
                mChronometer.start();
                btnStart.setEnabled(false);
                btnPause.setEnabled(true);
            }

        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastPause = SystemClock.elapsedRealtime();
                mChronometer.stop();
                btnStart.setEnabled(true);
            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChronometer.stop();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
                btnStart.setEnabled(true);
                btnPause.setEnabled(false);
                //Resetting score Vareibles
                txtHomeScore.setText("Home \n 0");
                txtAwayScore.setText("Away \n 0");
                selectedPlayer = -1;
                subPoolIndex = 0;
                s = null;
                substitutionIndex = -1;
                match = new Match();
                Bundle b = getIntent().getExtras();
                if (b != null) {
                    venue = b.getString("venue");
                    opponent = b.getString("opponent");
                    competition = b.getString("competition");
                    date = b.getString("date");
                    match.setVenue(venue);
                    match.setOpponent(opponent);
                    match.setCompetition(competition);
                    match.setDate(date);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match = null;
                startActivity(new Intent(StartGameContinue.this, Homepage.class));
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mRefMatch.push().getKey();
                if (match.getUserID() == null) {
                    match.setUserID(firebaseAuth.getUid());
                }
                mRefMatch.child(key).setValue(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Match Saved!",
                                Toast.LENGTH_SHORT).show();

                        //Reset Match
                        mChronometer.stop();
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                        lastPause = 0;
                        btnStart.setEnabled(true);
                        btnPause.setEnabled(false);
                        //Resetting score Vareibles
                        txtHomeScore.setText("Home \n 0");
                        txtAwayScore.setText("Away \n 0");
                        match = null;
                        startActivity(new Intent(StartGameContinue.this, Homepage.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
//                startActivity(new Intent(StartGameContinue.this, Homepage.class));
            }
        });

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 1;
                    inputStats(v);
                }
            }
        });

        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 2;
                    inputStats(v);
                }
            }
        });

        player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 3;
                    inputStats(v);
                }
            }
        });

        player4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPlayer = 4;
                inputStats(v);
            }
        });

        player5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 5;
                    inputStats(v);
                }
            }
        });

        player6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 6;
                    inputStats(v);
                }
            }
        });

        player7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 7;
                    inputStats(v);
                }
            }
        });

        player8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 8;
                    inputStats(v);
                }
            }
        });

        player9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 9;
                    inputStats(v);
                }
            }
        });

        player10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 10;
                    inputStats(v);
                }
            }
        });

        player11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 11;
                    inputStats(v);
                }
            }
        });

        player12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 12;
                    inputStats(v);
                }
            }
        });

        player13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPlayer = 13;
                inputStats(v);
            }
        });

        player14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 14;
                    inputStats(v);
                }
            }
        });

        player15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Start the match first",
                            Toast.LENGTH_SHORT).show();
                } else {
                    selectedPlayer = 15;
                    inputStats(v);
                }
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
                boolean flag = check();
                if (flag) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.homeTeamPoint(mPlayers[selectedPlayer].getPlayerID());
                    txtHomeScore.setText("HOME \n " + match.getHomeTeamScore());
                    Toast.makeText(this, "Point!", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.Goal:
                boolean flag1 = check();
                if (flag1) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.homeTeamGoal(mPlayers[selectedPlayer].getPlayerID());
                    txtHomeScore.setText("HOME \n " + match.getHomeTeamScore());
                    Toast.makeText(this, "Goal!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.pointConceded:
                boolean flag2 = check();
                if (flag2) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.awayTeamPoint();
                    txtAwayScore.setText("Away \n " + match.getAwayTeamScore());
                    Toast.makeText(this, "Point Conceded!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.goalConceded:
                boolean flag3 = check();
                if (flag3) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.awayTeamGoal();
                    txtAwayScore.setText("Away \n " + match.getAwayTeamScore());
                    Toast.makeText(this, "Goal Conceded!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.yellowCard:
                boolean flag4 = check();
                if (flag4) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {

                    String[] temp = match.getYcPlayers().split("\n");
                    boolean hasYellowCard = false;
                    for (String x : temp) {
                        if (x.matches(mPlayers[selectedPlayer].getPlayerID())) {
                            hasYellowCard = true;
                        }
                    }
                    if (hasYellowCard) {
                        match.yellowCard(mPlayers[selectedPlayer].getPlayerID());
                        Toast.makeText(this, "Second Yellow Card! Substitute a player if available", Toast.LENGTH_SHORT).show();
                        mDisPlayers.add(mPlayers[selectedPlayer]); //Player disabled.
                    } else {
                        match.yellowCard(mPlayers[selectedPlayer].getPlayerID());
                        Toast.makeText(this, "Yellow Card!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case R.id.redCard:
                boolean flag5 = check();
                if (flag5) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.redCard(mPlayers[selectedPlayer].getPlayerID());
                    Toast.makeText(this, "Red Card! Substitute a player if available", Toast.LENGTH_SHORT).show();
                    mDisPlayers.add(mPlayers[selectedPlayer]); //Player disabled.
                }
                return true;
            case R.id.wide:
                boolean flag6 = check();
                if (flag6) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.wide(mPlayers[selectedPlayer].getPlayerID());
                    Toast.makeText(this, "Wide!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.freeWon:
                boolean flag7 = check();
                if (flag7) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.freeWon(mPlayers[selectedPlayer].getPlayerID());
                    Toast.makeText(this, "Free won!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.matchFoul:
                boolean flag8 = check();
                if (flag8) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.foul(mPlayers[selectedPlayer].getPlayerID());
                    Toast.makeText(this, "Foul!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.mark:
                boolean flag9 = check();
                if (flag9) {
                    Toast.makeText(getApplicationContext(), "No additional information for" +
                            " this player can be added! Substitute if possible", Toast.LENGTH_SHORT).show();
                } else {
                    match.mark(mPlayers[selectedPlayer].getPlayerID());
                    Toast.makeText(this, "Mark!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.substitute:
                if (subPool.size() != 0) {
                    subPoolIndex++;
                    if (subPoolIndex < subPool.size()) {
                        Toast.makeText(this, subPool.get(subPoolIndex).getName() +
                                " selected. Confirm this substitution from the dropdown to progress.", Toast.LENGTH_SHORT).show();
                        s = subPool.get(subPoolIndex);
                    } else {
                        Toast.makeText(this, "No substitutions available!", Toast.LENGTH_SHORT).show();
                        subPoolIndex = 0;
                        subPool = new ArrayList<>();
                        substitutionIndex = -1;
                        s = null;
                    }
                } else {
                    for (int i = 0; i < subPlayer.size(); i++) {
                        if (selectedPlayer == 1 && subPlayer.get(i).getPosition().matches("Goalkeeper")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 1;
                        } else if (selectedPlayer == 2 && subPlayer.get(i).getPosition().matches("Right Corner-Back")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 2;
                        } else if (selectedPlayer == 3 && subPlayer.get(i).getPosition().matches("Full-Back")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 3;
                        } else if (selectedPlayer == 4 && subPlayer.get(i).getPosition().matches("Left Corner-Back")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 4;
                        } else if (selectedPlayer == 5 && subPlayer.get(i).getPosition().matches("Right Half-Back")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 5;
                        } else if (selectedPlayer == 6 && subPlayer.get(i).getPosition().matches("Centre-Back")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 6;
                        } else if (selectedPlayer == 7 && subPlayer.get(i).getPosition().matches("Left Half-Back")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 7;
                        } else if (selectedPlayer == 8 && subPlayer.get(i).getPosition().matches("Right Midfielder")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 8;
                        } else if (selectedPlayer == 9 && subPlayer.get(i).getPosition().matches("Left Midfielder")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 9;
                        } else if (selectedPlayer == 10 && subPlayer.get(i).getPosition().matches("Right Half-Forward")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 10;
                        } else if (selectedPlayer == 11 && subPlayer.get(i).getPosition().matches("Centre Forward")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 11;
                        } else if (selectedPlayer == 12 && subPlayer.get(i).getPosition().matches("Left Half-Forward")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 12;
                        } else if (selectedPlayer == 13 && subPlayer.get(i).getPosition().matches("Right Corner-Forward")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 13;
                        } else if (selectedPlayer == 14 && subPlayer.get(i).getPosition().matches("Full Forward")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 14;
                        } else if (selectedPlayer == 15 && subPlayer.get(i).getPosition().matches("Left Corner-Forward")) {
                            subPool.add(subPlayer.get(i));
                            substitutionIndex = 15;
                        }
                    }
                    if (subPool.isEmpty()) {
                        Toast.makeText(this, "No substitutions available!", Toast.LENGTH_SHORT).show();
                        subPoolIndex = 0;
                        subPool = new ArrayList<>();
                        substitutionIndex = -1;
                        s = null;
                    } else {
                        Toast.makeText(this, subPool.get(subPoolIndex).getName() + " selected. Confirm this substitution from the dropdown to progress.", Toast.LENGTH_SHORT).show();
                        s = subPool.get(subPoolIndex);
                    }
                }
                return true;
            case R.id.confirm:
                if (s == null) {
                    Toast.makeText(this, "Kindly substitute a player first", Toast.LENGTH_SHORT).show();
                } else {
                    Player p = mPlayers[substitutionIndex];
                    mPlayers[substitutionIndex] = s;
                    mDisPlayers.remove(p);
                    match.addPlayer(s.getPlayerID());
                    subPool = new ArrayList<>(); //Empty SubPool
                    substitutionIndex = -1;
                    subPoolIndex = 0;

                    Toast.makeText(this, "Substitution Successful", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return false;
        }
    }

    public boolean check() {
        boolean flag = false;
        for (int i = 0; i < mDisPlayers.size(); i++) {
            if (selectedPlayer == 1 && mDisPlayers.get(i).getPosition().matches("Goalkeeper"))
                flag = true;
            else if (selectedPlayer == 2 && mDisPlayers.get(i).getPosition().matches("Right Corner-Back"))
                flag = true;
            else if (selectedPlayer == 3 && mDisPlayers.get(i).getPosition().matches("Full-Back"))
                flag = true;
            else if (selectedPlayer == 4 && mDisPlayers.get(i).getPosition().matches("Left Corner-Back"))
                flag = true;
            else if (selectedPlayer == 5 && mDisPlayers.get(i).getPosition().matches("Right Half-Back"))
                flag = true;
            else if (selectedPlayer == 6 && mDisPlayers.get(i).getPosition().matches("Centre-Back"))
                flag = true;
            else if (selectedPlayer == 7 && mDisPlayers.get(i).getPosition().matches("Left Half-Back"))
                flag = true;
            else if (selectedPlayer == 8 && mDisPlayers.get(i).getPosition().matches("Right Midfielder"))
                flag = true;
            else if (selectedPlayer == 9 && mDisPlayers.get(i).getPosition().matches("Left Midfielder"))
                flag = true;
            else if (selectedPlayer == 10 && mDisPlayers.get(i).getPosition().matches("Right Half-Forward"))
                flag = true;
            else if (selectedPlayer == 11 && mDisPlayers.get(i).getPosition().matches("Centre Forward"))
                flag = true;
            else if (selectedPlayer == 12 && mDisPlayers.get(i).getPosition().matches("Left Half-Forward"))
                flag = true;
            else if (selectedPlayer == 13 && mDisPlayers.get(i).getPosition().matches("Right Corner-Forward"))
                flag = true;
            else if (selectedPlayer == 14 && mDisPlayers.get(i).getPosition().matches("Full Forward"))
                flag = true;
            else if (selectedPlayer == 15 && mDisPlayers.get(i).getPosition().matches("Left Corner-Forward"))
                flag = true;
        }
        return flag;
    }
}



