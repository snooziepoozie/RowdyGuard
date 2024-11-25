package edu.utsa.cs3443.rowdyguard.model;

import java.io.Serializable;

public class Password implements Serializable {
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
    public void setTitle(String title) { this.title = title; }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) { this.password = password; }
}