package edu.utsa.cs3443.rowdyguard.model.db;

import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.decryptData;
import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.deriveKeyFromPassword;
import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.encryptData;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.crypto.SecretKey;

import edu.utsa.cs3443.rowdyguard.model.Password;

/**
 * The Handler class represents an object that serves as an interface with the password database
 *
 * @author Jonathan Beierle
 */

public class Handler implements Serializable {
    private transient Context context;
    private File dbConnector;
    private transient SecretKey key;
    private String password;
    private ArrayList<Password> passwords;

    /**
     * Constructor to instantiate a new Handler object
     * @param password Password to try decrypting the database with
     * @param context The Android context object that allows for access to the database
     * @throws Exception Exception representing an error with database access/decryption
     */
    public Handler(String password, Context context) throws Exception {
        this.context = context;
        this.password = password;
        this.key = deriveKeyFromPassword(password, new byte[12]);
        this.passwords = new ArrayList<>();
        this.dbConnector = new File(
                context.getFilesDir(),
                "database.db"
        );

        this.createDatabaseIfNotExists();

        this.loadDatabase();
    }

    /**
     * Returns the database password to allow for inter-activity modification
     * @return String representation of the vault password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Generates a secret key based off of a given password and assigns it to the handler
     * @param password Plaintext password to derive the key from
     * @throws Exception Exception occurs if the key derivation process isn't successful
     */
    public void genKey(String password) throws Exception {
        this.key = deriveKeyFromPassword(password, new byte[12]);
    }

    /**
     * Creates the database if it doesn't already exist
     * @throws IOException Exception indicating an error in creating the database
     */
    private void createDatabaseIfNotExists() throws IOException {
        File file = new File(context.getFilesDir(), "database.db");
        if (file.createNewFile()) {
            System.out.println("Database didn't exist - created!");
        } else {
            System.out.println("Database already exists!");
        }
    }

    /**
     * Loads the database into the Handler object. Reads and decrypts database
     * @throws Exception Exception occurring if the database cannot be loaded properly
     */
    private void loadDatabase() throws Exception {
        System.out.println("Loading database...");
        Scanner scanner = new Scanner(this.dbConnector);
        while (scanner.hasNextLine()) {
            String[] arr = scanner.nextLine().split(",", 3);  // split line by commas
            try {
                String[] parts = arr[2].replace("[", "").replace("]", "").trim().split(",\\s*");  // parse ciphertext
                List<Byte> byteList = new ArrayList<>();
                for (String part : parts) {  // build byte array
                    byteList.add(Byte.parseByte(part));
                }
                byte[] cipherText = new byte[byteList.size()];
                for (int i = 0; i < byteList.size(); i++) {  // get cipher text from byte array
                    cipherText[i] = byteList.get(i);
                }

                this.passwords.add(new Password(arr[0], arr[1], decryptData(cipherText, this.key)));
            } catch (javax.crypto.AEADBadTagException e) {
                System.out.println("Failed to decrypt: " + arr[0]);
            }
            System.out.println(arr[0] + "," + arr[1] + "," + this.passwords.get(this.passwords.size() - 1).getPassword());
            System.out.println("Loaded password with title: " + arr[0]);
        }
        scanner.close();
        System.out.println("Finished loading database!");
    }

    /**
     * Method for returning all the decrypted database passwords in one arraylist
     * @return arraylist representing all passwords from the database
     */
    public ArrayList<Password> getPasswords() {
        return this.passwords;
    }

    /**
     * Adds a password to the database
     * @param title The password object's title
     * @param username The password object's username
     * @param password The password object's actual password
     * @throws Exception Occurs if the password cannot be added to the database.=
     */
    public void addPassword(String title, String username, String password, Context context) throws Exception {
        this.context = context;
        this.passwords.add(new Password(title, username, password));
        Writer output = new BufferedWriter(
                new FileWriter(
                        this.dbConnector,
                        true
                )
        );
        output.append(
                title + "," + username + "," + Arrays.toString(encryptData(password, this.key)) + "\n"  // write data to database
        );
        output.close();

        System.out.println("Wrote password with title \"" + title + "\" to database!");
    }

    /**
     * Removes a password from the database
     * @param title The title of the password to remove
     * @throws Exception Occurs if something went wrong while attempting to remove the password from the database
     */
    public void removePassword(String title, Context context) throws Exception {
        this.context = context;
        File tempFile = new File(context.getFilesDir(),"database.db.tmp");  // create temp database
        tempFile.createNewFile();

        BufferedReader reader = new BufferedReader(new FileReader(this.dbConnector));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.startsWith(title)) continue;  // skip the password to be deleted
            writer.write(currentLine + System.lineSeparator());  // write password to temp database
        }
        writer.close();
        reader.close();

        if (tempFile.renameTo(this.dbConnector)) {  // convert temp database to actual database
            for (Password p: this.passwords) {
                System.out.println(title + p.getTitle());
                if (p.getTitle().equals(title)) {  // if database was successfully renamed, remove old password
                    this.passwords.remove(p);
                    System.out.println("Removed '" + title + "' from database!");
                    return;
                }
            }
        }
    }

    /**
     * Edits a password in the database. For params that begin with new, pass an empty string to not update the values
     * @param oldTitle The old title of the password - used to find the password to edit
     * @param newTitle The new title of the password - this is used to update the old title if necessary
     * @param newUserName The new username of the password
     * @param newPassword The new password of the password object
     * @param context Context for editing the password
     * @throws Exception Error if one of the password edit operations failed
     */
    public void editPassword(String oldTitle, String newTitle, String newUserName, String newPassword, Context context) throws Exception {
        this.context = context;
        for (Password p : this.passwords) {
            if (p.getTitle().equals(oldTitle)) {  // found password with correct title to edit
                this.removePassword(oldTitle, context);
                this.addPassword(
                    !newTitle.isEmpty() ? newTitle : p.getTitle(),  // change title if necessary
                    !newUserName.isEmpty() ? newUserName : p.getUsername(),  // change username if necessary
                    !newPassword.isEmpty() ? newPassword : p.getPassword(),  // change password if necessary
                    context
                );
                return;
            }
        }
    }

    /**
     * Method to change the encryption password of the database
     * @param newPassword The new password to encrypt all passwords with
     * @throws Exception Error if the database's encrypted key couldn't be changed
     */
    public void changeVaultPassword(String newPassword, Context context) throws Exception {
        Context oldContext = this.context;
        SecretKey oldKey = this.key;
        ArrayList<Password> passwordBackup = new ArrayList<>(this.getPasswords());

        this.context = context;
        this.key = deriveKeyFromPassword(newPassword, new byte[12]);

        try {
            for (Password p : passwordBackup) {  // remove all passwords from database
                this.removePassword(p.getTitle(), context);
            }
            System.out.println("Removed passwords!");
            for (Password p : passwordBackup) {  // add password back to database with new encryption
                this.addPassword(p.getTitle(), p.getUsername(), p.getPassword(), context);
            }
        } catch (Exception ignored) {
            this.context = oldContext;
            this.key = oldKey;
            System.out.println("FAILED TO CHANGED VAULT PASSWORD");
            System.out.println(ignored);
            throw ignored;
        }
        this.password = newPassword;
        System.out.println("CHANGED VAULT PASSWORD");
    }
}