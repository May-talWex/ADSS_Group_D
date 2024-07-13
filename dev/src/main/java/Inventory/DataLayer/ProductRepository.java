package Inventory.DataLayer;

import Inventory.DomainLayer.Item;
import Inventory.DomainLayer.Product;

import java.util.List;
import java.util.Map;

public class ProductRepository {
    private ProductDAO productDAO;

    public ProductRepository() {
        this.productDAO = new ProductDAO();
    }

    public List<Product> getAllProductsWithItems() {
        return productDAO.getAllProductsWithItems();
    }

    public List<Item> getItemsByProductId(String productId) {
        return productDAO.getItemsByProductId(productId);
    }

    public boolean addProduct(Product product) {
        return productDAO.insertProduct(product);
    }

    public boolean deleteProduct(String makat) {
        return productDAO.deleteProduct(makat);
    }

    public Product getProductById(String makat) {
        return productDAO.getProductById(makat);
    }

    public List<Product> getProductsByCategoryId(String categoryID) {
        return productDAO.getProductsByCategoryId(categoryID);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    public List<Map<String, String>> getProductsByCategoryIdWithItemAmounts(String categoryID) {
        return productDAO.getProductsByCategoryIdWithItemAmounts(categoryID);
    }

    public List<Map<String, String>> getAllProductsWithItemAmounts() {
        return productDAO.getAllProductsWithItemAmounts();
    }




}
