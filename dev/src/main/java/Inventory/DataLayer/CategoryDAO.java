package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection connection;

    public CategoryDAO() {
        this.connection = DataBaseConnection.getConnection();
    }

    public List<Category> getAllCategoriesWithProducts() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("name");
                String startDiscountStr = rs.getString("Start_Discount");
                String endDiscountStr = rs.getString("End_Discount");
                LocalDate discountStartDate = null;
                LocalDate discountEndDate = null;
                Float discountPercentage = null;

                if (startDiscountStr != null && !startDiscountStr.isEmpty()) {
                    discountStartDate = LocalDate.parse(startDiscountStr);
                }

                if (endDiscountStr != null && !endDiscountStr.isEmpty()) {
                    discountEndDate = LocalDate.parse(endDiscountStr);
                }

                if (rs.getObject("Discount_Percentage") != null) {
                    discountPercentage = rs.getFloat("Discount_Percentage");
                }

                Category category = new Category(name, id, discountStartDate, discountEndDate, discountPercentage != null ? discountPercentage : 0);
                category.setProducts(getProductsByCategoryId(id));
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
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
                String startDiscountStr = rs.getString("Start_Discount");
                String endDiscountStr = rs.getString("End_Discount");
                float discountPercentage = rs.getFloat("Discount_Percentage");

                LocalDate startDiscountLocalDate = null;
                LocalDate endDiscountLocalDate = null;

                if (startDiscountStr != null && !startDiscountStr.isEmpty()) {
                    startDiscountLocalDate = LocalDate.parse(startDiscountStr);
                }

                if (endDiscountStr != null && !endDiscountStr.isEmpty()) {
                    endDiscountLocalDate = LocalDate.parse(endDiscountStr);
                }

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

    public ArrayList<Product> getProductsByCategoryId(String categoryId) {
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
                String subCategoryId = rs.getString("Sub_Category_ID");
                float fullPrice = rs.getFloat("Full_Price");

                LocalDate discountStartDate = null;
                LocalDate discountEndDate = null;

                // Retrieve the discount start and end dates as strings
                String discountStartStr = rs.getString("Discount_Start");
                String discountEndStr = rs.getString("Discount_End");

                // Parse the dates if they are not null or empty
                if (discountStartStr != null && !discountStartStr.isEmpty()) {
                    discountStartDate = LocalDate.parse(discountStartStr);
                }
                if (discountEndStr != null && !discountEndStr.isEmpty()) {
                    discountEndDate = LocalDate.parse(discountEndStr);
                }

                Product product = new Product(makat, name, supplier, costPrice, sellingPrice, categoryId, subCategoryId, minStockAmnt);
                product.setDiscount(discount, discountStartDate, discountEndDate); // Ensure discount dates are set
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return products;
    }





}
