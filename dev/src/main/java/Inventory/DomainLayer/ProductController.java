package src.main.java.Inventory.DomainLayer;

import java.io.FileWriter;
import java.io.IOException;
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

    public Product addDefaultProduct() {
        String defaultProductID = "Milk176";
        String defaultProductName = "Milk";
        String supplier = "Tnuva";
        double costPrice = 3.33;
        double sellingPrice = 4.5;
        String defaultCategoryID = "DP493";
        String defaultSubCategoryID = "Milk";
        int minimumAmount = 3;

        addProduct(defaultProductID, defaultProductName, supplier, costPrice, sellingPrice, defaultCategoryID, defaultSubCategoryID, minimumAmount);
        return getProduct(defaultProductID);
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
        System.out.println(report.toString());
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
        String directory = "C:\\githubclones\\ADSS_Group_D\\dev\\src\\main\\java\\resources\\"; // Specify your directory here
        String filePath = directory + "low_supply_report.csv";
        generateCSVReport(lowSupplyProducts, filePath);
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
}
