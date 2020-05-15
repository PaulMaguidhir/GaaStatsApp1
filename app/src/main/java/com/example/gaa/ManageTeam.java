package com.example.gaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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

public class ManageTeam extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mPlayerCheck;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    Button btnReturnHomepage, btnSave, btnViewTeam;
    EditText txtName, txtAge;
    Spinner spinner;
    Switch switchTeamPos;
    String playerPosition = null;
    boolean teamStatus = true;
    private ArrayList<Player> mPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team);
        mDatabase = FirebaseDatabase.getInstance().getReference("Player");
        mPlayerCheck = FirebaseDatabase.getInstance().getReference().child("Player");

        btnReturnHomepage = findViewById(R.id.btnReturnHomePage);
        btnSave = findViewById(R.id.btnStartGame);
        btnViewTeam = findViewById(R.id.btnViewTeam);
        txtName = findViewById(R.id.txtVenue);
        txtAge = findViewById(R.id.txtAge);
        spinner = findViewById(R.id.spinner);
        switchTeamPos = findViewById(R.id.onTeamSwitch);
        mPlayers = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.player_position_array, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                playerPosition = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switchTeamPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    teamStatus = false;
                    switchTeamPos.setText("Off Team ");
                } else {
                    teamStatus = true;
                    switchTeamPos.setText("On Team ");
                }
            }
        });

        mPlayerCheck.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player p = dataSnapshot.getValue(Player.class);
                // If the list does not contain the player, add him.
                if (!mPlayers.contains(p) && firebaseUser.getUid().matches(p.getUserID())) {
                    mPlayers.add(p);
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

        btnViewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mPlayers.size() >= 15) {
                startActivity(new Intent(ManageTeam.this, ManageTeamContinue.class));
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please add a minimum of 15 players!",
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btnReturnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageTeam.this, Homepage.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = txtName.getText().toString();
//                String playerPostion = txtPosition.getText().toString();
//input is taken and the player data is stored
                String playerAge = txtAge.getText().toString();

                if (playerPosition == null || playerName.matches("") || playerAge.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (mPlayers.size() > 25) {
                        Toast.makeText(getApplicationContext(), "You have reached the maximum number of players!",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        boolean flag = false;
                        if (teamStatus) {
                            for (int i = 0; i < mPlayers.size(); i++) {
                                if (mPlayers.get(i).getPosition().matches(playerPosition)) {
                                    flag = true;
                                }
                            }
                        }
                        if (flag) {
                            teamStatus = false;
                        }
                        String key = mDatabase.push().getKey();
                        Player player = new Player(key, firebaseUser.getUid(), playerName,
                                playerPosition, Integer.parseInt(playerAge), teamStatus);
                        mDatabase.child(key).setValue(player).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                txtName.setText("");
                                txtName.setHint("Enter Player Name Here");
                                txtAge.setText("");
                                txtAge.setHint("Enter Player Age Here");
                                Toast.makeText(getApplicationContext(), "Details Successfully Saved!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

