package ro.iasi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DBManager {

	private Connection connection;

	private void createTables() throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Documents (ID INTEGER PRIMARY KEY, DomainName TEXT, URL TEXT)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Words (ID INTEGER PRIMARY KEY, Value TEXT)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS VerticalIndexes (ID INTEGER PRIMARY KEY, WordID INTEGER, DocID INTEGER, "
				+ "FOREIGN KEY (WordID) REFERENCES Words(ID), FOREIGN KEY (DocID) REFERENCES Documents(ID))");
	}

	public List<Integer> getWordIds() {
		List<Integer> result = new ArrayList<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM words");
			while (rs.next()) {
				result.add(rs.getInt("id"));
			}
			rs.close();
		} catch (SQLException e) {
			raiseSQLExecutionError(e);
		}
		
		return result;
	}
	
	public int getWordsCount() {
		int result = -1;
		
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM words");
			while (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			raiseSQLExecutionError(e);
		}
		
		return result;
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
			raiseSQLExecutionError(e);
		}

		return result;
	}

	private int storeDocument(String domain, String url) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO documents (domain, url) VALUES (?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, domain);
		preparedStatement.setString(2, url);
		preparedStatement.executeUpdate();

		int result = -1;

		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		while (resultSet.next()) {
			result = resultSet.getInt(1);
		}
		
		resultSet.close();
		
		// already exists, get original id
		if (result == -1) {
			PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT id FROM documents WHERE domain = ? AND url = ?");
			preparedStatement2.setString(1, domain);
			preparedStatement2.setString(2, url);
			
			ResultSet rs = preparedStatement2.getResultSet();
			
			while (rs.next()) {
				result = rs.getInt(1);
			}
		}

		return result;
	}

	private void storeRelation(int docId, int wordId) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO relations (doc_id, word_id) VALUES (?, ?)");
		preparedStatement.setInt(1, docId);
		preparedStatement.setInt(2, wordId);
		preparedStatement.executeUpdate();
	}

	public void storeIndexes(Map<String, Map<String, List<Integer>>> indexes) {
		try {
			connection.setAutoCommit(false);
	
			for (Entry<String, Map<String, List<Integer>>> domainEntry : indexes.entrySet()) {
				String domain = domainEntry.getKey();
	
				for (Entry<String, List<Integer>> urlEntry : domainEntry.getValue().entrySet()) {
					String url = urlEntry.getKey();
	
					int docId = storeDocument(domain, url);
	
					for (Integer wordId : urlEntry.getValue()) {
						storeRelation(docId, wordId);
					}
				}
			}
			
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			raiseSQLExecutionError(e);
		}
	}

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://192.168.243.85/webcrawler?user=root&password=student");

			// TODO created by script
			// createTables();
		} catch (ClassNotFoundException ex) {
			System.err.println("Could not find database driver");
			System.exit(-1);
		} catch (SQLException e) {
			raiseSQLExecutionError(e);
		}
	}

	private void raiseSQLExecutionError(Exception ex) {
		System.err.println("SQL execution problem");
		ex.printStackTrace();
		System.exit(-1);
	}

}
