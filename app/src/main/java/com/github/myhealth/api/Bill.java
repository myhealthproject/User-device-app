package com.github.myhealth.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class Bill {
    public String id;
    private String userId;
    private Status status;
    private List<Line> lines;
    public Bill(String id, String userId, Status status, String[][] lines) throws NumberFormatException {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.lines = createLines(lines);
    }

    public Bill(String id, String userId, Status status, List<Line> lines){
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

    public Status getStatus() {
        return status;
    }

    public List<Line> getLines() {
        return lines;
    }

    public ArrayList<Line> createLines(String[][] lines) throws NumberFormatException {
        ArrayList<Line> lineList = new ArrayList<>();
        for(String[] line : lines){
            if(line.length == 3){
                lineList.add(new Line(line[0], line[1], Double.parseDouble(line[2])));
            }
        }
        return lineList;
    }

    public static class Line {
        public final String description, code;
        public final double price;

        public Line(String description, String code, double price){
            this.description = description;
            this.code = code;
            this.price = price;
        }
    }

    /**
     * Represents the different status a bill can have
     */
    public enum Status {
        PAID("paid"),
        UNPAID("unpaid");

        public final String value;

        Status(String value){
            this.value = value;
        }
    }
}
