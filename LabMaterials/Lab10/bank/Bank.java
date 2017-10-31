import java.rmi.*;

public interface Bank extends Remote {
	public int getBalance() throws RemoteException;
	public int deposit(int amount) throws RemoteException;
	public int withdraw(int amount) throws RemoteException;
}