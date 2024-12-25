package com.dreamgames.backendengineeringcasestudy.dto.request;

public class GetSuggestionsRequest {
    private Character abGroup;

    public GetSuggestionsRequest(Character abGroup) {
        this.abGroup = abGroup;
    }

    public Character getAbGroup() {
        return abGroup;
    }

    public void setAbGroup(Character abGroup) {
        this.abGroup = abGroup;
    }
}