package com.example.gaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class startgame extends AppCompatActivity {
private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startgame);
        mDatabase = FirebaseDatabase.getInstance().getReference("Pre Match Details");
    }


    public void saveMatchDetails(View view){


        String venue =((EditText) findViewById(R.id.editText_Venue)).getText().toString();
        String opponent =((EditText) findViewById(R.id.editText_Opponent)).getText().toString();
        String gametype =((EditText) findViewById(R.id.editText_GameType)).getText().toString();

        mDatabase.child("venue").setValue(venue);
        mDatabase.child("opponent").setValue(opponent);
        mDatabase.child("Competition").setValue(gametype);

            startActivity(new Intent(startgame.this, startGameContinue.class));

    }


    }

