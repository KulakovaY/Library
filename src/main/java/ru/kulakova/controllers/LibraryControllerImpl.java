package ru.kulakova.controllers;

import ru.kulakova.entities.Book;
import ru.kulakova.entities.LibraryStats;
import ru.kulakova.exceptions.DuplicatedDataException;
import ru.kulakova.exceptions.InvalidDataException;
import ru.kulakova.exceptions.NotFoundException;
import ru.kulakova.services.LibraryService;
import ru.kulakova.writers.Writable;

import java.util.List;
import java.util.UUID;

public class LibraryControllerImpl implements LibraryController {

    private final LibraryService _service;
    private final Writable _writer;

    public LibraryControllerImpl(LibraryService service, Writable writer) {
        _service = service;
        _writer = writer;
    }


    @Override
    public void handleAdd(String title, String author, int year) {
        try {
            UUID id = _service.addBook(title, author, year);
            _writer.writeUUID(id);
            _writer.writeSuccess("Книга успешно добавлена");
        } catch (InvalidDataException | DuplicatedDataException e) {
            _writer.writeError(e.getMessage());
        } catch (Exception e) {
            _writer.writeError("Произошла ошибка: " + e.getMessage());
        }
    }

    @Override
    public void handleRemove(UUID id) {
        try {
            Book removed = _service.removeBook(id);
            _writer.writeBook(removed);
            _writer.writeSuccess("Книга удалена");
        } catch (NotFoundException e) {
            _writer.writeError(e.getMessage());
        } catch (Exception e) {
            _writer.writeError("Произошла ошибка: " + e.getMessage());
        }
    }

    @Override
    public void handleList() {
        try {
            List<Book> books = _service.listBooksByTimeLine();
            if (books == null || books.isEmpty()) {
                _writer.writeNotification("Ничего не найдено");
                return;
            }
            _writer.writeBookList(books);
        } catch (Exception e) {
            _writer.writeError("Ошибка при выводе списка: " + e.getMessage());
        }
    }

    @Override
    public void handleListByTitle() {
        try {
            List<Book> books = _service.listBooksByTitle();
            if (books == null || books.isEmpty()) {
                _writer.writeNotification("Ничего не найдено");
                return;
            }
            _writer.writeBookList(books);
        } catch (Exception e) {
            _writer.writeError("Ошибка при выводе списка: " + e.getMessage());
        }
    }

    @Override
    public void handleListByAuthor() {
        try {
            List<Book> books = _service.listBooksByAuthor();
            if (books == null || books.isEmpty()) {
                _writer.writeNotification("Ничего не найдено");
                return;
            }
            _writer.writeBookList(books);
        } catch (Exception e) {
            _writer.writeError("Ошибка при выводе списка: " + e.getMessage());
        }
    }

    @Override
    public void handleListByYear() {
        try {
            List<Book> books = _service.listBooksByYear();
            if (books == null || books.isEmpty()) {
                _writer.writeNotification("Ничего не найдено");
                return;
            }
            _writer.writeBookList(books);
        } catch (Exception e) {
            _writer.writeError("Ошибка при выводе списка: " + e.getMessage());
        }
    }

    @Override
    public void handleFind(String query) {
        try {
            List<Book> books = _service.findBooks(query);
            if (books == null || books.isEmpty()) {
                _writer.writeNotification("Ничего не найдено");
                return;
            }
            _writer.writeBookList(books);
        } catch (Exception e) {
            _writer.writeError("Ошибка при поиске: " + e.getMessage());
        }
    }

    @Override
    public void handleStats() {
        try {
            LibraryStats stats = _service.getStats();
            _writer.writeStats(stats);
        } catch (Exception e) {
            _writer.writeError("Ошибка при получении статистики");
        }
    }
}
