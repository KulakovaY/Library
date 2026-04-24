package ru.kulakova.services;

import ru.kulakova.entities.Book;
import ru.kulakova.entities.LibraryStats;
import ru.kulakova.exceptions.DuplicatedDataException;
import ru.kulakova.exceptions.InvalidDataException;
import ru.kulakova.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface LibraryService {
    UUID addBook(String title, String author, int year)
            throws InvalidDataException, DuplicatedDataException;

    Book removeBook(UUID id) throws NotFoundException;

    List<Book> listBooksByTimeLine();
    List<Book> listBooksByTitle();
    List<Book> listBooksByAuthor();
    List<Book> listBooksByYear();

    List<Book> findBooks(String query);

    LibraryStats getStats();
}
