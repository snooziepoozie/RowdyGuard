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
    //Gettor Methods
    public String getTitle() {
        return this.title;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }

    //Setter Methods
    public void setTitle(String title) {
        this.title = title;
    }


    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}