package rmi;

class OrderInfo implements java.io.Serializable{
    /*
    OrderInfo class is define order's information to structure.
    orderCount = manage orderID not overlapping each other
    orderID = orderID
    virtualAccount = virtual account ID
    usrAdr = user address
    proID = product ID
    amount = order amount
    totalPrice = totalPrice related to order
    purchased = check purchased or not
    */
    private static int orderCount = 1;
    int orderID;
    int virtualAccount;
    String usrAdr;
    String proID;
    int amount;
    int totalPrice;
    String purchased;

    OrderInfo() { }

    OrderInfo(int virtualAccount, String usrAdr, String proID, int amount, int totalPrice, String purchased) {
        this.orderID = orderCount++;
        this.virtualAccount = virtualAccount;
        this.usrAdr = usrAdr;
        this.proID = proID;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.purchased = purchased;
    }

    boolean notPurchasedOrder () {
        return this.purchased.equals("not purchased");
    } // check purchased or not to know what order was not purchased.

    @Override
    public String toString() {
        return String.format("OrderID : %d / VirtualAccount: %d / Address : %s / Product ID : %s / amount : %d / price : %d / purchased : %s", orderID, virtualAccount, usrAdr, proID, amount, totalPrice, purchased);
    }
}
