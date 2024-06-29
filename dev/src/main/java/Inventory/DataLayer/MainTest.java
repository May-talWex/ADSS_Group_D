package src.main.java.Inventory.DataLayer;

import java.sql.Connection;
import java.sql.Date;

public class MainTest {
    public static void main(String[] args) {
        DataBaseConnection dbConnection = null;
        Connection connection = null;
        try {
            // Initialize the database connection
            dbConnection = DataBaseConnection.getInstance();
            connection = dbConnection.getConnection();

            // Create the tables (if not already created)
            dbConnection.createTables();

            // Create a CategoryDAO instance
            CategoryDAO categoryDAO = new CategoryDAO();

            // Insert a category into the database
            String categoryId = "C001";
            String categoryName = "Dairy";
            Date startDiscount = Date.valueOf("2024-01-01");
            Date endDiscount = Date.valueOf("2024-12-31");
            float discountPercentage = 10.0f;

            boolean result = categoryDAO.insertCategory(categoryId, categoryName, startDiscount, endDiscount, discountPercentage);

            // Print the result
            if (result) {
                System.out.println("Category inserted successfully.");
            } else {
                System.out.println("Failed to insert category.");
            }

            // Update the category
            Date newStartDiscount = Date.valueOf("2024-02-01");
            Date newEndDiscount = Date.valueOf("2024-11-30");
            float newDiscountPercentage = 15.0f;

            boolean updateResult = categoryDAO.updateCategory(categoryId, newStartDiscount, newEndDiscount, newDiscountPercentage);

            // Print the update result
            if (updateResult) {
                System.out.println("Category updated successfully.");
            } else {
                System.out.println("Failed to update category.");
            }

            // Close the database connection
            dbConnection.closeConnection();
        } finally {
            // Ensure the database connection is properly closed
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }
    }
}
