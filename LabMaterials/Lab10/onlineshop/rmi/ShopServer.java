package rmi;

import java.rmi.Naming;
import java.lang.SecurityManager;

public class ShopServer {
    public static void main(String[] args) throws Exception {
        if(System.getSecurityManager()==null) {
            System.setSecurityManager(new SecurityManager());
        }

        ShopImpl s = new ShopImpl();
        Naming.rebind("rmi://localhost:1099/TestShop", s);
        System.out.println("Binding ShopImpl object s name as Test Shop");
    }
}
