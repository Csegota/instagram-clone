package com.android.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity {

    private Button btnSave, btnGetData;
    private EditText etName, etScore, etRounds, etDuration, etGetName; //, etEasy;
    private TextView tvReturnData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btn_save);
        //btnSave.setOnClickListener(SignUp.this);
        btnGetData = findViewById(R.id.btn_getData);

        etName = findViewById(R.id.et_name);
        etScore = findViewById(R.id.et_score);
        etRounds = findViewById(R.id.et_rounds);
        etDuration = findViewById(R.id.et_duration);
        //etEasy = findViewById(R.id.et_easy);
        etGetName = findViewById(R.id.et_getName);

        tvReturnData = findViewById(R.id.tv_returnData);
    }

    //TODO Potentially might want to revert back to implementing View.OnClickListener interface. Could make code more readable for numerous onClick methods.
    public void onClickBtnSaveData(View v) {

        try { //TODO - Need to sanitize input in a better way.
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
                        FancyToast.makeText(SignUp.this, gameScore.get("name") + "'s game saved successfully.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }

    public void onClickBtnGetData (View v) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.whereEqualTo("name", etGetName.getText().toString());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    String output = "";
                    for (ParseObject game : scoreList ) {
                        output = output + game.get("name") + "'s Records || Score: " + game.get("score") + "\n";
                    }
                    tvReturnData.setText(output);
                } else {
                    FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            }
        });
    }

    public void onClickBtnGotoSignUpLogin (View v) {
        Intent intent = new Intent(SignUp.this, SignUpLoginActivity.class);
        startActivity(intent);
    }
}
