package ru.kulakova.entities;

import java.util.*;

public record LibraryStats(
        int totalCount,
        Optional<Book> oldest,
        Optional<Book> newest,
        Map<String, Long> topAuthors
) {}
