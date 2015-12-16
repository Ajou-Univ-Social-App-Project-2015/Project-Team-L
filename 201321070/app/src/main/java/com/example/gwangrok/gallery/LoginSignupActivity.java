package com.example.gwangrok.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by taerin on 2015. 5. 24..
 */


public class LoginSignupActivity extends Activity{


    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                ParseUser.logInInBackground(usernametxt,passwordtxt,new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exist and authenticated, send user to Welcome.class
                            Intent intent = new Intent(LoginSignupActivity.this,
                                    Welcome.class);
                            startActivity(intent);
                            finish();

                    }else {
                            Toast.makeText(getApplicationContext(),"존재하지 않는 사용자입니다. 가입해주세요",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                if (usernametxt.equals("")&& passwordtxt.equals("")){
                    Toast.makeText(getApplicationContext(),"가입 양식을 작성해주세요",
                            Toast.LENGTH_SHORT).show();
                }

                else{

                    ParseUser user= new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    //ArrayList<String> mypics = new ArrayList<String>();
                    //mypics.add("");
                    //user.put("myPics",mypics);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                Toast.makeText(getApplicationContext(),"가입이 완료되었습니다",
                                        Toast.LENGTH_SHORT).show();

                            }

                            else{Toast.makeText(getApplicationContext(),"가입 오류",
                                    Toast.LENGTH_SHORT).show();}
                        }
                    });

                }

            }
        });






    }
}








/*
public class LoginSignupActivity extends Activity {
        // Declare Variables
        Button loginbutton;
        Button signup;
        String usernametxt;
        String passwordtxt;
        EditText password;
        EditText username;


        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Get the view from main.xml
            setContentView(R.layout.main);
            // Locate EditTexts in main.xml
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);

            // Locate Buttons in main.xml
            loginbutton = (Button) findViewById(R.id.login);
            signup = (Button) findViewById(R.id.signup);

            // Login Button Click Listener
            loginbutton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    // Retrieve the text entered from the EditText
                    usernametxt = username.getText().toString();
                    passwordtxt = password.getText().toString();

                    // Send data to Parse.com for verification
                    ParseUser.logInInBackground(usernametxt, passwordtxt,
                            new LogInCallback() {
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null) {
                                        // If user exist and authenticated, send user to Welcome.class
                                        Intent intent = new Intent(
                                                LoginSignupActivity.this,
                                                Welcome.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),
                                                "Successfully Logged in",
                                                Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "No such user exist, please signup",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }


                                @Override
                                public void done(ParseUser parseUser, com.parse.ParseException e) {
                                    if (e == null) {
                                        // Hooray! Let them use the app now.
                                    } else {
                                        // Sign up didn't succeed. Look at the ParseException
                                        // to figure out what went wrong
                                    }
                                }
                            });
                }
            });
            // Sign up Button Click Listener
            signup.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    // Retrieve the text entered from the EditText
                    usernametxt = username.getText().toString();
                    passwordtxt = password.getText().toString();

                    // Force user to fill up the form
                    if (usernametxt.equals("") && passwordtxt.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Please complete the sign up form",
                                Toast.LENGTH_LONG).show();

                    } else {
                        // Save new user data into Parse.com Data Storage
                        ParseUser user = new ParseUser();
                        user.setUsername(usernametxt);
                        user.setPassword(passwordtxt);
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Show a simple Toast message upon successful registration
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Signed up, please log in.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Sign up Error", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }


                            @Override
                            public void done(com.parse.ParseException e) {
                                if (e == null) {
                                    // Hooray! Let them use the app now.
                                } else {
                                    // Sign up didn't succeed. Look at the ParseException
                                    // to figure out what went wrong
                                }
                            }
                        });
                    }

                }
            });

        }
    }

*/