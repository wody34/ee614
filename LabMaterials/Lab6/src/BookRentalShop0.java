package kr.ac.kaist;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService()
public class BookRentalShop {

    @WebMethod
    public String greeting(String username) {
        System.out.println("User " + username + " Entered!");
        return "Hello, " + username;
    }
}