package src.main.java.Inventory.DataLayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDAO {
    private Connection connection;

    public CategoryDAO() {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    public boolean insertCategory(String id, String name, Date startDiscount, Date endDiscount, float discountPercentage) {
        String sql = "INSERT INTO Category(id, name, Start_Discount, End_Discount, Discount_Percentage) VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, startDiscount.toString()); // Format date as 'YYYY-MM-DD'
            pstmt.setString(4, endDiscount.toString());   // Format date as 'YYYY-MM-DD'
            pstmt.setFloat(5, discountPercentage);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteCategory(String id) {
        String sql = "DELETE FROM Category WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateCategory(String id, Date startDiscount, Date endDiscount, float discountPercentage) {
        String sql = "UPDATE Category SET Start_Discount = ?, End_Discount = ?, Discount_Percentage = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, startDiscount.toString()); // Format date as 'YYYY-MM-DD'
            pstmt.setString(2, endDiscount.toString());   // Format date as 'YYYY-MM-DD'
            pstmt.setFloat(3, discountPercentage);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
