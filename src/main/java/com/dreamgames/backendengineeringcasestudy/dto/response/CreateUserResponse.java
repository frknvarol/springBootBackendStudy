package com.dreamgames.backendengineeringcasestudy.dto.response;

public class CreateUserResponse {
    private Long id;
    private Integer level;
    private Integer coins;
    private Character abGroup;

    public CreateUserResponse(Long id, Integer level, Integer coins, Character abGroup) {
        this.id = id;
        this.level = level;
        this.coins = coins;
        this.abGroup = abGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Character getAbGroup() {
        return abGroup;
    }

    public void setAbGroup(Character abGroup) {
        this.abGroup = abGroup;
    }
}
