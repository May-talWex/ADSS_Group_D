package HR.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "C:\\Users\\rotto\\IdeaProjects\\ADSS_Group_D"; // Replace with your SQLite database path
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }
}




