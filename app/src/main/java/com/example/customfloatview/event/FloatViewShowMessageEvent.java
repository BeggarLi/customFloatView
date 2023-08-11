package com.example.customfloatview.event;

public class FloatViewShowMessageEvent {
    public String title;
    public String message;

    public FloatViewShowMessageEvent(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
