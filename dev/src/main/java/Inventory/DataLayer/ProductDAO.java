package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Item;
import src.main.java.Inventory.DomainLayer.Product;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


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

    public boolean updateProduct(Product product) {
        String sql = "UPDATE Product SET discount = ?, Discount_Start = ?, Discount_End = ?, Selling_Price = ? WHERE makat = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setFloat(1, product.getDiscount());
            pstmt.setString(2, product.getStartDiscount().toString());
            pstmt.setString(3, product.getEndDiscount().toString());
            pstmt.setFloat(4, product.getSellingPrice());
            pstmt.setString(5, product.getMakat());
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

                Product product = new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
                product.setItems(getItemsByProductId(makat));
                return product;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ArrayList<Item> getItemsByProductId(String productId) {
        String sql = "SELECT * FROM Item WHERE productID = ?";
        ArrayList<Item> items = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, productId);
            System.out.println("Executing query with productID: " + productId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                boolean defective = rs.getBoolean("defective");
                boolean inWareHouse = rs.getBoolean("inWareHouse");
                int floorBuilding = rs.getInt("floorBuilding");
                int floorShelf = rs.getInt("floorShelf");
                float x = rs.getFloat("x");
                float y = rs.getFloat("y");
                String name = rs.getString("name");
                String id = rs.getString("id");
                String expireDateStr = rs.getString("expireDate");
                LocalDate expireDate = null;
                if (expireDateStr != null) {
                    try {
                        expireDate = LocalDate.parse(expireDateStr, formatter);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error parsing expireDate for item ID: " + id);
                        e.printStackTrace();
                    }
                }
                String categoryID = rs.getString("categoryID");
                String productID = rs.getString("productID");

                System.out.println("Found item: " + id);

                Item item = new Item(defective, inWareHouse, floorBuilding, floorShelf, x, y, name, id, expireDate, categoryID, productID);
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return items;
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

                Product product = new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
                product.setItems(getItemsByProductId(makat));  // Set the items for the product
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

}
