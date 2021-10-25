package com.example.notes_app;

public class Note {
    private String username;
    private String date;
    private String title;
    private String content;

    public Note(String date, String username, String title, String content) {
        this.username = username;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public String getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getUsername() {
        return username;
    }
}
