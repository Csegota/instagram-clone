package com.android.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private EditText etName, etScore, etRounds, etDuration; //, etEasy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(SignUp.this);

        etName = findViewById(R.id.et_name);
        etScore = findViewById(R.id.et_score);
        etRounds = findViewById(R.id.et_rounds);
        etDuration = findViewById(R.id.et_duration);
        //etEasy = findViewById(R.id.et_easy);
    }

    @Override
    public void onClick(View v) {
        final ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("name", etName.getText().toString());
        gameScore.put("score", Integer.parseInt(etScore.getText().toString()));
        gameScore.put("rounds", Integer.parseInt(etRounds.getText().toString()));
        gameScore.put("duration", Integer.parseInt(etDuration.getText().toString()));
//        gameScore.put("easyMode", etEasy.getText().toString()); //TODO - Add in boolean value.
        gameScore.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Toast.makeText(SignUp.this, gameScore.get("name") + "'s game saved successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
