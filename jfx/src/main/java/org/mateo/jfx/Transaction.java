package org.mateo.jfx;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private String type;
    private double amount;
    private String category;
    private LocalDate date;

    public Transaction(int id, String type, double amount, String category, LocalDate date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }
}
