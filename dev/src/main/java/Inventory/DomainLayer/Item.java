package Inventory.DomainLayer;

//River
// This class will represent an item, it will hold its ID, category ID, sub-category ID, location (aisle, shelf)
import java.time.LocalDate;

public class Item {
    public boolean defective;
    public boolean inWareHouse;
    public int floor;
    public int building;
    public float aisle;
    public float shelf;
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
                ", floorBuilding=" + floor +
                ", floorShelf=" + building +
                ", x=" + aisle +
                ", y=" + shelf +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", expireDate=" + expireDate +
                ", quantityInWareHouse=" + quantityInWareHouse +
                ", categoryID='" + categoryID + '\'' +
                ", productID='" + productID + '\'' +
                '}';
    }

    public Item(boolean defective, boolean inWareHouse, int floor, int building, float aisle, float shelf, String name, String id, LocalDate expireDate, String categoryID, String productID) {
        this.defective = defective;
        this.inWareHouse = inWareHouse;

        if (inWareHouse) {
            this.floor = -1;
            this.building = -1;
            this.aisle = -1;
            this.shelf = -1;
        } else {
            this.floor = floor;
            this.building = building;
            this.aisle = aisle;
            this.shelf = shelf;
        }

        this.name = name;
        this.id = id;
        this.expireDate = expireDate;
        this.categoryID = categoryID;
        this.productID = productID;
    }


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

    public String getCategoryID(){
        return categoryID;
    }

    public String getProductID(){
        return productID;
    }

    public LocalDate getExpireDate(){
        return expireDate;
    }

    public void setIsDefective(boolean isDefective){
        this.defective = isDefective;
    }

    public void setLocation(int floorBuilding, int floorShelf) {
        this.floor = floorBuilding;
        this.building = floorShelf;
    }




}