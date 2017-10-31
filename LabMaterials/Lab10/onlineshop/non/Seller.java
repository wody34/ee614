//package non;
//
//import rmi.Bank;
//import rmi.Info;
//import rmi.TransInfo;
//import rmi.UsrInfo;
//
//import java.rmi.Naming;
//import java.lang.SecurityManager;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Scanner;
//
//public class Seller {
//    public static void main(String[] args) throws Exception {
//        if(System.getSecurityManager()==null) {
//            System.setSecurityManager(new SecurityManager());
//        }
//        Info i = (Info)Naming.lookup("rmi://localhost:1099/TestInfo");
//        Bank b = (Bank) Naming.lookup("rmi://localhost:1099/TestBank");
//
//        int bankId, bankPwd;
//
//        while (true) {
//            System.out.println("Seller side --> 1. Add the product 2. Delete a product 3. Check the Orders 4. Banking System 5. quit");
//            Scanner userInput = new Scanner(System.in);
//            int num = userInput.nextInt();
//
//            if (num == 1) {
//                System.out.println("------Add the product page ------");
//                System.out.println("What is product type?");
//                Scanner productType = new Scanner(System.in);
//                String type = productType.next();
//                System.out.println("What is product brand?");
//                Scanner productBrand = new Scanner(System.in);
//                String brand = productBrand.next();
//                System.out.println("What is product price?");
//                Scanner productPrice = new Scanner(System.in);
//                int price = productPrice.nextInt();
//                System.out.println("What is product amount?");
//                Scanner productAmount = new Scanner(System.in);
//                int amount = productAmount.nextInt();
//                System.out.println("What is product ID?");
//                Scanner productID = new Scanner(System.in);
//                String ID = productID.next();
//
//                i.addProInfo(type, brand, price, amount, ID); //call object InfoImpl's addProInfo method
//            }
//            else if (num == 2) {
//                System.out.println("------Delete a product page ------");
//                System.out.println("What is product type?");
//                Scanner productType = new Scanner(System.in);
//                String type = productType.next();
//                System.out.println("What is product ID?");
//                Scanner productID = new Scanner(System.in);
//                String ID = productID.next();
//                System.out.println("What is product amount?");
//                Scanner productAmount = new Scanner(System.in);
//                int amount = productAmount.nextInt();
//
//                i.delProInfo(type, ID); //call object InfoImpl's delProInfo method
//            }
//
//            else if (num == 3) {
//                System.out.println("------Check the Orders page ------");
//                HashMap<String, UsrInfo> usrList = new HashMap<>();
//                usrList = i.checkTransfer(); //call object InfoImpl's checkTransfer method
//                for (String key : usrList.keySet()) {
//                    for(int k = 0; k < usrList.get(key).usrOrder.size(); k++) {
//                        System.out.println(usrList.get(key).usrOrder.get(k).toString());
//                    }
//                }
//            }
//
//            //user interface page in BankServer
//            else if (num == 4) {
//                System.out.println("-----Banking page-----");
//                do {
//                    System.out.println("what is your bank id?");
//                    Scanner scanBankId = new Scanner(System.in);
//                    bankId = scanBankId.nextInt();
//                    System.out.println("what is your bank password?");
//                    Scanner scanBankPwd = new Scanner(System.in);
//                    bankPwd = scanBankPwd.nextInt();
//                } while (b.login(bankId, bankPwd)); //call object BankImpl's login method
//
//                while (true) {
//                    System.out.println("Welcome Banking System.");
//                    System.out.println("1. Sending money 2. Check Deposit 3. Transfer Record 4. Quit Banking System");
//                    Scanner bankInput = new Scanner(System.in);
//                    int bI = bankInput.nextInt();
//                    if (bI == 1) {
//                        System.out.println("What is account name do you want send money?");
//                        Scanner scanAccName = new Scanner(System.in);
//                        int accName = scanAccName.nextInt();
//                        System.out.println("How much money do you want to transfer?");
//                        Scanner scanMoney = new Scanner(System.in);
//                        int money = scanMoney.nextInt();
//
//                        do {
//                            System.out.println("what is your bank id?");
//                            Scanner scanBankId = new Scanner(System.in);
//                            bankId = scanBankId.nextInt();
//                            System.out.println("what is your bank password?");
//                            Scanner scanBankPwd = new Scanner(System.in);
//                            bankPwd = scanBankPwd.nextInt();
//                        } while (b.transfer(bankId, bankPwd, accName, money)[0]); //call object BankImpl's transfer method
//                    }
//
//                    else if(bI == 2) {
//                        do {
//                            System.out.println("what is your bank id?");
//                            Scanner scanBankId = new Scanner(System.in);
//                            bankId = scanBankId.nextInt();
//                            System.out.println("what is your bank password?");
//                            Scanner scanBankPwd = new Scanner(System.in);
//                            bankPwd = scanBankPwd.nextInt();
//                        } while (b.login(bankId, bankPwd));
//
//                        System.out.println(String.format("Your account deposit is %d", b.checkDeposit(bankId))); //call object BankImpl's checkDeposit method
//                    }
//
//                    else if (bI == 3) {
//                        do {
//                            System.out.println("what is your bank id?");
//                            Scanner scanBankId = new Scanner(System.in);
//                            bankId = scanBankId.nextInt();
//                            System.out.println("what is your bank password?");
//                            Scanner scanBankPwd = new Scanner(System.in);
//                            bankPwd = scanBankPwd.nextInt();
//                        } while (b.login(bankId, bankPwd));
//
//                        ArrayList<TransInfo> transRecord = b.checkTransRecord(bankId); //call object BankImpl's checkTransRecord method
//
//                        //user can show account's transfer records.
//                        for (int k = 0; k < transRecord.size(); k++) {
//                            System.out.println(transRecord.get(k).toString());
//                        }
//                    }
//
//                    //exit BankServer and return ShopServer's main page
//                    else if (bI == 4) {
//                        break;
//                    }
//                }
//            }
//
//            else if (num == 5) {
//                break;
//            }
//        }
//    }
//}