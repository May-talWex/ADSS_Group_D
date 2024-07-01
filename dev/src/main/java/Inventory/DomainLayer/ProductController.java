package src.main.java.Inventory.DomainLayer;

import src.main.java.Inventory.DataLayer.ProductRepository;

import java.time.LocalDate;
import java.util.List;

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

    public void generateLowSupplyCSVReport() {
        // Implementation here
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
