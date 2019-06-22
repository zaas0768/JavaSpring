package com.myspring.com;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.sqlite.SQLiteConfig;

import com.myspring.account.Account;

public class DBCommon<T> {
	private String dbFileName;
	private String tableName;
	private Connection connection;
	private ArrayList<T> dataList;

	public DBCommon(String dbFileName, String tableName) {
		this.dbFileName = dbFileName;
		this.tableName = tableName;
	}

	private void open() {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			this.connection = DriverManager.getConnection("jdbc:sqlite:/" + this.dbFileName, config.toProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.connection = null;
	}

	public void createTable(T t) {
		try {
			Class<?> dataClass = t.getClass();
			Field[] dataClassFields = dataClass.getFields();
			String query = "";
			String primaryKey = "PRIMARY KEY(";
			for (Field field : dataClassFields) {
				String fieldType = field.getType().toString();
				String fieldName = field.getName();
				if ((fieldName.matches("id") || fieldName.matches("keyNumber")) && fieldType.matches(".*String")) {
					if (!primaryKey.equals("PRIMARY KEY(")) {
						primaryKey += ", ";
					}
					query = query + fieldName + " TEXT NOT NULL UNIQUE";
					primaryKey += fieldName;
				} else if (fieldType.matches("int")) {
					query = query + fieldName + " INTEGER NOT NULL";
				} else if (fieldType.matches("(double|float)")) {
					query = query + fieldName + " REAL NOT NULL";
				} else if (fieldType.matches(".*String")) {
					query = query + fieldName + " TEXT NOT NULL";
				}
				query = query + ", ";
			}
			primaryKey += ")";
			if (this.connection == null) {
				this.open();
			}
			query = "CREATE TABLE " + this.tableName + " (" + query + primaryKey + ");";
			Statement statement = this.connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			dropTable();
			createTable(t);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	
	public void dropTable() {
		try {
			if (this.connection == null) {
				this.open();
			}
			String query = "DROP TABLE " + this.tableName + ";";
			Statement statement = this.connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	
	public void deleteData(String column, String value) {
		try {
			if (this.connection == null) {
				this.open();
			}
			String query = "DELETE FROM " + this.tableName + " WHERE " + column + "='" + value + "';";
			Statement statement = this.connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	
	public boolean duplicateCheck(String column, String value) {
		boolean isSearched = false;
		try {
			if (this.connection == null) {
				this.open();
			}
			String query = "SELECT * FROM " + this.tableName + " WHERE " + column + "='" + value + "';";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				isSearched = true;
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return isSearched;
	}
	
	public boolean insertData(T t) {
		boolean resultBool = false;
		try {
			Class<?> dataClass = t.getClass();
			Field[] dataClassFields = dataClass.getFields();
			String fieldString = "";
			String valueString = "";
			for (Field field : dataClassFields) {
				if (!fieldString.isEmpty()) {
					fieldString += ",";
				}
				if (!valueString.isEmpty()) {
					valueString += ",";
				}
				String fieldType = field.getType().toString();
				String fieldName = field.getName();

				fieldString += fieldName;

				if (fieldType.matches(".*String")) {
					valueString += "'" + field.get(t) + "'";
				} else {
					valueString += field.get(t);
				}
			}
			if (this.connection == null) {
				this.open();
			}
			String query = "INSERT INTO " + this.tableName + "(" + fieldString + ") VALUES(" + valueString + ");";
			Statement statement = this.connection.createStatement();
			int result = statement.executeUpdate(query);
			resultBool = true;
			statement.close();
			return resultBool;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return resultBool;
	}
	
	public HashMap<String, String> doSearchId(String keyNumber) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("success", "fail");
		try {
			
			if (this.connection == null) {
				this.open();
			}
			String query = "SELECT id FROM " + this.tableName + " WHERE keyNumber='" + keyNumber  + "';";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				map.put("success", "success");
				map.put("id", resultSet.getString("id"));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return map;
	}

	public boolean doSearchPw(String keyNumber, String id) {
		boolean isSuccessed = false;
		try {	
			if (this.connection == null) {
				this.open();
			}
			String query = "SELECT password FROM " + this.tableName + " WHERE keyNumber='" + keyNumber +"' AND id='"+ id + "';";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				isSuccessed = true;
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return isSuccessed;
	}
	
	public void pwUpdateData(T t, String keyNumber, String id) {
		try {
			Class<?> dataClass = t.getClass();
			Field[] dataClassFields = dataClass.getFields();
			String queryString = "";
			for (Field field : dataClassFields) {
				if (!queryString.isEmpty()) {
					queryString = queryString + ",";
				}
				String fieldType = field.getType().toString();
				String fieldName = field.getName();
				if (fieldName.matches("identify_number")) {
					continue;
				}
				if (fieldType.matches(".*String")) {
					queryString = queryString + fieldName + "=" + "'" + field.get(t) + "'";
				} else {
					queryString = queryString + fieldName + "=" + field.get(t);
				}
			}
			if (this.connection == null) {
				this.open();
			}
			String query = "UPDATE " + this.tableName + " SET " + queryString + " WHERE keyNumber='" + keyNumber + "' AND id ='" + id + "';";
			Statement statement = this.connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	
	public HashMap<String, String> loginCheck(String id, String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("success", "fail");
		try {
			
			if (this.connection == null) {
				this.open();
			}
			String query = "SELECT id FROM " + this.tableName + " WHERE id='" + id  + "' AND password = '" + password + "';";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				map.put("success", "success");
				map.put("id", resultSet.getString("id"));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return map;
	}

	public void selectData(T t) {
		try {
			Class<?> dataClass = t.getClass();
			Field[] dataClassFields = dataClass.getFields();
			this.dataList = new ArrayList<T>();

			if (this.connection == null) {
				this.open();
			}
			String query = "SELECT * FROM " + this.tableName + " WHERE ?;";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				T fieldData = (T) dataClass.getDeclaredConstructor().newInstance();
				for (Field field : dataClassFields) {
					String fieldType = field.getType().toString();
					String fieldName = field.getName();
					if (fieldType.matches("int")) {
						field.setInt(fieldData, resultSet.getInt(fieldName));
					} else {
						field.set(fieldData, resultSet.getString(fieldName));
					}
				}
				this.dataList.add(fieldData);
			}
			preparedStatement.close();
		} catch (SQLException e) {
			createTable(t);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

	public HashMap<String, String> selectDataById(T t, String id) {
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			Class<?> dataClass = t.getClass();
			Field[] dataClassFields = dataClass.getFields();
			if (this.connection == null) {
				this.open();
			}
			String query = "SELECT * FROM " + this.tableName + " WHERE id=?;";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				for (Field field : dataClassFields) {
					String fieldType = field.getType().toString();
					String fieldName = field.getName();
					if (fieldType.matches("int")) {
						data.put(fieldName, Integer.toString(resultSet.getInt(fieldName)));
					} else {
						data.put(fieldName, resultSet.getString(fieldName));
					}
				}
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return data;
	}
	
	public ArrayList<T> selectArrayList(T t) {
		this.selectData(t);
		return this.dataList;
	}
	
	public String selectDataTableTag(T t, String id) {
		this.selectData(t);
		Collections.shuffle(this.dataList);
		Class<?> dataClass = t.getClass();
		String returnString = "";
		for (int i = 0; i < this.dataList.size(); i++) {
			if (i >= 10) {
				break;
			}
			try {
				String selectedId = (String) this.dataList.get(i).toString();
				if (selectedId.equals(id)) {
					continue;
				}
				Method toTableTagStringMethod = dataClass.getDeclaredMethod("toTableTagString");
				returnString += (String) toTableTagStringMethod.invoke(this.dataList.get(i));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnString;
	}

//	public HashMap<String, String> detailsData(T t, int idx) {
//		HashMap<String, String> details = new HashMap<String, String>();
//		try {
//			Class<?> dataClass = t.getClass();
//			Field[] dataClassFields = dataClass.getDeclaredFields();
//			this.dataList = new ArrayList<T>();
//
//			if (this.connection == null) {
//				this.open();
//			}
//			String query = "SELECT * FROM " + this.tableName + " WHERE idx=?;";
//
//			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
//			preparedStatement.setInt(1, idx);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			if (resultSet.next()) {
//				for (Field field : dataClassFields) {
//					String fieldType = field.getType().toString();
//					String fieldName = field.getName();
//					details.put(fieldName, resultSet.getString(fieldName));
//				}
//			}
//			preparedStatement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			this.close();
//		}
//		return details;
//	}
}
