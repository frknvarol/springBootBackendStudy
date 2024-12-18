package com.dreamgames.backendengineeringcasestudy.model;
import jakarta.persistence.*;

public class Partnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId1;

    @Column(nullable = false)
    private long userId2;

    @Column(nullable = false)
    private int heliumCollected;

    @Column(nullable = false)
    private int balloonProgress;

    @Column(nullable = false)
    private long eventId;

    @Column(nullable = false)
    private boolean rewardClaimed;


    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false) // Maps the foreign key
    private Event event; // Attribute type matches the parent entity


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId1() {
        return userId1;
    }

    public void setUserId1(long userId1) {
        this.userId1 = userId1;
    }

    public long getUserId2() {
        return userId2;
    }

    public void setUserId2(long userId2) {
        this.userId2 = userId2;
    }

    public int getHeliumCollected() {
        return heliumCollected;
    }

    public void setHeliumCollected(int heliumCollected) {
        this.heliumCollected = heliumCollected;
    }

    public int getBalloonProgress() {
        return balloonProgress;
    }

    public void setBalloonProgress(int balloonProgress) {
        this.balloonProgress = balloonProgress;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public boolean isRewardClaimed() {
        return rewardClaimed;
    }

    public void setRewardClaimed(boolean rewardClaimed) {
        this.rewardClaimed = rewardClaimed;
    }
}
