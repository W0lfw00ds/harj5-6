package com.example.iirol.harjoitus5_6.Class.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.iirol.harjoitus5_6.Class.Database.Repositories.KirjaRepository;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    // Fields
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database";

    private ArrayList<Repository> repositories;
    private KirjaRepository kirjaRepository;

    // Singleton
    private static Database database = null;
    public static void setDatabase(Context context) {
        if (Database.database != null) {
            return;
        }
        Database.database = new Database(context);
    }
    public static Database getDatabase() {
        return Database.database;
    }

    // Constructor
    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.repositories = new ArrayList<>();

        // Kirjarepository
        this.kirjaRepository = new KirjaRepository(this);
        this.repositories.add(this.kirjaRepository);

    }

    // @SQLiteOpenHelper
    @Override public void onCreate(SQLiteDatabase db) {

        // Luo jokaisen repositorien taulut
        for (Repository repository : this.repositories) {
            repository.createTableIfNotExists();
        }

    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Poista kaikki repositorien taulut
        for (Repository repository : this.repositories) {
            repository.deleteTableIfExists();
        }

        // Luo taulut sitten uudelleen
        this.onCreate(db);
    }

    // Methods
    public void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName + ";");
        db.close();
    }
    public void clearTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE * FROM " + tableName + ";");
        db.close();
    }
    public KirjaRepository getKirjaRepository() {
        return this.kirjaRepository;
    }

}