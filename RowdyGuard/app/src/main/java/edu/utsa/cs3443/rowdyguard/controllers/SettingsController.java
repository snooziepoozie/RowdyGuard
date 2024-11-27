package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.Settings;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;

public class SettingsController extends AppCompatActivity {
    private Settings settings;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        try {
            // Initialize Settings and Handler
            settings = new Settings("defaultPassword");
            handler = new Handler("defaultPassword", getApplicationContext());
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing the handler!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            finish();
            return;
        }

        // UI components
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        Button changePasswordButton = findViewById(R.id.button_change_password);
        EditText passwordInput = findViewById(R.id.editText_password);
        TextView logoutTextView = findViewById(R.id.text_logout);

        // Initial state
        toggleButton.setChecked(settings.isDarkThemeEnabled());
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settings.toggleTheme();
            Toast.makeText(this, "Dark theme: " + (settings.isDarkThemeEnabled() ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        // Change password logic
        changePasswordButton.setOnClickListener(v -> {
            String newPassword = passwordInput.getText().toString().trim();
            if (!newPassword.isEmpty()) {
                try {
                    settings.setPassword(newPassword, handler); // Update password using Handler
                    passwordInput.setText("");
                    Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Error changing password!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        // Logout logic
        logoutTextView.setOnClickListener(v -> {
            settings.logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}