package rmi;
import java.rmi.*;
public interface Summing extends Remote {
    int sum(int max) throws RemoteException;
}
