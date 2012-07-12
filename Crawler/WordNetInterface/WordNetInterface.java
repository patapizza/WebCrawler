import java.net.URL;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WordNetInterface {

	private URL url;
	
	public WordNetInterface(String path) {
		url = new URL(path);
	}

	public void runItBaby() {
		IDictionary dict = new Dictionary(url);
		dict.open();
		Iterator<IIndexWord> it = dict.getIndexWordIterator(POS.NOUN);

		Connection co = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//co = DriverManager.getConnection("jdbc:mysql://root:student@192.168.243.89/");
			co = DriverManager.getConnection("jdbc:mysql://192.168.243.89/test", "root", "student");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage() +
					   "SQLState: " + ex.getSQLState() +
					   "ErrorCode: " + ex.getErrorCode());
			return;
		}
		List iwIds;
		while (it.hasNext())
			try {
				iwIDs = (it.next()).getWordIDs();
				for (IWordID iwID : iwIDs)
					(co.createStatement()).executeQuery("INSERT INTO words (value) VALUES (" + iwID.getLemma() + ")");
			} catch (SQLException ex) {
				System.out.println("SQLException: " + ex.getMessage() +
						   "SQLState: " + ex.getSQLState() +
						   "ErrorCode: " + ex.getErrorCode());
			} finally {
				// supposed to close statement but pula mea
			}
		if (co)
			try {
				co.close();
			} catch (SQLException ex) {
			}
	}
	
	public static void main(String[] args) {
		(new WordNetInterface(args[1])).runItBaby();
	}

}
