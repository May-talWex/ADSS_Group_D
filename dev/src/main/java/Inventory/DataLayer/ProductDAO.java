package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO() {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    public boolean insertProduct(Product product) {
        String sql = "INSERT INTO Product(makat, name, supplier, costPrice, Selling_Price, Category_ID, Sub_Category_ID, Min_Stock_Amnt, Full_Price) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getMakat());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getSupplier());
            pstmt.setFloat(4, product.getCostPrice());
            pstmt.setFloat(5, product.getSellingPrice());
            pstmt.setString(6, product.getCategoryID());
            pstmt.setString(7, product.getSubCategoryID());
            pstmt.setInt(8, product.getMinimumAmount());
            pstmt.setFloat(9, product.getFullPrice());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(String makat) {
        String sql = "DELETE FROM Product WHERE makat = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, makat);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Product getProductById(String makat) {
        String sql = "SELECT * FROM Product WHERE makat = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, makat);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String supplier = rs.getString("supplier");
                float costPrice = rs.getFloat("costPrice");
                float sellingPrice = rs.getFloat("Selling_Price");
                String categoryID = rs.getString("Category_ID");
                String subCategoryID = rs.getString("Sub_Category_ID");
                int minimumAmount = rs.getInt("Min_Stock_Amnt");

                return new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Product> getProductsByCategoryId(String categoryID) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE Category_ID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String makat = rs.getString("makat");
                String name = rs.getString("name");
                String supplier = rs.getString("supplier");
                float costPrice = rs.getFloat("costPrice");
                float sellingPrice = rs.getFloat("Selling_Price");
                String subCategoryID = rs.getString("Sub_Category_ID");
                int minimumAmount = rs.getInt("Min_Stock_Amnt");

                products.add(new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String makat = rs.getString("makat");
                String name = rs.getString("name");
                String supplier = rs.getString("supplier");
                float costPrice = rs.getFloat("costPrice");
                float sellingPrice = rs.getFloat("Selling_Price");
                String categoryID = rs.getString("Category_ID");
                String subCategoryID = rs.getString("Sub_Category_ID");
                int minimumAmount = rs.getInt("Min_Stock_Amnt");

                products.add(new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
}
