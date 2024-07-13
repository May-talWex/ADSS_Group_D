package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.Item;
import src.main.java.Inventory.DomainLayer.Product;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDAO {
    private Connection connection;

    public ProductDAO() {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    public List<Product> getAllProductsWithItems() {
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
                product.setItems(getItemsByProductId(makat));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
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
            CategoryDAO categoryDAO = new CategoryDAO();
            Category category = categoryDAO.getCategoryById(product.getCategoryID());
            if (category != null) {
                category.getProducts().add(product);
            }
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

            // Remove from the category's product list
            Product product = getProductById(makat);
            if (product != null) {
                CategoryDAO categoryDAO = new CategoryDAO();
                Category category = categoryDAO.getCategoryById(product.getCategoryID());
                if (category != null) {
                    category.getProducts().remove(product);
                }
            }
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

    public ArrayList<Item> getItemsByProductId(String productId) {
        String sql = "SELECT * FROM Item WHERE productID = ?";
        ArrayList<Item> items = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                boolean defective = rs.getBoolean("defective");
                boolean inWareHouse = rs.getBoolean("inWareHouse");
                int floor = rs.getInt("floor");
                int building = rs.getInt("building");
                float aisle = rs.getFloat("aisle");
                float shelf = rs.getFloat("shelf");
                String name = rs.getString("name");
                String id = rs.getString("id");
                String expireDateStr = rs.getString("expireDate");
                LocalDate expireDate = null;
                if (expireDateStr != null) {
                    try {
                        expireDate = LocalDate.parse(expireDateStr);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error parsing expireDate for item ID: " + id);
                        e.printStackTrace();
                    }
                }
                String categoryID = rs.getString("categoryID");
                String productID = rs.getString("productID");

                Item item = new Item(defective, inWareHouse, floor, building, aisle, shelf, name, id, expireDate, categoryID, productID);
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

    public List<Map<String, String>> getProductsByCategoryIdWithItemAmounts(String categoryID) {
        String sql = "SELECT p.makat, p.name, p.supplier, p.costPrice, p.Selling_Price, p.discount, p.Min_Stock_Amnt, " +
                "p.Category_ID, p.Sub_Category_ID, p.Full_Price, COUNT(i.id) AS itemCount " +
                "FROM Product p LEFT JOIN Item i ON p.makat = i.productID " +
                "WHERE p.Category_ID = ? " +
                "GROUP BY p.makat, p.name, p.supplier, p.costPrice, p.Selling_Price, p.discount, p.Min_Stock_Amnt, " +
                "p.Category_ID, p.Sub_Category_ID, p.Full_Price";

        List<Map<String, String>> productsData = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> productData = new HashMap<>();
                productData.put("Makat", rs.getString("makat"));
                productData.put("Name", rs.getString("name"));
                productData.put("Supplier", rs.getString("supplier"));
                productData.put("Cost Price", String.valueOf(rs.getFloat("costPrice")));
                productData.put("Selling Price", String.valueOf(rs.getFloat("Selling_Price")));
                productData.put("Discount", String.valueOf(rs.getFloat("discount")));
                productData.put("Minimum Amount", String.valueOf(rs.getInt("Min_Stock_Amnt")));
                productData.put("Category ID", rs.getString("Category_ID"));
                productData.put("Sub Category ID", rs.getString("Sub_Category_ID"));
                productData.put("Full Price", String.valueOf(rs.getFloat("Full_Price")));
                productData.put("Item Amount", String.valueOf(rs.getInt("itemCount")));
                productsData.add(productData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productsData;
    }

    public List<Map<String, String>> getAllProductsWithItemAmounts() {
        String sql = "SELECT p.makat, p.name, p.supplier, p.costPrice, p.Selling_Price, p.discount, p.Min_Stock_Amnt, " +
                "p.Category_ID, p.Sub_Category_ID, p.Full_Price, COUNT(i.id) AS itemCount " +
                "FROM Product p LEFT JOIN Item i ON p.makat = i.productID " +
                "GROUP BY p.makat, p.name, p.supplier, p.costPrice, p.Selling_Price, p.discount, p.Min_Stock_Amnt, " +
                "p.Category_ID, p.Sub_Category_ID, p.Full_Price";

        List<Map<String, String>> productsData = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, String> productData = new HashMap<>();
                productData.put("Makat", rs.getString("makat"));
                productData.put("Name", rs.getString("name"));
                productData.put("Supplier", rs.getString("supplier"));
                productData.put("Cost Price", String.valueOf(rs.getFloat("costPrice")));
                productData.put("Selling Price", String.valueOf(rs.getFloat("Selling_Price")));
                productData.put("Discount", String.valueOf(rs.getFloat("discount")));
                productData.put("Minimum Amount", String.valueOf(rs.getInt("Min_Stock_Amnt")));
                productData.put("Category ID", rs.getString("Category_ID"));
                productData.put("Sub Category ID", rs.getString("Sub_Category_ID"));
                productData.put("Full Price", String.valueOf(rs.getFloat("Full_Price")));
                productData.put("Item Amount", String.valueOf(rs.getInt("itemCount")));
                productsData.add(productData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productsData;
    }



}
