	package dao;
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	
	public class DatabaseConnection {
	
		private static Connection c;
	
		public Connection getConnection() throws ClassNotFoundException, SQLException {
			String hostName = "localhost";
			String dbName = "PokemonDB2"; 
			String user = "aaa";
			String senha = "12345";
	
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
	
			String url = String.format("jdbc:jtds:sqlserver://%s:1433/%s;user=%s;password=%s;", hostName, dbName, user,
					senha);
	
			c = DriverManager.getConnection(url);
	
			return c;
		}
	}
