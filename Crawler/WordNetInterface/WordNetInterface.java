import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class WordNetInterface {

	private URL url;
	
	public WordNetInterface(String path) {
		try {
			url = new URL(path);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
	}

	public void runItBaby() {
		IDictionary dict = new Dictionary(url);
		try {
			dict.open();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Iterator<IIndexWord> it = dict.getIndexWordIterator(POS.NOUN);

		Connection co = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			co = DriverManager.getConnection("jdbc:mysql://localhost:3306/webcrawler", "crawler", "student");
		} catch (Exception ex) {
			if (ex instanceof SQLException)
				System.out.println("SQLException: " + ((SQLException) ex).getMessage() +
					   "SQLState: " + ((SQLException) ex).getSQLState() +
					   "ErrorCode: " + ((SQLException) ex).getErrorCode());
			else
				ex.printStackTrace();
			return;
		}
		List<IWordID> iwIDs;
		PreparedStatement ps;
		while (it.hasNext()) {
			iwIDs = (it.next()).getWordIDs();
			for (IWordID iwID : iwIDs)
				if ((iwID.getLemma()).length() < 42) {
					System.out.println("INSERT INTO words (value) VALUES ('" + iwID.getLemma() + "');");
					try {
						ps = co.prepareStatement("INSERT INTO words (value) VALUES (?)");
						ps.setString(1, iwID.getLemma());
						ps.executeUpdate();
					} catch (SQLException ex) {
						System.out.println("SQLException: " + ex.getMessage() + "SQLState: " + ex.getSQLState() + "ErrorCode: " + ex.getErrorCode());
					}
				}
		}
		if (co != null)
			try {
				co.close();
			} catch (SQLException ex) {
			}
	}
	
	public static void main(String[] args) {
		(new WordNetInterface(args[0])).runItBaby();
	}

}
