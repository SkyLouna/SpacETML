/**
 * ETML
 * Author: Lucas Charbonnier & Trana Valentin
 * Description:
 */


package ch.raylouna.spacetml;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Représente la fusée pilotée par le joueur.
 *
 * The rocket position is normalized on the width and height of the screen.
 */
public class Rocket {

    private final float rocketWidth = 0.1f;
    private float rotation;

    private float xVel;
    private float yVel;

    private float xPos;
    public static final float BOTTOM_POS = 0.2f;
    private float distanceFromStart;

    private float thrustPower;

    private Bitmap img;

    /**
     * Constructs a new rocket
     * @param bmp Bitmap resource to use for the rocket texture
     */
    public Rocket(Bitmap bmp) {
        rotation = 0;

        xPos = 0.25f;
        distanceFromStart = BOTTOM_POS;

        xVel = 0.0f;
        yVel = 0.0f;

        thrustPower = 0f;

        distanceFromStart = 0.f;

        img = bmp;
    }

    /**
     * Updates the rocket position and velocity
     * @param dt
     */
    public void update(float dt) {
        xPos += dt * xVel;
        distanceFromStart += dt * yVel;

        xVel += thrustPower * Math.cos(rotation) * dt;
        yVel -= thrustPower * Math.sin(rotation) * dt;

        xVel *= 0.95;
        yVel *= 0.95;
    }

    /**
     * Overwrites the previous angle of the rocket
     * @param rad Angle in radians
     */
    public void setRotation(float rad) {
        rotation = rad - (float)Math.PI / 2.f;
    }

    /**
     * Overwrite the previous thrust value
     * @param newValue Thrust power
     */
    public void setThrustPower(float newValue) {
        thrustPower = Math.abs(newValue);
    }

    /**
     * Draws the rocket on the given canvas
     * @param canvas Canvas to draw on
     */
    public void draw(Canvas canvas) {
        float width = canvas.getWidth() * rocketWidth;
        float height = width * (img.getHeight() / img.getWidth());
        float y = canvas.getHeight() - BOTTOM_POS * canvas.getHeight();
        float x = canvas.getWidth() * xPos;

        canvas.save();
        canvas.rotate(rotation * (180f / (float)Math.PI) + 90, x ,y);
        canvas.drawBitmap(img, null, new Rect((int)(x - width / 2),(int)(y - height / 2),(int)(x + width / 2),(int)(y + height / 2)), null);
        canvas.restore();
    }

    /**
     * Gives the distance travelled by the rocket since the start of the game
     * @return Travelled distance
     */
    public float getDistance() {
        return distanceFromStart;
    }

    /**
     * Gives the distance from the left side of the screen
     * @return X position as a percentage of the screen width
     */
    public float getXPos() {return xPos;}
}
