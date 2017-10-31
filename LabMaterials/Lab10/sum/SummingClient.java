import java.rmi.*;

public class SummingClient {
    public static void main(String args[]) {
        if(args.length < 2) {
            System.out.println("usage: java HelloClient <host:port> <string> \n");
            System.exit(1);
        }

        try {
            String serverURL = "rmi://" + args[0] + "/SumServer";
            Summing s = (Summing) Naming.lookup(serverURL);
            System.out.println("Your Input = " + args[1]);
            int max = Integer.parseInt(args[1]);
            System.out.println("The sum 1 to " + max + " is " + s.sum(max));
        } catch(Exception e) {
            System.out.println("Sum Client exeption: " + e);
        }
    }
}