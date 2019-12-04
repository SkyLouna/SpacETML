package ch.raylouna.spacetml.Track;

public enum TrackDifficulty {

    EASY(20), MEDIUM(40);

    private int height;           //Define the road size. 0 is the bigger, 100 the lower

    /**
     * Track difficulty constructor
     * @param height
     */
    TrackDifficulty(int height){
        this.height = height;
    }

    /**
     * Gets the height of the track
     * @return  track height
     */
    public int getHeight(){
        return this.height;
    }

}
