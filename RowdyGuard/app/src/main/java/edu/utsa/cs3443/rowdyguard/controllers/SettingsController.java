package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.Settings;

public class SettingsController extends AppCompatActivity {
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        // Initialize Settings
        settings = new Settings("defaultPassword");

        //UI components
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        Button changePasswordButton = findViewById(R.id.button_change_password);
        EditText passwordInput = findViewById(R.id.editText_password);
        TextView logoutTextView = findViewById(R.id.text_logout);

        //Initial state
        toggleButton.setChecked(settings.isDarkThemeEnabled());
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settings.toggleTheme();
            //Apply the theme immediately.
            Toast.makeText(this, "Dark theme: " + (settings.isDarkThemeEnabled() ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        changePasswordButton.setOnClickListener(v -> {
            String newPassword = passwordInput.getText().toString().trim();
            if (!newPassword.isEmpty()) {
                settings.setPassword(newPassword);
                passwordInput.setText("");
                Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        logoutTextView.setOnClickListener(v -> {
            settings.logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}