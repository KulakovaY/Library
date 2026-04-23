package ru.kulakova.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class Book {
    private final UUID _id;
    private String _title;
    private String _author;
    private int _year;

    public Book(String title, String author, int year) {
        _id = UUID.randomUUID();
        _title = title;
        _author = author;
        _year = year;
    }
}
