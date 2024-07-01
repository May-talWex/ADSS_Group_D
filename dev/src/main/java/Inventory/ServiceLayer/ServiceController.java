package src.main.java.Inventory.ServiceLayer;

import src.main.java.Inventory.DomainLayer.CategoryController;
import src.main.java.Inventory.DomainLayer.ItemController;
import src.main.java.Inventory.DomainLayer.ProductController;

import java.time.LocalDate;


public class ServiceController {
    private CategoryController categoryController;
    private ProductController productController;
    private ItemController itemController;

    public ServiceController() {
        categoryController = new CategoryController();
        productController = new ProductController();
        itemController = new ItemController();
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
        } else if (!productController.doesProductExist(productID)) {
            System.out.println("ProductID " + productID + " does not exist. Please create product first");
            return false;
        } else {
            boolean itemAdded = itemController.addNewItem(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, name, id, expireDate, categoryID, productID);
            if (itemAdded) {
                System.out.println("Item added successfully to product and inventory");
                return true;
            } else {
                System.out.println("Failed to add item to inventory");
                return false;
            }
        }
    }

    public boolean removeItem(String id) {
        if (itemController.doesItemExist(id)) {
            if (itemController.removeItem(id)) {
                System.out.println("Item removed successfully from product and inventory");
                return true;
            } else {
                System.out.println("Failed to remove item from inventory");
                return false;
            }
        } else {
            System.out.println("Item " + id + " does not exist");
            return false;
        }
    }

    public void removeExpire() {
        itemController.removeExpiredItems();
    }

    public void removeDefective() {
        itemController.removeDefectiveItems();
    }


    public boolean updateItemDefective(boolean isDefective, String itemID) {
        return itemController.reportDefectiveItem(isDefective, itemID);
    }

    // Product Service Methods
    public boolean addProduct(String makat, String name, String supplier, float costPrice, float sellingPrice, String categoryID, String subCategoryID, int minimumAmount) {
        if (!categoryController.doesCategoryExist(categoryID)) {
            System.out.println("Category doesn't exist, register a category first");
            return false;
        }

        boolean productAdded = productController.addProduct(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
        if (productAdded) {
            System.out.println("Product " + name + " added to category " + categoryID);
            return true;
        } else {
            System.out.println("Failed to add product");
            return false;
        }
    }

    public boolean removeProduct(String makat) {
        if (productController.productContainsItems(makat)) {
            System.out.println("Cannot remove product " + makat + " because it still contains items.");
            return false;
        }
        return productController.removeProduct(makat);
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

    public void generateExpiredCSVReport() {
        itemController.generateExpiredItems();
    }

    // Category Service Methods
    public boolean addCategory(String name, String id) {
        return categoryController.addCategory(id, name);
    }

    public boolean removeCategory(String id) {
        if (!categoryController.doesCategoryExist(id)) {
            System.out.println("Category ID " + id + " does not exist. Removal unsuccessful.");
            return false;
        }
        return categoryController.removeCategory(id);
    }



    public boolean updateCategory(String id, LocalDate startDiscount, LocalDate endDiscount, float discountPercentage) {
        return categoryController.updateCategory(id, startDiscount, endDiscount, discountPercentage);
    }
}
