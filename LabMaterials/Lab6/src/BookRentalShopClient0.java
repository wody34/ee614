package kr.ac.kaist;

import stub.BookRentalShop;
import stub.BookRentalShopService;

public class BookRentalShopClient {
  public static void main(String[] argv) {
      BookRentalShop service = new BookRentalShopService().getBookRentalShopPort();
      //invoke business method
      System.out.println(service.greeting("ee614"));
  }
}
