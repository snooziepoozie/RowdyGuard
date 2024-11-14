package edu.utsa.cs3443.rowdyguard.model;

import java.util.ArrayList;

public class Vault {
    private String vaultFile;
    private ArrayList<Password> passwords;

    public Vault(String vaultFile) {
        this.vaultFile = vaultFile;
        this.passwords = new ArrayList<Password>();
    }
}
