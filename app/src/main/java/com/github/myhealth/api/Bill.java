package com.github.myhealth.api;

import java.lang.reflect.Array;
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
    public Bill(int id, int userId, String status, String[][] lines) throws ParseException {
        this.id = id;
        this.status = status;
        this.lines = createLines(lines);
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

    private ArrayList<Line> createLines(String[][] lines) throws ParseException {
        ArrayList<Line> lineList = new ArrayList<>();
        for(String[] line : lines){
            if(line.length == 3){
                lineList.add(new Line(line[0], line[1], Double.parseDouble(line[2])));
            }
        }
        return lineList;
    }

    public class Line {
        public final String description, code;
        public final double price;

        public Line(String description, String code, double price){
            this.description = description;
            this.code = code;
            this.price = price;
        }
    }
}
