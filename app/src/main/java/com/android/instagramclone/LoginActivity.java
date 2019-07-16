package com.android.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etLoginUsername, etLoginPassword;
    private Button btnLogin, btnGotoSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = findViewById(R.id.et_login_un);
        etLoginPassword = findViewById(R.id.et_login_pw);

        btnLogin = findViewById(R.id.btn_login);
        btnGotoSignup = findViewById(R.id.btn_login_goto_signup);

        btnLogin.setOnClickListener(this);
        btnGotoSignup.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:

                ParseUser.logInInBackground(etLoginUsername.getText().toString(), etLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            FancyToast.makeText(LoginActivity.this, "User Login Success.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
                break;

            case R.id.btn_login_goto_signup:

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
