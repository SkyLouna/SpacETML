package ch.raylouna.spacetml;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    private SensorManager sensorManager; // Gestionnaire de capteurs
    private Sensor gyroscope; // Capteur pour la rotation du téléphone (gyroscope)

    private GameThread thread;
    private Rocket rocket;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);
        setFocusable(true);

        rocket = new Rocket(BitmapFactory.decodeResource(getResources(),R.drawable.spaceship));

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
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

    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        rocket.draw(canvas);
        if (canvas != null) {
            //canvas.drawColor(Color.BLUE);
            //Paint paint = new Paint();
            //paint.setColor(Color.rgb(250, 0, 0));
            //canvas.drawRect(100, 100, 200, 200, paint);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if(sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            rocket.setRotation(x);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
