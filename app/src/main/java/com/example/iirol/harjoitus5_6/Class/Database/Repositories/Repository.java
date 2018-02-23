package com.example.iirol.harjoitus5_6.Class.Database.Repositories;

public interface Repository {

	String getCreateTableIfNotExistsSQL();
	void deleteTableIfExists();
	String getTableName();
	void clearTable();

}
