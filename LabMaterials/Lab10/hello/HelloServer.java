import java.rmi.*;
import java.rmi.server.*;

public class HelloServer {
	public static void main(String args[]) {
        if(System.getSecurityManager()==null) {
            System.setSecurityManager(new SecurityManager());
        }
		try {
			Naming.rebind("Hello", new Hello("Hello, world!"));
			System.out.println("server is running...");
		} catch(Exception e) {
			System.out.println("Hello server failed: " + e.getMessage());
		}
	}
}