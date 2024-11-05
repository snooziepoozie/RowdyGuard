package edu.utsa.cs3443.rowdyguard.model.db;

import android.content.res.AssetManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import edu.utsa.cs3443.rowdyguard.controllers.MainActivity;

public class Handler {
    final private String database_name = "database.json";
    private JSONObject database;
    private AssetManager assetManager;

    public Handler(MainActivity a) {
        this.assetManager = a.getAssets();
    }
    public void loadDB() {
        Scanner scanner = null;
        try {
            InputStream file = this.assetManager.open(this.database_name);
            scanner = new Scanner(file);
        } catch (IOException ignored) {}
    }
}
