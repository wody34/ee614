package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Bank extends Remote{
    public boolean login(int name, int pwd) throws RemoteException;
    public Account makeVirtualAccount(int accPwd) throws RemoteException;
    public boolean[] transfer(int name, int pwd, int obj, int num) throws RemoteException;
    public int checkDeposit(int name) throws RemoteException;
    public ArrayList<TransInfo> checkTransRecord(int name) throws RemoteException;
}
