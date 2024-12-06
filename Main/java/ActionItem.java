package com.example.vvvvv;

public class ActionItem {

    private String title;
    private int imageResourceId;

    public ActionItem(String title, int imageResourceId) {
        this.title = title;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
