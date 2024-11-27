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
import edu.utsa.cs3443.rowdyguard.model.Password;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;


public class PasswordViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.password_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Retrieve Handler from the intent
        Intent intent = PasswordViewActivity.this.getIntent();
        Handler myPasswordHandler = (Handler) intent.getSerializableExtra("handler");

        Button passUser = PasswordViewActivity.this.findViewById(R.id.usernameBtn);
        Button passPass = PasswordViewActivity.this.findViewById(R.id.passwordBtn);
        TextView passTitle = PasswordViewActivity.this.findViewById(R.id.PasswordTitle);

        Button passSettings = PasswordViewActivity.this.findViewById(R.id.passwordSettingBtn);
        Button passDelete = PasswordViewActivity.this.findViewById(R.id.passwordDeleteBtn);

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

        // change password button
        // finalMyPassword prevents errors(?)
        Password finalMyPassword = myPassword;
        passSettings.setOnClickListener(view -> {
            Intent i = new Intent(PasswordViewActivity.this, PasswordChangeActivity.class);
            i.putExtra("handler", myPasswordHandler); // Pass the handler
            i.putExtra("passwordTitle", finalMyPassword.getTitle()); // Pass the password title
            PasswordViewActivity.this.startActivity(i);
        });

        // delete password button
        passDelete.setOnClickListener(view -> {
            try {
                myPasswordHandler.removePassword(titleToLookFor);
                // make a toast?
                Toast.makeText(PasswordViewActivity.this, "Removed the password " + titleToLookFor, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                System.out.println("ERROR: Unable to remove the password " + titleToLookFor);
                // make a toast?
                Toast.makeText(PasswordViewActivity.this, "Unable to remove password, please try again.", Toast.LENGTH_SHORT).show();

                throw new RuntimeException(e);
            }
        });
    }
}