package ru.kulakova.writers;

import ru.kulakova.entities.Book;
import ru.kulakova.entities.LibraryStats;

import java.util.List;
import java.util.UUID;

public class ConsoleWriter implements Writable{

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    @Override
    public void writeSuccess(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    @Override
    public void writeNotification(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }


    @Override
    public void writeError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    @Override
    public void writeUUID(UUID id) {
        System.out.println("ID: " + id);
    }

    @Override
    public void writeBook(Book book) {
        System.out.printf(" %s - %s (%d) [ID: %s]%n",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getId());
    }

    @Override
    public void writeBookList(List<Book> books) {
        System.out.println("Книги:");
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            System.out.printf("%d. %s — %s (%d)%n",
                    i + 1, b.getTitle(), b.getAuthor(), b.getYear());
            System.out.printf("   [%s]%n", b.getId());
        }
    }

    @Override
    public void writeStats(LibraryStats stats) {
        System.out.println("Всего книг: " + stats.totalCount());

        stats.oldest().ifPresent(b ->
                System.out.println("Самая старая: " + b.getTitle() + " (" + b.getYear() + ")"));
        stats.newest().ifPresent(b ->
                System.out.println("Самая новая: " + b.getTitle() + " (" + b.getYear() + ")"));

        System.out.println("\nТоп авторов:");
        if (stats.topAuthors().isEmpty()) {
            writeNotification("  - Нет данных");
        } else {
            int rank = 1;
            for (var entry : stats.topAuthors().entrySet()) {
                System.out.printf("  %d. %s: %d книг%n",
                        rank++, entry.getKey(), entry.getValue());
            }
        }
    }
}
