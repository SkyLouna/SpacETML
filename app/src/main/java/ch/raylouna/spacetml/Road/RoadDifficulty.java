package ch.raylouna.spacetml.Road;

import android.hardware.HardwareBuffer;

public enum RoadDifficulty {



    EASY(100, 20, 10), MEDIUM(150, 50,50), HARD(200, 100,100);

    private int size;           //Define the road size. 0 is the bigger, 100 the lower
    private int border;
    private int slope;

    RoadDifficulty(int size, int border, int slope){
        this.size = size;
        this.border = border;
        this.slope = slope;
    }

    public int getSize(){
        return this.size;
    }

    public int getBorder(){
        return this.border;
    }

    public int getSlope(){
        return this.slope;
    }
}
