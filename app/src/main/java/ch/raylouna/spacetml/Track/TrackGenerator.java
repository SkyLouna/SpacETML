package ch.raylouna.spacetml.Track;

import java.util.Random;

public class TrackGenerator {

    private static TrackGenerator instance;

    public static TrackGenerator getInstance(){
        if(instance == null)
            instance = new TrackGenerator();

        return instance;
    }

    Random random;

    double x;

    TrackDifficulty trackDifficulty;


    private TrackGenerator(){
        this.random = new Random();
        this.x = random.nextInt(2) < 1 ? random.nextInt(100) : random.nextInt(100) * -1;
    }

    public void setup(TrackDifficulty trackDifficulty){
        this.trackDifficulty = trackDifficulty;
    }

    public double next(double alpha){
        x+= alpha;

        return (Math.sin(x*0.3)*1.4 + Math.sin(x*0.2)*0.2 + Math.cos(x*0.1) *1.7) * trackDifficulty.getHeight() + 70;
    }



}
