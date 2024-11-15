package edu.utsa.cs3443.rowdyguard.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;


public class MainActivity extends AppCompatActivity {
    private Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btn = findViewById(R.id.addButton);
        try {
            this.handler = new Handler("P@ssw0rd", this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final int[] i = {0};

        LinearLayout layout = findViewById(R.id.linearLayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    handler.addPassword("Password" + i[0], "username", "P@ssw0rd");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                i[0]++;
            }
        });
    }
//    private void addVaultButton(LinearLayout layout, String text) {
//        Button button = new Button(this);
//        button.setText(text);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        layout.addView(button);
//    }
}