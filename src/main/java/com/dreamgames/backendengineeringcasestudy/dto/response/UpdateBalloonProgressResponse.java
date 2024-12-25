package com.dreamgames.backendengineeringcasestudy.dto.response;

public class UpdateBalloonProgressResponse {

    private boolean success;
    private int newBalloonProgress;
    private int remainingHelium;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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
