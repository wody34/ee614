package rmi;

import java.rmi.Naming;
import java.lang.SecurityManager;

public class BankServer {
    public static void main(String[] args) throws Exception {
        if(System.getSecurityManager()==null) {
            System.setSecurityManager(new SecurityManager());
        }

        BankImpl b = new BankImpl();
        Naming.rebind("rmi://localhost:1099/TestBank", b);
        System.out.println("Binding BankImpl object b name as TestBank");
    }
}
