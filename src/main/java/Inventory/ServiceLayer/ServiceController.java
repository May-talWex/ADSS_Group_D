package Inventory.ServiceLayer;

import Inventory.DomainLayer.Item;
import Inventory.DomainLayer.CategoryController;
import Inventory.DomainLayer.Category;
import Inventory.DomainLayer.ItemController;
import Inventory.DomainLayer.ProductController;
import Inventory.DomainLayer.Product;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ServiceController {
    public CategoryController categoryController = new CategoryController();
    public ProductController productController = new ProductController();
    public ItemController itemController = new ItemController();

    public ServiceController() {
        categoryController = new CategoryController();
        productController = new ProductController();
        itemController = new ItemController();

        System.out.println("");
        System.out.println("Start of adding deafult info");
        categoryController.addDefaultCategory();
        productController.addDefaultProduct();
        itemController.addDefaultItem();         //So there is something already in the database
        System.out.println("End of adding deafult info");
    }

    //item service
    public boolean addItem(boolean defective, boolean inWareHouse,int floorBuilding,
                           int floorShelf, float x, float y,
                           float supplierCost, float priceNoDiscount, String name,
                           String id, LocalDate expireDate,
                           String categoryID, String productID){
        if(itemController.addNewItem(defective,inWareHouse,floorBuilding,
                floorShelf,x,y,
                supplierCost,priceNoDiscount,name,id, expireDate,
                categoryID,productID)){
            productController.addItemToProduct(id);
            return true;
        }
        return false;
    }

    public void reportExpired(){
        itemController.reportExpiredItems();
    }

    public void generateStockReport() {
        productController.generateStockReport();
    }

    public void removeExpire(){
        itemController.removeExpired(productController);
    }

    public boolean moveItemToStore(String id){
        return itemController.moveItemToStore(id);
    }

    public boolean removeItemFromProduct(String itemID) {
        return productController.removeItemFromProduct(itemID);
    }


    public boolean removeItem(String id) {
        boolean removedFromProduct = productController.removeItemFromProduct(id);
        boolean removedFromInventory = itemController.removeItem(id, productController);
        return removedFromProduct && removedFromInventory;
    }


    public List<Product> getAllProducts() {
        return productController.getAllProducts();
    }

    public List<Item> getWarehouseItems() {
        return itemController.getWarehouseItems();
    }

    public List<Item> getStoreItems() {
        return itemController.getStoreItems();
    }


    //product service

    public boolean addNewProduct(String makat, String name, String supplier, double costPrice, double sellingPrice, String categoryID, String subCategoryID, int minimumAmount) {
        if (categoryController.doesCategoryExist(categoryID)) {
            boolean productAdded = productController.addProduct(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
            if (productAdded) {
                Category category = categoryController.getCategoryByID(categoryID);
                Product product = productController.getProduct(makat);
                if (category != null && product != null) {
                    category.addProduct(product);
                    System.out.println("Product " + name + " added to category " + categoryID);
                    return true;
                }
            }
        } else {
            System.out.println("Category doesn't exist, register a category first");
        }
        return false;
    }

    public boolean removeProduct(String makat) {
        Product product = productController.getProduct(makat);
        if (product != null) {
            if (!product.getItems().isEmpty()) {
                System.out.println("Cannot remove product " + makat + " because it still contains items.");
                return false;
            }
            String categoryID = product.getCategoryID();
            if (categoryID != null && !categoryID.isEmpty()) {
                boolean removedFromCategory = categoryController.removeProductFromCategory(categoryID, product);
                if (removedFromCategory) {
                    return productController.removeProduct(makat);
                } else {
                    System.out.println("Failed to remove product from category.");
                }
            } else {
                System.out.println("Product category ID is null or empty.");
            }
        } else {
            System.out.println("Product not found.");
        }
        return false;
    }




    //category service
    public boolean addCategory(String name, String id) {

        return categoryController.addCategory(name, id);


    }

    public boolean removeCategory(String id) {
        return categoryController.removeCategory(id);
    }

    public void clearCategories() {
        categoryController.clearCategories();
    }

    public HashMap<String, Category> getCategories() {
        return categoryController.getCategories();
    }


}

