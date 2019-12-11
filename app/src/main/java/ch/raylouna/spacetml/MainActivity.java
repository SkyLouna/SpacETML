package ch.raylouna.spacetml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import ch.raylouna.spacetml.Helper.HSDatabaseHelper;
import ch.raylouna.spacetml.Road.RoadDifficulty;
import ch.raylouna.spacetml.Road.RoadGenerator;
import ch.raylouna.spacetml.Track.TrackDifficulty;
import ch.raylouna.spacetml.Track.TrackGenerator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HSDatabaseHelper test = new HSDatabaseHelper(this);

        System.out.println("Amount of entries: " + test.getScores(10).getCount());

        //Set the content view to the main activity
        setContentView(R.layout.activity_main);

        //Set window fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Create play intent and add event listener with activity redirection
        final Intent playIntent = new Intent(this, GameActivity.class);
        Button playButton = this.findViewById(R.id.btnPlayGame);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(playIntent);
            }
        });

        //Create high scores intent and add event listener with activity redirection
        final Intent highScoresIntent = new Intent(this, HighScoresActivity.class);
        Button highScoresButton = this.findViewById(R.id.btnHighscore);
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(highScoresIntent);
            }
        });

        //Create credits intent and add event listener with activity redirection
        final Intent creditsIntent = new Intent(this, CreditsActivity.class);
        Button creditsButton = this.findViewById(R.id.btnCredits);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(creditsIntent);
            }
        });


    }

    public void sendToGameOver(){
        //Init the game over view intent
        final Intent gameOverIntent = new Intent(this, GameOverActivity.class);
        //Add the score to the flag "score"
        gameOverIntent.putExtra("score", 310);
        //Start the game over activity
        startActivity(gameOverIntent);
    }
    
}
