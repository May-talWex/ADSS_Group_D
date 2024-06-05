package Inventory.DomainLayer;

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

//    public void addDefaultProduct() {
//        String defaultProductID = "333";
//        String defaultProductName = "DefaultProduct";
//        String supplier = "DefaultSupplier";
//        double costPrice = 3.33;
//        double sellingPrice = 33.33;
//        String defaultCategoryID = "3";
//        String defaultSubCategoryID = "3";
//        int minimumAmount = 3;
//
//        addProduct(defaultProductID, defaultProductName, supplier, costPrice, sellingPrice, defaultCategoryID, defaultSubCategoryID, minimumAmount);
//    }


    public boolean addProduct(String makat, String name, String supplier, double costPrice, double sellingPrice, String categoryID, String subCategoryID, int minimumAmount) {
        Product product = new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
        products.put(makat, product);
        productItems.put(makat, new HashMap<>());
        return true;
    }



    public boolean addItemToProduct(String itemID) {
        Item item = ItemController.getItemByID(itemID);
        if (products.containsKey(item.productID)) {
            products.get(item.productID).setItemAmount(products.get(item.productID).getItemAmount());
            return true;
        }
        return false;
    }

    public boolean removeItemFromProduct(String itemID) {
        Item item = ItemController.getItemByID(itemID);
        if (item != null && products.containsKey(item.productID)) {
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

    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStockProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.isLowStock()) {
                lowStockProducts.add(product);
            }
        }
        return lowStockProducts;
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