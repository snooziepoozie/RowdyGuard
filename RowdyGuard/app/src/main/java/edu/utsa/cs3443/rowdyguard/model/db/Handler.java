package edu.utsa.cs3443.rowdyguard.model.db;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

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
        String [] list;
        try {
            list = this.context.getAssets().list("");
            if (list.length > 0) {
                for (String file : list) {
                    System.out.println("Found vault file: " + file);
                    this.vaults.add(new Vault(file));
                }
            }
        } catch (IOException e) {
            System.out.println("Handler Error: Could not read vaults correctly!");
            return;
        }
    }
    public Vault addVault(String vaultFile) {
        return new Vault("");
    }
}
