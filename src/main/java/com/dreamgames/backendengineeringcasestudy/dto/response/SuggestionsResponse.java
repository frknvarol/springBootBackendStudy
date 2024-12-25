package com.dreamgames.backendengineeringcasestudy.dto.response;

import com.dreamgames.backendengineeringcasestudy.model.User;

import java.util.List;

public class SuggestionsResponse {
    private List<User> suggestions;

    // Constructor
    public SuggestionsResponse(List<User> suggestions) {
        this.suggestions = suggestions;
    }

    public List<User> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<User> suggestions) {
        this.suggestions = suggestions;
    }
}
