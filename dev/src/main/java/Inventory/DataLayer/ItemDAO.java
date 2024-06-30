package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Item;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection connection;

    public ItemDAO() {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    public boolean insertItem(Item item) {
        String sql = "INSERT INTO Item (id, name, defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, expireDate, categoryID, productID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getID());
            pstmt.setString(2, item.getName());
            pstmt.setBoolean(3, item.defective);
            pstmt.setBoolean(4, item.inWareHouse);
            pstmt.setInt(5, item.floorBuilding);
            pstmt.setInt(6, item.floorShelf);
            pstmt.setFloat(7, item.x);
            pstmt.setFloat(8, item.y);
            pstmt.setFloat(9, item.supplierCost);
            pstmt.setFloat(10, item.priceNoDiscount);
            pstmt.setDate(11, Date.valueOf(item.expireDate));
            pstmt.setString(12, item.categoryID);
            pstmt.setString(13, item.productID);
            pstmt.executeUpdate();
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
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Item getItemById(String id) {
        String sql = "SELECT * FROM Item WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                boolean defective = rs.getBoolean("defective");
                boolean inWareHouse = rs.getBoolean("inWareHouse");
                int floorBuilding = rs.getInt("floorBuilding");
                int floorShelf = rs.getInt("floorShelf");
                float x = rs.getFloat("x");
                float y = rs.getFloat("y");
                float supplierCost = rs.getFloat("supplierCost");
                float priceNoDiscount = rs.getFloat("priceNoDiscount");
                LocalDate expireDate = rs.getDate("expireDate").toLocalDate();
                String name = rs.getString("name");
                String categoryID = rs.getString("categoryID");
                String productID = rs.getString("productID");

                return new Item(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, name, id, expireDate, categoryID, productID);
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
                defectiveItems.add(createItemFromResultSet(rs));
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
        int floorBuilding = rs.getInt("floorBuilding");
        int floorShelf = rs.getInt("floorShelf");
        float x = rs.getFloat("x");
        float y = rs.getFloat("y");
        float supplierCost = rs.getFloat("supplierCost");
        float priceNoDiscount = rs.getFloat("priceNoDiscount");
        LocalDate expireDate = rs.getDate("expireDate").toLocalDate();
        String name = rs.getString("name");
        String id = rs.getString("id");
        String categoryID = rs.getString("categoryID");
        String productID = rs.getString("productID");

        return new Item(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, name, id, expireDate, categoryID, productID);
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

    public boolean updateItemLocation(Item item) {
        String sql = "UPDATE Item SET inWareHouse = ?, floorBuilding = ?, floorShelf = ?, x = ?, y = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, item.inWareHouse);
            pstmt.setInt(2, item.floorBuilding);
            pstmt.setInt(3, item.floorShelf);
            pstmt.setFloat(4, item.x);
            pstmt.setFloat(5, item.y);
            pstmt.setString(6, item.getID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateItemDefectiveStatus(String itemID, boolean isDefective) {
        String sql = "UPDATE Item SET defective = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, isDefective);
            pstmt.setString(2, itemID);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
