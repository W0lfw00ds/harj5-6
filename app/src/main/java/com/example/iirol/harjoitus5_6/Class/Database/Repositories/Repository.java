package com.example.iirol.harjoitus5_6.Class.Database.Repositories;

import java.util.ArrayList;

public interface Repository<T> {

	String getCreateTableIfNotExistsSQL();
	void deleteTableIfExists();
	String getTableName();
	void clearTable();

	void add(T kirja);
	ArrayList<T> getAll();
	T getByID(int id);
	T getFirst();
	boolean modify(T entity);
	boolean delete(T entity);
	boolean deleteFirst();
}
