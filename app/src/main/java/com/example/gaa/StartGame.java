package com.example.gaa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    Button btnReturn, btnStartGame;
    EditText txtVenue, txtOpponent, txtDate;
    Spinner spinnerCompetition;
    private String competitionType = null;
    private Player[] mPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startgame);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Player");
        btnReturn = findViewById(R.id.btnReturnHomePage3);
        btnStartGame = findViewById(R.id.btnStartGame);
        txtVenue = findViewById(R.id.txtVenue);
        txtOpponent = findViewById(R.id.txtOpponent);
        spinnerCompetition = findViewById(R.id.spinnerCompetition);
        txtDate = findViewById(R.id.txtDate);

        txtDate.setShowSoftInputOnFocus(false);

        mPlayers = new Player[16];
        for (int i = 0; i < mPlayers.length; i++) {
            mPlayers[i] = null;
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.competition_array, R.layout.spinner_layout_game);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCompetition.setAdapter(adapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player p = dataSnapshot.getValue(Player.class);
                // If the list does not contain the player, add him.
                if (firebaseUser.getUid().matches(p.getUserID())) {
                    if (p.getPosition().matches("Goalkeeper") && mPlayers[1] == null && p.isOnTeam()) {
                        mPlayers[1] = p;
                    } else if (p.getPosition().matches("Right Corner-Back") && mPlayers[2] == null && p.isOnTeam()) {
                        mPlayers[2] = p;
                    } else if (p.getPosition().matches("Full-Back") && mPlayers[3] == null && p.isOnTeam()) {
                        mPlayers[3] = p;
                    } else if (p.getPosition().matches("Left Corner-Back") && mPlayers[4] == null && p.isOnTeam()) {
                        mPlayers[4] = p;
                    } else if (p.getPosition().matches("Right Half-Back") && mPlayers[5] == null && p.isOnTeam()) {
                        mPlayers[5] = p;
                    } else if (p.getPosition().matches("Centre-Back") && mPlayers[6] == null && p.isOnTeam()) {
                        mPlayers[6] = p;
                    } else if (p.getPosition().matches("Left Half-Back") && mPlayers[7] == null && p.isOnTeam()) {
                        mPlayers[7] = p;
                    } else if (p.getPosition().matches("Right Midfielder") && mPlayers[8] == null && p.isOnTeam()) {
                        mPlayers[8] = p;
                    } else if (p.getPosition().matches("Left Midfielder") && mPlayers[9] == null && p.isOnTeam()) {
                        mPlayers[9] = p;
                    } else if (p.getPosition().matches("Right Half-Forward") && mPlayers[10] == null && p.isOnTeam()) {
                        mPlayers[10] = p;
                    } else if (p.getPosition().matches("Centre Forward") && mPlayers[11] == null && p.isOnTeam()) {
                        mPlayers[11] = p;
                    } else if (p.getPosition().matches("Left Half-Forward") && mPlayers[12] == null && p.isOnTeam()) {
                        mPlayers[12] = p;
                    } else if (p.getPosition().matches("Right Corner-Forward") && mPlayers[13] == null && p.isOnTeam()) {
                        mPlayers[13] = p;
                    } else if (p.getPosition().matches("Full Forward") && mPlayers[14] == null && p.isOnTeam()) {
                        mPlayers[14] = p;
                    } else if (p.getPosition().matches("Left Corner-Forward") && mPlayers[15] == null && p.isOnTeam()) {
                        mPlayers[15] = p;
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


        spinnerCompetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                competitionType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtVenue.getText().toString().matches("")
                        || txtOpponent.getText().toString().matches("")
                        || competitionType == null
                        || txtDate.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    boolean flag = false;
                    Loop:
                    for (int i = 1; i < mPlayers.length; i++) {
                        if (mPlayers[i] == null) {
                            flag = true;
                            break Loop;
                        }
                    }
                    if (flag) {
                        Toast.makeText(getApplicationContext(), "Please assign a player to each position first",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(StartGame.this, StartGameContinue.class);
                        Bundle b = new Bundle();
                        b.putString("venue", txtVenue.getText().toString());
                        b.putString("opponent", txtOpponent.getText().toString());
                        b.putString("competition", competitionType);
                        b.putString("date", txtDate.getText().toString());
                        intent.putExtras(b); //Put your id to your next Intent
                        txtOpponent.setText("");
                        txtVenue.setText("");
                        txtDate.setText(null);
                        startActivity(intent);
                    }
                }
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartGame.this, Homepage.class));
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = month + "/" + dayOfMonth + "/" + year;
        txtDate.setText(date);
    }
}

