package com.example.gaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class Homepage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    private ArrayList<Player> mPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mPlayers = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Player");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player p = dataSnapshot.getValue(Player.class);
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
    }

    public void logout(View view) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Homepage.this, MainActivity.class));
    }

    public void manageTeam(View view) {
        if (mPlayers.size() < 26) {
            startActivity(new Intent(Homepage.this, ManageTeam.class));
        } else {
            startActivity(new Intent(Homepage.this, ManageTeamContinue.class));
        }
    }

    public void matchStats(View view) {
        startActivity(new Intent(Homepage.this, MatchStats.class));
    }

    public void playerStats(View view) {
        startActivity(new Intent(Homepage.this, PlayerStats.class));

    }

    public void startGame(View view) {
        startActivity(new Intent(Homepage.this, StartGame.class));
    }
}
