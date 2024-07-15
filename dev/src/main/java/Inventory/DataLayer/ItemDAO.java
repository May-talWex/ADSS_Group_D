package Inventory.DataLayer;

import Inventory.DomainLayer.Item;
import Inventory.DomainLayer.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ItemDAO {
    private Connection connection;

    public ItemDAO() {
        this.connection = DataBaseConnection.getConnection();
    }
    public boolean insertItem(Item item) {
        String sql = "INSERT INTO Item(id, name, defective, inWareHouse, floor, branchID, aisle, shelf, expireDate, categoryID, productID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getID());
            pstmt.setString(2, item.getName());
            pstmt.setBoolean(3, item.defective);
            pstmt.setBoolean(4, item.inWareHouse);
            pstmt.setInt(5, item.floor);
            pstmt.setInt(6, item.branchID);
            pstmt.setFloat(7, item.aisle);
            pstmt.setFloat(8, item.shelf);
            pstmt.setString(9, item.expireDate.toString());  // Ensure correct date format
            pstmt.setString(10, item.categoryID);
            pstmt.setString(11, item.productID);
            pstmt.executeUpdate();
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(item.getProductID());
            if (product != null) {
                product.getItems().add(item);
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteItem(String id) {
        String sql = "DELETE FROM Item WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            Item item = getItemById(id);
            if (item != null) {
                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.getProductById(item.getProductID());
                if (product != null) {
                    product.getItems().remove(item);
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateItemDefectiveStatus(String itemID, boolean isDefective) {
        String query = "UPDATE Item SET defective = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, isDefective);
            statement.setString(2, itemID);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Item getItemById(String id) {
        String sql = "SELECT * FROM Item WHERE id = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                boolean defective = rs.getBoolean("defective");
                boolean inWareHouse = rs.getBoolean("inWareHouse");
                int floor = rs.getInt("floor");
                int branchID = rs.getInt("branchID");
                float aisle = rs.getFloat("aisle");
                float shelf = rs.getFloat("shelf");
                String name = rs.getString("name");
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

                return new Item(defective, inWareHouse, floor, branchID, aisle, shelf, name, id, expireDate, categoryID, productID);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Item> getExpiredItems() {
        List<Item> expiredItems = new ArrayList<>();
        String sql = "SELECT * FROM Item WHERE expireDate < CURRENT_DATE";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                expiredItems.add(createItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expiredItems;
    }

    public List<Item> getDefectiveItems() {
        List<Item> defectiveItems = new ArrayList<>();
        String sql = "SELECT * FROM Item WHERE defective = true";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                boolean defective = rs.getBoolean("defective");
                boolean inWareHouse = rs.getBoolean("inWareHouse");
                int floor = rs.getInt("floor");
                int branchID = rs.getInt("branchID");
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

                Item item = new Item(defective, inWareHouse, floor, branchID, aisle, shelf, name, id, expireDate, categoryID, productID);
                defectiveItems.add(item);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return defectiveItems;
    }

    public List<Item> getWarehouseItems() {
        return getItemsByLocation(true);
    }

    public List<Item> getStoreItems() {
        return getItemsByLocation(false);
    }

    private List<Item> getItemsByLocation(boolean inWareHouse) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM Item WHERE inWareHouse = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, inWareHouse);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                items.add(createItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    private Item createItemFromResultSet(ResultSet rs) throws SQLException {
        boolean defective = rs.getBoolean("defective");
        boolean inWareHouse = rs.getBoolean("inWareHouse");
        int floor = rs.getInt("floor");
        int branchID = rs.getInt("branchID");
        float aisle = rs.getFloat("aisle");
        float shelf = rs.getFloat("shelf");
        LocalDate expireDate = rs.getDate("expireDate").toLocalDate();
        String name = rs.getString("name");
        String id = rs.getString("id");
        String categoryID = rs.getString("categoryID");
        String productID = rs.getString("productID");

        return new Item(defective, inWareHouse, floor, branchID, aisle, shelf, name, id, expireDate, categoryID, productID);
    }

    public boolean removeExpiredItems() {
        String sql = "DELETE FROM Item WHERE expireDate < CURRENT_DATE";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removeDefectiveItems() {
        String sql = "DELETE FROM Item WHERE defective = true";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateItemLocation(String itemID, int floor, int branchID, float aisle, float shelf, boolean inWareHouse) {
        String query = "UPDATE Item SET floor = ?, branchID = ?, aisle = ?, shelf = ?, inWareHouse = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, floor);
            statement.setInt(2, branchID);
            statement.setFloat(3, aisle);
            statement.setFloat(4, shelf);
            statement.setBoolean(5, inWareHouse);
            statement.setString(6, itemID);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
