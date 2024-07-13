package src.main.java.Inventory.DataLayer;

import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.Product;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;

    public CategoryRepository() {
        this.categoryDAO = new CategoryDAO();
    }

    public List<Category> getAllCategoriesWithProducts() {
        return categoryDAO.getAllCategoriesWithProducts();
    }

    public List<Product> getProductsByCategoryId(String categoryId) {
        return categoryDAO.getProductsByCategoryId(categoryId);
    }

    public boolean addCategory(Category category) {
        return categoryDAO.insertCategory(
                category.getID(),
                category.getName(),
                null,
                null,
                0.0f
        );
    }

    public boolean deleteCategory(String id) {
        return categoryDAO.deleteCategory(id);
    }

    public boolean updateCategory(String id, LocalDate startDiscount, LocalDate endDiscount, float discountPercentage) {
        return categoryDAO.updateCategory(
                id,
                startDiscount,
                endDiscount,
                discountPercentage
        );
    }

    public Category getCategoryById(String id) {
        return categoryDAO.getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
}
