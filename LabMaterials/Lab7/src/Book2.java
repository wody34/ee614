package kr.ac.kaist;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Book {
    static private int bookCounter = 0;
    private int id;
    private String name;
    private int price;

    public Book(String name, int price) {
        this(bookCounter++, name, price);
    }

    public Book(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Book(JSONObject jsonObject) {
        this((jsonObject.get("id") == null)?bookCounter++:((Long)jsonObject.get("id")).intValue(),
                (String) jsonObject.get("name"), ((Long)jsonObject.get("price")).intValue());
    }

    public Book(String json) throws ParseException {
        this((JSONObject)(new JSONParser()).parse(json));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String toJSON(boolean withID) {
        return toJSONObj(withID).toJSONString();
    }

    public JSONObject toJSONObj(boolean withID) {
        JSONObject jsonObject = new JSONObject();
        if(withID)
            jsonObject.put("id", this.id);
        jsonObject.put("name", this.name);
        jsonObject.put("price", this.price);
        return jsonObject;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Price: %d", this.id, this.name, this.price);
    }
}