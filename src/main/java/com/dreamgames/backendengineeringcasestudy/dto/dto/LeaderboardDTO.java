package com.dreamgames.backendengineeringcasestudy.dto.dto;

public class LeaderboardDTO {
    private Long userId;
    private int level;
    private String username;

    public LeaderboardDTO() {
    }

    public LeaderboardDTO(Long userId, int level, String username) {
        this.userId = userId;
        this.level = level;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int score) {
        this.level = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

