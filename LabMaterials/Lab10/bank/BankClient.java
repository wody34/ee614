import java.rmi.*;

public class BankClient {
	public static void main(String[] args) throws Exception {
		int balance = 0;
		Bank bank = (Bank)Naming.lookup("//localhost/BankIp");
		System.out.println("Bank was given from server");
		balance = bank.getBalance();
		System.out.println("current balance: " + balance);
		balance = bank.deposit(1000);
		System.out.println("deposit 1000\ncurrent balance: " + balance);
		balance = bank.withdraw(5000);
		System.out.println("withdraw 5000\ncurrent balance: " + balance);
	}
}