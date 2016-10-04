package com.github.myhealth.api;

import android.util.Log;
import android.util.StringBuilderPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class Bill {
    private String id;
    private String userId;
    private String status;
    private List<Line> lines;
    public Bill(String id, String userId, String status, JSONArray lines) throws JSONException {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.lines = createLines(lines);
    }

    public Bill(String id, String userId, String status, List<Line> lines){
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.lines = lines;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public List<Line> getLines() {
        return lines;
    }

    public ArrayList<Line> createLines(JSONArray lines) throws JSONException {
        ArrayList<Line> lineList = new ArrayList<>();
        for(int i = 0; i < lines.length(); i++){
            JSONObject line = lines.getJSONObject(i);
            lineList.add(new Line(line.getString("description"), line.getString("code"), line.getDouble("price")));
        }
        return lineList;
    }

    public static class Line {
        public final String description, code;
        public final double price;

        public static String toEncodedString(List<Bill.Line> lines){
            StringBuilder builder = new StringBuilder();
            for(Bill.Line line : lines){
                builder.append("&line[]={\"description\":\""+line.description+ "\",");
                builder.append("\"code\":\""+ line.code + "\",");
                builder.append("\"price\":\"" + line.price + "\"}");
            }
            return builder.toString();
        }

        public Line(String description, String code, double price){
            this.description = description;
            this.code = code;
            this.price = price;
        }
    }
}
