package Inventory.ServiceLayer;

import Inventory.DomainLayer.CategoryController;
import Inventory.DomainLayer.ItemController;
import Inventory.DomainLayer.ProductController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ServiceController {
    private CategoryController categoryController;
    private ProductController productController;
    private ItemController itemController;

    public ServiceController() {
        categoryController = new CategoryController();
        productController = new ProductController();
        itemController = new ItemController();
    }

    public void initialize() {
        productController.initializeProducts();
        categoryController.initializeCategories();
    }

    // Item Service Methods
    public boolean addItem(boolean defective, boolean inWareHouse, int floor,
                           int branchID, float aisle, float shelf,
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
            boolean itemAdded = itemController.addNewItem(defective, inWareHouse, floor, branchID, aisle, shelf, name, id, expireDate, categoryID, productID);
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

    public boolean doesItemExist(String itemID) {
        return itemController.getItemByID(itemID) != null;
    }

    public boolean isItemInWarehouse(String itemID) {
        return itemController.isItemInWarehouse(itemID);
    }

    public boolean updateItemLocation(String itemID, int floorBuilding, int floorShelf, float aisle, float shelf, boolean inWarehouse) {
        return itemController.updateItemLocation(itemID, floorBuilding, floorShelf, aisle, shelf, inWarehouse);
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

    public void generateLowSupplyCSVReport() {
        List<Map<String, String>> reportData = productController.generateLowSupplyReportData();
        String fileName = getFilePath("low_supply_report.csv");
        generateCSV(reportData, fileName);
    }

    public void generateLowSupplyDeltaCSVReport() {
        List<Map<String, String>> reportData = productController.generateLowSupplyReportDataWithDelta();
        String fileName = getFilePath("low_supply_delta_report.csv");
        generateCSV(reportData, fileName);
    }

    public void generateReorderCSVReport() {
        List<Map<String, String>> reportData = productController.generateReorderReportData();
        String fileName = getFilePath("reorder_report.csv");
        generateCSV(reportData, fileName);
    }

    public boolean updateProductDiscount(String productID, int discount, LocalDate startDate, LocalDate endDate) {
        return productController.updateProductDiscount(productID, discount, startDate, endDate);
    }

    public void generateDefectiveItemsCSVReport() {
        List<Map<String, String>> reportData = itemController.generateDefectiveItemsReportData();
        String fileName = getFilePath("defective_items_report.csv");
        generateCSV(reportData, fileName);
    }

    public void generateExpiredCSVReport() {
        List<Map<String, String>> reportData = itemController.generateExpiredItemsReportData();
        String fileName = getFilePath("expired_items_report.csv");
        generateCSV(reportData, fileName);
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

    public void generateStockCSVReport() {
        List<Map<String, String>> reportData = productController.generateStockReportData();
        String fileName = getFilePath("stock_report.csv");
        generateCSV(reportData, fileName);
    }

    public void generateCategoryProductReport(String categoryID) {
        List<Map<String, String>> reportData = productController.generateCategoryProductReportData(categoryID);
        String fileName = getFilePath("category_product_report_" + categoryID + ".csv");
        generateCSV(reportData, fileName);
    }

    private void generateCSV(List<Map<String, String>> data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            if (data.isEmpty()) {
                System.out.println("No data to write to CSV.");
                return;
            }

            // Write header
            Map<String, String> firstRow = data.get(0);
            writer.append(String.join(",", firstRow.keySet()));
            writer.append('\n');

            // Write data
            for (Map<String, String> row : data) {
                writer.append(String.join(",", row.values()));
                writer.append('\n');
            }

            System.out.println("CSV report generated successfully at " + filePath);
        } catch (IOException e) {
            System.out.println("Error while generating CSV report: " + e.getMessage());
        }
    }

    private String getFilePath(String fileName) {
        String userHome = System.getProperty("user.home");
        String directoryPath = userHome + File.separator + "inventory_reports";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directoryPath + File.separator + fileName;
    }

}
