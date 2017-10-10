package kr.ac.kaist;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Main {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/Book");
        Response res = target.request(MediaType.APPLICATION_JSON).get();
        String entity = res.readEntity(String.class);
        Book book = new Book(entity);
        System.out.print(String.format("Name: %s, Price: %d\n", book.getName(), book.getPrice()));
    }
}



