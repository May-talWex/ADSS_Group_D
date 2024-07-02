package src.main.java.Inventory.DomainLayer;

import src.main.java.Inventory.DataLayer.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {
    private ProductRepository productRepository;

    public ProductController() {
        productRepository = new ProductRepository();
    }

    public boolean addProduct(String makat, String name, String supplier, float costPrice, float sellingPrice, String categoryID, String subCategoryID, int minimumAmount) {
        Product product = new Product(makat, name, supplier, costPrice, sellingPrice, categoryID, subCategoryID, minimumAmount);
        return productRepository.addProduct(product);
    }

    public boolean removeProduct(String makat) {
        Product product = productRepository.getProductById(makat);
        if (product != null) {
            if (!product.getItems().isEmpty()) {
                System.out.println("Cannot remove product " + makat + " because it still contains items.");
                return false;
            }
            return productRepository.deleteProduct(makat);
        } else {
            System.out.println("Product not found.");
        }
        return false;
    }


    public boolean doesProductExist(String productID) {
        return productRepository.getProductById(productID) != null;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void generateStockReport() {
        // Implementation here
    }

    public List<Map<String, String>> generateStockReportData() {
        List<Product> products = productRepository.getAllProducts();
        List<Map<String, String>> data = new ArrayList<>();

        for (Product product : products) {
            Map<String, String> productData = new HashMap<>();
            productData.put("Makat", product.getMakat());
            productData.put("Name", product.getName());
            productData.put("Supplier", product.getSupplier());
            productData.put("Cost Price", String.valueOf(product.getCostPrice()));
            productData.put("Selling Price", String.valueOf(product.getSellingPrice()));
            productData.put("Discount", String.valueOf(product.getDiscount()));
            productData.put("Minimum Amount", String.valueOf(product.getMinimumAmount()));
            productData.put("Category ID", product.getCategoryID());
            productData.put("Sub Category ID", product.getSubCategoryID());
            productData.put("Full Price", String.valueOf(product.getFullPrice()));
            productData.put("Item Amount", String.valueOf(product.getItemAmount()));  // Ensure correct item amount is populated

            data.add(productData);
        }

        return data;
    }

    public List<Map<String, String>> generateCategoryProductReportData(String categoryID) {
        List<Product> products = getProductsByCategoryId(categoryID);
        List<Map<String, String>> data = new ArrayList<>();

        for (Product product : products) {
            Map<String, String> productData = new HashMap<>();
            productData.put("Makat", product.getMakat());
            productData.put("Name", product.getName());
            productData.put("Supplier", product.getSupplier());
            productData.put("Cost Price", String.valueOf(product.getCostPrice()));
            productData.put("Selling Price", String.valueOf(product.getSellingPrice()));
            productData.put("Discount", String.valueOf(product.getDiscount()));
            productData.put("Minimum Amount", String.valueOf(product.getMinimumAmount()));
            productData.put("Category ID", product.getCategoryID());
            productData.put("Sub Category ID", product.getSubCategoryID());
            productData.put("Full Price", String.valueOf(product.getFullPrice()));
            productData.put("Item Amount", String.valueOf(product.getItemAmount()));

            data.add(productData);
        }

        return data;
    }


    public List<Map<String, String>> generateLowSupplyReportData() {
        List<Product> products = productRepository.getAllProducts();
        List<Map<String, String>> data = new ArrayList<>();

        for (Product product : products) {
            if (product.getItemAmount() < product.getMinimumAmount()) {
                Map<String, String> productData = new HashMap<>();
                productData.put("Makat", product.getMakat());
                productData.put("Name", product.getName());
                productData.put("Supplier", product.getSupplier());
                productData.put("Cost Price", String.valueOf(product.getCostPrice()));
                productData.put("Selling Price", String.valueOf(product.getSellingPrice()));
                productData.put("Discount", String.valueOf(product.getDiscount()));
                productData.put("Minimum Amount", String.valueOf(product.getMinimumAmount()));
                productData.put("Current Amount", String.valueOf(product.getItemAmount()));
                productData.put("Category ID", product.getCategoryID());
                productData.put("Sub Category ID", product.getSubCategoryID());
                productData.put("Full Price", String.valueOf(product.getFullPrice()));

                data.add(productData);
            }
        }

        return data;
    }

    public List<Map<String, String>> generateLowSupplyReportDataWithDelta() {
        List<Product> products = productRepository.getAllProducts();
        List<Map<String, String>> data = new ArrayList<>();

        for (Product product : products) {
            int amountToOrder = product.getMinimumAmount() - product.getItemAmount();
            if (amountToOrder > 0) {
                Map<String, String> productData = new HashMap<>();
                productData.put("Makat", product.getMakat());
                productData.put("Name", product.getName());
                productData.put("Supplier", product.getSupplier());
                productData.put("Amount to Order", String.valueOf(amountToOrder)); // Calculated delta
                data.add(productData);
            }
        }

        return data;
    }



    public boolean updateProductDiscount(String productID, int discount, LocalDate startDate, LocalDate endDate) {
        // Implementation here
        return true;
    }

    public void generateCategoryCSVReport(String categoryID) {
        // Implementation here
    }

    public static Product getProductByID(String productID) {
        return new ProductRepository().getProductById(productID);
    }

    public boolean productContainsItems(String makat) {
        Product product = productRepository.getProductById(makat);
        return product != null && !product.getItems().isEmpty();
    }

    public List<Product> getProductsByCategoryId(String categoryID) {
        return productRepository.getProductsByCategoryId(categoryID);
    }


//    public void ProductsNoItem() {
//        List<Product> allProducts = productRepository.getAllProducts();
//        List<String> productsWithNoItems = new ArrayList<>();
//
//        for (Product product : allProducts) {
//            if (product.getItemAmount() == 0) {
//                productsWithNoItems.add(product.getName());
//            }
//        }
//
//        if (!productsWithNoItems.isEmpty()) {
//            System.out.println("WARNING: PRODUCTS WITH NO ITEMS");
//            for (String productName : productsWithNoItems) {
//                System.out.println(productName);
//            }
//        }
//    }

}
