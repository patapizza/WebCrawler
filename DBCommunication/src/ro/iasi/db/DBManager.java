package ro.iasi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBManager {

	private Connection connection;

	private void createTables() throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Documents (ID INTEGER PRIMARY KEY, DomainName TEXT, URL TEXT)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Words (ID INTEGER PRIMARY KEY, Value TEXT)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS VerticalIndexes (ID INTEGER PRIMARY KEY, WordID INTEGER, DocID INTEGER, "
				+ "FOREIGN KEY (WordID) REFERENCES Words(ID), FOREIGN KEY (DocID) REFERENCES Documents(ID))");
	}

	public Map<String, Integer> getDictionary() {
		Map<String, Integer> result = new LinkedHashMap<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, value FROM words");
			while (rs.next()) {
				result.put(rs.getString("value"), rs.getInt("id"));
			}
			rs.close();
		} catch (SQLException e) {
			raiseSQLExecutionError();
		}

		return result;
	}

	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:test.db");
			
			// TODO created by script
			//createTables();
		} catch (ClassNotFoundException ex) {
			System.err.println("Could not find database driver");
			System.exit(-1);
		} catch (SQLException e) {
			raiseSQLExecutionError();
		}
	}
	
	private void raiseSQLExecutionError() {
		System.err.println("SQL execution problem");
		System.exit(-1);
	}

}
