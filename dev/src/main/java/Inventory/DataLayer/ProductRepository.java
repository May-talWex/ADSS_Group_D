package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Product;

import java.util.List;

public class ProductRepository {
    private ProductDAO productDAO;

    public ProductRepository() {
        this.productDAO = new ProductDAO();
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

}
