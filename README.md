WebCrawler
==========

Connection to the MySQL database
================================

Host:     192.168.243.85
User:     root
Pass:     student
Database: webcrawler

+----------------------+
| Tables_in_webcrawler |
+----------------------+
| documents            |
| relations            |
| words                |
+----------------------+

mysql> describe documents;
+--------+--------------+------+-----+---------+-------+
| Field  | Type         | Null | Key | Default | Extra |
+--------+--------------+------+-----+---------+-------+
| id     | int(11)      | NO   | PRI | NULL    |       |
| domain | varchar(500) | YES  |     | NULL    |       |
| url    | varchar(500) | YES  |     | NULL    |       |
+--------+--------------+------+-----+---------+-------+

mysql> describe relations;
+---------+---------+------+-----+---------+-------+
| Field   | Type    | Null | Key | Default | Extra |
+---------+---------+------+-----+---------+-------+
| word_id | int(11) | NO   |     | NULL    |       |
| doc_id  | int(11) | NO   |     | NULL    |       |
+---------+---------+------+-----+---------+-------+

mysql> describe words;
+-------+-------------+------+-----+---------+-------+
| Field | Type        | Null | Key | Default | Extra |
+-------+-------------+------+-----+---------+-------+
| id    | int(11)     | NO   | PRI | NULL    |       |
| value | varchar(50) | YES  |     | NULL    |       |
+-------+-------------+------+-----+---------+-------+


== TO Connect use this: ==

static public final String driver = "com.mysql.jdbc.Driver";
static public final String connection =
"jdbc:mysql://192.168.243.85:3306/webcrawler";
static public final String user = "root";
static public final String password = "student";

public static void main(String args[]) {
try {
Class.forName(driver);
Connection con =
DriverManager.getConnection(connection, user, password);

System.out.println("Jdbc Mysql Connection String :");
System.out.println(connection);

System.out.println("User Name :" + user);
System.out.println("Password :" + password);

if (!con.isClosed()) {
con.close();
}
