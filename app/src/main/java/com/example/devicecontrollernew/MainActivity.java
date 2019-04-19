package com.example.devicecontrollernew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

//
//        FirebaseUser currentUser = auth.getCurrentUser();
//
//        if(currentUser!=null){
//            Intent i=new Intent(this,MainPage.class);
//            startActivity(i);
//            finish();
//        }
    }


    public void launchSignupActivity(View view) {
     //   Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
