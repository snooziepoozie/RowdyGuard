package edu.utsa.cs3443.rowdyguard.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.rowdyguard.R;
import edu.utsa.cs3443.rowdyguard.model.db.Handler;


public class MainActivity extends AppCompatActivity {
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
        TextView txt = findViewById(R.id.multiLine);
        final int[] i = {0};

        Handler handler = new Handler("P@ssw0rd", this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    handler.addVault("test" + i[0]);
                    i[0]++;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                StringBuilder o = new StringBuilder();
                for (String vault : handler.getVaultNames()) {
                    System.out.println(vault);
                    o.append(vault).append("\n");
                }
                txt.setText(o);
            }
        });
    }
}