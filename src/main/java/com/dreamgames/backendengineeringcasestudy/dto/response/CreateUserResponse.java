package com.dreamgames.backendengineeringcasestudy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("coins")
    private Integer coins;
    @JsonProperty("abGroup")
    private Character abGroup;

    public CreateUserResponse() {}

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
