package kr.ac.kaist;

import stub.BookRentalShop;
import stub.BookRentalShopService;

import java.util.ArrayList;
import java.util.List;

public class BookRentalShopClient {
    private BookRentalShop bookRentalShop;
    private String username;
    private ArrayList<String> myBookList = new ArrayList<>();

    public BookRentalShopClient(String username) {
        this.bookRentalShop = new BookRentalShopService().getBookRentalShopPort();
        this.username = username;
        System.out.println(this.bookRentalShop.greeting(username));
    }

    public void printBookList() {
        List<String> bookList = this.bookRentalShop.getBookList();
        System.out.println("Retreived Book List: ");
        for(String bookname : bookList)
            System.out.println(" - " + bookname);
    }

    public void rentBook(String bookname) {
        if(this.bookRentalShop.rentBook(bookname)) {
            this.myBookList.add(bookname);
            System.out.println("Book " + bookname + " is successfully rented!");
        }
        else
            System.out.println("There are no book named " + bookname);
    }

    public void returnBook(String bookname) {
        if(this.myBookList.contains(bookname)) {
            this.myBookList.remove(bookname);
            this.bookRentalShop.returnBook(bookname);
            System.out.println("Book " + bookname + " is successfully returned to bookshelf!");
        }
        else
            System.out.println("I have no book named " + bookname);
    }

    public static void main(String[] argv) {
        BookRentalShopClient c = new BookRentalShopClient("shkim");
        c.printBookList();
        c.rentBook("Machine Learning");
        c.rentBook("UNP");
        c.printBookList();
        c.returnBook("Software Engineering");
        c.returnBook("UNP");
        c.printBookList();
  }
}
