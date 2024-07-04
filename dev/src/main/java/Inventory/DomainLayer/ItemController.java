package src.main.java.Inventory.DomainLayer;

import src.main.java.Inventory.DataLayer.ItemRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemController {

    private static HashMap<String, Item> wareHouseItems = new HashMap<>(); //items in warehouse by category ID
    private static HashMap<String, Item> storeItems = new HashMap<>();
    private ItemRepository itemRepository;

    public ItemController() {
        wareHouseItems = new HashMap<>();
        storeItems = new HashMap<>();
        itemRepository = new ItemRepository();
    }

    public Item getItemByID(String id) {
        return itemRepository.getItemById(id);
    }

    public boolean addNewItem(boolean defective, boolean inWareHouse, int floorBuilding, int floorShelf, float x, float y, String name, String id, LocalDate expireDate, String categoryID, String productID) {
        Item itemToAdd = new Item(defective, inWareHouse, floorBuilding, floorShelf, x, y, name, id, expireDate, categoryID, productID);

        if (wareHouseItems.containsKey(itemToAdd.getID()) || storeItems.containsKey(itemToAdd.getID())) {
            System.out.println("Item with same ID already exists");
            return false;
        } else {
            if (inWareHouse) {
                wareHouseItems.put(itemToAdd.getID(), itemToAdd);
                System.out.println("Item added successfully to warehouse");
            } else {
                storeItems.put(itemToAdd.getID(), itemToAdd);
                System.out.println("Item added successfully to store");
            }
            return itemRepository.insertItem(itemToAdd);
        }
    }

    public boolean removeItem(String id) {
        Item item = getItemByID(id);
        if (item == null) {
            System.out.println("Item " + id + " does not exist in inventory");
            return false;
        }
        if (item.inWareHouse) {
            wareHouseItems.remove(id);
        } else {
            storeItems.remove(id);
        }
        return itemRepository.deleteItem(id);
    }

    public List<Map<String, String>> generateExpiredItemsReportData() {
        List<Map<String, String>> data = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Collect expired items from warehouse
        for (Item item : wareHouseItems.values()) {
            if (item.getExpireDate().isBefore(today)) {
                Map<String, String> itemData = new HashMap<>();
                itemData.put("ID", item.getID());
                itemData.put("Name", item.getName());
                itemData.put("CategoryID", item.getCategoryID());
                itemData.put("ProductID", item.getProductID());
                itemData.put("FloorBuilding", String.valueOf(item.floor));
                itemData.put("FloorShelf", String.valueOf(item.building));
                itemData.put("X", String.valueOf(item.aisle));
                itemData.put("Y", String.valueOf(item.shelf));
                itemData.put("ExpireDate", item.getExpireDate().toString());
                data.add(itemData);
            }
        }

        // Collect expired items from store
        for (Item item : storeItems.values()) {
            if (item.getExpireDate().isBefore(today)) {
                Map<String, String> itemData = new HashMap<>();
                itemData.put("ID", item.getID());
                itemData.put("Name", item.getName());
                itemData.put("CategoryID", item.getCategoryID());
                itemData.put("ProductID", item.getProductID());
                itemData.put("FloorBuilding", String.valueOf(item.floor));
                itemData.put("FloorShelf", String.valueOf(item.building));
                itemData.put("X", String.valueOf(item.aisle));
                itemData.put("Y", String.valueOf(item.shelf));
                itemData.put("ExpireDate", item.getExpireDate().toString());
                data.add(itemData);
            }
        }

        return data;
    }


    public List<Map<String, String>> generateDefectiveItemsReportData() {
        List<Map<String, String>> data = new ArrayList<>();
        List<Item> defectiveItems = itemRepository.getDefectiveItems();

        for (Item item : defectiveItems) {
            Map<String, String> itemData = new HashMap<>();
            itemData.put("ID", item.getID());
            itemData.put("Name", item.getName());
            itemData.put("CategoryID", item.getCategoryID());
            itemData.put("ProductID", item.getProductID());
            itemData.put("FloorBuilding", String.valueOf(item.floor));
            itemData.put("FloorShelf", String.valueOf(item.building));
            itemData.put("X", String.valueOf(item.aisle));
            itemData.put("Y", String.valueOf(item.shelf));
            itemData.put("ExpireDate", item.getExpireDate().toString());
            data.add(itemData);
        }

        return data;
    }

    public boolean reportDefectiveItem(boolean isDefective, String itemID) {
        Item item = getItemByID(itemID);
        if (item == null) {
            System.out.println("Item not found");
            return false;
        }
        item.setIsDefective(isDefective);
        return itemRepository.updateItemDefectiveStatus(itemID, isDefective);
    }

    public boolean updateItemLocation(String itemID, int floor, int building, float aisle, float shelf, boolean inWareHouse) {
        return itemRepository.updateItemLocation(itemID, floor, building, aisle, shelf, inWareHouse);
    }

    public boolean isItemInWarehouse(String itemID) {
        Item item = getItemByID(itemID);
        return item != null && item.inWareHouse;
    }

    public boolean doesItemExist(String itemID) {
        return getItemByID(itemID) != null;
    }

    public void removeExpiredItems() {
        boolean removed = false;
        // Remove expired items from warehouse
        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().isExpired()) {
                iteratorWarehouse.remove();
                removed = true;
            }
        }

        // Remove expired items from store
        Iterator<Map.Entry<String, Item>> iteratorStore = storeItems.entrySet().iterator();
        while (iteratorStore.hasNext()) {
            Map.Entry<String, Item> entry = iteratorStore.next();
            if (entry.getValue().isExpired()) {
                iteratorStore.remove();
                removed = true;
            }
        }

        if (!removed) {
            System.out.println("No expired items found.");
        } else {
            System.out.println("Expired items removed successfully.");
        }
    }

    public void removeDefectiveItems() {
        boolean removed = false;
        // Remove defective items from warehouse
        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().defective) {
                iteratorWarehouse.remove();
                removed = true;
            }
        }

        // Remove defective items from store
        Iterator<Map.Entry<String, Item>> iteratorStore = storeItems.entrySet().iterator();
        while (iteratorStore.hasNext()) {
            Map.Entry<String, Item> entry = iteratorStore.next();
            if (entry.getValue().defective) {
                iteratorStore.remove();
                removed = true;
            }
        }

        if (!removed) {
            System.out.println("No defective items found.");
        } else {
            System.out.println("Defective items removed successfully.");
        }
    }

}
