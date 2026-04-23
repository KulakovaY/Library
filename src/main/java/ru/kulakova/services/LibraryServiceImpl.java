package ru.kulakova.services;

import ru.kulakova.entities.Book;
import ru.kulakova.repositories.LibraryRepository;
import ru.kulakova.writers.Writable;

import java.util.List;
import java.util.UUID;

public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository _libraryRepository = LibraryRepository.getInstance();

    private final Writable _writer;

    public LibraryServiceImpl(Writable writer) {
        _writer = writer;
    }

    @Override
    public String addBook(String title, String author, int year) {
        return "";
    }

    @Override
    public String removeBook(UUID id) {
        return "";
    }

    @Override
    public List<Book> listBooksByTimeLine() {
        return List.of();
    }

    @Override
    public List<Book> listBooksByTitle() {
        return List.of();
    }

    @Override
    public List<Book> listBooksByAuthor() {
        return List.of();
    }

    @Override
    public List<Book> listBooksByYear() {
        return List.of();
    }

    @Override
    public List<Book> findBooks(String query) {
        return List.of();
    }

    @Override
    public String getStats() {
        return "";
    }
}
