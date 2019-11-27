package ch.raylouna.spacetml;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Représente la fusée pilotée par le joueur.
 */
public class Rocket {

    private final float rocketWidth = 0.1f;
    private float rotation;

    private float xVel;
    private float yVel;

    private float xPos;
    private float yPos;

    private float thrustPower;

    private Bitmap img;

    public Rocket(Bitmap bmp) {
        rotation = 0;

        xPos = 0.5f;
        yPos = 0.5f;

        xVel = 0.0f;
        yVel = 0.0f;

        thrustPower = 0f;

        img = bmp;
    }

    public void update(float dt) {
        xPos += dt * xVel;
        yPos += dt * yVel;

        xVel += thrustPower * Math.cos(rotation) * dt;
        yVel -= thrustPower * Math.sin(rotation) * dt;

        xVel *= 0.9;
        yVel *= 0.9;
    }

    public void setRotation(float rad) {
        rotation = rad - (float)Math.PI / 2.f;
    }

    public void setThrustPower(float newValue) {
        thrustPower = newValue;
    }

    public void draw(Canvas canvas) {
        float width = canvas.getWidth() * rocketWidth;
        float height = width * (img.getHeight() / img.getWidth());
        float y = canvas.getHeight() - yPos * canvas.getHeight();
        float x = canvas.getWidth() * xPos;

        canvas.save();
        canvas.rotate(rotation * (180f / (float)Math.PI) + 90, x ,y);
        canvas.drawBitmap(img, null, new Rect((int)(x - width / 2),(int)(y - height / 2),(int)(x + width / 2),(int)(y + height / 2)), null);
        canvas.restore();

        /*
        float width = canvas.getWidth() * rocketWidth;
        float imgHeight = img.getHeight();
        float height = width * (img.getHeight() / img.getWidth());
        float y = canvas.getHeight() - rocketPositionBottom * canvas.getHeight();
        float x = canvas.getWidth() * xPos;

        canvas.save();
        canvas.rotate(rotation, x ,y);
        canvas.drawBitmap(img, null, new Rect((int)(x - width / 2),(int)(y - height / 2),(int)(x + width / 2),(int)(y + height / 2)), null);
        canvas.restore();*/


    }
}
