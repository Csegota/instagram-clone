package com.android.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        etSignupPassword.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                //If user presses enter button on virtual keyboard instead of signup button execute signup.
                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                    event.getAction() == KeyEvent.ACTION_DOWN)  {
                    onClick(btnSignUp);
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

            case R.id.btn_signup:

                //Field validation.
                if (etSignupEmail.getText().toString().equals("") ||
                        etSignupUsername.getText().toString().equals("") ||
                        etSignupPassword.getText().toString().equals("")) {

                    FancyToast.makeText(SignUpActivity.this,
                            "Email, Username, and Password required.",
                            Toast.LENGTH_SHORT, FancyToast.INFO,
                    true).show();
                } else {

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
                                gotoSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUpActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;

            case R.id.btn_signup_goto_login:

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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

        Intent intent = new Intent(SignUpActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
