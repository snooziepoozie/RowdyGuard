ackage edu.utsa.cs3443.rowdyguard.model.db;

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
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        this.key = deriveKeyFromPassword(password, new byte[12]);
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
        if (file.createNewFile()) {
            System.out.println("Database didn't exist - created!");
        } else {
            System.out.println("Database already exists!");
        }
    }
    private void loadDatabase() throws Exception {
        System.out.println("Loading database...");
        Scanner scanner = new Scanner(this.dbConnector);
        while (scanner.hasNextLine()) {
            String[] arr = scanner.nextLine().split(",", 3);
            try {
                String[] parts = arr[2].replace("[", "").replace("]", "").trim().split(",\\s*");
                List<Byte> byteList = new ArrayList<>();
                for (String part : parts) {
                    byteList.add(Byte.parseByte(part));
                }
                byte[] cipherText = new byte[byteList.size()];
                for (int i = 0; i < byteList.size(); i++) {
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
    public boolean removePassword(String title) throws Exception {
        File tempFile = new File(context.getFilesDir(),"database.db.tmp");
        tempFile.createNewFile();

        BufferedReader reader = new BufferedReader(new FileReader(this.dbConnector));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.startsWith(title)) continue;
            writer.write(currentLine + System.lineSeparator());
        }
        writer.close();
        reader.close();

        if (tempFile.renameTo(this.dbConnector)){
            for (Password p: this.passwords) {
                if (p.getTitle().equals(title)) {
                    this.passwords.remove(p);
                    System.out.println("Removed " + title + " from database!");
                    return true;
                }
            }
        }
        return false;
    }
}