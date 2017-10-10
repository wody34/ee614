package kr.ac.kaist;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Book")
public class BookInterface {
    private Book book = new Book("UNP", 1);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBook() {
        return book.toJSON();
    }
}

