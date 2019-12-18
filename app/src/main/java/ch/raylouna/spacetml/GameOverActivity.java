/**
 * ETML
 * Author: Lucas Charbonnier & Trana Valentin
 * Description:
 */

package ch.raylouna.spacetml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ch.raylouna.spacetml.Helper.HSDatabaseHelper;

public class GameOverActivity extends AppCompatActivity {


    private int score;          //User score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        setScore(intent.getIntExtra("score", 0));

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

                gotoMainView();
            }
        });

    }

    public void setScore(int score){
        TextView scoreView = findViewById(R.id.tvGOScore);
        scoreView.setText(score + " pts");

        this.score = score;
    }

    public void submitScore(){

        EditText nameEdit = findViewById(R.id.etGOName);

        String name = nameEdit.getText().toString().trim();

        if(name.toCharArray().length == 0){
            Toast.makeText(this,"Compléter le nom svp.", Toast.LENGTH_SHORT).show();
            return;
        }

        HSDatabaseHelper dbHelper = new HSDatabaseHelper(this);
        dbHelper.insertScore(name, score);

        System.out.println("Name: -" + name + "-" + name.toCharArray().length);

        this.gotoMainView();
    }

    /**
     * Sends user to main view
     */
    public void gotoMainView(){
        //Create intent to the main view
        final Intent mainIntent = new Intent(this, MainActivity.class);

        //Send user to main view
        startActivity(mainIntent);
    }
}
