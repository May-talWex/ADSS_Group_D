package src.main.java.Inventory.DomainLayer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {
    private Map<String, Product> products;
    private Map<String, Map<String, Item>> productItems = new HashMap<>();

    public ProductController() {
        this.products = new HashMap<>();
    }

    public boolean addProduct(String makat, String name, String supplier, double costPrice, double sellingPrice, String categoryID, String subCategoryID, int minimumAmount) {
        Product product = new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
        products.put(makat, product);
        productItems.put(makat, new HashMap<>());
        return true;
    }

    public boolean addItemsToProduct(String itemID) {
        Item item = ItemController.getItemByID(itemID);
        if (item == null) {
            System.out.println("Item " + itemID + " doesn't exist");
            return false;
        }
        if (products.containsKey(item.productID)) {
            Product product = products.get(item.productID);
            product.addItem(item);
            productItems.get(item.productID).put(itemID, item);
            return true;
        }
        return false;
    }


    public boolean removeItemFromProduct(String itemID) {
        Item item = ItemController.getItemByID(itemID);
        if (item == null){
            System.out.println("Item " + itemID + " does not exist");
            return false;
        }
        if (products.containsKey(item.productID)) {
            Product product = products.get(item.productID);
            product.removeItem(item);
            productItems.get(item.productID).remove(itemID);
            System.out.println("Item " + itemID + " removed from product " + item.productID);
            return true;
        }
        System.out.println("Item " + itemID + " or product " + item.productID + " not found.");
        return false;
    }

    public void generateStockReport() {
        StringBuilder report = new StringBuilder();
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            Product product = entry.getValue();
            report.append(product.toString()).append("\n");
        }
        String jarDir = getJarDirectory();
        String relativePath = "dev/src/main/java/resources/stock_report.csv";
        String fullPath = Paths.get(jarDir, relativePath).toString();
        ensureDirectoryExists(fullPath);
        generateCSV(fullPath);
    }

    private String getJarDirectory() {
        try {
            String jarPath = ProductController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return new File(jarPath).getParentFile().getParentFile().getParentFile().getParent(); // Adjust according to your directory structure
        } catch (Exception e) {
            throw new RuntimeException("Unable to determine JAR file directory", e);
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

    public void generateCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Makat,Name,Supplier,Cost Price,Selling Price, Full Price, Category ID,Sub Category ID,Minimum Amount,Current Quantity,Discount\n");
            for (Product product : products.values()) {
                writer.append(product.getMakat()).append(',')
                        .append(product.getName()).append(',')
                        .append(product.getSupplier()).append(',')
                        .append(String.valueOf(product.getCostPrice())).append(',')
                        .append(String.valueOf(product.getSellingPrice())).append(',')
                        .append(String.valueOf(product.getFullPrice())).append(',')
                        .append(product.getCategoryID()).append(',')
                        .append(product.getSubCategoryID()).append(',')
                        .append(String.valueOf(product.getMinimumAmount())).append(',')
                        .append(String.valueOf(product.getCurrentQuantity())).append(',')
                        .append(String.valueOf(product.getDiscount())).append('\n');
            }
            System.out.println("CSV stock report generated successfully at path" + filePath + ".");
        } catch (IOException e) {
            System.out.println("Error while generating CSV report: " + e.getMessage());
        }
    }



    public boolean removeProduct(String makat) {
        Product product = products.get(makat);
        if (product != null) {
            if (!product.getItems().isEmpty()) {
                System.out.println("Cannot remove product " + makat + " because it still contains items.");
                return false;
            }
            products.remove(makat);
            productItems.remove(makat);
            System.out.println("Product " + makat + " removed successfully.");
            return true;
        }
        System.out.println("Product " + makat + " does not exist.");
        return false;
    }

    public Product getProduct(String makat) {
        return products.get(makat);
    }

    public int getItemAmountByProductID(String makat){
        return getProduct(makat).getItemAmount();
    }

    public ArrayList<Product> getLowStockProducts() {
        ArrayList<Product> lowStockProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.isLowStock()) {
                lowStockProducts.add(product);
            }
        }
        return lowStockProducts;
    }

    public void generateLowSupplyCSVReport() {
        ArrayList<Product> lowSupplyProducts = getLowStockProducts();
        String jarDir = getJarDirectory();
        String relativePath = "dev/src/main/java/resources/low_supply_report.csv";
        String fullPath = Paths.get(jarDir, relativePath).toString();
        ensureDirectoryExists(fullPath);
        generateCSVReport(lowSupplyProducts, fullPath);
    }


    private void generateCSVReport(ArrayList<Product> products, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Writing header
            writer.append("Product Makat,Product Name,Quantity\n");

            // Writing data
            for (Product product : products) {
                writer.append(product.getMakat()).append(",");
                writer.append(product.getName()).append(",");
                writer.append(String.valueOf(product.getItemAmount())).append("\n");
            }

            System.out.println("CSV report generated successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void generateStockAlerts() {
        for (Product product : products.values()) {
            if (product.isLowStock()) {
                System.out.println("Alert: Product " + product.getName() + " is below the minimum stock level. Current stock: " + product.getCurrentQuantity() + ", Minimum stock: " + product.getMinimumAmount());
            }
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Product> generateReportByCategory(String category) {
        List<Product> categoryProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getCategoryID().equals(category)) {
                categoryProducts.add(product);
            }
        }
        return categoryProducts;
    }

    public boolean updateProductDiscount(String productID, int discount, LocalDate startDate, LocalDate endDate) {
        Product product = getProduct(productID);
        if (product != null) {
            product.setDiscount(discount, startDate, endDate);
            return true;
        }
        return false;
    }

    //adding for when DB is implemented
    public void setCurrentPrice(String productID) {
        Product product = getProduct(productID);
        if (product != null) {
            if(!product.isDiscountActive()){
                product.setSellingPrice(product.getFullPrice());
            }
        }
    }

    private ArrayList<Product> getProductsByCategoryID(String categoryID) {
        ArrayList<Product> productsByCategory = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getCategoryID().equals(categoryID)) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory;
    }

    public void generateCategoryCSVReport(String categoryID) {
        ArrayList<Product> productsByCategory = getProductsByCategoryID(categoryID);
        String jarDir = getJarDirectory();
        String relativePath = "dev/src/main/java/resources/category_" + categoryID + "_report.csv";
        String fullPath = Paths.get(jarDir, relativePath).toString();
        ensureDirectoryExists(fullPath);
        generateCSVReport(productsByCategory, fullPath);
    }

}
