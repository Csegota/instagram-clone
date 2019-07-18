package com.android.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

        etLoginPassword.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                //If user presses enter button on virtual keyboard instead of signup button execute signup.
                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN)  {
                    onClick(btnLogin);
                }
                return false;
            }
        });

        //Log out any current user. Token session issue.
        if (ParseUser.getCurrentUser() != null) {
            gotoSocialMediaActivity();
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
                            gotoSocialMediaActivity();
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

    //Hide user keyboard when user taps on empty area of the layout.
    //Function is tied to onClick() of root ConstraintLayout
    public void rootLayoutTapped(View view) {

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e ) {

            e.printStackTrace();
        }
    }

    private void gotoSocialMediaActivity() {

        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
