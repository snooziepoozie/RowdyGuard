package edu.utsa.cs3443.rowdyguard.model.db;

import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.deriveKeyFromPassword;
import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.encryptAndWriteToFile;
import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.generateSalt;

import android.content.Context;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import edu.utsa.cs3443.rowdyguard.model.Vault;

public class Handler {
    private Context context;
    public ArrayList<Vault> vaults;
    private String password;

    public Handler(String password, Context context) {
        this.context = context;
        this.vaults = new ArrayList<>();
        this.password = password;

        this.loadVaults();
    }
    private void loadVaults() {
        File directory = this.context.getFilesDir();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".vault")) {
                    this.vaults.add(new Vault(file.getName()));
                }
            }
        }
    }
    public Vault findVaultByName(String search) {
        for (Vault v : this.vaults) {
            if (v.getName().equals(search))
                return v;
        }
        return null;
    }
    public ArrayList<String> getVaultNames() {
        ArrayList<String> out = new ArrayList<>();
        for (Vault v : this.vaults) {
            out.add(v.getName());
        }
        return out;
    }
    public void addVault(String vaultFile) throws Exception {
        encryptAndWriteToFile("eee", deriveKeyFromPassword(this.password, generateSalt()), vaultFile, this.context);
        this.vaults.add(new Vault(vaultFile));
    }
}
