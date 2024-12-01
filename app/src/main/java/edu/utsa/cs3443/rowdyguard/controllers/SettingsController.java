package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Context;
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

/**
 * The SeetingsController class manages UI for app settings.
 * This includes toggling the theme, changing the user's vault password, and handling logout functionality.
 * Author: Jeronimo Gonzalez
 */
public class SettingsController extends AppCompatActivity {
    /**
     * Initializes the settings and handler.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        // Initialize Settings
        Handler handler = (Handler) this.getIntent().getSerializableExtra("handler");
        assert handler != null;
        try {
            handler.genKey(handler.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Settings settings = new Settings();

        // UI components
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        Button changePasswordButton = findViewById(R.id.button_change_password);
        EditText passwordInput = findViewById(R.id.editText_password);
        TextView logoutTextView = findViewById(R.id.text_logout);

        // Initial state
        toggleButton.setChecked(settings.isDarkThemeEnabled());
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settings.toggleTheme();
            // Apply the theme immediately.
            Toast.makeText(this, "Dark theme: " + (settings.isDarkThemeEnabled() ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        //Password Change
        changePasswordButton.setOnClickListener(v -> {
            String newPassword = passwordInput.getText().toString().trim();
            if (!newPassword.isEmpty()) {
                try {
                    handler.changeVaultPassword(newPassword, (Context) this);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                passwordInput.setText("");
                Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        //Logout function
        logoutTextView.setOnClickListener(v -> {
            settings.logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
