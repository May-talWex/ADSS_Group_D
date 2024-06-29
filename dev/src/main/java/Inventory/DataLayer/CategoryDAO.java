package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public boolean updateCategory(String id, Date startDiscount, Date endDiscount, float discountPercentage) {
        String sql = "UPDATE Category SET Start_Discount = ?, End_Discount = ?, Discount_Percentage = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, startDiscount);
            pstmt.setDate(2, endDiscount);
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

                ArrayList<Product> products = getProductsByCategoryId(id);
                return new Category(name, id, startDiscount.toLocalDate(), endDiscount.toLocalDate(), discountPercentage, products);
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

                Category category = new Category(name, id, startDiscount.toLocalDate(), endDiscount.toLocalDate(), discountPercentage);
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
                double costPrice = rs.getDouble("costPrice");
                double sellingPrice = rs.getDouble("Selling_Price");
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
