package ch.raylouna.spacetml;

import android.graphics.Canvas;
import android.graphics.PointF;

public class TimeBonus {
    public static final float TIME_BONUS_WIDTH = 0.08f;
    public static final float SPACE_BETWEEN_TIME_BONUSES = 1.5f;

    private PointF pos;

    public TimeBonus(float x, float y) {
        pos = new PointF(x,y);
    }

    public PointF getPos() {
        return pos;
    }

    public boolean rocketIntersects(PointF rocketPos) {
        return
            rocketPos.y <= pos.y &&
            rocketPos.y >= pos.y - TIME_BONUS_WIDTH &&
            rocketPos.x >= pos.x &&
            rocketPos.x <= pos.x + TIME_BONUS_WIDTH;
    }
}
