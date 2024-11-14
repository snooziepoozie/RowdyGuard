package edu.utsa.cs3443.rowdyguard.model.db;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3443.rowdyguard.model.Vault;
import edu.utsa.cs3443.rowdyguard.model.Password;

public class Handler {
    private Context context;
    private ArrayList<Vault> vaults;

    public Handler(String key, Context context) {
        this.context = context;
        this.vaults = new ArrayList<Vault>();

        this.loadVaults();
    }
    private void loadVaults() {
        File directory = context.getFilesDir();
        File[] files = directory.listFiles();

        // Check if the directory is empty
        if (files != null) {
            for (File file : files) {
                // Add files with the specified extension (e.g., ".enc") to the list
                if (file.isFile() && file.getName().endsWith(".vault")) {
                    encryptedFiles.add(file);
                }
            }
        }
    }
    public Vault addVault(String vaultFile) {
        return new Vault("");
    }
}
