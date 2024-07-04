package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Item;

import java.util.List;

public class ItemRepository {
    private ItemDAO itemDAO;

    public ItemRepository() {
        this.itemDAO = new ItemDAO();
    }

    public boolean insertItem(Item item) {
        return itemDAO.insertItem(item);
    }

    public boolean deleteItem(String id) {
        return itemDAO.deleteItem(id);
    }

    public Item getItemById(String id) {
        return itemDAO.getItemById(id);
    }

    public List<Item> getExpiredItems() {
        return itemDAO.getExpiredItems();
    }

    public List<Item> getDefectiveItems() {
        return itemDAO.getDefectiveItems();
    }

    public List<Item> getWarehouseItems() {
        return itemDAO.getWarehouseItems();
    }

    public List<Item> getStoreItems() {
        return itemDAO.getStoreItems();
    }

    public boolean removeExpiredItems() {
        return itemDAO.removeExpiredItems();
    }

    public boolean removeDefectiveItems() {
        return itemDAO.removeDefectiveItems();
    }

    public boolean updateItemLocation(String itemID, int floor, int building, float aisle, float shelf, boolean inWareHouse) {
        return itemDAO.updateItemLocation(itemID, floor, building, aisle, shelf, inWareHouse);
    }


    public boolean updateItemDefectiveStatus(String itemID, boolean isDefective) {
        return itemDAO.updateItemDefectiveStatus(itemID, isDefective);
    }
}
