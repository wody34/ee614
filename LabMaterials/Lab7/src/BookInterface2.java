package kr.ac.kaist;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        return toJSON();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addBook(String json) {
        try {
            bookList.add(new Book(json));
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return "Book added";
    }

    @DELETE
    @Path("/{name}")
    public String deleteBook(@PathParam("name") String name) {
        Book b = null;
        for (Book book : this.bookList) {
            if(book.getName().equals(name))
                b = book;
        }
        if(b == null) {
            return "No book named " + name;
        }
        else {
            bookList.remove(b);
            return "Book is removed: " + name;
        }
    }

    public String toJSON() {
        JSONArray jsonArray = new JSONArray();

        for (Book book : this.bookList) {
            jsonArray.add(book.getJSONObject());
        }

        return jsonArray.toJSONString();
    }
}

