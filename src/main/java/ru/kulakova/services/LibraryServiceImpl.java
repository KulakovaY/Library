package ru.kulakova.services;

import ru.kulakova.entities.Book;
import ru.kulakova.entities.LibraryStats;
import ru.kulakova.exceptions.DuplicatedDataException;
import ru.kulakova.exceptions.InvalidDataException;
import ru.kulakova.exceptions.NotFoundException;
import ru.kulakova.repositories.LibraryRepository;

import java.util.*;

public class LibraryServiceImpl implements LibraryService {

    private static final int TOP_AUTHORS_LIMIT = 3;

    private final LibraryRepository _libraryRepository = LibraryRepository.getInstance();


    @Override
    public UUID addBook(String title, String author, Integer year) throws InvalidDataException, DuplicatedDataException {
        if (title == null || title.isBlank()) {
            throw new InvalidDataException("Необходимо ввести название книги");
        }
        if (author == null || author.isBlank()) {
            throw new InvalidDataException("Необходимо ввести автора книги");
        }
        if (year == null ) {
            throw new InvalidDataException("Необходимо ввести год издания");
        }
        if (year < 0){
            throw new InvalidDataException("Некорректный год издания. Год должен быть больше 0");
        }

        Book book = new Book(title, author, year);
        boolean added = _libraryRepository.add(book);

        if (added) {
            return book.getId();
        } else {
            throw new DuplicatedDataException("Такая книга уже существует");
        }
    }

    @Override
    public Book removeBook(UUID id) throws NotFoundException {
        if (id == null) {
            throw new InvalidDataException("ID не может быть пустым");
        }

        return _libraryRepository.removeById(id)
                .orElseThrow(() -> new NotFoundException("Книга с ID " + id + " не найдена"));
    }

    @Override
    public List<Book> listBooksByTimeLine() {
        return new ArrayList<>(_libraryRepository.getAll());
    }

    @Override
    public List<Book> listBooksByTitle() {
        return _libraryRepository.getAll().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }

    @Override
    public List<Book> listBooksByAuthor() {
        return _libraryRepository.getAll().stream()
                .sorted(Comparator.comparing(Book::getAuthor))
                .toList();
    }

    @Override
    public List<Book> listBooksByYear() {
        return _libraryRepository.getAll().stream()
                .sorted(Comparator.comparingInt(Book::getYear))
                .toList();
    }

    @Override
    public List<Book> findBooks(String query) { // can be edited
        return _libraryRepository.findByQuery(query);
    }

    @Override
    public LibraryStats getStats() {
        return new LibraryStats(
                _libraryRepository.count(),
                _libraryRepository.findOldest(),
                _libraryRepository.findNewest(),
                _libraryRepository.getTopAuthors(TOP_AUTHORS_LIMIT)
        );
    }
}
