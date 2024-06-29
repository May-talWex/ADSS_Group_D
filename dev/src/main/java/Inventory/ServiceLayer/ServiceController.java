package src.main.java.Inventory.ServiceLayer;

import src.main.java.Inventory.DomainLayer.Item;
import src.main.java.Inventory.DomainLayer.CategoryController;
import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.ItemController;
import src.main.java.Inventory.DomainLayer.ProductController;
import src.main.java.Inventory.DomainLayer.Product;
//import src.main.java.Inventory.DataLayer.DefaultDataInitializer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ServiceController {
    private CategoryController categoryController;
    private ProductController productController;
    private ItemController itemController;

    public ServiceController(boolean addDefault) {
        categoryController = new CategoryController();
        productController = new ProductController();
        itemController = new ItemController();

//        if (addDefault) {
//            DefaultDataInitializer.initializeDefaultData(categoryController, productController, itemController);
//        }
    }

    // Item Service Methods
    public boolean addItem(boolean defective, boolean inWareHouse, int floorBuilding,
                           int floorShelf, float x, float y,
                           float supplierCost, float priceNoDiscount, String name,
                           String id, LocalDate expireDate,
                           String categoryID, String productID) {
        if (!categoryController.doesCategoryExist(categoryID)) {
            System.out.println("CategoryID " + categoryID + " does not exist. Please create category first");
            return false;
        } else if (!productController.getAllProducts().contains(productController.getProduct(productID))) {
            System.out.println("ProductID " + productID + " does not exist. Please create product first");
            return false;
        } else if (itemController.addNewItem(defective, inWareHouse, floorBuilding,
                floorShelf, x, y,
                supplierCost, priceNoDiscount, name, id, expireDate,
                categoryID, productID)) {
            return true;
        }
        return false;
    }

    public void addItemsToProduct(String itemID) {
        productController.addItemsToProduct(itemID);
    }

    public void reportExpired() {
        itemController.generateExpiredItems();
    }

    public void removeExpire() {
        itemController.removeExpiredItems(productController);
    }

    public void removeDefective() {
        itemController.removeDefectiveItems(productController);
    }

    public boolean moveItemToStore(String id) {
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

    public List<Item> getWarehouseItems() {
        return itemController.getWarehouseItems();
    }

    public List<Item> getStoreItems() {
        return itemController.getStoreItems();
    }

    public boolean updateItemDefective(boolean isDefective, String itemID) {
        return itemController.reportDefectiveItem(isDefective, itemID);
    }

    // Product Service Methods
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

    public List<Product> getAllProducts() {
        return productController.getAllProducts();
    }

    public void generateStockReport() {
        productController.generateStockReport();
    }

    public void generateLowSupplyCSVReport() {
        productController.generateLowSupplyCSVReport();
    }

    public boolean updateProductDiscount(String productID, int discount, LocalDate startDate, LocalDate endDate) {
        return productController.updateProductDiscount(productID, discount, startDate, endDate);
    }

    public void generateDefectiveCSVReport() {
        itemController.generateDefectiveItemsReport();
    }

    public void generateCategoryCSVReport(String categoryID) {
        productController.generateCategoryCSVReport(categoryID);
    }

    // Category Service Methods
    public boolean addCategory(String name, String id) {
        return categoryController.addCategory(name, id);
    }

    public boolean removeCategory(String id) {
        return categoryController.removeCategory(id);
    }

    public List<Category> getCategories() {
        return categoryController.getCategories();
    }

    // Category Service Methods
    public boolean updateCategory(String id, LocalDate startDiscount, LocalDate endDiscount, float discountPercentage) {
        return categoryController.updateCategory(id, startDiscount, endDiscount, discountPercentage);
    }

}
