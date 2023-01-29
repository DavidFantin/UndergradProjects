import java.sql.*;
import java.io.*;

class jdbcMySQL {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			// db parameters
//		    String url       = "jdbc:mysql://129.244.40.38:3306/userID";
//		    String user      = "userID";
//		    String password  = "yourPassword";
			// create a connection to the database
//		    conn = DriverManager.getConnection(url, user, password);

			// Substitute your userId and your password in the following statement
			conn = DriverManager.getConnection("jdbc:mysql://129.244.40.38:3306/user23", "user23",
					"TU2020-silver-hippo");
			// more processing here
			Statement stmt = conn.createStatement(); // Create a Statement

			// Replace the following query text with the SQL query corresponding to the
			// query in the assignment
			String qry  = "SELECT DISTINCT Poster, P.Gender, Recipient, R.Gender "
						+ "FROM comments AS C "
							+ "JOIN users AS R ON R.Id = C.Recipient "
								+ "JOIN users AS P ON P.Id = C.Poster "
						+ "WHERE R.Gender != P.Gender";

			// All the records after executing "qry" are fetched a ResultSet rset.
			ResultSet rset = stmt.executeQuery(qry);

			ResultSetMetaData metadata = rset.getMetaData(); // get metadata from the ResultSet
			int columnCount = metadata.getColumnCount(); // get the number of columns from metadata
			for (int i = 1; i <= columnCount; i++) { // print out the column headers
				System.out.print(metadata.getColumnName(i) + " ");
			}
			System.out.println();

			int i = 0;
			while (rset.next()) { // Loop over each record
				String row = "";
				// rset.getString(i) to get the ith column information
				for (int j = 1; j <= columnCount; j++) {
					row = row.concat(rset.getString(j) + "    ");
				}
				System.out.println(" " + row); // print out the accumulated current record
				i++;
			}
			System.out.println("\nNumber of records fetched: " + i + "\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
//				System.out.println("Success");
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

	}
}
