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
            String categoryID = product.getCategoryID();
            if (categoryID != null && !categoryID.isEmpty()) {
                CategoryController categoryController = new CategoryController();
                Category category = categoryController.getCategoryByID(categoryID);
                if (category != null) {
                    category.removeProduct(product);
                    boolean removedFromCategory = categoryController.updateCategory(
                            categoryID,
                            category.getDiscountStartDate(),
                            category.getDiscountEndDate(),
                            category.getDiscountPercentage()
                    );
                    if (removedFromCategory) {
                        return productRepository.deleteProduct(makat);
                    } else {
                        System.out.println("Failed to remove product from category.");
                    }
                }
            } else {
                System.out.println("Product category ID is null or empty.");
            }
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

}
