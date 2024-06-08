package src.main.java.Inventory.DomainLayer;

//controls the items, here we will implement for example a function that returns all expired products

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemController {

    private static HashMap<String, Item> wareHouseItems = new HashMap<>(); //items in warehouse by category ID
    private static HashMap<String, Item> storeItems = new HashMap<>();



    public ItemController() {
        wareHouseItems = new HashMap<>();
        storeItems = new HashMap<>();
    }

    public static Item getItemByID(String id) {
        if (wareHouseItems.containsKey(id)) {
            return wareHouseItems.get(id);
        } else if (storeItems.containsKey(id)) {
            return storeItems.get(id);
        } else {
            return null;
        }
    }

    public void generateExpiredItems() {
        List<Item> expiredItems = new ArrayList<>();

        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().isExpired()) {
                expiredItems.add(entry.getValue());
                System.out.println("Expired warehouse item: " + entry.getValue());
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
            generateExpiredItemsCSV(expiredItems, "C:\\githubclones\\ADSS_Group_D\\dev\\src\\main\\java\\resources\\expired_items_report.csv");
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
            System.out.println("CSV report for expired items generated successfully.");
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
            generateDefectiveItemsCSV(defectiveItems, "C:\\githubclones\\ADSS_Group_D\\dev\\src\\main\\java\\resources\\defective_items_report.csv");
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
            System.out.println("CSV report for defective items generated successfully.");
        } catch (IOException e) {
            System.out.println("Error while generating CSV report for defective items: " + e.getMessage());
        }


        // Check defective items in store
        Iterator<Map.Entry<String, Item>> iteratorStore = storeItems.entrySet().iterator();
        while (iteratorStore.hasNext()) {
            Map.Entry<String, Item> entry = iteratorStore.next();
            if (entry.getValue().defective) {
                defectiveItems.add(entry.getValue());
                System.out.println("Defective store item: " + entry.getValue());
            }
        }

        if (defectiveItems.isEmpty()) {
            System.out.println("No defective items found.");
        }
    }


    public void removeExpiredItems(ProductController productController) {
        boolean removed = false;
        // Remove expired items from inWareHouse
        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().isExpired()) {
                iteratorWarehouse.remove();
                removed = true;
            }
        }

        // Remove expired items from inStore
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


    public void removeDefectiveItems(ProductController productController) {
        boolean removed = false;
        // Remove defective items from inWareHouse
        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().defective) {
                iteratorWarehouse.remove();
                removed = true;
            }
        }

        // Remove defective items from inStore
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
        }
        return true;
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

    public boolean removeItem(String id, ProductController productController) {

        // Item exists, remove it from the appropriate HashMap
        if (wareHouseItems.containsKey(id)) {
            Item item = wareHouseItems.get(id);
            wareHouseItems.remove(id);
            System.out.println("removed succesfully from warehouse");
        }
        else if(storeItems.containsKey(id)) {
            Item item = storeItems.get(id);
            storeItems.remove(id);
            System.out.println("removed succesfully from store");
        }
        else {
            System.out.println("Item doesnt exist");
            return false;
        }

        return true; // Item removed successfully
    }


    public List<Item> getWarehouseItems() {
        return new ArrayList<>(wareHouseItems.values());
    }

    public List<Item> getStoreItems() {
        return new ArrayList<>(storeItems.values());
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

}