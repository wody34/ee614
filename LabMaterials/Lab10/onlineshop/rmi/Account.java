package rmi;

import java.util.ArrayList;

class Account implements java.io.Serializable{
    /*
    Account class is define account's information to structure.
    accName = account ID
    accPwd = account password
    deposit = account balance
    transferRecord = account transfer records
     */
    int accName;
    int accPwd;
    int deposit;
    ArrayList<TransInfo> transRecord;

    Account() { }

    Account(int accName, int accPwd, int deposit) {
        this.accName = accName;
        this.accPwd = accPwd;
        this.deposit = deposit;
        this.transRecord = new ArrayList<TransInfo>();
    }
}
