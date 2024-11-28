package edu.utsa.cs3443.rowdyguard.model;

import android.widget.ToggleButton;

import edu.utsa.cs3443.rowdyguard.model.db.Handler;

public class Settings {
    //Variables
    private String password;
    private boolean isDarkTheme;

    //Constructor
    public Settings(String initialPassword) {
        this.password = initialPassword;
        this.isDarkTheme = false;
    }

    //Getter methods
    public String getPassword() { return password;}
    public boolean isDarkThemeEnabled() { return isDarkTheme; }

    // Setter methods
    public void setPassword(String newPassword, Handler handler) throws Exception {
        this.password = newPassword;
        handler.changeVaultPassword(newPassword);
    }
    public void toggleTheme() { this.isDarkTheme = !this.isDarkTheme; }

    //Logout function
    public void logout() { System.out.println("User logged out successfully."); }
}

