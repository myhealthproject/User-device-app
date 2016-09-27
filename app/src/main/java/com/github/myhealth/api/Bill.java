package com.github.myhealth.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class Bill {
    public int id;
    private int userId;
    private String status;
    private List<Line> lines;
    public Bill(int id, int userId, String status, String[][] lines) throws NumberFormatException {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.lines = createLines(lines);
    }

    public Bill(int id, int userId, String status, List<Line> lines){
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.lines = lines;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
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
}
