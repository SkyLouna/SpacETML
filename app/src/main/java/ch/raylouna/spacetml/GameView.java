package ch.raylouna.spacetml;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    private SensorManager sensorManager; // Gestionnaire de capteurs
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] accelerometerData = new float[3];
    private float[] magnetometerData = new float[3];

    private GameThread thread;
    private Rocket rocket;
    private DrawableTrack track;

    private final float TIME_BETWEEN_SCORE_UPDATES = 0.1f;
    private float timeTillNextScoreUpdate = 0.f;

    private final float THRUST_SENSITIVITY = 2.5f;

    private boolean isGameOver;

    private float timeLeft;
    private float lastBonusPos;

    private List<TimeBonus> timeBonuses;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);
        setFocusable(true);

        rocket = new Rocket(BitmapFactory.decodeResource(getResources(),R.drawable.spaceship));

        track = new DrawableTrack();

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

        //scoreTextView.bringToFront();

        isGameOver = false;

        timeBonuses = new ArrayList<>();
        lastBonusPos = 1.f;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunningState(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunningState(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(float elapsedTime) {
        if(isGameOver) {
           return;
        }
        rocket.update(elapsedTime);
        track.updateGeneration(rocket.getDistance());

        timeTillNextScoreUpdate -= elapsedTime;
        if(timeTillNextScoreUpdate <= 0.f) {
            //scoreTextView.setText("Score : " + (int)rocket.getDistance() * 17);
            timeTillNextScoreUpdate = TIME_BETWEEN_SCORE_UPDATES;
        }

        float[] bounds = track.getTrackBoundsAt(rocket.getDistance());
        if(bounds != null) {
            float xPos = rocket.getXPos();
            if(xPos > bounds[0] && xPos < bounds[1]) {
            }
            else {
                GameOver();

            }
        }
        else {
            System.out.println("Error when checking for track boundaries");
        }

        checkForCollisionsWithBonuses();

        updateBonuses();
    }

    private void checkForCollisionsWithBonuses() {
        for(int i = 0; i < timeBonuses.size(); ++i) {
            TimeBonus b = timeBonuses.get(i);
            if(b.rocketIntersects(new PointF(rocket.getXPos(), rocket.getDistance()))) {
                timeLeft += 7.f;
                timeBonuses.remove(i);
                return;
            }
        }
    }

    private void updateBonuses() {
        for(int i = 0; i < timeBonuses.size(); ++i) {
            TimeBonus current = timeBonuses.get(i);
            if(current.getPos().y <= rocket.getDistance() - 1.f) {
                timeBonuses.remove(i);
            }
        }

        if(timeBonuses.size() <= 1) {
            generateNewBonuses();
        }
    }

    private void generateNewBonuses() {
        for(int i = 0; i < 5; ++i) {
            float yPos = lastBonusPos + (i+1) * TimeBonus.SPACE_BETWEEN_TIME_BONUSES;

            Random r = new Random();
            int rand = r.nextInt(100);
            float xPos = 0.1f + (float)rand / 100.f * 0.8f;

            timeBonuses.add(new TimeBonus(xPos, yPos));
        }

        lastBonusPos = timeBonuses.get(timeBonuses.size() - 1).getPos().y;
    }

    private float computeCurrentScore() {
        return rocket.getDistance() * 17;
    }

    private void GameOver() {
        isGameOver = true;

        //Init the game over view intent
        final Intent gameOverIntent = new Intent(this.getContext(), GameOverActivity.class);
        //Add the score to the flag "score"
        gameOverIntent.putExtra("score", (int) computeCurrentScore());
        //Start the game over activity
        this.getContext().startActivity(gameOverIntent);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        rocket.draw(canvas);
        track.draw(canvas, rocket.getDistance());

        Paint p = new Paint();
        p.setColor(Color.rgb(120,0,120));
        p.setTextSize(90);
        String text = "Score : " + Math.round(computeCurrentScore());
        canvas.drawText(text, 10, 100, p);

        float bonusWidth = TimeBonus.TIME_BONUS_WIDTH * (float)canvas.getWidth();
        for(TimeBonus b : timeBonuses) {
            PointF pos = b.getPos();
            float x = pos.x * canvas.getWidth();
            float y = Rocket.BOTTOM_POS - (rocket.getDistance() - pos.y);
            y *= canvas.getHeight();
            y = canvas.getHeight() - y;

            canvas.drawRect(new RectF(x,y, x + (float)bonusWidth, y - (float)bonusWidth), p);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor sensor = sensorEvent.sensor;

        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerData = sensorEvent.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetometerData = sensorEvent.values.clone();
                break;
            default:
                return;
        }

        float[] rotationMatrix = new float[9];
        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,null, accelerometerData, magnetometerData);
        float orientationValues[] = new float[3];
        if (rotationOK) {
            SensorManager.getOrientation(rotationMatrix, orientationValues);
        }

        rocket.setRotation(orientationValues[2]);

        Log.d("", "" + orientationValues[1]);
        rocket.setThrustPower(THRUST_SENSITIVITY * ((orientationValues[1] + (float)Math.PI / 2.f)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
