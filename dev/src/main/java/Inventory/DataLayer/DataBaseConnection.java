package Inventory.DataLayer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    private static Connection connection;
    private static final String url;

    static {
        String dbPath = "";
        try {
            // Determine the current directory where the application is running
            File currentDir = new File(System.getProperty("user.dir"));
            // If running from a JAR file, get the parent directory of the JAR file
            if (currentDir.getName().equals("release")) {
                dbPath = currentDir.getAbsolutePath() + File.separator + "InventoryDB.db";
            } else {
                // If running from IntelliJ or other environment, assume the structure is different
                File releaseDir = new File(currentDir, "release");
                if (!releaseDir.exists() || !releaseDir.isDirectory()) {
                    throw new Exception("Release directory not found in " + releaseDir.getAbsolutePath());
                }
                dbPath = releaseDir.getAbsolutePath() + File.separator + "InventoryDB.db";
            }

            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                throw new Exception("Database file InventoryDB.db not found in " + dbFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error determining database path: " + e.getMessage());
        }
        url = "jdbc:sqlite:" + dbPath;
    }

    public static Connection DataBaseConnection() {
        if (connection != null) return connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            createTables();  // Ensure tables are created when the connection is established
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
                + "branchID INTEGER NOT NULL,"
                + "aisle FLOAT,"
                + "shelf FLOAT,"
                + "expireDate DATE,"
                + "categoryID TEXT,"
                + "productID TEXT,"
                + "FOREIGN KEY (categoryID) REFERENCES Category(id),"
                + "FOREIGN KEY (productID) REFERENCES Product(makat));";

        try (Statement stmt = connection.createStatement()) {
            // Create Category table
            boolean categoryCreated = stmt.execute(createCategoryTable);
            if (categoryCreated) {
                System.out.println("Category table created successfully.");
            } else {
                System.out.println("Category table already exists or no changes made.");
            }

            // Create Product table
            boolean productCreated = stmt.execute(createProductTable);
            if (productCreated) {
                System.out.println("Product table created successfully.");
            } else {
                System.out.println("Product table already exists or no changes made.");
            }

            // Create Item table
            boolean itemCreated = stmt.execute(createItemTable);
            if (itemCreated) {
                System.out.println("Item table created successfully.");
            } else {
                System.out.println("Item table already exists or no changes made.");
            }

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
        closeConnection();
    }
}
