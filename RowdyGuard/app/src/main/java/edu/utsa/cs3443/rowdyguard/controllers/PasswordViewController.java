package edu.utsa.cs3443.rowdyguard.controllers;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;
import edu.utsa.cs3443.rowdyguard.model.Password;

public class PasswordViewController {

    private final PasswordViewActivity passwordViewActivity;


    public PasswordViewController(PasswordViewActivity myPasswordViewActivity) {

        this.passwordViewActivity = myPasswordViewActivity;

        // Retrieve Handler from the intent
        Intent intent = passwordViewActivity.getIntent();
        Handler myPasswordHandler = (Handler) intent.getSerializableExtra("handler");



        Button passUser = passwordViewActivity.findViewById(R.id.usernameBtn);
        Button passPass = passwordViewActivity.findViewById(R.id.passwordBtn);
        TextView passTitle = passwordViewActivity.findViewById(R.id.PasswordTitle);

        Button passSettings = passwordViewActivity.findViewById(R.id.passwordSettingBtn);

        // Get data
        // Get titleToLookFor from intent from vault view
        Password myPassword = null;
        String titleToLookFor = intent.getStringExtra("title");

        for (Password curPassword : myPasswordHandler.getPasswords()){
            //if title in arraylist = titleToLookFor, get data from it and exit.

            if (curPassword.getTitle().equals(titleToLookFor)) {
                myPassword = curPassword;
            }
        }

        passUser.setText(myPassword.getUsername());
        passPass.setText(myPassword.getPassword());
        passTitle.setText(myPassword.getTitle());

        // finalMyPassword prevents errors(?)
        Password finalMyPassword = myPassword;
        passSettings.setOnClickListener(view -> {
            Intent i = new Intent(passwordViewActivity, PasswordChangeActivity.class);
            i.putExtra("handler", myPasswordHandler); // Pass the handler
            i.putExtra("passwordTitle", finalMyPassword.getTitle()); // Pass the password title
            passwordViewActivity.startActivity(i);
        });


    }

    @Override
    public void onClick(View v) {

        //Deprecated?
        /* FIX ME
        // Lets you copy & paste stuff on buttons
        ClipboardManager myClipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        Button selectedButton = (Button) v;

        if (v.getId() == R.id.usernameBtn) {
            String myItem = selectedButton.getText().toString().trim();
            if (!myItem.isEmpty()) {
                ClipData clip = ClipData.newPlainText("Username", myItem);
                myClipBoard.setPrimaryClip(clip);

                Toast.makeText(passwordViewActivity, "Saved to clip board!", Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.passwordBtn) {
            String myItem = selectedButton.getText().toString().trim();
            if (!myItem.isEmpty()) {
                ClipData clip = ClipData.newPlainText("Password", myItem);
                myClipBoard.setPrimaryClip(clip);

                Toast.makeText(passwordViewActivity, "Saved to clip board!", Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.urlBtn) {
            String myItem = selectedButton.getText().toString().trim();
            if (!myItem.isEmpty()) {
                ClipData clip = ClipData.newPlainText("URL", myItem);
                myClipBoard.setPrimaryClip(clip);

                Toast.makeText(passwordViewActivity, "Saved to clip board!", Toast.LENGTH_SHORT).show();
            }
        }
        */

        //Do password setting activity
        /*
        if (v.getId() == R.id.passwordSettingBtn) {
            Intent i = new Intent(passwordViewActivity, PasswordChangeActivity.class);

            // get current ids(?) of vault items
            i.putExtra("thisPassword", myPassword);

            passwordViewActivity.startActivity(i);
        }
         */

    }
}

