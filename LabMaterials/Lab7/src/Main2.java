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
import java.util.ArrayList;

public class Main {
    private Client client;
    private WebTarget target;
    private WebTarget specificTarget;

    public Main() {
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080/Book");
        this.specificTarget = client.target("http://localhost:8080/Book/{id}");
    }

    public ArrayList<Book> getBookList() {
        Response res = target.request(MediaType.APPLICATION_JSON).get();
        String entity = res.readEntity(String.class);
        System.out.println("Get Book List from Server");

        JSONParser parser = new JSONParser();
        try {
            ArrayList<Book> list = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) parser.parse(entity);
            for (Object o: jsonArray) {
                Book book = new Book((JSONObject)o);
                list.add(book);
                System.out.println(" - " + book);
            }
            return list;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getBook(int id) {
        Response res = completeSpecificTarget(id).request(MediaType.APPLICATION_JSON).get();
        String entity = res.readEntity(String.class);

        try {
            Book book = new Book(entity);
            System.out.print(book);
            return book;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addBook(Book b) {
        Response res = target.request().post(Entity.entity(b.toJSON(false), MediaType.APPLICATION_JSON_TYPE));
        String entity = res.readEntity(String.class);
        System.out.println(entity);
    }

    public void deleteBook(int id) {
        Response res = completeSpecificTarget(id).request().delete();
        String entity = res.readEntity(String.class);
        System.out.println(entity);
    }

    private WebTarget completeSpecificTarget(int id) {
        return specificTarget.resolveTemplate("id", id);
    }

    public static void main(String[] args) {
        Main client = new Main();
        ArrayList<Book> list;
        list = client.getBookList();
        client.addBook(new Book("ABC", 5));
        list = client.getBookList();
        client.deleteBook(5);
        client.deleteBook(list.get(1).getId());
        list = client.getBookList();
    }
}



