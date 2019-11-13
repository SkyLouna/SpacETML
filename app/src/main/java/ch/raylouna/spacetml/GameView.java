package ch.raylouna.spacetml;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    private SensorManager sensorManager; // Gestionnaire de capteurs
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] accelerometerData = new float[3];
    private float[] magnetometerData = new float[3];

    private GameThread thread;
    private Rocket rocket;

    private final float THRUST_SENSITIVITY = 0.01f;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);
        setFocusable(true);

        rocket = new Rocket(BitmapFactory.decodeResource(getResources(),R.drawable.spaceship));

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
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
        rocket.update(elapsedTime);
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

        rocket.setRotation(orientationValues[2] * (180f / (float)Math.PI));
        rocket.setThrustPower(THRUST_SENSITIVITY * (orientationValues[1] / (float)Math.PI));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}