package Inventory.DataLayer;

import java.sql.*;

public class DataBaseConnection {
    private static Connection connection;
    private static final String url = "jdbc:sqlite:InventoyDB.db";

    public static Connection DataBaseConnection() {
        if (connection != null) return connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Connection getInstance() {
        if (connection == null) {
            connection = DataBaseConnection();
        }
        return connection;
    }

    public static Connection getConnection() {
        if (connection != null) return connection;
        return DataBaseConnection();
    }

    public static void createTables() {
        String createCategoryTable = "CREATE TABLE IF NOT EXISTS Category ("
                + "id TEXT PRIMARY KEY,"
                + "name TEXT NOT NULL,"
                + "Start_Discount DATE,"
                + "End_Discount DATE,"
                + "Discount_Percentage FLOAT);";

        String createProductTable = "CREATE TABLE IF NOT EXISTS Product ("
                + "makat TEXT PRIMARY KEY,"
                + "name TEXT NOT NULL,"
                + "supplier TEXT NOT NULL,"
                + "costPrice FLOAT NOT NULL,"
                + "Selling_Price FLOAT NOT NULL,"
                + "discount FLOAT,"
                + "Min_Stock_Amnt INTEGER NOT NULL,"
                + "Discount_Start DATE,"
                + "Discount_End DATE,"
                + "Category_ID INTEGER NOT NULL,"
                + "Sub_Category_ID INTEGER NOT NULL,"
                + "Full_Price FLOAT NOT NULL,"
                + "FOREIGN KEY (Category_ID) REFERENCES Category(id));";

        String createItemTable = "CREATE TABLE IF NOT EXISTS Item ("
                + "id TEXT PRIMARY KEY,"
                + "name TEXT,"
                + "defective BOOLEAN,"
                + "inWareHouse BOOLEAN,"
                + "floor INTEGER,"
                + "branchID INTEGER NOT NULL," // Changed from building to branchID
                + "aisle FLOAT,"
                + "shelf FLOAT,"
                + "expireDate DATE,"
                + "categoryID TEXT,"
                + "productID TEXT,"
                + "FOREIGN KEY (categoryID) REFERENCES Category(id),"
                + "FOREIGN KEY (productID) REFERENCES Product(makat));";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCategoryTable);
            System.out.println("Category table created successfully.");
            stmt.execute(createProductTable);
            System.out.println("Product table created successfully.");
            stmt.execute(createItemTable);
            System.out.println("Item table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        DataBaseConnection();
        createTables();
        closeConnection();
    }
}
