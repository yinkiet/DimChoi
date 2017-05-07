package dimchoi.com.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dimchoi.com.my.widget.AnimatedSvgView;

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
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SplashScreen.this, Pair.create((View)svgView,"logo"));
                startActivity(intent, options.toBundle());
            }
        }, 3000);
    }
}
