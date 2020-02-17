package com.example.gaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homepage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser =firebaseAuth.getCurrentUser();
    }
    public void logout(View view){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(homepage.this, MainActivity.class));
    }
    public void manageTeam(View view){
        startActivity(new Intent(homepage.this, manageTeam.class));
    }
    public void matchStats(View view){
        startActivity(new Intent(homepage.this, Matchstats.class));
    }
    public void playerStats(View view){
        startActivity(new Intent(homepage.this, PlayerStats.class));

    }
    public void startGame(View view){
        startActivity(new Intent(homepage.this, startgame.class));
    }


}
