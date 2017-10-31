import java.rmi.*;
import java.rmi.server.*;

public class BankImpl extends UnicastRemoteObject implements Bank {
	private int total;
	public BankImpl(int total) throws RemoteException {
		this.total = total;
	}
	
	public int getBalance() throws RemoteException {
		return total;
	}
	
	public int deposit(int amount) throws RemoteException {
		total += amount;
		return getBalance();
	}
	
	public int withdraw(int amount) throws RemoteException {
		total -= amount;
		return getBalance();
	}
	
	public static void main(String[] args) throws Exception {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		BankImpl bankip = new BankImpl(10000);
		Naming.rebind("//localhost/BankIp", bankip);
		System.out.println("bank was rebinded with name BankIp");
	}
}