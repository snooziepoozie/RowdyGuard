/**
 * <h1>Viewing (Individual) Passwords</h1>
 * The purpose of PasswordView.java is to enable users to view their (decrypted)
 * password data (title, password, and username).
 * Users are also able to change their password's data and delete them.
 *
 * @author  Danny Nguyen
 * @since   11-04-2024
 */

package edu.utsa.cs3443.rowdyguard.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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


public class PasswordView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * This method is used to initialize the Android app.
         * There should be little interactivity with this view in general,
         * but users can either alter their passwords (by clicking on the "settings" button,
         * which brings them to the view, password_change_view), or delete them with the
         * "delete" button.
         * The exact details on how passwords are found in the model directory through
         * Password.java, Handler.java, etc.
         *
         * @param1  savedInstanceState  Helps initialize the activity for android.
         * @return  void
         */

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.password_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.PasswordList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Retrieve Handler from the intent
        Intent intent = this.getIntent();
        Handler handler = (Handler) intent.getSerializableExtra("handler");
        try {
            assert handler != null;
            handler.genKey(handler.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Button passUser = this.findViewById(R.id.usernameBtn);
        Button passPass = this.findViewById(R.id.passwordBtn);
        TextView passTitle = this.findViewById(R.id.PasswordTitle);

        Button overView = this.findViewById(R.id.overviewButton);
        ImageButton passSettings = this.findViewById(R.id.passwordSettingBtn);
        ImageButton passDelete = this.findViewById(R.id.passwordDeleteBtn);

        // Get data
        // Get titleToLookFor from intent from vault view
        Password myPassword = null;
        String titleToLookFor = intent.getStringExtra("title");

        for (Password curPassword : handler.getPasswords()){
            //if title in arraylist = titleToLookFor, get data from it and exit.

            if (curPassword.getTitle().equals(titleToLookFor)) {
                myPassword = curPassword;
            }
        }

        assert myPassword != null;
        passTitle.setText(myPassword.getTitle());
        passUser.setText(myPassword.getUsername());
        passPass.setText(myPassword.getPassword());

        // change password button
        // finalMyPassword prevents errors(?)
        Password finalMyPassword = myPassword;
        passSettings.setOnClickListener(view -> {
            Intent i = new Intent(this, PasswordChange.class);
            i.putExtra("handler", handler); // Pass the handler
            i.putExtra("passwordTitle", finalMyPassword.getTitle()); // Pass the password title
            this.startActivity(i);
        });

        // delete password button
        passDelete.setOnClickListener(view -> {
            try {
                handler.removePassword(titleToLookFor, (Context) this);
                Toast.makeText(this, "Removed the password " + titleToLookFor, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, VaultOverview.class);
                i.putExtra("handler", handler); // Pass the handler
                this.startActivity(i);
            } catch (Exception e) {
                System.out.println("ERROR: Unable to remove the password " + titleToLookFor);
                Toast.makeText(this, "Unable to remove password, please try again.", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        });

        overView.setOnClickListener(view -> {
            Intent i = new Intent(this, VaultOverview.class);
            i.putExtra("handler", handler);
            this.startActivity(i);
        });
    }
}