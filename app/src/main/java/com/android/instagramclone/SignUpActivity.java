package com.android.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etSignupEmail, etSignupUsername, etSignupPassword;
    private Button btnSignUp, btnGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        etSignupEmail = findViewById(R.id.et_signup_email);
        etSignupUsername = findViewById(R.id.et_signup_un);
        etSignupPassword = findViewById(R.id.et_signup_pw);

        btnSignUp = findViewById(R.id.btn_signup);
        btnGotoLogin = findViewById(R.id.btn_signup_goto_login);

        btnSignUp.setOnClickListener(this);
        btnGotoLogin.setOnClickListener(this);

        //Log out any current user.
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_signup:

                final ParseUser appUser = new ParseUser();
                appUser.setEmail(etSignupEmail.getText().toString());
                appUser.setUsername(etSignupUsername.getText().toString());
                appUser.setPassword(etSignupPassword.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing you up " + etSignupUsername.getText().toString() + "!");
                progressDialog.show();
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null ) {
                            FancyToast.makeText(SignUpActivity.this, "User Sign Up Success.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            FancyToast.makeText(SignUpActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
                break;

            case R.id.btn_signup_goto_login:

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
