/**
 * ETML
 * Author: Lucas Charbonnier & Trana Valentin
 */


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

    private static final float GENERATION_MARGIN_DISTANCE = 1.f;            //Margin distance
    private static final float SPACE_BETWEEN_POINTS = 0.05f;                //Distance between points
    public static final float TRACK_WIDTH = 0.5f;                           //Track width

    ArrayList<PointF> points;                                               //Track points

    /**
     * Constructs a new drawable track
     */
    public DrawableTrack(){

        //init points list
        points = new ArrayList<>();

        //for first 10 points (Construct a linear road for 10 points)
        for(int i = 0; i < 10; ++i) {
            points.add(new PointF(0.2f, i * SPACE_BETWEEN_POINTS));
        }
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

        //While the track is not fully generated
        while(getLastPoint().y < (rocketPos + GENERATION_MARGIN_DISTANCE)) {
            points.add(generateNextPoint());
        }

        //Removes the unnecessary points from the track
        removeUnnecessaryPoints(rocketPos);
    }

    /**
     * Generates the next point
     * @return
     */
    private PointF generateNextPoint() {
        return new PointF((float)TrackGenerator.getInstance().nextNormalized(0.5) * (1-TRACK_WIDTH), getLastPoint().y + SPACE_BETWEEN_POINTS);
    }

    /**
     * Clears all the points that are behind the rocket
     * @param rocketPos Y-pos of the rocket
     */
    private void removeUnnecessaryPoints(float rocketPos) {

        //While points are out of the screen size, remove it
        while(getLastPoint().y < (rocketPos - Rocket.BOTTOM_POS)) {
            points.remove(points.size());
        }
    }

    /**
     * Gets the track at the rocket position
     * @param rocketPos
     * @return
     */
    public float[] getTrackBoundsAt(float rocketPos) {

        //Foreach track points
        for(PointF p : points) {
            //poisiton is lower than space between points
            if(Math.abs(rocketPos - p.y) <= SPACE_BETWEEN_POINTS) {
                float[] array = new float[2];
                array[0] = p.x;                 //Left point
                array[1] = p.x + TRACK_WIDTH;   //Right point
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
        //Create red paint brush
        Paint p = new Paint();
        p.setStrokeWidth(20);
        p.setColor(Color.rgb(255,0,0));

        //Foreach track point
        for(int i = 0; i < points.size(); ++i){

            //Get the left point
            PointF left = points.get(i);
            float x = left.x * c.getWidth();
            float y = Rocket.BOTTOM_POS - (rocketDistance - left.y);
            y *= c.getHeight();
            y = c.getHeight() - y;

            //right point
            PointF right = new PointF(x + TRACK_WIDTH * c.getWidth(), y);
            if(i != points.size() - 1) {
                c.drawLine(x, y, points.get(i+1).x * c.getWidth(), y - SPACE_BETWEEN_POINTS * c.getHeight(), p);
                c.drawLine(right.x, y, (points.get(i+1).x + TRACK_WIDTH) * c.getWidth(), y - SPACE_BETWEEN_POINTS * c.getHeight(), p);
            }
        }
    }
}
