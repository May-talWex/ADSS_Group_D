package src.main.java.Inventory.DataLayer;

import java.sql.*;


public class DataBaseConnection {
    private static DataBaseConnection connectionDB = null;
    private Connection connection;
    private final String url="jdbc:sqlite:InventoyDB.db";

    private DataBaseConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataBaseConnection getInstance() {
        if (connectionDB == null) {
            connectionDB = new DataBaseConnection();
        }
        return connectionDB;
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTables() {
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
                + "discount INTEGER NULL,"
                + "Min_Stock_Amnt INTEGER NOT NULL,"
                + "Discount_Start DATE,"
                + "Discount_End DATE,"
                + "Category_ID INTEGER NOT NULL,"
                + "Sub_Category_ID INTEGER NOT NULL,"
                + "Full_Price FLOAT NULL,"
                + "FOREIGN KEY (Category_ID) REFERENCES Category(id));";
        String createItemTable = "CREATE TABLE IF NOT EXISTS Item ("
                + "id TEXT PRIMARY KEY,"
                + "productID TEXT NOT NULL,"
                + "categoryID TEXT NOT NULL,"
                + "inWarehouse BOOLEAN NOT NULL,"
                + "isDefective BOOLEAN NOT NULL,"
                + "expirationDate DATE,"
                + "floorBuilding INTEGER,"
                + "floorShelf INTEGER,"
                + "X FLOAT,"
                + "Y FLOAT,"
                + "FOREIGN KEY (id) REFERENCES Product(makat),"
                + "FOREIGN KEY (categoryID) REFERENCES Category(id));";
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

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}


