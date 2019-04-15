package connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;





public final class DbConnection {

	
	public static Connection Connection_to_my_db_Max()  {
		Connection connection = null;

		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("ERROR JDBC driver connection:");
			e.printStackTrace();
		}
		try {
			
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/biadbtest", "maxim",
					"yoloplayer1337");
		} catch (SQLException e) {
			System.out.println("ERROR connection to DB:");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	

}
