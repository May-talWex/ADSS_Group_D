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
        Item itemToAdd = new Item(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, name, id, expireDate, categoryID, productID);

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

    public void generateExpiredItems() {
        List<Item> expiredItems = new ArrayList<>();

        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().isExpired()) {
                expiredItems.add(entry.getValue());
            }
        }

        // Check expired items in store
        Iterator<Map.Entry<String, Item>> iteratorStore = storeItems.entrySet().iterator();
        while (iteratorStore.hasNext()) {
            Map.Entry<String, Item> entry = iteratorStore.next();
            if (entry.getValue().isExpired()) {
                expiredItems.add(entry.getValue());
            }
        }

        if (expiredItems.isEmpty()) {
            System.out.println("No expired items found.");
        } else {
            String jarDir = getJarDirectory();
            String relativePath = "dev/src/main/java/resources/expired_items_report.csv";
            String fullPath = Paths.get(jarDir, relativePath).toString();
            ensureDirectoryExists(fullPath);
            generateExpiredItemsCSV(expiredItems, fullPath);
        }
    }

    private String getProjectRootDirectory() {
        return System.getProperty("user.dir");
    }

    private void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    public void generateExpiredItemsCSV(List<Item> expiredItems, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Name,Defective,InWarehouse,FloorBuilding,FloorShelf,X,Y,SupplierCost,PriceNoDiscount,ExpireDate,CategoryID,ProductID\n");
            for (Item item : expiredItems) {
                writer.append(item.getID()).append(',')
                        .append(item.getName()).append(',')
                        .append(String.valueOf(item.defective)).append(',')
                        .append(String.valueOf(item.inWareHouse)).append(',')
                        .append(String.valueOf(item.floorBuilding)).append(',')
                        .append(String.valueOf(item.floorShelf)).append(',')
                        .append(String.valueOf(item.x)).append(',')
                        .append(String.valueOf(item.y)).append(',')
                        .append(String.valueOf(item.supplierCost)).append(',')
                        .append(String.valueOf(item.priceNoDiscount)).append(',')
                        .append(item.expireDate.toString()).append(',')
                        .append(item.categoryID).append(',')
                        .append(item.productID).append('\n');
            }
            System.out.println("CSV report for expired items generated successfully in the path" + filePath + ".");
        } catch (IOException e) {
            System.out.println("Error while generating CSV report for expired items: " + e.getMessage());
        }
    }

    public void generateDefectiveItemsReport() {
        List<Item> defectiveItems = new ArrayList<>();

        // Check defective items in warehouse
        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().defective) {
                defectiveItems.add(entry.getValue());
            }
        }

        // Check defective items in store
        Iterator<Map.Entry<String, Item>> iteratorStore = storeItems.entrySet().iterator();
        while (iteratorStore.hasNext()) {
            Map.Entry<String, Item> entry = iteratorStore.next();
            if (entry.getValue().defective) {
                defectiveItems.add(entry.getValue());
            }
        }

        if (defectiveItems.isEmpty()) {
            System.out.println("No defective items found.");
        } else {
            String jarDir = getJarDirectory();
            String relativePath = "dev/src/main/java/resources/defective_items_report.csv";
            String fullPath = Paths.get(jarDir, relativePath).toString();
            ensureDirectoryExists(fullPath);
            generateDefectiveItemsCSV(defectiveItems, fullPath);
        }
    }

    private String getJarDirectory() {
        try {
            String jarPath = ProductController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return new File(jarPath).getParentFile().getParentFile().getParentFile().getParent(); // Adjust according to your directory structure
        } catch (Exception e) {
            throw new RuntimeException("Unable to determine JAR file directory", e);
        }
    }

    public void generateDefectiveItemsCSV(List<Item> defectiveItems, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Name,Defective,InWarehouse,FloorBuilding,FloorShelf,X,Y,SupplierCost,PriceNoDiscount,ExpireDate,CategoryID,ProductID\n");
            for (Item item : defectiveItems) {
                writer.append(item.getID()).append(',')
                        .append(item.getName()).append(',')
                        .append(String.valueOf(item.defective)).append(',')
                        .append(String.valueOf(item.inWareHouse)).append(',')
                        .append(String.valueOf(item.floorBuilding)).append(',')
                        .append(String.valueOf(item.floorShelf)).append(',')
                        .append(String.valueOf(item.x)).append(',')
                        .append(String.valueOf(item.y)).append(',')
                        .append(String.valueOf(item.supplierCost)).append(',')
                        .append(String.valueOf(item.priceNoDiscount)).append(',')
                        .append(item.expireDate.toString()).append(',')
                        .append(item.categoryID).append(',')
                        .append(item.productID).append('\n');
            }
            System.out.println("CSV report for defective items generated successfully in the path" + filePath + ".");
        } catch (IOException e) {
            System.out.println("Error while generating CSV report for defective items: " + e.getMessage());
        }
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
