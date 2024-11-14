package edu.utsa.cs3443.rowdyguard.model;

import java.util.ArrayList;

public class Vault {
    private String vaultFile;
    private ArrayList<Password> passwords = new ArrayList<Password>();

    public Vault(String vaultFile) {
        this.vaultFile = vaultFile;
    }
    public String getName() {
        return this.vaultFile;
    }
    public ArrayList<String> dumpPasswords() {
        ArrayList<String> out = new ArrayList<>();
        for (Password p : this.passwords) {
            out.add(p.getPassword());
        }
        return out;
    }
}
