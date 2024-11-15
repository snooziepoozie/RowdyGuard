package edu.utsa.cs3443.rowdyguard.model.db;

import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.deriveKeyFromPassword;
import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.encryptData;
import static edu.utsa.cs3443.rowdyguard.model.db.Encryption.generateSalt;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.SecretKey;

import edu.utsa.cs3443.rowdyguard.model.Password;

public class Handler {
    private Context context;
    private File dbConnector;
    private SecretKey key;
    private ArrayList<Password> passwords;

    public Handler(String password, Context context) throws Exception {
        this.context = context;
        this.key = deriveKeyFromPassword(password, new byte[16]);
        this.passwords = new ArrayList<>();
        this.dbConnector = new File(
                context.getFilesDir(),
                "database.db"
        );

        this.createDatabaseIfNotExists();
        this.loadDatabase();
    }
    private void createDatabaseIfNotExists() throws IOException {
        File file = new File(context.getFilesDir(), "database.db");
        if (file.createNewFile())
            System.out.println("Database didn't exist - created!");
        else
            System.out.println("Database already exists!");
    }
    private void loadDatabase() throws FileNotFoundException {
        // TODO: decrypt the passwords...
        System.out.println("Loading database...");
        Scanner scanner = new Scanner(this.dbConnector);
        while (scanner.hasNextLine()) {
            String[] arr = scanner.nextLine().split(",");
            this.passwords.add(new Password(arr[0], arr[1], arr[2]));
            System.out.println(arr[0] + "," + arr[1] + "," + arr[2]);
            System.out.println("Loaded password with title: " + arr[0]);
        }
        scanner.close();
        System.out.println("Finished loading database!");
    }
    public ArrayList<Password> getPasswords() {
        return this.passwords;
    }
    public void addPassword(String title, String username, String password) throws Exception {
        this.passwords.add(new Password(title, username, password));
        Writer output = new BufferedWriter(
                new FileWriter(
                        this.dbConnector,
                        true
                )
        );
        output.append(
                title + "," + username + "," + Arrays.toString(encryptData(password, this.key)) + "\n"
        );
        output.close();
        System.out.println("Wrote password with title \"" + title + "\"to database!");
    }
}
