package edu.utsa.cs3443.rowdyguard.model;


public class Settings {
    //Variables
    private boolean isDarkTheme;

    //Constructor
    public Settings() {
        this.isDarkTheme = false;
    }

    //Getter methods
    public boolean isDarkThemeEnabled() { return isDarkTheme; }

    // Setter methods
    public void toggleTheme() { this.isDarkTheme = !this.isDarkTheme; }

    //Logout function
    public void logout() { System.out.println("User logged out successfully."); }
}

