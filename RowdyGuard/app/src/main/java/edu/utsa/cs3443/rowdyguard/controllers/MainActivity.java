package edu.utsa.cs3443.rowdyguard.controllers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.Settings;


public class MainActivity extends AppCompatActivity {
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        //iniatilize with default password
        settings = new Settings("password");

        // Change Password Button
        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(v -> {
            settings.setPassword("password1");
            System.out.println("Password changed successfully.");
        });

        ToggleButton themeToggleButton = findViewById(R.id.themeToggleButton);
        themeToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settings.toggleTheme();
            System.out.println("Theme toggled. Dark Theme: " + settings.isDarkThemeEnabled());
        });

        // Logout Button
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            settings.logout();
            finish();
        });
    }
}