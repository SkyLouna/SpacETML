/**
 * ETML
 * Author: Lucas Charbonnier & Trana Valentin
 * Description:
 */


package ch.raylouna.spacetml;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Main thread on which the game must run.
 */
public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    /**
     * Constructs a new GameThread
     * @param surfaceHolder SurfaceHolder to draw on
     * @param gameView The game view
     */
    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    /**
     * Sets the state of the thread.
     * @param newState new state
     */
    public void setRunningState(boolean newState) {
        running = newState;
    }

    /**
     * Starts the thread.
     */
    @Override
    public void run() {

        final float FLT_TIME_STEP_IN_SECONDS = 0.020f;

        long lastUpdateTime = System.currentTimeMillis();
        while (running) {
            if(System.currentTimeMillis() - lastUpdateTime > FLT_TIME_STEP_IN_SECONDS * 1000) {
                lastUpdateTime = System.currentTimeMillis();

                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized(surfaceHolder) {
                        this.gameView.update(FLT_TIME_STEP_IN_SECONDS);
                        this.gameView.draw(canvas);
                    }
                }
                catch (Exception e) {} finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
