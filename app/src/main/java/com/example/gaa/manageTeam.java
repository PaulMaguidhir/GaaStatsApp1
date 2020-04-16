package com.example.gaa;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class  manageTeam extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team);
        mDatabase = FirebaseDatabase.getInstance().getReference("Player Details");
    }


    public void saveMatchDetails(View view){


        String playerName =((EditText) findViewById(R.id.editText_Venue)).getText().toString();
        String playerPostion =((EditText) findViewById(R.id.editText_Opponent)).getText().toString();
        String playerAge =((EditText) findViewById(R.id.editText_Competition)).getText().toString();

        mDatabase.child("Player Name").setValue(playerName);
        mDatabase.child("Player Postion").setValue(playerPostion);
        mDatabase.child("Player Age").setValue(playerAge);

        startActivity(new Intent(manageTeam.this, homepage.class));

    }


}

