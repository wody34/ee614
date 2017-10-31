package rmi;

public class ProductInfo implements java.io.Serializable {
    /*
    ProductInfo class is define product's information to structure.
    brand = product brand
    price = product price
    amount = total product amount
    ID = product ID
    */
    public String brand;
    public int price;
    public int amount;
    public String ID;

    ProductInfo() { }

    ProductInfo(String brand, int price, int amount, String ID) {
        this.brand = brand;
        this.price = price;
        this.amount = amount;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return String.format("brand : %s / price : %d / amount : %d / ID: %s", brand, price, amount, ID);
    }
}
