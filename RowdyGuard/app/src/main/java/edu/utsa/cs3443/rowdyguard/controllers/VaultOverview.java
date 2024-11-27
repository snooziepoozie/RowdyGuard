package edu.utsa.cs3443.rowdyguard.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.app.AlertDialog;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;



public class VaultOverview extends AppCompatActivity {
    private Handler handler;
    private LinearLayoutContainer layout;
    private Context context;
    private EditText titleInput, usernameInput, passwordInput;


    private void confirmationDialog(Button btn){ // dialog for confirming password deletion
        new AlertDialog.Builder(this)
                .setTitle("Delete Button")
                .setMessage("Are you sure you want to delete this password?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    linearLayout.removeView(btn);
                    Toast.makeText(this, "Password deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
        try{
            removePassword(getTitle());
        } catch (java.lang.RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPass(){
        String title = titleInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        if (title.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter a value in every field", Toast.LENGTH_SHORT).show();
            return;
        }

        Button button = new Button(this);
        button.setText(title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        button.setLayoutParams(params);

        button.setOnClickListener(v -> {
            @Override
            Intent intent = new Intent(VaultOverview.this, PasswordView.class);
            intent.putExtra("title", title);
            startActivity(intent);
        });
        button.setOnLongClickListener(v -> {
            confirmationDialog(button);
            return true;

        });
        layout.addView(button);

        titleInput.setText("");
        usernameInput.setText("");
        passwordInput.setText("");

        addPassword(title, username, password);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.vault_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Activity activity = this;
        Context context = this;
        linearLayout = findViewById(R.id.myLinearLayout);
        titleInput = findViewById(R.id.titleInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button addButton = findViewById(R.id.addButton);
        Button removeButton = findViewById(R.id.removeButton);

        try {
            this.handler = new Handler("P@ssw0rd", this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final int[] i = {0};

        LinearLayout layout = new LinearLayout(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPass();
                try {
                    handler.addPassword("Password" + i[0], "username", "P@ssw0rd");
                    Button addButton = new Button(context);
                    layout.addView(addButton);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                i[0]++;
            }
        });

       /* removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    handler.removePassword(handler.getPasswords().get(handler.getPasswords().size() - 1).getTitle());
                } catch (java.lang.IndexOutOfBoundsException e) {
                    System.out.println("Cannot find password to delete!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        */
// pass title as string, use getpasswords and iterate through to get each title and add to a list
        ArrayList<String> allTitles = new ArrayList<String>();
        ArrayList<Password> allPasswords = getPasswords(); // not sure if initalized correctly

        for (Password p : allPasswords){
            allTitles.add(getTitle()); // iterate through each password and get the title and add to alLTitles list
        }


        for (String title : allTitles) {
            Button button = new Button(this);
            button.setText(title);
            button.setBackgroundColor(Color.parseColor"#2A42A0"));
            button.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                @Override
                Intent intent = new Intent(VaultOverview.this, PasswordView.class);
                intent.putExtra("title", title);
                startActivity(intent);
            });
            button.setOnLongClickListener(v -> {
                confirmationDialog(button);
                return true;

            });

            layout.addView(button);
        }
        setContentView(layout);






    }
}