package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Intent;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.utsa.cs3443.rowdyguard.model.db.Handler;

import org.w3c.dom.Text;
import edu.utsa.cs3443.rowdyguard.model.Password;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;

import edu.utsa.cs3443.rowdyguard.R;

public class PasswordChangeController {

    private final PasswordChangeActivity passwordChangeActivity;


    public PasswordChangeController(PasswordChangeActivity myPasswordChangeActivity) {

        this.passwordChangeActivity = myPasswordChangeActivity;

        Intent intent = passwordChangeActivity.getIntent();
        Handler myPasswordHandler = (Handler) intent.getSerializableExtra("handler");
        String passwordTitle = intent.getStringExtra("passwordTitle");

        TextView newUserField = passwordChangeActivity.findViewById(R.id.enterNewUser);
        TextView newPassField = passwordChangeActivity.findViewById(R.id.enterNewPass);
        TextView newTitleField = passwordChangeActivity.findViewById(R.id.enterNewTitle);

        Button submitButton = passwordChangeActivity.findViewById(R.id.passwordSubmitChangeBtn);

        submitButton.setOnClickListener(view -> {
            try {
                String newUser = newUserField.getText().toString().trim();
                String newPass = newPassField.getText().toString().trim();
                String newTitle = newTitleField.getText().toString().trim();

                // assume handler is not null?
                assert myPasswordHandler != null;
                myPasswordHandler.editPassword(passwordTitle, newTitle, newUser, newPass);

                Intent i = new Intent(passwordChangeActivity, PasswordViewActivity.class);
                i.putExtra("handler", myPasswordHandler);
                i.putExtra("title", newTitle.isEmpty() ? passwordTitle : newTitle); // Use new title if changed
                passwordChangeActivity.startActivity(i);

            } catch (Exception e) {
                Toast.makeText(passwordChangeActivity, "ERROR: Failed to update password!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {

        // deprecated?
        /*
        TextView newUserLabel = (TextView) v;

        String myNewUser;
        String myNewPass;
        String myNewTitle;



        if (v.getId() == R.id.passwordSubmitChangeBtn) {
            TextView usernameField = passwordChangeActivity.findViewById(R.id.enterNewUser);
            TextView passwordField = passwordChangeActivity.findViewById(R.id.enterNewPass);
            TextView titleField = passwordChangeActivity.findViewById(R.id.enterNewTitle);

            myNewUser = usernameField.getText().toString().trim();
            myNewPass = passwordField.getText().toString().trim();
            myNewTitle = titleField.getText().toString().trim();

            try {
                Handler handler = HandlerSingleton.getInstance();
                handler.editPassword(thisPassword.getTitle(), myNewTitle, myNewUser, myNewPass);

                Toast.makeText(passwordChangeActivity, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(passwordChangeActivity, PasswordViewActivity.class);
                passwordChangeActivity.startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(passwordChangeActivity, "Failed to update password!", Toast.LENGTH_SHORT).show();
            }
        }

        */


    }
}
