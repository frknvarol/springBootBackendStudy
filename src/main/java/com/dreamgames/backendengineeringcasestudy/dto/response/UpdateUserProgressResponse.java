package com.dreamgames.backendengineeringcasestudy.dto.response;

public class UpdateUserProgressResponse {
    private Long userId;
    private int level;
    private int coins;

    public UpdateUserProgressResponse() {}

    public UpdateUserProgressResponse(Long userId, int level, int coins) {
        this.userId = userId;
        this.level = level;
        this.coins = coins;
    }

    public Long getUserId() {
        return userId;
    }

    public int getLevel() {
        return level;
    }

    public int getCoins() {
        return coins;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
