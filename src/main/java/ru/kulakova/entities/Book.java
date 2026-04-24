package ru.kulakova.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class Book {
    private final UUID id;
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        id = UUID.randomUUID();
        this.title = title;
        this.author = author;
        this.year = year;
    }
}
