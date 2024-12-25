package com.dreamgames.backendengineeringcasestudy.model;
import jakarta.persistence.*;

import java.io.CharArrayReader;

@Entity
public class Partnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int heliumCount;

    @Column(nullable = false)
    private int balloonProgress;

    @Column(nullable = false)
    private boolean rewardClaimed;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Character abGroup;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getHeliumCount() {
        return heliumCount;
    }

    public void setHeliumCount(int heliumCollected) {
        this.heliumCount = heliumCollected;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
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
