package com.soob.monthlybudget.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.soob.monthlybudget.R;

/**
 * Activity for the application's splash screen that is shown on start up
 */
public class SplashActivity extends AppCompatActivity
{
    /**
     * Delay to show the screen for
     */
    private static final int SPLASH_DISPLAY_LENGTH = 2000;

    /**
     * On creation of the activity. This activity is just an opening splash screen that shows for
     * two seconds and then moves onto the home screen activity
     *
     * @param savedInstanceState state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() ->
        {
            // Create an Intent that will start the main menu activity and end this splash activity
            Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
            SplashActivity.this.startActivity(homeIntent);
            SplashActivity.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}