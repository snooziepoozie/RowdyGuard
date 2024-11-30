package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;


public class PasswordChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.password_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = this.getIntent();
        Handler handler = (Handler) intent.getSerializableExtra("handler");
        try {
            assert handler != null;
            handler.genKey(handler.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String passwordTitle = intent.getStringExtra("passwordTitle");

        TextView newUserField = this.findViewById(R.id.enterNewUser);
        TextView newPassField = this.findViewById(R.id.enterNewPass);
        TextView newTitleField = this.findViewById(R.id.enterNewTitle);

        Button submitButton = this.findViewById(R.id.passwordSubmitChangeBtn);

        submitButton.setOnClickListener(view -> {
            String newTitle = newTitleField.getText().toString().trim();
            String newUser = newUserField.getText().toString().trim();
            String newPass = newPassField.getText().toString().trim();

            try {
                handler.editPassword(passwordTitle, newTitle, newUser, newPass, (Context) this);
            } catch (Exception e) {
                Toast.makeText(this, "ERROR: Failed to update password!", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }

            Intent i = new Intent(this, PasswordView.class);
            i.putExtra("handler", (Serializable) handler);
            i.putExtra("title", newTitle.isEmpty() ? passwordTitle : newTitle);
            this.startActivity(i);
        });
    }
}