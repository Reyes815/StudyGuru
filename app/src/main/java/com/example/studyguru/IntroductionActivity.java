package com.example.studyguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class IntroductionActivity extends AppCompatActivity {

    private TextView introductionTextView;
    private View fullScreenView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        introductionTextView = findViewById(R.id.introductionTextView);
        mediaPlayer = MediaPlayer.create(this, R.raw.introduction);

        playAudio();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollTextSlowly();
                enableClickToHomePage();
            }
        }, 3000);
    }

    private void scrollTextSlowly() {
        final Handler handler = new Handler();
        final int delayMillis = 50;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Scroll the text by adjusting the top padding
                int paddingTop = introductionTextView.getPaddingTop();
                paddingTop -= 1; // Adjust this value for the scrolling speed
                introductionTextView.setPadding(0, paddingTop, 0, 0);

                // Check if the scrolling is complete
                if (paddingTop + introductionTextView.getHeight() > 0) {
                    handler.postDelayed(this, delayMillis);
                }
            }
        }, delayMillis);
    }

    private void enableClickToHomePage() {
        Toast.makeText(this, "Click anywhere to skip...", Toast.LENGTH_SHORT).show();
        fullScreenView = findViewById(R.id.fullScreenView);
        fullScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch HomePage when the user clicks anywhere on the screen
                onDestroy();
                launchHomePage();
            }
        });
    }

    private void launchHomePage() {
        Intent homePage = new Intent(this, HomePage.class);
        this.startActivity(homePage);
        Toast.makeText(this, "Skipping introduction...", Toast.LENGTH_SHORT).show();
    };


    private void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}