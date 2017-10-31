package rmi;

import java.util.ArrayList;

class UsrInfo implements java.io.Serializable{
    /*
    UsrInfo class is define user's information to structure.
    usrName = user ID in ShopServer
    usrPwd = user password in ShopServer
    usrOrder = user order list
    */
    String usrName;
    int usrPwd;
    ArrayList<OrderInfo> usrOrder;

    UsrInfo() { }

    UsrInfo(String usrName, int usrPwd) {
        this.usrName = usrName;
        this.usrPwd = usrPwd;
        this.usrOrder = new ArrayList<OrderInfo>();
    }
}
