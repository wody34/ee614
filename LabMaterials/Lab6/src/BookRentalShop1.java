package kr.ac.kaist;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService()
public class BookRentalShop {
    private ArrayList<String> bookList = new ArrayList<>();

    public BookRentalShop() {
        this.bookList.add("UNP");
        this.bookList.add("Linux Programming Guide");
        this.bookList.add("Introduction to Optimization");
    }

    @WebMethod
    public String greeting(String username) {
        System.out.println("User " + username + " Entered!");
        return "Hello, " + username;
    }

    @WebMethod
    public ArrayList<String> getBookList() {
        return this.bookList;
    }

    @WebMethod
    public boolean rentBook(String bookname) {
        return this.bookList.remove(bookname);
    }

    @WebMethod
    public void returnBook(String bookname) {
        this.bookList.add(bookname);
    }
}