package com.dreamgames.backendengineeringcasestudy.dto.response;

public class UpdateBalloonProgressResponse {

    private int newBalloonProgress;
    private int remainingHelium;


    public int getNewBalloonProgress() {
        return newBalloonProgress;
    }

    public void setNewBalloonProgress(int newBalloonProgress) {
        this.newBalloonProgress = newBalloonProgress;
    }

    public int getRemainingHelium() {
        return remainingHelium;
    }

    public void setRemainingHelium(int remainingHelium) {
        this.remainingHelium = remainingHelium;
    }
}
