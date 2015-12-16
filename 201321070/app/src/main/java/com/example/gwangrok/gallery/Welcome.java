package com.example.gwangrok.gallery;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends Activity {

    // Declare Variable
    Button logout;
    Button enterBn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome);


        ParseUser currentUser = ParseUser.getCurrentUser();

        String struser = currentUser.getUsername().toString();


        TextView txtuser = (TextView) findViewById(R.id.txtuser);


        txtuser.setText("You are logged in as " + struser);


        logout = (Button) findViewById(R.id.logout);
        enterBn = (Button) findViewById(R.id.enterBn);



        enterBn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, CameraActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // 현재 사용자 로그아웃시키기
                Intent intent = new Intent(Welcome.this, LoginSignupActivity.class);
                startActivity(intent);
                ParseUser.logOut();
                 //finish(); 걍 앱꺼짐
            }
        });
    }
}