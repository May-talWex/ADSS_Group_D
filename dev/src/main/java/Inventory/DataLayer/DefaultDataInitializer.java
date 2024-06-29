package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.Item;
import src.main.java.Inventory.DomainLayer.Product;
import src.main.java.Inventory.DomainLayer.CategoryController;
import src.main.java.Inventory.DomainLayer.ItemController;
import src.main.java.Inventory.DomainLayer.ProductController;

import java.time.LocalDate;

public class DefaultDataInitializer {

    public static void initializeDefaultData(CategoryController categoryController, ProductController productController, ItemController itemController) {
        System.out.println("");
        System.out.println("Start of adding default info");

        // Add categories
        Category dairyCategory = addCategory(categoryController, "DP493", "Dairy Products");
        Category bakeryCategory = addCategory(categoryController, "BK123", "Bakery Products");

        // Add products to categories
        Product milkProduct = addProduct(productController, "Milk176", "Milk", "Tnuva", 3.33, 4.5, "DP493", "Milk", 3);
        Product cheeseProduct = addProduct(productController, "Cheese789", "Cheese", "Tnuva", 10.0, 12.5, "DP493", "Cheese", 2);
        Product breadProduct = addProduct(productController, "Bread001", "Bread", "Baker", 1.0, 2.0, "BK123", "Bread", 5);
        Product croissantProduct = addProduct(productController, "Croissant002", "Croissant", "Baker", 2.0, 3.0, "BK123", "Pastry", 4);

        // Link products to categories
        categoryController.addProductToCategory(dairyCategory.getID(), milkProduct);
        categoryController.addProductToCategory(dairyCategory.getID(), cheeseProduct);
        categoryController.addProductToCategory(bakeryCategory.getID(), breadProduct);
        categoryController.addProductToCategory(bakeryCategory.getID(), croissantProduct);

        // Add items to products
        addItem(itemController, productController, "Milk3Tnuva1", "Milk 3% Tnuva", 3, 3, 3.3f, 3.3f, 3.3f, 4.5f, LocalDate.now().plusMonths(3), "DP493", "Milk176");
        addItem(itemController, productController, "Milk3Tnuva2", "Milk 3% Tnuva", 3, 3, 3.3f, 3.3f, 3.3f, 4.5f, LocalDate.now().plusMonths(3), "DP493", "Milk176");
        addItem(itemController, productController, "Milk3Tnuva3", "Milk 3% Tnuva", 3, 3, 3.3f, 3.3f, 3.3f, 4.5f, LocalDate.now().plusMonths(3), "DP493", "Milk176");

        addItem(itemController, productController, "Cheese7891", "Cheese 1", 3, 3, 3.3f, 3.3f, 10.0f, 12.5f, LocalDate.now().plusMonths(3), "DP493", "Cheese789");
        addItem(itemController, productController, "Cheese7892", "Cheese 2", 3, 3, 3.3f, 3.3f, 10.0f, 12.5f, LocalDate.now().plusMonths(3), "DP493", "Cheese789");
        addItem(itemController, productController, "Cheese7893", "Cheese 3", 3, 3, 3.3f, 3.3f, 10.0f, 12.5f, LocalDate.now().plusMonths(3), "DP493", "Cheese789");

        addItem(itemController, productController, "Bread0011", "Bread 1", 3, 3, 1.0f, 2.0f, 1.0f, 2.0f, LocalDate.parse("2024-01-01"), "BK123", "Bread001");
        addItem(itemController, productController, "Bread0012", "Bread 2", 3, 3, 1.0f, 2.0f, 1.0f, 2.0f, LocalDate.now().plusMonths(1), "BK123", "Bread001");
        addItem(itemController, productController, "Bread0013", "Bread 3", 3, 3, 1.0f, 2.0f, 1.0f, 2.0f, LocalDate.now().plusMonths(1), "BK123", "Bread001");

        addItem(itemController, productController, "Croissant0021", "Croissant 1", 3, 3, 2.0f, 3.0f, 2.0f, 3.0f, LocalDate.now().plusMonths(1), "BK123", "Croissant002");
        addItem(itemController, productController, "Croissant0022", "Croissant 2", 3, 3, 2.0f, 3.0f, 2.0f, 3.0f, LocalDate.now().plusMonths(1), "BK123", "Croissant002");
        addItem(itemController, productController, "Croissant0023", "Croissant 3", 3, 3, 2.0f, 3.0f, 2.0f, 3.0f, LocalDate.now().plusMonths(1), "BK123", "Croissant002");

        System.out.println("End of adding default info");
        System.out.println("");
    }

    private static Category addCategory(CategoryController categoryController, String categoryID, String categoryName) {
        categoryController.addCategory(categoryName, categoryID);
        return categoryController.getCategoryByID(categoryID);
    }

    private static Product addProduct(ProductController productController, String productID, String productName, String supplier, double costPrice, double sellingPrice, String categoryID, String subCategoryID, int minimumAmount) {
        productController.addProduct(productID, productName, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
        return productController.getProduct(productID);
    }

    private static Item addItem(ItemController itemController, ProductController productController, String itemID, String name, int floorBuilding, int floorShelf, float x, float y, float supplierCost, float priceNoDiscount, LocalDate expireDate, String categoryID, String productID) {
        boolean defective = false;
        boolean inWareHouse = true;

        itemController.addNewItem(defective, inWareHouse, floorBuilding, floorShelf, x, y, supplierCost, priceNoDiscount, name, itemID, expireDate, categoryID, productID);
        Item item = itemController.getItemByID(itemID);

        if (item != null) {
            productController.addItemsToProduct(itemID);
        }

        return item;
    }
}