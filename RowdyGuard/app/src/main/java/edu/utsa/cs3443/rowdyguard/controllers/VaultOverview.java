package edu.utsa.cs3443.rowdyguard.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.Password;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;

/**
 * The VaultOverview activity provides an overview of all stored passwords in a single place
 *
 * @author Jonathan Beierle
 */

public class VaultOverview extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.vault_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout layout = findViewById(R.id.linearLayout);
        Context context = this;
        Activity activity = this;

        Button addButton = findViewById(R.id.addButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        Handler handler = (Handler) this.getIntent().getSerializableExtra("handler");
        assert handler != null;
        try {
            handler.genKey(handler.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Password p: handler.getPasswords()) {  // Populate passwords in view
            Button button = new Button(context);
            button.setText(p.getTitle());
            button.setOnClickListener(v -> {  // add event listener to change views when clicked
                Intent intent = new Intent(activity, PasswordView.class);
                intent.putExtra("handler", handler);
                intent.putExtra("title", p.getTitle());
                startActivity(intent);
            });
            System.out.println("Created a new password button '" + p.getTitle() + "'");
            layout.addView(button);
        }

        addButton.setOnClickListener(v -> {  // Add a default password to the database and view
            try {
                handler.addPassword("New Password", "username", "P@ssw0rd", this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Button button = new Button(context);
            button.setText("New Password");
            button.setOnClickListener(v1 -> {
                Intent intent = new Intent(activity, PasswordView.class);
                intent.putExtra("handler", handler);
                intent.putExtra("title", "New Password");
                startActivity(intent);
            });
            layout.addView(button);
        });

        settingsButton.setOnClickListener(view -> {  // Navigate to the settings activity
            Intent intent = new Intent(activity, SettingsController.class);
            intent.putExtra("handler", handler);
            startActivity(intent);
        });
    }
}