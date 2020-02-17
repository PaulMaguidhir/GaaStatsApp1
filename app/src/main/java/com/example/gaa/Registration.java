package com.example.gaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class Registration extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }
    public void goLogin(View view){
        startActivity(new Intent(Registration.this, MainActivity.class));
    }

    public void registerUser(View view) {
        //getting emails and passwords from edit text
        String email = ((EditText) findViewById(R.id.editText_Reg__Email)).getText().toString();
        String password = ((EditText) findViewById(R.id.editText_Reg_Password)).getText().toString();

        //checking if email and password are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

    //if email andpasssword are not empty progressbar displays
        progressBar.setVisibility(View.VISIBLE);
        //creating user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Registration.this, "Succesffully Registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Registration.this, MainActivity.class));
                    finish();
                } else {
                    FirebaseAuthException e = (FirebaseAuthException) task.getException();
                    Toast.makeText(Registration.this, "Failed Registtration:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        }
    }
