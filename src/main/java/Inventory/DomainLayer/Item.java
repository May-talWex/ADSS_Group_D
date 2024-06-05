package Inventory.DomainLayer;

//River
// This class will represent an item, it will hold its ID, category ID, sub-category ID, location (aisle, shelf)
import java.time.LocalDate;

public class Item {
    public boolean defective;
    public boolean inWareHouse;
    public int floorBuilding;
    public int floorShelf;
    public float x;
    public float y;
    public float supplierCost;
    public float priceNoDiscount;
    public String name;
    public String id;
    public LocalDate expireDate;
    public int quantityInWareHouse;
    public String categoryID;
    public String productID;

    @Override
    public String toString() {
        return "Item{" +
                "defective=" + defective +
                ", inWareHouse=" + inWareHouse +
                ", floorBuilding=" + floorBuilding +
                ", floorShelf=" + floorShelf +
                ", x=" + x +
                ", y=" + y +
                ", supplierCost=" + supplierCost +
                ", priceNoDiscount=" + priceNoDiscount +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", expireDate=" + expireDate +
                ", quantityInWareHouse=" + quantityInWareHouse +
                ", categoryID='" + categoryID + '\'' +
                ", productID='" + productID + '\'' +
                '}';
    }

    public Item(boolean defective, boolean inWareHouse, int floorBuilding,
                int floorShelf, float x, float y, float supplierCost,
                float priceNoDiscount, String name, String id, LocalDate expireDate,
                String categoryID, String productID) {
        this.defective = defective;
        this.inWareHouse = inWareHouse;
        if (inWareHouse) {
            floorBuilding = -1;
            floorShelf = -1;
            x = Integer.MIN_VALUE;
            y = Integer.MIN_VALUE;
        } else {
            this.floorBuilding = floorBuilding;
            this.floorShelf = floorShelf;
            this.x = x;
            this.y = y;
        }
        this.supplierCost = supplierCost;
        this.priceNoDiscount = priceNoDiscount;
        this.name = name;
        this.id = id;  // Ensure the ID is set correctly
        this.expireDate = expireDate;
        this.quantityInWareHouse = quantityInWareHouse;
        this.categoryID = categoryID;
        this.productID = productID;
    }

//    public double getPrice() {
//        Category category = categoryController.getCategoryByID(categoryID);
//        Product product = productController.getProduct(productID);
//        double discount = 0;
//        assert category != null;
//        if (category.getDiscountPercentage() > discount) {
//            discount = category.getDiscountPercentage();
//        }
//
//        if (product.getDiscount() > discount) {
//            discount = product.getDiscount();
//        }
//        return priceNoDiscount - (priceNoDiscount * discount / 100);
//    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean isExpired() {
        LocalDate currentDate = LocalDate.now();
        return expireDate.isBefore(currentDate);
    }


    public void setSupplierCost(float newPrice){
        supplierCost = newPrice;
    }

    public void setPriceNoDiscount(float newPrice){
        priceNoDiscount = newPrice;
    }

}