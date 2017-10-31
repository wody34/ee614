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

public class RMISeller extends JFrame {
    private JPanel onlineShopRootPanel;
    private JTabbedPane tabbedPane1;
    private JPanel addTab;
    private JPanel deleteTab;
    private JPanel checkTab;
    private JPanel bankTab;
    private JComboBox addTypeBox;
    private JComboBox addBrandBox;
    private JTextField addPriceText;
    private JTextField addAmountText;
    private JTextField addIDText;
    private JButton addProductButton;
    private JLabel addLabel1;
    private JTextPane addTextPane;
    private JLabel addLabel2;
    private JLabel addTypeLabel;
    private JLabel addBrandLabel;
    private JLabel addPriceLabel;
    private JLabel addAmountLabel;
    private JLabel addIDLabel;
    private JLabel manageLabel1;
    private JLabel checkLabel1;
    private JTextField editIDText;
    private JTextField editPriceText;
    private JTextField editAmountText;
    private JButton manageEditButton;
    private JButton manageDeleteButton;
    private JButton manageSearchButton;
    private JTextPane manageTextPane;
    private JLabel manageLabel9;
    private JLabel manageLabel2;
    private JLabel manageLabel3;
    private JLabel manageLabel4;
    private JLabel manageLabel6;
    private JLabel manageLabel7;
    private JLabel manageLabel8;
    private JLabel manageLabel5;
    private JTextField welcomeToOurOnlineTextField;
    private JComboBox manageTypeBox;
    private JPanel manageLabel10;
    private JLabel manageLabel11;
    private JComboBox editTypeBox;
    private JTextField deleteIDText;
    private JLabel manageLabel12;
    private JComboBox deleteTypeBox;
    private JButton checkOrderButton;
    private JTextPane checkOrderTextPane;
    private JPanel checkLabel2;
    private JTextField bankAccNameText;
    private JTextField bankAccPwdText;
    private JLabel bankLabel3;
    private JLabel bankLabel2;
    private JLabel bankLabel4;
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

    public RMISeller() throws Exception {
        /*
        RMISeller's init method
         */
        super("Online Shop (Seller ver)");
        if(System.getSecurityManager()==null) {
            System.setSecurityManager(new SecurityManager());
        }

        Shop i = (Shop)Naming.lookup("rmi://localhost:1099/TestShop");
        Bank b = (Bank) Naming.lookup("rmi://localhost:1099/TestBank");

        addProductButton.addActionListener(new ActionListener() {
            /*
            When seller click Add Product Button in Add Product Tab.
            Seller can add product information.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = addTypeBox.getSelectedItem().toString();
                String brand = addBrandBox.getSelectedItem().toString();
                String ID = addIDText.getText();

                if (checkEmpty(addPriceText.getText()) && checkEmpty(addAmountText.getText()) && checkEmpty(ID)) {
                    int price = Integer.parseInt(addPriceText.getText());
                    int amount = Integer.parseInt(addAmountText.getText());
                    try {
                        i.addProInfo(type, brand, price, amount, ID); //call object InfoImpl's addProInfo method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    addTextPane.setText(String.format("Product Type : %s\nProduct Brand : %s\nProduct Price : %d\nProduct Amount : %d\nProduct ID : %s", type, brand, price, amount, ID));
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Add Product error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        manageSearchButton.addActionListener(new ActionListener() {
            /*
            When seller click Check Search Button in Manage Product Tab.
            Seller can search product information list.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = manageTypeBox.getSelectedItem().toString();
                HashMap<String, ProductInfo> proList = new HashMap<>();
                String buf;
                try {
                    proList = i.getProInfoByType(type); //call object InfoImpl's getProInfoByType method
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                manageTextPane.setText(String.format("Type is %s", type));
                for(String key : proList.keySet()) {
                    buf = proList.get(key).toString();
                    try {
                        Document doc = manageTextPane.getDocument();
                        doc.insertString(doc.getLength(), "\n" + buf, null);
                    } catch (BadLocationException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        manageEditButton.addActionListener(new ActionListener() {
            /*
            When seller click Check Edit Button in Manage Product Tab.
            Seller can edit product information.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = editTypeBox.getSelectedItem().toString();
                String ID = editIDText.getText();
                HashMap<String, ProductInfo> proList = new HashMap<>();
                String buf;

                if (checkEmpty(editAmountText.getText()) && checkEmpty(editPriceText.getText()) && checkEmpty(ID)) {
                    int price = Integer.parseInt(editPriceText.getText());
                    int amount = Integer.parseInt(editAmountText.getText());
                    try {
                        i.editProInfo(type, ID, price, amount); //call object InfoImpl's editProInfo method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        proList = i.getProInfoByType(type); //call object InfoImpl's getProInfoByType method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    manageTextPane.setText(String.format("Type is %s", type));
                    for(String key : proList.keySet()) {
                        buf = proList.get(key).toString();
                        try {
                            Document doc = manageTextPane.getDocument();
                            doc.insertString(doc.getLength(), "\n" + buf, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Edit Product error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        manageDeleteButton.addActionListener(new ActionListener() {
            /*
            When seller click Check Delete Button in Manage Product Tab.
            Seller can delete product information.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = deleteTypeBox.getSelectedItem().toString();
                String ID = deleteIDText.getText();
                HashMap<String, ProductInfo> proList = new HashMap<>();
                String buf;

                if (checkEmpty(ID)) {
                    try {
                        i.delProInfo(type, ID); //call object InfoImpl's delProInfo method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        proList = i.getProInfoByType(type); //call object InfoImpl's getProInfoByType method
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    manageTextPane.setText(String.format("Type is %s", type));
                    for(String key : proList.keySet()) {
                        buf = proList.get(key).toString();
                        try {
                            Document doc = manageTextPane.getDocument();
                            doc.insertString(doc.getLength(), "\n" + buf, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please check empty text", "Delete Product error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        checkOrderButton.addActionListener(new ActionListener() {
            /*
            When seller click Check Order Button in Check Order Tab.
            seller can show order list.
            Check user's necessary inputs are filled or empty.
            Call methods using RMI.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, UsrInfo> usrList = new HashMap<>();
                String buf;

                try {
                    usrList = i.checkTransfer(); //call object InfoImpl's checkTransfer method
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }

                checkOrderTextPane.setText("----------------------------------------------------");

                for (String key : usrList.keySet()) {
                    for(int k = 0; k < usrList.get(key).usrOrder.size(); k++) {
                        buf = usrList.get(key).usrOrder.get(k).toString();
                        try {
                            Document doc = checkOrderTextPane.getDocument();
                            doc.insertString(doc.getLength(), "\n" + buf, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    }
                }
            }
        });

        bankSendButton.addActionListener(new ActionListener() {
            /*
            When seller click Send Button in Bank Service Tab.
            seller can send money to other account.
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
            When seller click Deposit Button in Bank Service Tab.
            seller can check account's deposit.
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
            When seller click Record Button in Bank Service Tab.
            seller can check account's transfer records.
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
        RMISeller's main method.
        Try connect RMISeller and RMISeller.form for providing GUI.
         */
        JFrame frame = new JFrame("RMISeller");
        frame.setContentPane(new RMISeller().onlineShopRootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public boolean checkEmpty(String text) {
        return (text != null && !text.isEmpty());
    }
}
