package com.example.android_advertisement_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                // if you are redirecting from a fragment then use getActivity() as the context

                startActivity(new Intent(MainPage.this, Login.class));
                finish();
            }
        };

        Handler h = new Handler();
        // The Runnable will be executed after the given delay time
        h.postDelayed(run1, 3500);

    }
}