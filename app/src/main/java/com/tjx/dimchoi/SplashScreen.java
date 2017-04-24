package com.tjx.dimchoi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tjx.dimchoi.widget.AnimatedSvgView;

public class SplashScreen extends AppCompatActivity {
    AnimatedSvgView svgView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        context = this;
        svgView = (AnimatedSvgView) findViewById(R.id.animated_logo);

        svgView.postDelayed(new Runnable() {

            @Override public void run() {
                svgView.start();
                GoToMainScreen();
            }
        }, 500);
    }

    private void GoToMainScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
