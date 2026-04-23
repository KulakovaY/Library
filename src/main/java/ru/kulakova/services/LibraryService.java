package ru.kulakova.services;

import ru.kulakova.entities.Book;

import java.util.List;
import java.util.UUID;

public interface LibraryService {
    String addBook(String title, String author, int year);

    String removeBook(UUID id);

    List<Book> listBooksByTimeLine();
    List<Book> listBooksByTitle();
    List<Book> listBooksByAuthor();
    List<Book> listBooksByYear();

    List<Book> findBooks(String query);

    String getStats();
}
