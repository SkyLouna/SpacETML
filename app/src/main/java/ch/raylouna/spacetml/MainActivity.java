package ch.raylouna.spacetml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import ch.raylouna.spacetml.Road.RoadDifficulty;
import ch.raylouna.spacetml.Road.RoadGenerator;
import ch.raylouna.spacetml.Track.TrackDifficulty;
import ch.raylouna.spacetml.Track.TrackGenerator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Intent playIntent = new Intent(this, GameActivity.class);
        Button playButton = this.findViewById(R.id.btnPlayGame);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(playIntent);
            }
        });

        final Intent highScoresIntent = new Intent(this, HighScoresActivity.class);
        Button highScoresButton = this.findViewById(R.id.btnHighscore);
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(highScoresIntent);
            }
        });

        final Intent creditsIntent = new Intent(this, CreditsActivity.class);
        Button creditsButton = this.findViewById(R.id.btnCredits);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(creditsIntent);
            }
        });


    }

    public void drawRoad(int amount){

        for(int i = 0; i < amount; i++){

            System.out.print('*');
        }
        System.out.println(' ');
    }
}
