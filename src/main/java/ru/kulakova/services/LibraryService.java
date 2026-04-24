package ru.kulakova.services;

import ru.kulakova.entities.Book;
import ru.kulakova.entities.LibraryStats;

import java.util.List;
import java.util.UUID;

public interface LibraryService {
    UUID addBook(String title, String author, int year);

    Book removeBook(UUID id);

    List<Book> listBooksByTimeLine();
    List<Book> listBooksByTitle();
    List<Book> listBooksByAuthor();
    List<Book> listBooksByYear();

    List<Book> findBooks(String query);

    LibraryStats getStats();
}
