package kr.ac.kaist;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Book {
    private String name;
    private int price;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Book(JSONObject jsonObject) {
        this.name = (String) jsonObject.get("name");
        this.price = ((Long)jsonObject.get("price")).intValue();
    }

    public Book(String json) throws ParseException {
        this((JSONObject)(new JSONParser()).parse(json));
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String toJSON() {
        return getJSONObject().toJSONString();
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        jsonObject.put("price", this.price);
        return jsonObject;
    }

    public static void main(String[] args) {
        Book book = new Book("UNP", 1);
        System.out.println(book.toJSON());
    }
}