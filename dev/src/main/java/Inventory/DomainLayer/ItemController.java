package src.main.java.Inventory.DomainLayer;

import src.main.java.Inventory.DataLayer.ItemRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
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

    public boolean addNewItem(boolean defective, boolean inWareHouse, int floorBuilding, int floorShelf, float x, float y, float supplierCost, float priceNoDiscount, String name, String id, LocalDate expireDate, String categoryID, String productID) {
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

    public boolean doesItemExist(String id) {
        return itemRepository.getItemById(id) != null;
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
                itemData.put("FloorBuilding", String.valueOf(item.floorBuilding));
                itemData.put("FloorShelf", String.valueOf(item.floorShelf));
                itemData.put("X", String.valueOf(item.x));
                itemData.put("Y", String.valueOf(item.y));
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
                itemData.put("FloorBuilding", String.valueOf(item.floorBuilding));
                itemData.put("FloorShelf", String.valueOf(item.floorShelf));
                itemData.put("X", String.valueOf(item.x));
                itemData.put("Y", String.valueOf(item.y));
                itemData.put("ExpireDate", item.getExpireDate().toString());
                data.add(itemData);
            }
        }

        return data;
    }


    private String getJarDirectory() {
        try {
            String jarPath = ProductController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return new File(jarPath).getParentFile().getParentFile().getParentFile().getParent(); // Adjust according to your directory structure
        } catch (Exception e) {
            throw new RuntimeException("Unable to determine JAR file directory", e);
        }
    }

    public List<Map<String, String>> generateDefectiveItemsReportData() {
        List<Map<String, String>> data = new ArrayList<>();

        // Collect defective items from warehouse
        for (Item item : wareHouseItems.values()) {
            if (item.defective) {
                Map<String, String> itemData = new HashMap<>();
                itemData.put("ID", item.getID());
                itemData.put("Name", item.getName());
                itemData.put("CategoryID", item.getCategoryID());
                itemData.put("ProductID", item.getProductID());
                itemData.put("FloorBuilding", String.valueOf(item.floorBuilding));
                itemData.put("FloorShelf", String.valueOf(item.floorShelf));
                itemData.put("X", String.valueOf(item.x));
                itemData.put("Y", String.valueOf(item.y));
                itemData.put("ExpireDate", item.getExpireDate().toString());
                data.add(itemData);
            }
        }

        // Collect defective items from store
        for (Item item : storeItems.values()) {
            if (item.defective) {
                Map<String, String> itemData = new HashMap<>();
                itemData.put("ID", item.getID());
                itemData.put("Name", item.getName());
                itemData.put("CategoryID", item.getCategoryID());
                itemData.put("ProductID", item.getProductID());
                itemData.put("FloorBuilding", String.valueOf(item.floorBuilding));
                itemData.put("FloorShelf", String.valueOf(item.floorShelf));
                itemData.put("X", String.valueOf(item.x));
                itemData.put("Y", String.valueOf(item.y));
                itemData.put("ExpireDate", item.getExpireDate().toString());
                data.add(itemData);
            }
        }

        return data;
    }


    public boolean moveItemToStore(String id){
        if(storeItems.containsKey(id)){
            System.out.println("Error item already in the store");
            return false;
        }
        else if(!wareHouseItems.containsKey(id)){
            System.out.println("Error item doesnt exist in ware house");
            return false;
        }
        else{
            System.out.println("Item was moved");
            Item item = wareHouseItems.remove(id);
            storeItems.put(item.id, item);
            return true;
        }
    }


    public boolean reportDefectiveItem(boolean isDefective, String itemID){
        Item item = getItemByID(itemID);
        if(item == null){
            System.out.println("Item not found");
            return false;
        }
        item.defective = isDefective;
        return true;
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
