package ua.itea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author SergeyK
 */
public class DBUtil {
	
	private static Connection connection;
	
	public static void initDataBase() {
		try {
			String url = "jdbc:postgresql://localhost:5432/TestData?user=supervisor&password=admin&useUnicode=yes&characterEncoding=utf-8";
			connection = DriverManager.getConnection(url);
			Class.forName("org.postgresql.Driver");
			System.out.println("Connected to PostgreSQL database!");
			Statement statement = connection.createStatement();
			statement.execute("CREATE SCHEMA IF NOT EXISTS bomber");
			statement.execute("SET search_path TO bomber, public");
			statement.execute("CREATE TABLE IF NOT EXISTS users(id SERIAL primary key, name varchar(100), "
					+ "level numeric(3,0) NOT NULL DEFAULT 1, score numeric(20,0));");
			//statement.execute("CREATE TABLE IF NOT EXISTS users(id SERIAL primary key, name varchar(100), score numeric(20,0));");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Добавить новый результат в таблицу
	 * @param name имя игрока
	 * @param score очки игрока
	 */
	@Deprecated
	public static void addScore(String name, int score) {		
		try {
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO users (name, score) VALUES ('" + name + "', " + score +")");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	/**
	 * Сохранить игру 
	 * @param name Имя игрока
	 * @param level уровень игры
	 * @param score очки
	 */
	public static void saveGame(String name, int level, int score) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO users (name, level, score) VALUES ('" + name + "', " + level + ", " + score +")");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadGame(String name) {
		try {
			Statement statement = connection.createStatement();
			//statement.execute("INSERT INTO users (name, level, score) VALUES ('" + name + "', " + level + ", " + score +")");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
