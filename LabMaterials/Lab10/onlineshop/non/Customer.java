//package non;
//
//import rmi.*;
//
//import java.rmi.Naming;
//import java.lang.SecurityManager;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Scanner;
//
//public class Customer {
//    public static void main(String[] args) throws Exception {
//        if(System.getSecurityManager()==null) {
//            System.setSecurityManager(new SecurityManager());
//        }
//        String usrName;
//        int usrPwd;
//        int bankId, bankPwd;
//        Info i = (Info)Naming.lookup("rmi://localhost:1099/TestInfo");
//        Bank b = (Bank) Naming.lookup("rmi://localhost:1099/TestBank");
//
//        //check customer to login ShopServer
//        do {
//            System.out.println("what is your user name?");
//            Scanner scanUsrName = new Scanner(System.in);
//            usrName = scanUsrName.next();
//            System.out.println("what is your user password?");
//            Scanner scanUsrPwd = new Scanner(System.in);
//            usrPwd = scanUsrPwd.nextInt();
//        } while (i.checkCustomer(usrName, usrPwd)); //call object InfoImpl's checkCustomer method
//
//        //user interface page in ShopServer
//        while (true) {
//            System.out.println("Customer Side --> 1. Search 2. Submit Order 3. Check Order 4. Banking 5. Quit");
//            Scanner userInput = new Scanner(System.in);
//            int num = userInput.nextInt();
//
//            if (num == 1) {
//                System.out.println("-----Search page-----");
//                System.out.println("The products you search are? (TV, MP3, COM, PHONE)");
//                Scanner scanPro = new Scanner(System.in);
//                String goods = scanPro.next();
//
//                //printout product's list related to product type user want to show.
//                HashMap<String, ProductInfo> goodsList = i.getProInfoByType(goods); //call object InfoImpl's getProInfo method
//                System.out.println(goodsList.size());
//
//                for(String key : goodsList.keySet()) {
//                    System.out.println(goods + goodsList.get(key).toString());
//                }
//            }
//
//            else if (num == 2) {
//                System.out.println("-----Submit Order page-----");
//                System.out.println("where is your address?");
//                Scanner scanUsrAdr = new Scanner(System.in);
//                String usrAdr = scanUsrAdr.next();
//                System.out.println("what is product type do you want to buy? (TV, MP3, COM, PHONE)");
//                Scanner scanType = new Scanner(System.in);
//                String type = scanType.next();
//                System.out.println("what is product ID do you want to buy?");
//                Scanner scanProID = new Scanner(System.in);
//                String proID = scanProID.next();
//                System.out.println("How many numbers do you want to buy?");
//                Scanner scanAmount = new Scanner(System.in);
//                int amount = scanAmount.nextInt();
//
//                //user submit a order (when product amount fewer compared to user order amount, order cancel.)
//                OrderInfo orderInfo = i.usrBuy(usrName, usrAdr, type, proID, amount); //call object InfoImpl's usrBuy method
//                if (orderInfo != null){
//                    System.out.println("Order complete!!");
//                    System.out.println(orderInfo.toString());
//
//                    //ShopServer inform virtual account information relate to order to user.
//                    System.out.println(String.format("You have to transfer %d to account number %d", orderInfo.totalPrice, orderInfo.virtualAccount));
//                }
//                else
//                    System.out.println("Order canceled...");
//            }
//
//            else if (num == 3) {
//                System.out.println("-----Check Order page-----");
//                UsrInfo usrOrderList = i.getOrderList(usrName); //call object InfoImpl's getOrderList method
//
//                //user can show order list.
//                for(int k = 0; k < usrOrderList.usrOrder.size(); k++) {
//                    System.out.println("Name : " + usrName + usrOrderList.usrOrder.get(k).toString());
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
//            //quit customer program
//            else if (num == 5) {
//                break;
//            }
//        }
//    }
//}
