package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.Password;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;

public class MainController implements Serializable {

    private final MainActivity activity;

    public MainController(MainActivity activity) {

        this.activity = activity;

        TextView editPassword = activity.findViewById(R.id.editPassword);
        Button login = activity.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = editPassword.getText().toString();

                try {
                    Handler handler = new Handler(input, activity);
                    ArrayList<Password> passwords = handler.getPasswords();
                    Toast.makeText(activity, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, PasswordOverview.class);
                    intent.putExtra("passwords", passwords);
                    activity.startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
