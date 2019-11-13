package ch.raylouna.spacetml.Road;

import java.util.Random;

public class RoadGenerator {

    private static RoadGenerator instance;

    RoadDifficulty actualDifficulty;

    double actualValue;

    double actualSlope;

    double borderSpace;

    private RoadGenerator(){
        this.actualValue = 0;
        this.actualSlope = 0;
        this.borderSpace = 0;
    }

    public static RoadGenerator getInstance(){
        if(instance == null){
            instance = new RoadGenerator();
        }
        return instance;
    }

    public void setup(RoadDifficulty roadDifficulty) {
        this.actualDifficulty = roadDifficulty;

        this.actualValue = actualDifficulty.getSize() / 2;
        this.borderSpace = actualDifficulty.getSize() / 20;
        this.actualSlope = 1;
    }

    private void generateBorderSpace(){
        Random random = new Random();

        this.borderSpace = random.nextInt(this.actualDifficulty.getBorder()) + 10;
    }

    private void generateRandomSlope(boolean descending){
        Random random = new Random();

        this.actualSlope = random.nextInt(this.actualDifficulty.getSlope()) + 1;

        //reverse actual slope
        this.actualSlope = descending ? -this.actualSlope : this.actualSlope;

    }

    public double next(double alpha){

        actualValue += (actualSlope * alpha);

        //Actual value cannot be lower than the borderspace
        if(actualValue < this.borderSpace){
            this.generateRandomSlope(false);
            this.generateBorderSpace();
        }

        if(actualValue > this.actualDifficulty.getSize() - this.borderSpace){
            this.generateRandomSlope(true);
            this.generateBorderSpace();
        }

        return actualValue;
    }

}
























