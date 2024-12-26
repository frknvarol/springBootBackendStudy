package com.dreamgames.backendengineeringcasestudy.dto.response;

public class GetBalloonsInfoResponse {

    private Long user1Id;
    private Long user2Id;
    private Long eventId;
    private int balloonProgress;
    private boolean rewardClaimed;
    private int heliumCount;
    private boolean active;
    private Character abGroup;

    public GetBalloonsInfoResponse() {}

    public GetBalloonsInfoResponse(Long user1Id, Long user2Id, Long eventId, int balloonProgress, boolean rewardClaimed, int heliumCount, boolean active, Character abGroup) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.eventId = eventId;
        this.balloonProgress = balloonProgress;
        this.rewardClaimed = rewardClaimed;
        this.heliumCount = heliumCount;
        this.active = active;
        this.abGroup = abGroup;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Long user1Id) {
        this.user1Id = user1Id;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Long user2Id) {
        this.user2Id = user2Id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public int getBalloonProgress() {
        return balloonProgress;
    }

    public void setBalloonProgress(int balloonProgress) {
        this.balloonProgress = balloonProgress;
    }

    public boolean isRewardClaimed() {
        return rewardClaimed;
    }

    public void setRewardClaimed(boolean rewardClaimed) {
        this.rewardClaimed = rewardClaimed;
    }

    public int getHeliumCount() {
        return heliumCount;
    }

    public void setHeliumCount(int heliumCount) {
        this.heliumCount = heliumCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Character getAbGroup() {
        return abGroup;
    }

    public void setAbGroup(Character abGroup) {
        this.abGroup = abGroup;
    }
}
