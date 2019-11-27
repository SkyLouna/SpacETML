package ch.raylouna.spacetml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {


    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        setScore(241);

        Button saveHighScore = this.findViewById(R.id.btnGOSave);
        saveHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitScore();
            }
        });

        Button skipButton = this.findViewById(R.id.btnGOSkip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                skipHighScores();
            }
        });

    }

    public void setScore(int score){
        TextView scoreView = findViewById(R.id.tvGOScore);
        scoreView.setText(score + " pts");

        this.score = score;
    }

    public void submitScore(){

    }

    public void skipHighScores(){

    }
}
