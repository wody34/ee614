import java.rmi.*;

public interface HelloInterface extends Remote {
	public String say(String msg) throws RemoteException;
 }