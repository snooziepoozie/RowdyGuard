package edu.utsa.cs3443.rowdyguard.model;
/**
 * Object represents application settings such as app theme and 
 * app password.
 * @author Jeronimo Gonzalez
 */

public class Settings {
    //Variables
    private boolean isDarkTheme;

    //Constructor
    /**
     * Constructor to initialize values, sets theme to light mode.
     */
    public Settings() {
        this.isDarkTheme = false;
    }

    //Getter methods
    public boolean isDarkThemeEnabled() { return isDarkTheme; }

    /** 
    * Setter methods, toggles between light and dark mode.
    */
    public void toggleTheme() { this.isDarkTheme = !this.isDarkTheme; }

    /**
     * Logout function.
     */
    public void logout() { System.out.println("User logged out successfully."); }
}

