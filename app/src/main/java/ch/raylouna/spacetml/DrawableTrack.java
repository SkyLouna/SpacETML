package ch.raylouna.spacetml;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import ch.raylouna.spacetml.Track.TrackGenerator;

/**
 * Represents the track on which the rocket is racing.
 */
public class DrawableTrack {

    private final float GENERATION_MARGIN_DISTANCE = 1.f;
    private final float SPACE_BETWEEN_POINTS = 0.05f;
    private final float TRACK_WIDTH = 0.5f;

    ArrayList<PointF> points;

    /**
     * Constructs a new drawable track
     */
    public DrawableTrack(){
        points = new ArrayList<>();
        points.add(new PointF(0f,0f));
    }

    /**
     * Returns the most recently generated point on the track
     * @return
     */
    private PointF getLastPoint() {
        return points.get(points.size() - 1);
    }

    /**
     * Updates the list of points stored in the track
     * @param rocketPos Y-pos of the rocket
     */
    public void updateGeneration(float rocketPos) {
        while(getLastPoint().y < (rocketPos + GENERATION_MARGIN_DISTANCE)) {
            points.add(generateNextPoint());
        }

        removeUnnecessaryPoints(rocketPos);
        //System.out.println("Nb pts : " + points.size());
    }

    private PointF generateNextPoint() {
        return new PointF((float)TrackGenerator.getInstance().nextNormalized(0.5) * (1-TRACK_WIDTH), getLastPoint().y + SPACE_BETWEEN_POINTS);
    }

    /**
     * Clears all the points that are behind the rocket
     * @param rocketPos Y-pos of the rocket
     */
    private void removeUnnecessaryPoints(float rocketPos) {
        while(getLastPoint().y < (rocketPos - Rocket.BOTTOM_POS)) {
            points.remove(points.size());
        }
    }

    public float[] getTrackBoundsAt(float rocketPos) {
        for(PointF p : points) {
            if(Math.abs(rocketPos - p.y) <= SPACE_BETWEEN_POINTS) {
                float[] array = new float[2];
                array[0] = p.x;
                array[1] = p.x + TRACK_WIDTH;
                return array;
            }
        }
        return null;
    }

    /**
     * Draws the track on the given canvas
     * @param c Canvas on which to draw
     * @param rocketDistance Y-pos of the rocket
     */
    public void draw(Canvas c, float rocketDistance){
        Paint p = new Paint();
        p.setColor(Color.rgb(255,0,0));

        for(int i = 0; i < points.size(); ++i){
            PointF left = points.get(i);
            float x = left.x * c.getWidth();
            float y = Rocket.BOTTOM_POS - (rocketDistance - left.y);
            y *= c.getHeight();
            y = c.getHeight() - y;

            c.drawCircle(x, y, 10.f, p);

            PointF right = new PointF(x + TRACK_WIDTH * c.getWidth(), y);
            c.drawCircle(right.x, right.y, 10.f, p);
        }
    }
}
