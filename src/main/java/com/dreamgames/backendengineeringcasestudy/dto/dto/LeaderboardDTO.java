package com.dreamgames.backendengineeringcasestudy.dto.dto;

public class LeaderboardDTO {
    private Long userId;
    private int level;

    public LeaderboardDTO(Long userId, int score) {
        this.userId = userId;
        this.level = score;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getScore() {
        return level;
    }

    public void setScore(int score) {
        this.level = score;
    }
}

