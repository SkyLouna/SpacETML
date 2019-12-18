package ch.raylouna.spacetml;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Represents a time bonus on the track.
 * Time bonuses are objects that the player can absorb to gain more time to play.
 */
public class TimeBonus {
    public static final float TIME_BONUS_WIDTH = 0.08f;
    public static final float SPACE_BETWEEN_TIME_BONUSES = 1.5f;

    private PointF pos;

    /**
     * Constructs a new TimeBonus
     * @param x X pos (normalized)
     * @param y Y pos (normalized)
     */
    public TimeBonus(float x, float y) {
        pos = new PointF(x,y);
    }

    /**
     * @return the position of the bonus
     */
    public PointF getPos() {
        return pos;
    }

    /**
     * Check if the rocket is colliding with the bonus.
     * @param rocketPos Center of the rocket.
     * @return True if the point is inside the bonus, false otherwise.
     */
    public boolean rocketIntersects(PointF rocketPos) {
        return
            rocketPos.y <= pos.y &&
            rocketPos.y >= pos.y - TIME_BONUS_WIDTH &&
            rocketPos.x >= pos.x &&
            rocketPos.x <= pos.x + TIME_BONUS_WIDTH;
    }
}
