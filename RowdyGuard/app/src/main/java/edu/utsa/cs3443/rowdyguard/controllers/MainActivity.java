package edu.utsa.cs3443.rowdyguard.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;


public class MainActivity extends AppCompatActivity {
    private Activity activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView editPassword = this.findViewById(R.id.editPassword);
        Button login = this.findViewById(R.id.login);
        this.activity = this;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = editPassword.getText().toString();

                try {
                    System.out.println("Trying password " + input);
                    Handler handler = new Handler(input, activity);
                    // ArrayList<Password> passwords = handler.getPasswords();
                    Toast.makeText(activity, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, VaultOverview.class);
                    intent.putExtra("handler", handler);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(activity, "Login Failed", Toast.LENGTH_SHORT).show();
                    System.out.println("Failed to load database!");
                    try {
                        throw e;
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}