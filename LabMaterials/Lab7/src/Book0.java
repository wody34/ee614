package kr.ac.kaist;

import org.json.simple.JSONObject;

public class Book {
    private String name;
    private int price;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        jsonObject.put("price", this.price);
        return jsonObject.toJSONString();
    }

    public static void main(String[] args) {
        Book book = new Book("UNP", 1);
        System.out.println(book.toJSON());
    }
}