package com.example.studyguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class IntroductionActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_vid);
        videoView.setVideoURI(videoUri);

        videoView.setOnCompletionListener(mediaPlayer -> {
            Intent home = new Intent(this, HomePage.class);
            this.startActivity(home);
        });

        videoView.start();

        videoView.setOnClickListener(view -> {
            videoView.stopPlayback();
            Intent home = new Intent(this, HomePage.class);
            this.startActivity(home);
        });
    }
}