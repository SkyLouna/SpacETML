package ch.raylouna.spacetml;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Représente la fusée pilotée par le joueur.
 */
public class Rocket {
    private final float rocketWidth = 0.5f;
    private final float rocketPositionBottom = 0.3f;
    private float rotation;
    private float xpos;
    private Bitmap img;

    public Rocket(Bitmap bmp) {
        rotation = 0;
        xpos = 0.5f;
        img = bmp;
    }

    public void setRotation(float newValue) {
        rotation = newValue;
    }

    public void draw(Canvas canvas) {
        float width = canvas.getWidth() * rocketWidth;
        float imgHeight = img.getHeight();
        float height = width * (img.getHeight() / img.getWidth());
        float y = canvas.getHeight() - rocketPositionBottom * canvas.getHeight();
        float x = canvas.getWidth() * xpos;

        canvas.save();
        canvas.rotate(rotation, x ,y);
        canvas.drawBitmap(img, null, new Rect((int)(x - width / 2),(int)(y - height / 2),(int)(x + width / 2),(int)(y + height / 2)), null);
        canvas.restore();
    }
}
