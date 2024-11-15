package edu.utsa.cs3443.rowdyguard.model;

public class Password {
    private String title;
    private String username;
    private String password;

    public Password (String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}