package com.pluralsight;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

public class ToDoItem {
    int id;
    String item;
    LocalDate date;

    public ToDoItem(int id, String item, LocalDate date) {
        this.id = id;
        this.item = item;
        this.date = date;
    }

    public ToDoItem(int id, String item, String dateStr) {
        this.id = id;
        this.item = item;
        this.date = convertDate(dateStr);
    }

    public ToDoItem(String item, String dateStr) {
        this.item = item;
        this.date = convertDate(dateStr);
    }

    public LocalDate convertDate(String dateStr) {
        LocalDate date = LocalDate.now();

        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(dateStr, df);
            return date;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public ToDoItem(String item, LocalDate date) {
        this.item = item;
        this.date = date;
    }

    public ToDoItem(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDateStr() {
        if (date.equals(LocalDate.now())) {
            return "Today";
        }
        else if (date.equals(LocalDate.now().plusDays(1))) {
            return "Tomorrow";
        }
        else if (date.compareTo(LocalDate.now().plusDays(5)) < 0 ) {
            return StringUtils.capitalize(date.getDayOfWeek().name().toLowerCase());
        }
        else if (date.getYear() == LocalDate.now().getYear() )  {
            return StringUtils.capitalize(date.getMonth().name().toLowerCase()) + " " + date.getDayOfMonth();
        }
        else {
            return StringUtils.capitalize(date.getMonth().name().toLowerCase()) + " " + date.getDayOfMonth() + ", " + date.getYear();
        }
    }
}
