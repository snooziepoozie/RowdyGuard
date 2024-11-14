package edu.utsa.cs3443.rowdyguard.model.db;

import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.encryptAndWriteToFile;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import edu.utsa.cs3443.rowdyguard.model.Vault;
import edu.utsa.cs3443.rowdyguard.model.Password;

public class Handler {
    private Context context;
    private ArrayList<Vault> vaults;
    private String key;

    public Handler(String key, Context context) {
        this.context = context;
        this.vaults = new ArrayList<>();
        this.key = key;

        this.loadVaults();
    }
    private void loadVaults() {
        File directory = context.getFilesDir();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".vault")) {
                    this.vaults.add(new Vault(file.getName()));
                }
            }
        }
    }
    public ArrayList<String> getVaultNames() {
        ArrayList<String> out = new ArrayList<>();
        for (Vault v : this.vaults) {
            out.add(v.getName());
        }
        return out;
    }
    public Vault addVault(String vaultFile) {
        encryptAndWriteToFile("", this.key, vaultFile, this.context);
        return new Vault("");
    }
}
