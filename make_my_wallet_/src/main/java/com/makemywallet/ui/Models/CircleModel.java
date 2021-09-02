package com.makemywallet.ui.Models;

public class CircleModel {
    private String id;
    private String state;

    public CircleModel(String id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
