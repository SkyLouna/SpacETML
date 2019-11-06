package ch.raylouna.spacetml;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}
