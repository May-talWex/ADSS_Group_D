package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            pstmt.setDate(3, startDiscount);
            pstmt.setDate(4, endDiscount);
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

    public boolean updateCategory(String id, LocalDate startDiscount, LocalDate endDiscount, float discountPercentage) {
        String sql = "UPDATE Category SET Start_Discount = ?, End_Discount = ?, Discount_Percentage = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, startDiscount != null ? startDiscount.toString() : null);
            pstmt.setString(2, endDiscount != null ? endDiscount.toString() : null);
            pstmt.setFloat(3, discountPercentage);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Category getCategoryById(String id) {
        String sql = "SELECT * FROM Category WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                Date startDiscount = rs.getDate("Start_Discount");
                Date endDiscount = rs.getDate("End_Discount");
                float discountPercentage = rs.getFloat("Discount_Percentage");

                LocalDate startDiscountLocalDate = startDiscount != null ? startDiscount.toLocalDate() : null;
                LocalDate endDiscountLocalDate = endDiscount != null ? endDiscount.toLocalDate() : null;

                Category category = new Category(name, id, startDiscountLocalDate, endDiscountLocalDate, discountPercentage);
                category.getProducts().addAll(getProductsByCategoryId(id)); // Add products to category
                return category;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM Category";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                Date startDiscount = rs.getDate("Start_Discount");
                Date endDiscount = rs.getDate("End_Discount");
                float discountPercentage = rs.getFloat("Discount_Percentage");

                Category category = new Category(name, id, startDiscount != null ? startDiscount.toLocalDate() : null, endDiscount != null ? endDiscount.toLocalDate() : null, discountPercentage);
                category.getProducts().addAll(getProductsByCategoryId(id)); // Add products to category
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }

    private ArrayList<Product> getProductsByCategoryId(String categoryId) {
        String sql = "SELECT * FROM Product WHERE Category_ID = ?";
        ArrayList<Product> products = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String makat = rs.getString("makat");
                String name = rs.getString("name");
                String supplier = rs.getString("supplier");
                float costPrice = rs.getFloat("costPrice");
                float sellingPrice = rs.getFloat("Selling_Price");
                int discount = rs.getInt("discount");
                int minStockAmnt = rs.getInt("Min_Stock_Amnt");
                Date discountStart = rs.getDate("Discount_Start");
                Date discountEnd = rs.getDate("Discount_End");
                String subCategoryId = rs.getString("Sub_Category_ID");
                float fullPrice = rs.getFloat("Full_Price");

                Product product = new Product(makat, name, supplier, costPrice, sellingPrice, categoryId, subCategoryId, minStockAmnt);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return products;
    }

}
