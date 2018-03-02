package com.example.iirol.harjoitus5_6.Class.Database.Repositories.Kirja;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.iirol.harjoitus5_6.Class.Database.Database;
import com.example.iirol.harjoitus5_6.Class.Database.Repositories.Repository;

import java.util.ArrayList;

public class KirjaRepository implements Repository<Kirja> {

    // FIELDS STATIC
    public static final String TABLENAME = "Kirja";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMERO = "numero";
    public static final String COLUMN_NIMI = "nimi";
    public static final String COLUMN_PAINOS = "painos";
    public static final String COLUMN_HANKINTAPVM = "hankintapvm";

    // FIELDS
    private Database database;

    // CONSTRUCTOR
    public KirjaRepository(Database databaseHelper) {
        this.database = databaseHelper;
    }

    // @Repository
    @Override public String getCreateTableIfNotExistsSQL() {

        return "CREATE TABLE IF NOT EXISTS " + KirjaRepository.TABLENAME + " (" +
		        "  " + COLUMN_ID + " INTEGER PRIMARY KEY," +
		        "  " + COLUMN_NUMERO + " INTEGER NOT NULL," +
		        "  " + COLUMN_NIMI + " TEXT NOT NULL," +
		        "  " + COLUMN_PAINOS + " INTEGER NOT NULL," +
		        "  " + COLUMN_HANKINTAPVM + " INTEGER NOT NULL UNIQUE" +
		        ");";
    }
    @Override public void deleteTableIfExists() {
        this.database.deleteTable(this.getTableName());
    }
    @Override public String getTableName() {
        return KirjaRepository.TABLENAME;
    }
    @Override public void clearTable() {
        this.database.clearTable(KirjaRepository.TABLENAME);
    }
    @Override public void add(Kirja kirja) {
        SQLiteDatabase db = this.database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KirjaRepository.COLUMN_NUMERO, kirja.getNumero());
        values.put(KirjaRepository.COLUMN_NIMI, kirja.getNimi());
        values.put(KirjaRepository.COLUMN_PAINOS, kirja.getPainos());
        values.put(KirjaRepository.COLUMN_HANKINTAPVM, kirja.getHankintapvmUnixTime());

        db.insert(
            KirjaRepository.TABLENAME,
            null,
            values
        );

        db.close();
    }
    @Override public ArrayList<Kirja> getAll() {
        SQLiteDatabase db = this.database.getReadableDatabase();
        ArrayList<Kirja> kirjat = new ArrayList<>();

        Cursor cursor = db.query(
            KirjaRepository.TABLENAME,
            new String[] {
                KirjaRepository.COLUMN_ID,
                KirjaRepository.COLUMN_NUMERO,
                KirjaRepository.COLUMN_NIMI,
                KirjaRepository.COLUMN_PAINOS,
                KirjaRepository.COLUMN_HANKINTAPVM
            },
            null,
            null,
            null,
            null,
            KirjaRepository.COLUMN_HANKINTAPVM,
            null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    int parsedId = cursor.getInt(0);
                    int parsedNumero = cursor.getInt(1);
                    String parsedNimi = cursor.getString(2);
                    int parsedPainos = cursor.getInt(3);
                    long parsedHankintapvmUnixTime = cursor.getLong(4);

                    kirjat.add(new Kirja(parsedId, parsedNumero, parsedNimi, parsedPainos, parsedHankintapvmUnixTime));

                    cursor.moveToNext();
                }
            }

            cursor.close();
        }

        return kirjat;
    }
    @Override public Kirja getByID(int id) {
        SQLiteDatabase db = this.database.getReadableDatabase();

        Cursor cursor = db.query(
            KirjaRepository.TABLENAME,
            new String[] {
                KirjaRepository.COLUMN_ID,
                KirjaRepository.COLUMN_NUMERO,
                KirjaRepository.COLUMN_NIMI,
                KirjaRepository.COLUMN_PAINOS,
                KirjaRepository.COLUMN_HANKINTAPVM
            },
            KirjaRepository.COLUMN_ID + "=?",
            new String[] {
                String.valueOf(id)
            },
            null,
            null,
            null,
            null
        );

        if (cursor != null) {
            cursor.moveToFirst();

            int parsedId = Integer.parseInt(cursor.getString(0));
            int parsedNumero = cursor.getInt(1);
            String parsedNimi = cursor.getString(2);
            int parsedPainos = cursor.getInt(3);
            long parsedHankintapvmUnixTime = cursor.getLong(4);

            cursor.close();
            return new Kirja(parsedId, parsedNumero, parsedNimi, parsedPainos, parsedHankintapvmUnixTime);

        } else {
            return null;
        }
    }
    @Override public Kirja getFirst() {
        SQLiteDatabase db = this.database.getReadableDatabase();

        Cursor cursor = db.query(
                KirjaRepository.TABLENAME,
                new String[] {
                        KirjaRepository.COLUMN_ID,
                        KirjaRepository.COLUMN_NUMERO,
                        KirjaRepository.COLUMN_NIMI,
                        KirjaRepository.COLUMN_PAINOS,
                        KirjaRepository.COLUMN_HANKINTAPVM
                },
                null,
                null,
                null,
                null,
                KirjaRepository.COLUMN_ID,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {

	            int parsedId = cursor.getInt(0);
	            int parsedNumero = cursor.getInt(1);
	            String parsedNimi = cursor.getString(2);
	            int parsedPainos = cursor.getInt(3);
	            long parsedHankintapvmUnixTime = cursor.getLong(4);

	            cursor.close();
	            return new Kirja(parsedId, parsedNumero, parsedNimi, parsedPainos, parsedHankintapvmUnixTime);
            }
	        cursor.close();
            return null;

        } else {
            return null;
        }
    }
    @Override public boolean modify(Kirja kirja) {
        SQLiteDatabase db = this.database.getWritableDatabase();
        boolean updated = false;

        // Jos henkilö ei ole vielä kannassa, insert
        if (kirja.getId() == null) {
            this.add(kirja);
            updated = true;

        // Muuten update
        } else {
            ContentValues values = new ContentValues();
            values.put(KirjaRepository.COLUMN_NUMERO, kirja.getNumero());
            values.put(KirjaRepository.COLUMN_NIMI, kirja.getNimi());
            values.put(KirjaRepository.COLUMN_PAINOS, kirja.getPainos());
            values.put(KirjaRepository.COLUMN_HANKINTAPVM, kirja.getHankintapvmUnixTime());

            updated = db.update(
                KirjaRepository.TABLENAME,
                values,
                KirjaRepository.COLUMN_ID + "=?",
                new String[] { String.valueOf(kirja.getId()) }
            ) > 0;
        }

        db.close();
        return updated;
    }
    @Override public boolean delete(Kirja kirja) {

        // Jos henkilö ei edes ole kannassa, poistu
        if (kirja.getId() == null) {
            return false;
        }

        SQLiteDatabase db = this.database.getWritableDatabase();

        boolean deleted = db.delete(
            KirjaRepository.TABLENAME,
            KirjaRepository.COLUMN_ID + "=?",
            new String[] { String.valueOf(kirja.getId()) }
        ) > 0;

        db.close();
        return deleted;
    }
    @Override public boolean deleteFirst() {

        Kirja ekaKirja = this.getFirst();
        if (ekaKirja != null) {
            return this.delete(ekaKirja);
        } else {
            return false;
        }
    }
}