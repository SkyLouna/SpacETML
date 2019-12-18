/**
 * ETML
 * Author: Lucas Charbonnier & Trana Valentin
 * Description:
 *      Contains HighScore activity methods
 */

package ch.raylouna.spacetml;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.raylouna.spacetml.Helper.HSDatabaseHelper;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        //Set full screen flag
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Init database helper in this context
        HSDatabaseHelper hsDatabaseHelper = new HSDatabaseHelper(this);

        //Get the top 1000 scores
        Cursor cursor = hsDatabaseHelper.getScores(1000);

        // if Cursor is contains results
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    // Get name and score from Cursor
                    String scoreName = cursor.getString(cursor.getColumnIndex("scoName"));
                    int scoreAmount = cursor.getInt(cursor.getColumnIndex("scoAmount"));

                    addHighScoreDisplayTextView(scoreName, scoreAmount);

                    // move to next row
                } while (cursor.moveToNext());
            }
        }
    }

    /**
     * Add a label with the name and the score to the highscore view
     * @param name
     * @param score
     */
    public void addHighScoreDisplayTextView(String name, int score){

        //Create new textview
        TextView tv = new TextView(this);

        //Set text "Score - NAME"
        tv.setText(score + " - " + name);

        //Set text view padding
        tv.setPadding(50,20,50,20);

        //set text view color
        tv.setTextColor(Color.parseColor("#F57F17"));

        //Set text view font
        tv.setTypeface(Typeface.MONOSPACE);

        //Set text size
        tv.setTextSize(30F);

        //Get the highscores layout
        LinearLayout linearLayout = findViewById(R.id.llHSScrollLayout);

        //Add the textview to the layout
        linearLayout.addView(tv);

    }

}
