package kr.ac.kaist;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/Book")
public class BookInterface {
    static private ArrayList<Book> bookList = new ArrayList<>();

    static {
        bookList.add(new Book("UNP", 3));
        bookList.add(new Book("Linux Programming Guide", 1));
        bookList.add(new Book("Introduction to Optimization", 4));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookList() {
        System.out.println("GetBookList");
        String json = toJSONBookList();
        System.out.println(json);
        return json;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBook(@PathParam("id") int id) {
        System.out.println("GetBook: " + id);

        for (Book book : this.bookList) {
            if(book.getId() == id) {
                String json = book.toJSON(true);
                System.out.println(json);
                return json;
            }
        }
        return "No book: " + id;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addBook(String json) {
        System.out.println("AddBook: " + json);
        Book b = null;
        try {
            b = new Book(json);
            bookList.add(b);
            return "Book added - " + b;
        } catch(ParseException e) {
            e.printStackTrace();
            return "Fail to add a Book";
        }
    }

    @DELETE
    @Path("/{id}")
    public String deleteBook(@PathParam("id") int id) {
        System.out.println("DeleteBook: " + id);
        Book b = null;
        for (Book book : this.bookList) {
            if(book.getId() == id)
                b = book;
        }
        if(b == null) {
            return "No book: " + id;
        }
        else {
            bookList.remove(b);
            return "Book is removed - " + b;
        }
    }

    private String toJSONBookList() {
        JSONArray jsonArray = new JSONArray();
        for (Book book : this.bookList)
            jsonArray.add(book.toJSONObj(true));
        return jsonArray.toJSONString();
    }
}