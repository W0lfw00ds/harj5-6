package com.example.iirol.harjoitus5_6.Class.Database;

public interface Repository {

    void createTableIfNotExists();
    void deleteTableIfExists();
    void clearTable();

}
