package src.main.java.Inventory.DataLayer;


public class TableInitializer {
    public static void main(String[] args) {
        DataBaseConnection dbConnection = DataBaseConnection.getInstance();
        dbConnection.createTables();
        dbConnection.closeConnection();
    }
}
