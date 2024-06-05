package Inventory.DomainLayer;

//controls the items, here we will implement for example a function that returns all expired products

import java.util.HashMap;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.*;

public class ItemController {

    private static HashMap<String, Item> wareHouseItems = new HashMap<>(); //items in warehouse by category ID
    private static HashMap<String, Item> storeItems = new HashMap<>();



    public ItemController() {
        HashMap<String, Item> wareHouseItems = new HashMap<>();
        HashMap<String, Item> storeItems = new HashMap<>();
    }

    public void addDefaultItem() {
        // Add non-expired item
        boolean defective = false;
        boolean inWareHouse = true;
        int floorBuilding = 3;
        int floorShelf = 3;
        float x = 3.3f;
        float y = 3.3f;
        float supplierCost = 3.3f;
        float priceNoDiscount = 4.5f;
        String name = "Milk 3% Tnuva";
        String id = "Milk3Tnuva1";
        LocalDate expireDate = LocalDate.now().plusMonths(3);
        String categoryID = "MP493";
        String productID = "Milk176";

        addNewItem(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, name, id, expireDate, categoryID, productID);

        // Add expired item
        String expiredName = "Milk 3% Tnuva";
        String expiredId = "Milk3Tnuva2";
        LocalDate expiredDate = LocalDate.now().minusDays(1);

        addNewItem(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, expiredName, expiredId, expiredDate, categoryID, productID);
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

    public void reportExpiredItems() {
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
                System.out.println("Expired store item: " + entry.getValue());
            }
        }
    }

    public void reportDefectionItems() {
        List<Item> defectiveItems = new ArrayList<>();

        // Check defective items in warehouse
        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().defective) {
                defectiveItems.add(entry.getValue());
                System.out.println("Defective warehouse item: " + entry.getValue());
            }
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
    }


    public void removeExpired(ProductController productController) {
        // Remove expired items from inWareHouse
        Iterator<Map.Entry<String, Item>> iteratorWarehouse = wareHouseItems.entrySet().iterator();
        while (iteratorWarehouse.hasNext()) {
            Map.Entry<String, Item> entry = iteratorWarehouse.next();
            if (entry.getValue().isExpired()) {
                productController.getProduct(entry.getValue().productID).setItemAmount(productController.getProduct(entry.getValue().productID).getItemAmount() - 1);
                iteratorWarehouse.remove();
            }
        }

        // Remove expired items from inStore
        Iterator<Map.Entry<String, Item>> iteratorStore = storeItems.entrySet().iterator();
        while (iteratorStore.hasNext()) {
            Map.Entry<String, Item> entry = iteratorStore.next();
            if (entry.getValue().isExpired()) {
                productController.getProduct(entry.getValue().productID).setItemAmount(productController.getProduct(entry.getValue().productID).getItemAmount() - 1);
                iteratorStore.remove();
            }
        }
    }



    public boolean addNewItem(boolean defective, boolean inWareHouse, int floorBuilding, int floorShelf, float x, float y, float supplierCost, float priceNoDiscount, String name, String id, LocalDate expireDate, String categoryID, String productID) {
        Item itemToAdd = new Item(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, name, id, expireDate, categoryID, productID);

        if (wareHouseItems.containsKey(itemToAdd.id) || storeItems.containsKey(itemToAdd.id)) {
            System.out.println("Item with same ID already exists");
            return false;
        } else {
            if (inWareHouse) {
                wareHouseItems.put(itemToAdd.id, itemToAdd);
                System.out.println("Item added successfully to warehouse");
            } else {
                storeItems.put(itemToAdd.id, itemToAdd);
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
            productController.getProduct(item.productID).setItemAmount(productController.getProduct(item.productID).getItemAmount() - 1);
            wareHouseItems.remove(id);
            System.out.println("removed succesfully");
        }
        else if(storeItems.containsKey(id)) {
            Item item = storeItems.get(id);
            productController.getProduct(item.productID).setItemAmount(productController.getProduct(item.productID).getItemAmount() - 1);
            storeItems.remove(id);
            System.out.println("removed succesfully");
        }
        else {
            System.out.println("Item doesnt exist");
            return false;
        }

        return true; // Item removed successfully
    }

    public boolean setDefective(String id) {
        Item item = getItemByID(id);
        if (item != null) {
            item.defective = true;
            return true;
        } else {
            System.out.println("Item doesn't exist");
            return false;
        }
    }

    public List<Item> getWarehouseItems() {
        return new ArrayList<>(wareHouseItems.values());
    }

    public List<Item> getStoreItems() {
        return new ArrayList<>(storeItems.values());
    }


}