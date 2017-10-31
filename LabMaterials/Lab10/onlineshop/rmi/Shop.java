package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Shop extends Remote {
    Boolean checkCustomer(String usrName, int usrPwd) throws RemoteException;
    ArrayList<ProductInfo> getProInfoByPrice(int from, int to) throws RemoteException;
    HashMap<String, ProductInfo> getProInfoByType(String type) throws RemoteException;
    ArrayList<ProductInfo> getProInfoByBrand(String brand) throws RemoteException;
    ProductInfo getProInfoByID(String ID) throws RemoteException;
    OrderInfo usrBuy(String usrName, String usrAdr, String type, String proID, int amount) throws RemoteException;
    UsrInfo getOrderList(String usrName) throws RemoteException;
    void addProInfo(String type, String brand, int price, int amount, String ID) throws RemoteException;
    void editProInfo(String type, String ID, int price, int amount) throws RemoteException;
    void delProInfo(String type, String ID) throws RemoteException;
    HashMap<String, UsrInfo> checkTransfer() throws RemoteException;
}
