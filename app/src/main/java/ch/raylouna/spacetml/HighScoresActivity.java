package ch.raylouna.spacetml;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import ch.raylouna.spacetml.Helper.HSDatabaseHelper;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        HSDatabaseHelper hsDatabaseHelper = new HSDatabaseHelper(this);

        Cursor cursor = hsDatabaseHelper.getScores(10);

        // if Cursor is contains results
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    // Get version from Cursor
                    String scoreName = cursor.getString(cursor.getColumnIndex("scoName"));
                    int scoreAmount = cursor.getInt(cursor.getColumnIndex("scoAmount"));

                    addHighScoreDisplayTextView(scoreName, scoreAmount);

                    // move to next row
                } while (cursor.moveToNext());
            }
        }
    }

    public void addHighScoreDisplayTextView(String name, int score){

        TextView tv = new TextView(this);
        tv.setText(score + " - " + name);

        LinearLayout linearLayout = findViewById(R.id.llHSScrollLayout);
        linearLayout.addView(tv);

    }

}
