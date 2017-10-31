import java.rmi.*;

public class HelloClient {
	public static void main(String args[]) {
		String path = "//localhost/Hello";
		try {
			if(args.length < 1)
				System.out.println("usage: java HelloClient <host:port> <string> \n");
			else
				path = "//" + args[0] + "/Hello";
			HelloInterface hello = (HelloInterface)Naming.lookup(path);
			for(int i = 1; i < args.length; ++i)
				System.out.println(hello.say(args[i]));
		} catch(Exception e) {
			System.out.println("Hello Client exeption: " + e);
		}
	}
}