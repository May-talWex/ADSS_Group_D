package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DataLayer.DataBaseConnection;

public class MainApp {
    public static void main(String[] args) {
        DataBaseConnection db = DataBaseConnection.getInstance();
        db.createTables();  // Ensure tables are created

        // Test the database connection
        if (db.testConnection()) {
            System.out.println("Database connection test successful.");
        } else {
            System.out.println("Database connection test failed.");
        }

        db.closeConnection();  // Close the connection when done
    }
}
