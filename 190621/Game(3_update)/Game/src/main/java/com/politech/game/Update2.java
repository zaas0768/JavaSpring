package com.politech.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

import org.sqlite.SQLiteConfig;

public class Update2 {
	public void method2(int idx) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/c:\\tomcat\\game.db", config.toProperties());
			
			Random rand = new Random();
			int attackPower = rand.nextInt(100);
			int attackRate = rand.nextInt(100);
			int defensePower = rand.nextInt(100);
			int defenseRate = rand.nextInt(100);
			
			String query = "UPDATE player SET attackPower=" + attackPower + ", attackRate=" + attackRate 
					+ ", defensePower=" + defensePower + ", defenseRate=" + defenseRate + " WHERE idx=" + idx;
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
