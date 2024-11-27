package edu.utsa.cs3443.rowdyguard.controllers;

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

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;


public class PasswordChangeActivity extends AppCompatActivity {

    private PasswordChangeController passwordChangeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.password_change_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //deprecated?
        //passwordChangeController = new PasswordChangeController(this);

        Intent intent = PasswordChangeActivity.this.getIntent();
        Handler myPasswordHandler = (Handler) intent.getSerializableExtra("handler");
        String passwordTitle = intent.getStringExtra("passwordTitle");

        TextView newUserField = PasswordChangeActivity.this.findViewById(R.id.enterNewUser);
        TextView newPassField = PasswordChangeActivity.this.findViewById(R.id.enterNewPass);
        TextView newTitleField = PasswordChangeActivity.this.findViewById(R.id.enterNewTitle);

        Button submitButton = PasswordChangeActivity.this.findViewById(R.id.passwordSubmitChangeBtn);

        submitButton.setOnClickListener(view -> {
            try {
                String newUser = newUserField.getText().toString().trim();
                String newPass = newPassField.getText().toString().trim();
                String newTitle = newTitleField.getText().toString().trim();

                // assume handler is not null?
                assert myPasswordHandler != null;
                myPasswordHandler.editPassword(passwordTitle, newTitle, newUser, newPass);

                Intent i = new Intent(PasswordChangeActivity.this, PasswordViewActivity.class);
                i.putExtra("handler", myPasswordHandler);
                i.putExtra("title", newTitle.isEmpty() ? passwordTitle : newTitle); // Use new title if changed
                PasswordChangeActivity.this.startActivity(i);

            } catch (Exception e) {
                Toast.makeText(PasswordChangeActivity.this, "ERROR: Failed to update password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}