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
	private final static String connectionString = "jdbc:sqlite:sample.db";
	private static Game game;
	
	public static void initDataBase(Game game) {
		DBUtil.game = game;
		try {
			//String url = "jdbc:postgresql://localhost:5432/TestData?user=supervisor&password=admin&useUnicode=yes&characterEncoding=utf-8";
			connection = DriverManager.getConnection(connectionString);
			//Class.forName("org.postgresql.Driver");
			Class.forName("org.sqlite.JDBC");
			System.out.println("Connected to sqlite database!");
			Statement statement = connection.createStatement();
			/*statement.execute("CREATE SCHEMA IF NOT EXISTS bomber");
			statement.execute("SET search_path TO bomber, public");
			statement.execute("CREATE TABLE IF NOT EXISTS users(id SERIAL primary key, name varchar(100), "
					+ "level numeric(3,0) NOT NULL DEFAULT 1, score numeric(20,0));");			
			statement.close();*/
			statement.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
					"name TEXT NOT NULL," +
					"level INTEGER NOT NULL DEFAULT 1," +
					"score INTEGER)");

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
		try (Statement statement = connection.createStatement()) {			
			statement.execute("INSERT INTO users (name, level, score) VALUES ('" + name + "', " + level + ", " + score +")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 /*
	  * @return level
	  */
	public static int loadGame(String name) {
		int level = 1;
		int score = 0;
		try (Statement statement = connection.createStatement()) {			
			ResultSet rs = statement.executeQuery("SELECT level, score FROM users WHERE name = '" + name + "' ORDER BY level DESC LIMIT 1");
			System.out.print("loadGame..." );
			int rowCount=0;
			while (rs.next()) {
				rowCount++;
				level = rs.getInt("level");
				score = rs.getInt("score");
				System.out.println("Sucess! Player: " + name  + " level: " + level);
			}
			if (rowCount > 0) {				
				game.gameOver("Sucess! Player: " + name  + " level: " + level);
				game.setLevelScore(level, score);
			} else {
				System.out.println("Player " + name  + " does not exist!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return level;
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
