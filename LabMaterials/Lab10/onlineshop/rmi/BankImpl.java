package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BankImpl extends UnicastRemoteObject implements Bank{
    HashMap<Integer, Account> accountList = new HashMap<>(); // hashmap accountList consist of <accName, Account> to find Account using accName.

    public BankImpl() throws RemoteException {
        /*
        class init
         */
        super();
        accountList.put(123, new Account(123, 123, 5000));
        accountList.put(456, new Account(456, 456, 5000));
    }

    @Override
    public boolean login(int name, int pwd) throws RemoteException {
        /*
        proving login user is bank's customer using account ID(name), password(pwd).
         */
        boolean check = true;

        if (accountList.get(name) != null) {
            if (accountList.get(name).accPwd == pwd)
                check = false;
        }

        return check;
    }

    @Override
    public Account makeVirtualAccount(int accPwd) throws RemoteException {
        /*
        When customer do make a order in ShopServer , ShopServer request make a virtual account related a order.
         */

        Random random = new Random();

        int accName;
        do {
            accName = random.nextInt(999) + 1;
        } while(accountList.containsKey(accName));

        Account account = new Account(accName, accPwd, 0);
        accountList.put(accName, account);
        return account;
    }

    @Override
    public boolean[] transfer(int name, int pwd, int obj, int num) throws RemoteException {
        /*
        Transfer money from account to object account. Before transfer money user must insert account ID nad password.
         */
        boolean[] checks = new boolean[2];
        boolean transferCheck = true;
        boolean objAccCheck = false;

        System.out.println(String.format("name = %d / pwd = %d", name, pwd));
        if (!this.login(name, pwd)) {
            if (accountList.get(obj) != null) {
                if (accountList.get(name).deposit >= num) {
                    accountList.get(name).deposit -= num;
                    accountList.get(obj).deposit += num;

                    accountList.get(name).transRecord.add(new TransInfo(name, obj, num));
                    accountList.get(obj).transRecord.add(new TransInfo(name, obj, num));

                    transferCheck = false;
                }
            }
            else {
                objAccCheck = true;
            }
        }
        checks[0] = transferCheck;
        checks[1] = objAccCheck;
        return checks;
    }

    @Override
    public int checkDeposit(int name) throws RemoteException {
        /*
        Check request account ID's deposit.
         */
        return accountList.get(name).deposit;
    }

    @Override
    public ArrayList<TransInfo> checkTransRecord(int name) throws RemoteException {
        /*
        Check transfer records.
         */
        return accountList.get(name).transRecord;
    }
}
