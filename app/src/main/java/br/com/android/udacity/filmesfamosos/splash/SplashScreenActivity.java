package br.com.android.udacity.filmesfamosos.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.com.android.udacity.filmesfamosos.allmovies.AllMoviesActivity;
import br.com.android.udacity.filmesfamosos.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_creen);

        setUpScreenDelay();
    }

    private void setUpScreenDelay() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, AllMoviesActivity.class));
            }
        }, 2500);
    }
}
