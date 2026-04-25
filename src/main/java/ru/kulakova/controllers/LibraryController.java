package ru.kulakova.controllers;

import java.util.UUID;

public interface LibraryController {
    void handleAdd(String title, String author, Integer year);

    void handleRemove(UUID id);

    void handleList();
    void handleListByTitle();
    void handleListByAuthor();
    void handleListByYear();

    void handleFind(String query);

    void handleStats();
}
