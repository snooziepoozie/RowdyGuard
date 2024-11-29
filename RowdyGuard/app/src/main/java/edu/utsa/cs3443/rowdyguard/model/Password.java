package edu.utsa.cs3443.rowdyguard.model;

import java.io.Serializable;

/**
 * Object representing a title, username, and password
 * @author Jonathan Beierle
 */

public class Password implements Serializable {
    private String title;
    private String username;
    private String password;

    /**
     * Constructor for instantiating a new password object
     * @param title Title to create the password object with
     * @param username Username to create the password object with
     * @param password Password to create the password object with
     */
    public Password (String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
    }

    /**
     * Method to get the title of the password object
     * @return String representing the title of the password object
     */
    public String getTitle() {return this.title;}

    /**
     * Method to get the username of the password object
     * @return String representing the username of the password object
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Method to get the password of the password object
     * @return String representing the password of the password object
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Method to set the title of the password object
     * @param title String representing the title to set the password object's title password to
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method to set the username of the password object
     * @param username String representing the username to set the password object's username to
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to set the password of the password object
     * @param password String representing the password to set the password object's password to
     */
    public void setPassword(String password) {
        this.password = password;
    }
}