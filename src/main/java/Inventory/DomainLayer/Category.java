package Inventory.DomainLayer;

import java.util.ArrayList;
import java.util.Date;

//May-tal
// This class will represent a category, it will hold its ID, name, and description and
public class Category {
    private String name;
    private String ID;
    private Date discountStartDate;
    private Date discountEndDate;
    private int discountPercentage;
    ArrayList<Product> products = new ArrayList<Product>();

    //Category constructors
    public Category(String name, String ID){
        this.name = name;
        this.ID = ID;
        ArrayList<Product> products = new ArrayList<Product>();
    }

    //Getters

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public Date getDiscountStartDate() {
        return discountStartDate;
    }

    public Date getDiscountEndDate() {
        return discountEndDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public ArrayList<Product> getProducts() {
        return products;

    }

    //Setters
    public void setDiscountStartDate(Date discountStartDate){
        this.discountStartDate = discountStartDate;
    }

    public void setDiscountEndDate(Date discountEndDate){
        this.discountEndDate = discountEndDate;
    }

    public void setDiscountPercentage(int discountPercentage){
        this.discountPercentage = discountPercentage;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }


    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}