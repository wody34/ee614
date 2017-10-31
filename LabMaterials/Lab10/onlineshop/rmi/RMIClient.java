package rmi;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.lang.SecurityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class RMIClient extends JFrame {
    private JTextField textMain;
    private JTabbedPane tabbedPane1;
    private JPanel seachTab;
    private JPanel submitTab;
    private JPanel checkTab;
    private JTextField submitNameText;
    private JLabel submitLabel1;
    private JTextPane submitTextPane;
    private JLabel submitNameLabel;
    private JRadioButton searchTypeRButton;
    private JRadioButton searchBrandRButton;
    private JButton searchButton;
    private JRadioButton searchPriceRButton;
    private JLabel searchPriceLabel1;
    private JLabel searchPriceLabel2;
    private JTextField searchPriceFromText;
    private JTextField searchPriceToText;
    private JComboBox searchTypeBox;
    private JComboBox searchBrandBox;
    private JTextPane searchTextPane;
    private JTextField searchIDText;
    private JRadioButton searchIDRButton;
    private JLabel searchLabel2;
    private JTextField submitPwdText;
    private JTextField submitAmountText;
    private JButton submitOrderButton;
    private JLabel submitPwdLabel;
    private JLabel submitAmountLabel;
    private JTextField checkOrderNameText;
    private JTextField checkOrderPwdText;
    private JButton checkOrderButton;
    private JTextPane checkOrderTextPane;
    private JLabel searchLabel1;
    private JLabel checkOrderPwdLabel;
    private JLabel checkOrderNameLabel;
    private JPanel onlineShopRootPanel;
    private JTextField submitAddText;
    private JLabel submitAddLabel;
    private JTextField submitIDText;
    private JLabel submitIDLabel;
    private JLabel submitTypeLabel;
    private JComboBox submitTypeBox;
    private JLabel bankLabel2;
    private JLabel bankLabel3;
    private JTextField bankAccNameText;
    private JLabel bankLabel4;
    private JTextField bankAccPwdText;
    private JLabel bankLabel5;
    private JLabel bankLabel7;
    private JLabel bankLabel6;
    private JLabel bankLabel8;
    private JLabel bankLabel9;
    private JTextField bankTransObjText;
    private JTextField bankTransAmountText;
    private JButton bankSendButton;
    private JButton bankDepositButton;
    private JButton bankRecordButton;
    private JLabel bankLabel1;
    private JTextPane bankTextPane;
    private JLabel checkLabel1;
    private JLabel checkLabel2;
    private JPanel bankLabel;
    private JLabel submitLabel2;

    public RMIClient() throws Exception {
        /*
        RMIClient's init method
         */
        super("Online Shop (Only Customer)");
        if(System.getSecurityManager()==null) {
            System.setSecurityManager(new SecurityManager());
        }
        Shop i = (Shop)Naming.lookup("rmi://localhost:1099/TestShop");
        Bank b = (Bank) Naming.lookup("rmi://localhost:1099/TestBank");

        searchButton.addActionListener(new ActionListener() {
            /*
            When customer click Search Button in Search Tab.
            Printout product's list related to Customer want to show.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ProductInfo> productsInfo = new ArrayList<>();
                HashMap<String, ProductInfo> goodsList = new HashMap<>();
                ProductInfo productInfo = new ProductInfo();
                String buf;

                //check radio button which button selected
                if (searchPriceRButton.isSelected()) {
                    if (checkEmpty(searchPriceFromText.getText()) && checkEmpty(searchPriceToText.getText())) {
                        int from = Integer.parseInt(searchPriceFromText.getText());
                        int to = Integer.parseInt(searchPriceToText.getText());

                        try {
                            productsInfo = i.getProInfoByPrice(from, to); //call object InfoImpl's getProInfoByPrice method
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }

                        searchTextPane.setText(String.format("Search price from %d to %d.", from, to));
                        for(int k = 0; k < productsInfo.size(); k++) {
                            buf = productsInfo.get(k).toString();
                            try {
                                Document doc = searchTextPane.getDocument();
                                doc.insertString(doc.getLength(), "\n" + buf, null);
                            } catch (BadLocationException exc) {
                                exc.printStackTrace();
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please check empty text", "Search by price error!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                else if (searchTypeRButton.isSelected()) {
                    String type = searchTypeBox.getSelectedItem().toString();
                    try {
                        goodsList = i.getProInfoByType(type); //call object InfoImpl's getProInfoByType method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    searchTextPane.setText(String.format("Search Type : %s", type));
                    for(String key : goodsList.keySet()) {
                        buf = goodsList.get(key).toString();
                        try {
                            Document doc = searchTextPane.getDocument();
                            doc.insertString(doc.getLength(), "\n" + buf, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    }
                }
                else if (searchBrandRButton.isSelected()) {
                    String brand = searchBrandBox.getSelectedItem().toString();

                    try {
                        productsInfo = i.getProInfoByBrand(brand); //call object InfoImpl's getProInfoByBrand method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    searchTextPane.setText(String.format("Search brand : %s", brand));
                    for(int k = 0; k < productsInfo.size(); k++) {
                        buf = productsInfo.get(k).toString();
                        try {
                            Document doc = searchTextPane.getDocument();
                            doc.insertString(doc.getLength(), "\n" + buf, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    }
                }
                else if (searchIDRButton.isSelected()) {
                    String ID = searchIDText.getText();
                    if (checkEmpty(ID)) {

                        try {
                            productInfo = i.getProInfoByID(ID); //call object InfoImpl's getProInfoByID method
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }

                        searchTextPane.setText(String.format("Search ID : %s", ID));
                        try {
                            Document doc = searchTextPane.getDocument();
                            doc.insertString(doc.getLength(), "\n" + productInfo.toString(), null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please check empty text", "Search by ID error!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        submitOrderButton.addActionListener(new ActionListener() {
            /*
            When customer click Submit Order Button in Submit Order Tab.
            Customer submit a order.(when product amount fewer compared to user order amount, order cancel.)
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderInfo orderInfo = new OrderInfo();
                String usrName = submitNameText.getText();
                String type = submitTypeBox.getSelectedItem().toString();
                String ID = submitIDText.getText();
                String usrAdd = submitAddText.getText();
                boolean loginCheck = true;

                if (checkEmpty(usrName) && checkEmpty(submitPwdText.getText()) && checkEmpty(ID) && checkEmpty(usrAdd) && checkEmpty(submitAmountText.getText())) {
                    int usrPwd = Integer.parseInt(submitPwdText.getText());
                    int amount = Integer.parseInt(submitAmountText.getText());

                    try {
                        loginCheck = i.checkCustomer(usrName, usrPwd); //call object InfoImpl's checkCustomer method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    if (!loginCheck) {
                        try {
                            orderInfo = i.usrBuy(usrName, usrAdd, type, ID, amount); //call object InfoImpl's usrBuy method
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }

                        if (orderInfo != null) {
                            submitTextPane.setText(orderInfo.toString());

                            //ShopServer inform virtual account information relate to order to user.
                            try {
                                Document doc = submitTextPane.getDocument();
                                doc.insertString(doc.getLength(), String.format("\nYou have to transfer %d to account number %d", orderInfo.totalPrice, orderInfo.virtualAccount), null);
                            } catch (BadLocationException exc) {
                                exc.printStackTrace();
                            }
                        }

                        else {
                            JOptionPane.showMessageDialog(null, "Please check product ID and product amount", "Submit order canceled!", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please check account name and password.", "Online shop login error!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Submit order error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        checkOrderButton.addActionListener(new ActionListener() {
            /*
            When customer click Check Order Button in Check Order Tab.
            Customer can show order list.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                UsrInfo usrOrderList = new UsrInfo();
                String buf;
                String usrName = checkOrderNameText.getText();
                boolean loginCheck = true;

                if (checkEmpty(usrName) && checkEmpty(checkOrderPwdText.getText())) {
                    int usrPwd = Integer.parseInt(checkOrderPwdText.getText());

                    try {
                        loginCheck = i.checkCustomer(usrName, usrPwd); //call object InfoImpl's checkCustomer method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    if (!loginCheck) {
                        try {
                            usrOrderList = i.getOrderList(usrName); //call object InfoImpl's getOrderList method
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }

                        checkOrderTextPane.setText(String.format("User name : %s", usrName));

                        for(int k = 0; k < usrOrderList.usrOrder.size(); k++) {
                            buf = usrOrderList.usrOrder.get(k).toString();
                            try {
                                Document doc = checkOrderTextPane.getDocument();
                                doc.insertString(doc.getLength(), "\n" + buf, null);
                            } catch (BadLocationException exc) {
                                exc.printStackTrace();
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please check account name and password.", "Online shop login error!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Check order error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        bankSendButton.addActionListener(new ActionListener() {
            /*
            When customer click Send Button in Bank Service Tab.
            Customer can send money to other account.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginCheck = true;
                boolean transferCheck = true;
                boolean objAccCheck = false;
                boolean[] checks = new boolean[2];

                if (checkEmpty(bankAccNameText.getText()) && checkEmpty(bankAccPwdText.getText()) && checkEmpty(bankTransObjText.getText()) && checkEmpty(bankTransAmountText.getText())) {
                    int accName = Integer.parseInt(bankAccNameText.getText());
                    int accPwd = Integer.parseInt(bankAccPwdText.getText());
                    int objAcc = Integer.parseInt(bankTransObjText.getText());
                    int amount = Integer.parseInt(bankTransAmountText.getText());

                    try {
                        loginCheck = b.login(accName, accPwd); //call object BankImpl's login method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    if (!loginCheck) {
                        try {
                            checks = b.transfer(accName, accPwd, objAcc, amount); //call object BankImpl's transfer method
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                        transferCheck = checks[0];
                        objAccCheck = checks[1];

                        if (!objAccCheck) {
                            if (!transferCheck) {
                                bankTextPane.setText(String.format("Transfer Success!\nYour account : %d\nObject account : %d\nAmount transfer money : %d", accName, objAcc, amount));
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Your account deposit is lack.\nPlease check account deposit", "Bank send money denied!", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Please check object account name.", "Bank send money denied!", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                    else  {
                        JOptionPane.showMessageDialog(null, "Please check account name and password.", "Bank login error!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Bank send money error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        bankDepositButton.addActionListener(new ActionListener() {
            /*
            When customer click Deposit Button in Bank Service Tab.
            Customer can check account's deposit.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginCheck = true;
                int deposit = 0;

                if (checkEmpty(bankAccNameText.getText()) && checkEmpty(bankAccPwdText.getText())) {
                    int accName = Integer.parseInt(bankAccNameText.getText());
                    int accPwd = Integer.parseInt(bankAccPwdText.getText());

                    try {
                        loginCheck = b.login(accName, accPwd); //call object BankImpl's login method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    if (!loginCheck) {
                        try {
                            deposit = b.checkDeposit(accName); //call object BankImpl's checkDeposit method
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                        bankTextPane.setText(String.format("Your account's deposit : %d", deposit));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please check account name and password.", "Bank login error!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Bank check deposit error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        bankRecordButton.addActionListener(new ActionListener() {
            /*
            When customer click Record Button in Bank Service Tab.
            Customer can check account's transfer records.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginCheck = true;
                ArrayList<TransInfo> transInfo = new ArrayList<>();
                String buf;

                if (checkEmpty(bankAccNameText.getText()) && checkEmpty(bankAccPwdText.getText())) {
                    int accName = Integer.parseInt(bankAccNameText.getText());
                    int accPwd = Integer.parseInt(bankAccPwdText.getText());

                    try {
                        loginCheck = b.login(accName, accPwd); //call object BankImpl's login method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    if (!loginCheck) {
                        try {
                            transInfo = b.checkTransRecord(accName); //call object BankImpl's checkTransRecord method
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                        bankTextPane.setText(String.format("Your account name : %s", accName));
                        for(int k = 0; k < transInfo.size(); k++) {
                            buf = transInfo.get(k).toString();
                            try {
                                Document doc = bankTextPane.getDocument();
                                doc.insertString(doc.getLength(), "\n" + buf, null);
                            } catch (BadLocationException exc) {
                                exc.printStackTrace();
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please check account name and password.", "Bank login error!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Bank check transfer records error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        /*
        RMIClient's main method.
        Try connect RMIClient and RMIClient.form for providing GUI.
         */
        JFrame frame = new JFrame("RMIClient");
        frame.setContentPane(new RMIClient().onlineShopRootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public boolean checkEmpty(String text) {
        return (text != null && !text.isEmpty());
    }
}
