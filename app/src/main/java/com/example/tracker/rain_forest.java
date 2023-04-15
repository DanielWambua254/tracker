package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class rain_forest extends AppCompatActivity {
    MediaPlayer player;
    Button playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_forest);
        playBtn = findViewById(R.id.play);

        if (player == null) {
            player = MediaPlayer.create(this, R.raw.walk_in_the_rain);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });


        }

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

    }

    private void play() {
        if(player.isPlaying()) {
            playBtn.setBackgroundResource(R.drawable.ic_play);
            player.pause();
        }
        else if (!player.isPlaying()){
            playBtn.setBackgroundResource(R.drawable.ic_pause);
            player.start();
        } else {
            Toast.makeText(this, "Please play a song.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
        player.release();
    }
}