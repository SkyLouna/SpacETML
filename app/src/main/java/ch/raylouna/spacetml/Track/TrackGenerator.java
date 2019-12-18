/**
 * ETML
 * Author: Lucas Charbonnier & Trana Valentin
 * Description:
 */


package ch.raylouna.spacetml.Track;

import java.util.Random;

        /*
        SAMPLE OF TRACK GENERATOR
        TrackGenerator.getInstance().setup(TrackDifficulty.EASY);

        for(int i = 0; i < 1000; i++){
            System.out.print(TrackGenerator.getInstance().nextNormalized(0.5) + "\n");
        }*/

/**
 * TrackGenerator Class
 * Author: Trana Valentin
 * Desc:
 *      Generate a random track.
 */
public class TrackGenerator {

    private static TrackGenerator instance;         //Generator instance

    /**
     * Gets the instance of the generator
     * @return generator instance
     */
    public static TrackGenerator getInstance(){
        //If no instance, create new one
        if(instance == null)
            instance = new TrackGenerator();

        //Return the instance
        return instance;
    }


    Random random;                                  //Random instance

    double x;                                       //Current position of the track

    TrackDifficulty trackDifficulty;                //Track current difficulty


    /**
     * Track generator instance
     */
    private TrackGenerator(){
        //Create new instance of random
        this.random = new Random();

        //Randomize actual position from -100 to +100
        this.x = random.nextInt(2) < 1 ? random.nextInt(100) : random.nextInt(100) * -1;
    }

    /**
     * Setup the current track with track difficulty
     * @param trackDifficulty
     */
    public void setup(TrackDifficulty trackDifficulty){
        this.trackDifficulty = trackDifficulty;
    }

    /**
     * Gets the current track value
     * @param alpha
     * @return
     */
    public double next(double alpha){
        //Increment x
        x+= alpha;

        return (Math.sin(x*0.3)*1.4 + Math.sin(x*0.2)*0.2 + Math.cos(x*0.1) *1.7) * trackDifficulty.getHeight() + (trackDifficulty.getHeight() * 3);
    }

    /**
     * Gets the current track value normalized (0<->1)
     * @param alpha
     * @return
     */
    public double nextNormalized(double alpha){
        //Increment x
        x+= alpha;

        double y = (Math.sin(x*0.3)*1.4 + Math.sin(x*0.2)*0.2 + Math.cos(x*0.1) *1.7) + 3.3;

        return y / 6.6;
    }



}
