import java.rmi.*;
import java.rmi.server.*;

public class SummingImpl extends UnicastRemoteObject implements Summing {
    public SummingImpl() throws RemoteException {
    }

    public int sum(int max) throws RemoteException {
        if(max <= 0) return 0;
        else return (max + sum(max - 1));
    }

    public static void main(String[] args) throws Exception {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        SummingImpl s = new SummingImpl();
        Naming.rebind("SumServer", s);
        System.out.println("Summing was rebinded with name SumServer");
    }
}