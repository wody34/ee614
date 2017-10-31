import java.rmi.*;
import java.rmi.server.*;

public class Hello extends UnicastRemoteObject implements HelloInterface {
	private String message;
	public Hello(String msg) throws RemoteException {
		message = msg;
	}
	public String say(String m) throws RemoteException {
		return new StringBuffer(m).reverse().toString() + "\n" + message;
	}
}