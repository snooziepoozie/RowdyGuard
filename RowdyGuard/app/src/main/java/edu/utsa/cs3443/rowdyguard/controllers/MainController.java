package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.Vault;

public class MainController {

    private final MainActivity activity;
    private static final String password = "password";

    public MainController(MainActivity activity) {
        this.activity = activity;
        TextView editPassword = activity.findViewById(R.id.editPassword);
        Button login = activity.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editPassword.getText().toString();
                if (input.equals(password)) {
                    Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, Vault.class);
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}