package com.pjs.finaltest;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.sqlite.SQLiteConfig;

public class Insert<T> {
	private String dbFileName;
	private String tableName;
	private Connection connection;

	public Insert(String dbFileName, String tableName) {
		this.dbFileName = dbFileName;
		this.tableName = tableName;
	}

	public void insertData(T t) {
		try {
			if (this.connection == null) {
				try {
					Class.forName("org.sqlite.JDBC");
					SQLiteConfig config = new SQLiteConfig();
					this.connection = DriverManager.getConnection("jdbc:sqlite:/" + this.dbFileName, config.toProperties());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			Class<?> dataClass = t.getClass();
			Field[] dataClassFields = dataClass.getDeclaredFields();
			String fieldString = "";
			String valueString = "";
			for (Field field : dataClassFields) {
				if (!fieldString.isEmpty()) {
					fieldString = fieldString + ",";
				}
				String fieldType = field.getType().toString();
				String fieldName = field.getName();
				if (fieldName.matches("idx")) {
					continue;
				}
				fieldString = fieldString + fieldName;
				if (!valueString.isEmpty()) {
					valueString = valueString + ",";
				}
				if (fieldType.matches(".*String")) {
					valueString = valueString + "'" + field.get(t) + "'";
				} else {
					valueString = valueString + field.get(t);
				}
			}
			
			String query = "INSERT INTO " + this.tableName + "(" + fieldString + ") VALUES(" + valueString + ");";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.connection = null;
		}
	}
}
