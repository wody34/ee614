package kr.ac.kaist;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Main {
    public void getBookList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/Book");
        Response res = target.request(MediaType.APPLICATION_JSON).get();
        String entity = res.readEntity(String.class);

        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(entity);
            for (Object o: jsonArray) {
                Book book = new Book((JSONObject)o);
                System.out.print(String.format("Name: %s, Price: %d\n", book.getName(), book.getPrice()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book b) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/Book");
        Response res = target.request().post(Entity.entity(b.toJSON(), MediaType.APPLICATION_JSON_TYPE));
        String entity = res.readEntity(String.class);
        System.out.println(entity);
    }

    public void deleteBook(String bookName) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/Book/" + bookName);
        Response res = target.request().delete();
        String entity = res.readEntity(String.class);
        System.out.println(entity);
    }

    public static void main(String[] args) {
        Main client = new Main();
        client.addBook(new Book("ABC", 5));
        client.getBookList();
        client.deleteBook("Machine Learning");
        client.deleteBook("Linux Programming Guide");
        client.getBookList();
    }
}



